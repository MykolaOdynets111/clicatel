package api.controls;

import api.clients.JiraClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import io.qameta.allure.Step;
import lombok.Synchronized;
import lombok.extern.java.Log;
import lombok.val;
import util.listeners.models.SortedTestFloResult;
import util.listeners.models.TestFloResult;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static util.DateProvider.getCurrentTimeStamp;
import static util.readers.PropertiesReader.getProperty;

@Log
public class JiraControl {

    private static final String OPEN = "Open";
    private static final String PASS = "Pass";
    private static final String FAIL = "Fail";
    private static final String RETEST = "Retest";
    private static final String IN_PROGRESS = "In Progress";

    private static final int IN_PROGRESS_STATUS_AFTER_OPEN_CODE = 11;
    private static final int PASS_STATUS_CODE = 21;
    private static final int FAIL_STATUS_CODE = 31;
    private static final int RETEST_STATUS_CODE_AFTER_FAIL = 51;
    private static final int RETEST_STATUS_CODE_AFTER_PASS = 61;
    private static final int IN_PROGRESS_STATUS_CODE = 81;

    private static final int JIRA_CLIENT_TIMEOUT = 9;
    private static final String JIRA_DESCRIPTION = "TC Status has been changed by the automation test framework at: %s";
    private static final String JIRA_DESCRIPTION_WITH_LINK = "TC Status has been changed by the automation test framework at: %s \n " +
            "Follow link to get test video record: %s";
    private static final String JIRA_TC_TEMPLATE_NAME = "TC Template";
    private static final String JIRA_TEST_PLAN_PATH = "/rest/api/2/issue/";
    private static final String JIRA_JSON_TC_PATH = "fields.subtasks.key";

    @Synchronized
    @Step("Set tests results into Jira test cases")
    public static void setTestFloResults(final Map<String, TestFloResult> allureResults) {
        log.info("Start writing tests results into Jira");

        val currentTestPlanTestCases = getCurrentTestPlanTestCases();
        val currentTestPlanTestCaseTemplates = getTestCaseTemplates(allureResults, currentTestPlanTestCases);

        if (currentTestPlanTestCaseTemplates.isEmpty()){
            throw new IllegalArgumentException("Current test plan test case templates shouldn't be empty !!");
        }

        for (val finalResultToSet : currentTestPlanTestCaseTemplates) {
            val testStatus = finalResultToSet.getTestFloResult().getTestStatus();
            val videoLink = finalResultToSet.getTestFloResult().getVideoRecord();

            val testCase = finalResultToSet.getIssue();
            if (testCase == null) continue;
            val testCaseStatus = testCase.getStatus().getName();

            if (testStatus.equals(testCaseStatus)) {
                if (testStatus.equals(FAIL)) {
                    setDescriptionWithLink(testCase.getKey(), format(JIRA_DESCRIPTION_WITH_LINK, getCurrentTimeStamp(), videoLink));
                } else {
                    setDescription(testCase.getKey(), format(JIRA_DESCRIPTION, getCurrentTimeStamp()));
                }
            } else {
                if (requireNonNull(testCaseStatus, "Test case status shouldn't be null !!").equals(OPEN) && testStatus.equals(PASS)) {
                    setPassAfterOpen(testCase);
                } else if (testCaseStatus.equals(OPEN) && testStatus.equals(FAIL)) {
                    setFailAfterOpen(testCase, videoLink);
                } else if (testCaseStatus.equals(PASS)) {
                    setFailAfterPass(testCase, videoLink);
                } else if (testCaseStatus.equals(FAIL)) {
                    setPassAfterFail(testCase);
                } else if (testCaseStatus.equals(RETEST) && testStatus.equals(PASS)) {
                    setPassAfterRetest(testCase);
                } else if (testCaseStatus.equals(RETEST) && testStatus.equals(FAIL)) {
                    setFailAfterRetest(testCase, videoLink);
                } else if (testCaseStatus.equals(IN_PROGRESS) && testStatus.equals(PASS)) {
                    setPass(testCase);
                } else if (testCaseStatus.equals(IN_PROGRESS) && testStatus.equals(FAIL)) {
                    setFail(testCase, videoLink);
                }
            }
        }
        log.info("Tests results have been written into Jira");
    }

