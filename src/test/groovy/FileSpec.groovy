import spock.lang.Specification

class FileSpec extends Specification {

    def "Should create and clena up a file"() {
        given:
        def file = new File('logs/app.log')
        file.createNewFile()

        expect:
        assert file

        cleanup:
        file.delete()

    }

}
