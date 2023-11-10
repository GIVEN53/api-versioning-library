package given.apiversion.core.util;

import java.util.Arrays;

/**
 * Validates the version format.
 *
 * @since 0.1.0
 */
public class VersionValidator {
    private static final String VERSION_REGEX = "^\\d+(\\.\\d+){0,2}$";

    public void validate(String[] apiVersions) {
        Arrays.stream(apiVersions)
                .forEach(this::validateVersionRegex);
    }

    private void validateVersionRegex(String apiVersion) {
        if (!apiVersion.matches(VERSION_REGEX)) {
            throw new IllegalArgumentException("Invalid version format. ex) 1, 1.0, 1.1.1");
        }
    }
}