    private static void setTestCaseStatusAs(final Issue testCase,
                                            final int testCaseStatus) {
        try {
            JiraClient.myJiraClient
                    .getRestClient()
                    .getIssueClient()
                    .transition(testCase, new TransitionInput(testCaseStatus))
                    .get(JIRA_CLIENT_TIMEOUT, SECONDS);
        } catch (InterruptedException e) {
            log.info(format("Results setting into Jira was interrupted by: '%s'", e.getMessage()));
            if (e.getCause() != null)
                log.info(format("The cause of interruption is: '%s'", e.getCause().getMessage()));
        } catch (ExecutionException | TimeoutException e) {
            e.getMessage();
        }
    }

    private static List<SortedTestFloResult> getTestCaseTemplates(final Map<String, TestFloResult> allureResults,
                                                                  final List<String> testPlanTestCases) {

        String allureResultModelKey = null;
        final List<SortedTestFloResult> finalResultsToSet = new LinkedList<>();

        try {
            for (val template : requireNonNull(testPlanTestCases, "Test Plan test cases shouldn't be null !!")) {
                Object tcTemplate = null;
                try {
                    tcTemplate = Objects.requireNonNull(JiraClient.myJiraClient
                            .getRestClient()
                            .getIssueClient()
                            .getIssue(template).get()
                            .getFieldByName(JIRA_TC_TEMPLATE_NAME), format("Test case template for the test case: '%s' shouldn't be null !!", template))
                            .getValue();
                } catch (InterruptedException e) {
                    log.info(format("Test case was not found because of: '%s'", e.getMessage()));
                    if (e.getCause() != null)
                        log.info(format("The cause of why test case was not found is: '%s'", e.getCause().getMessage()));
                } catch (ExecutionException e) {
                    log.info(format("Test case was not found because of: '%s'", e.getMessage()));
                }

                for (val allureResultModel : requireNonNull(allureResults, "Allure results shouldn't be null !!").entrySet()) {
                    allureResultModelKey = allureResultModel.getKey();

                    if (requireNonNull(tcTemplate, "Test case template shouldn't be null !!").equals(allureResultModelKey)) {
                        try {
                            val issue = JiraClient.myJiraClient
                                    .getRestClient()
                                    .getIssueClient()
                                    .getIssue(template)
                                    .get(JIRA_CLIENT_TIMEOUT, SECONDS);

                            finalResultsToSet.add(SortedTestFloResult.builder()
                                    .issue(issue)
                                    .testFloResult(allureResultModel.getValue())
                                    .build());

                        } catch (InterruptedException e) {
                            log.info(format("Test case template was not fount because of: '%s'", e.getMessage()));
                            if (e.getCause() != null)
                                log.info(format("The cause of why test case template was not fount is: '%s'", e.getCause().getMessage()));
                            Thread.currentThread().interrupt();
                        } catch (ExecutionException e) {
                            log.info(format("Test case template was not fount because of: '%s'", e.getMessage()));
                            if (e.getCause() != null)
                                log.info(format("The cause of why test case template was not fount is: '%s'", e.getCause().getMessage()));
                        } catch (TimeoutException e) {
                            log.info(format("Test case template was not fount because of: '%s'", e.getMessage()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.warning(format("Test case: '%s' was not matched into the Test Plan !!", allureResultModelKey));
            log.info(format("Test case was not matched into the Test Plan because of: '%s'", e.getMessage()));
            if (e.getCause() != null)
                log.info(format("The cause of way test case was not matched into the Test Plan is: '%s'", e.getCause().getMessage()));
        }
        return finalResultsToSet;
    }

    private static List<String> getCurrentTestPlanTestCases() {
        return given()
                .auth().preemptive().basic(getProperty("jira.name"), getProperty("jira.pass"))
                .get(format("%s%s%s", getProperty("jira.base.url"), JIRA_TEST_PLAN_PATH, getProperty("jira.test.plan")))
                .getBody().jsonPath().getList(JIRA_JSON_TC_PATH);
    }

    private static void setDescription(final String testCaseNumber,
                                       final String description) {

        val descriptionInput = new IssueInputBuilder()
                .setDescription(description)
                .build();

        JiraClient.myJiraClient
                .getRestClient()
                .getIssueClient()
                .updateIssue(testCaseNumber, descriptionInput)
                .claim();
    }

    private static void setDescriptionWithLink(final String testCaseNumber,
                                               final String description) {

        val descriptionInput = new IssueInputBuilder()
                .setDescription(description)
                .build();

        JiraClient.myJiraClient
                .getRestClient()
                .getIssueClient()
                .updateIssue(testCaseNumber, descriptionInput)
                .claim();
    }

    private static void setPassAfterOpen(final Issue testCase) {
        setTestCaseStatusAs(testCase, IN_PROGRESS_STATUS_AFTER_OPEN_CODE);
        setTestCaseStatusAs(testCase, PASS_STATUS_CODE);
        setDescription(testCase.getKey(), format(JIRA_DESCRIPTION, getCurrentTimeStamp()));
    }

    private static void setFailAfterOpen(final Issue testCase, final String videoLink) {
        setTestCaseStatusAs(testCase, IN_PROGRESS_STATUS_AFTER_OPEN_CODE);
        setTestCaseStatusAs(testCase, FAIL_STATUS_CODE);
        setDescriptionWithLink(testCase.getKey(), format(JIRA_DESCRIPTION_WITH_LINK, getCurrentTimeStamp(), videoLink));
    }

    private static void setFailAfterPass(final Issue testCase, final String videoLink) {
        setTestCaseStatusAs(testCase, RETEST_STATUS_CODE_AFTER_PASS);
        setTestCaseStatusAs(testCase, IN_PROGRESS_STATUS_CODE);
        setTestCaseStatusAs(testCase, FAIL_STATUS_CODE);
        setDescriptionWithLink(testCase.getKey(), format(JIRA_DESCRIPTION_WITH_LINK, getCurrentTimeStamp(), videoLink));
    }

    private static void setPassAfterFail(final Issue testCase) {
        setTestCaseStatusAs(testCase, RETEST_STATUS_CODE_AFTER_FAIL);
        setTestCaseStatusAs(testCase, IN_PROGRESS_STATUS_CODE);
        setTestCaseStatusAs(testCase, PASS_STATUS_CODE);
        setDescription(testCase.getKey(), format(JIRA_DESCRIPTION, getCurrentTimeStamp()));
    }

    private static void setPassAfterRetest(final Issue testCase) {
        setTestCaseStatusAs(testCase, IN_PROGRESS_STATUS_CODE);
        setTestCaseStatusAs(testCase, PASS_STATUS_CODE);
        setDescription(testCase.getKey(), format(JIRA_DESCRIPTION, getCurrentTimeStamp()));
    }

    private static void setFailAfterRetest(final Issue testCase, final String videoLink) {
        setTestCaseStatusAs(testCase, IN_PROGRESS_STATUS_CODE);
        setTestCaseStatusAs(testCase, FAIL_STATUS_CODE);
        setDescriptionWithLink(testCase.getKey(), format(JIRA_DESCRIPTION_WITH_LINK, getCurrentTimeStamp(), videoLink));
    }

    private static void setPass(final Issue testCase) {
        setTestCaseStatusAs(testCase, PASS_STATUS_CODE);
        setDescription(testCase.getKey(), format(JIRA_DESCRIPTION, getCurrentTimeStamp()));
    }

    private static void setFail(final Issue testCase, final String videoLink) {
        setTestCaseStatusAs(testCase, FAIL_STATUS_CODE);
        setDescriptionWithLink(testCase.getKey(), format(JIRA_DESCRIPTION_WITH_LINK, getCurrentTimeStamp(), videoLink));
    }
}
