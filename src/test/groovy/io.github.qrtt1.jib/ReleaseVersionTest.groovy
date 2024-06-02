package io.github.qrtt1.jib

import spock.lang.Specification

class ReleaseVersionTest extends Specification {

    def "detect default profile: prod_0.0.0"() {
        when:
        def r = new ReleaseVersion("prod_0.0.0")

        then:
        r.profile == "prod"
        r.version == "0.0.0"
        r.isDefaultProfile
    }

    def "detect non-default profile: prod-meme_0.55.66"() {
        when:
        def r = new ReleaseVersion("prod-meme_0.55.66")

        then:
        r.profile == "prod-meme"
        r.version == "0.55.66"
        !r.isDefaultProfile
    }
}
