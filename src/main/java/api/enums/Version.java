package api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Version {

    V1 ("v1"),
    V2 ("v2"),
    V3 ("v3"),
    V4 ("v4");

    private final String version;
}
