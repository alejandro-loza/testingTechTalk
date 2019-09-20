package mx.com.kubo.techTalk

import spock.lang.Specification

class MapSpec extends Specification {
    def "HashMap accepts null key"() {
        given:
        def map = new HashMap()

        when:
        map.put(null, "elem")

        then:
        notThrown(NullPointerException)
    }
}
