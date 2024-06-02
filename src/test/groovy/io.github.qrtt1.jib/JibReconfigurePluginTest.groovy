package io.github.qrtt1.jib

import spock.lang.Specification

class JibReconfigurePluginTest extends Specification {

    def "image[docker.foobarbar.io/foo/barbar-api] to envName[BARBAR_API_REPO]"() {
        when:
        def envName = JibReconfigurePlugin.toEnvName("docker.foobarbar.io/foo/barbar-api")

        then:
        envName == "BARBAR_API_REPO"
    }

}
