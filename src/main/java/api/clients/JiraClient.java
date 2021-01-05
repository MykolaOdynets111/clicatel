package api.clients;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

import static util.readers.PropertiesReader.getProperty;

@Getter
@Setter
public class JiraClient {

    private String userName;
    private String password;
    private String jiraUrl;
    private JiraRestClient restClient;

    public static JiraClient myJiraClient = new JiraClient(
            getProperty("jira.name"),
            getProperty("jira.pass"),
            getProperty("jira.base.url"));

    public JiraClient(String userName, String password, String jiraUrl) {
        this.userName = userName;
        this.password = password;
        this.jiraUrl = jiraUrl;
        this.restClient = getJiraRestClient();
    }

    private JiraRestClient getJiraRestClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(getJiraUri(), this.userName, this.password);
    }

    private URI getJiraUri() {
        return URI.create(this.jiraUrl);
    }
}
