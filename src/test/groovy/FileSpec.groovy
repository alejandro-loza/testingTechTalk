import spock.lang.See
import spock.lang.Specification

@See("http://spockframework.org/spec")
class FileSpec extends Specification {

    def "Should create and clean up a file"() {
        given:
        def file = new File('logs/app.log')
        file.createNewFile()

        expect:
        assert file

        cleanup:
        file.delete()

    }

}
