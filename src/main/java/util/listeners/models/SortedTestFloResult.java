package util.listeners.models;

import com.atlassian.jira.rest.client.api.domain.Issue;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class SortedTestFloResult {

    private final TestFloResult testFloResult;
    private final Issue issue;
}
