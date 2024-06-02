package io.github.qrtt1.jib

import org.apache.commons.io.FileUtils
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

class JibReconfigurePluginE2ETest extends Specification {
    @TempDir
    File testProjectDir

    File buildFile


    def setup() {
        def sampleProjectDir = new File("src/test/resources/gradle.projects/sample-project/")
        assert sampleProjectDir.exists()
        FileUtils.copyDirectory(sampleProjectDir, testProjectDir)
    }

    def "do nothing without releaseVersion"() {
        when:
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withPluginClasspath()
                .withArguments("jibDockerBuild")
                .build()

        then:
        result.getOutput().contains("Found jib-plugin for project[default-target-name]")
        result.getOutput().contains("ext[releaseVersion] not found")
        result.getOutput().contains("skip patching image process.")
    }

    def "do nothing with default profile in the releaseVersion"() {
        when:
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withPluginClasspath()
                .withArguments("jibDockerBuild", "-PreleaseVersion=prod_0.0.0")
                .build()

        then:
        result.getOutput().contains("Found jib-plugin for project[default-target-name]")
        result.getOutput().contains("Found default profile[prod]")
        result.getOutput().contains("skip patching image process.")
    }

    def "lost required envvar for patch image by non-default profile: prod-meme_0.0.0"() {
        when:
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withPluginClasspath()
                .withArguments("jibDockerBuild", "-PreleaseVersion=prod-meme_0.0.0")
                .buildAndFail()

        then:
        result.getOutput().contains("Found jib-plugin for project[default-target-name]")
        result.getOutput().contains("found non-default profile[prod-meme]")
        result.getOutput().contains("required env[JIB_PLUGIN_TEST_REPO] to override jib.to.image")
    }

    def "patch image by non-default profile: prod-meme_0.0.0"() {
        when:
        def env = ["JIB_PLUGIN_TEST_REPO": "qrtt1/patched-image"] + System.getenv()
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withPluginClasspath()
                .withEnvironment(env)
                .withArguments("jibDockerBuild", "-PreleaseVersion=prod-meme_0.0.0")
                .build()

        then:
        result.getOutput().contains("Found jib-plugin for project[default-target-name]")
        result.getOutput().contains("found non-default profile[prod-meme]")
        result.getOutput().contains("required env[JIB_PLUGIN_TEST_REPO] to override jib.to.image")
        result.getOutput().contains("override jib.to.image by [qrtt1/patched-image]")
        result.getOutput().contains("assign project.ext.JIB_OVERRIDE_REPO=qrtt1/patched-image")
    }

}
