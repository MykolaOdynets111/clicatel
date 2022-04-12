package util.listeners;

import io.qameta.allure.TmsLink;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import util.listeners.models.TestFloResult;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.String.format;

public class AllureApiTestListener extends TestListenerAdapter {

    public static AtomicReference<Map<String, TestFloResult>> apiTestsResults = new AtomicReference<>(new TreeMap<>());

    private static final String FAIL = "Fail";
    private static final String PASS = "Pass";

    @Override
    public void onTestSuccess(ITestResult result) {
        apiTestsResults.get().put(getTestCaseId(result), TestFloResult.builder()
                .testStatus(PASS)
                .build());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        apiTestsResults.get().put(getTestCaseId(result), TestFloResult.builder()
                .testStatus(FAIL)
                .videoRecord(format("%s \n\n The cause of the failure: %s", "No video records, it was an API test", result.getThrowable().getMessage()))
                .build());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        apiTestsResults.get().put(getTestCaseId(result), TestFloResult.builder()
                .testStatus(FAIL)
                .videoRecord(format("%s \n\n The cause of the failure: %s", "No video records, it was an API test", result.getThrowable().getMessage()))
                .build());
    }

    private String getTestCaseId(ITestResult result) {
        return result
                .getMethod()
                .getConstructorOrMethod().getMethod()
                .getAnnotation(TmsLink.class).value();
    }
}
