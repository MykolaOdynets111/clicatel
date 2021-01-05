package util.listeners.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class TestFloResult {

    private final String testStatus;
    private final String videoRecord;
}
