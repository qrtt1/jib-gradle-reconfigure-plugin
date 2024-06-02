package io.github.qrtt1.jib

class ReleaseVersion {
    private String version;
    private String profile;
    private boolean isDefaultProfile = false;
    private boolean isValidReleaseVersion = false;

    boolean getIsValidReleaseVersion() {
        return isValidReleaseVersion
    }

    ReleaseVersion(String releaseVersion) {
        if (releaseVersion == null) {
            isValidReleaseVersion = false;
            return;
        }

        String[] parts = releaseVersion.split("_")
        if (parts.length != 2) {
            isValidReleaseVersion = false;
            return;
        }
        profile = parts[0];
        version = parts[1];

        isDefaultProfile = profile.split("-").length == 1
        isValidReleaseVersion = true;
    }

    String getVersion() {
        return version
    }

    String getProfile() {
        return profile
    }

    boolean getIsDefaultProfile() {
        return isDefaultProfile
    }
}
