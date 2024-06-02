package io.github.qrtt1.jib

import org.gradle.api.Plugin
import org.gradle.api.Project

class JibReconfigurePlugin implements Plugin<Project> {


    public static final String RELEASE_VERSION = "releaseVersion"
    public static final String JIB_EXTENSION_NAME = "jib"

    @Override
    void apply(Project project) {
        Plugin jib = project.plugins.findPlugin("com.google.cloud.tools.jib")
        if (jib != null) {
            reconfigureJib(project);
        }

    }

    private static void reconfigureJib(Project currentProject) {
        info("Found jib-plugin for project[${currentProject.name}]")
        currentProject.afterEvaluate {
            if (currentProject.hasProperty(RELEASE_VERSION)) {
                handleReconfigure(currentProject)
            } else {
                info("ext[releaseVersion] not found.")
                info("skip patching image process.")
            }
        }
    }

    private static void handleReconfigure(Project currentProject) {
        String input = currentProject.getProperties().get(RELEASE_VERSION) as String;
        ReleaseVersion rv = new ReleaseVersion(input)
        if (rv.isValidReleaseVersion) {
            if (rv.isDefaultProfile) {
                info("Found default profile[${rv.profile}]")
                info("skip patching image process.")
                return
            }
            def jibExt = currentProject.extensions.findByName(JIB_EXTENSION_NAME)
            def defaultRepo = jibExt.to.image as String
            def envName = toEnvName(defaultRepo)
            warn("found non-default profile[${rv.profile}]")
            warn("required env[${envName}] to override jib.to.image")

            if (System.getenv(envName) == null) {
                throw new IllegalArgumentException("Cannot find the env[${envName}] to override jib.to.image[${jibExt.to.image}]")
            }

            info("override jib.to.image by [${System.getenv(envName)}]")
            jibExt.to.image = System.getenv(envName)
            info("assign project.ext.JIB_OVERRIDE_REPO=${System.getenv(envName)}")
            currentProject.properties.put("JIB_OVERRIDE_REPO", System.getenv(envName))

        }
    }

    private static info(String message) {
        println("${JibReconfigurePlugin.class.simpleName} INFO: ${message}")
    }

    private static warn(String message) {
        println("${JibReconfigurePlugin.class.simpleName} WARN: ${message}")
    }

    static String toEnvName(String jibTargetImage) {
        assert jibTargetImage != null
        String last = jibTargetImage.split("/")[-1]
        return last.replaceAll("[^\\w+]", "_").toUpperCase() + "_REPO"
    }
}