package mx.com.kubo.techTalk.services.impl


import mx.com.kubo.assessor.assign.commons.validators.AssessorCreateRequestValidator
import mx.com.kubo.assessor.assign.commons.validators.AssessorUpdateRequestValidator
import mx.com.kubo.techTalk.exceptions.AssessorAlreadyExistException
import mx.com.kubo.techTalk.exceptions.AssessorNotFoundException
import mx.com.kubo.techTalk.models.Assessor
import mx.com.kubo.techTalk.repository.AssessorRepository
import mx.com.kubo.techTalk.services.AssessorService
import mx.com.kubo.techTalk.services.imp.AssessorServiceImpl
import mx.com.kubo.techTalk.utils.MapperConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

@DataJpaTest
@Import([AssessorServiceImpl, MapperConverter])
//agregamos al contexto estos beans
class AssessorServiceIntSpec extends Specification {

    private static final String USER_ID = 'assessor'
    @Autowired
    private AssessorRepository assessorRepository

    @Autowired
    private AssessorService assessorService

    Assessor assessor = new Assessor(USER_ID, false)

    def "Should create an assessor"() {
        given: 'an saved assessor'

        AssessorCreateRequestValidator assessorInputRequest = new AssessorCreateRequestValidator(
                'userSalvaje', true)

        when:
        def response = assessorService.create(assessorInputRequest)

        then:
        response.class.simpleName == 'AssessorDto'
        response.with {
            assert user == 'userSalvaje'
            assert assigned
            assert id != null
            assert id.class.simpleName == 'Long'

        }

    }

    def "Should try to create an assessor that already exist and throw already exist exception"() {
        given: 'an saved assessor'
        Assessor alreadySavedAssessor = assessorRepository.save(assessor)

        and:
        AssessorCreateRequestValidator assessorInputRequest = new AssessorCreateRequestValidator(
                alreadySavedAssessor.user, true)

        when:
        assessorService.create(assessorInputRequest)

        then:
        thrown(AssessorAlreadyExistException)

    }

    def "Should update an assessor's assigned "() {
        given: 'an saved assessor'
        assessorRepository.save(assessor)

        AssessorUpdateRequestValidator assessorUpdateRequestValidator = new AssessorUpdateRequestValidator()
        assessorUpdateRequestValidator.with { assigned = true } //queremos actualizar solo su status que era falso

        when: 'load an assessor task entity'
        def response = assessorService.update(assessorUpdateRequestValidator, USER_ID)

        then:
        response.with {
            assert user == USER_ID
            assert assigned
        }

    }

    def "Should update an assessor's user "() {
        given: 'an string user id'
        assessorRepository.save(assessor)

        AssessorUpdateRequestValidator assessorUpdateRequestValidator = new AssessorUpdateRequestValidator()
        assessorUpdateRequestValidator.with { user = 'anotherUserId' }

        when: 'load an assessor task entity'
        def response = assessorService.update(assessorUpdateRequestValidator, USER_ID)

        then:
        response.with {
            assert user == 'anotherUserId'
            assert !assigned
        }

    }

    def "Should throw assignment type exception on update"() {
        given:
        AssessorUpdateRequestValidator assessorUpdateRequestValidator = new AssessorUpdateRequestValidator()
        assessorUpdateRequestValidator.with { user = 'anotherUserId' }

        when: 'load an assessor task entity'
        assessorService.update(assessorUpdateRequestValidator, 'NOT_FOUND')

        then:
        thrown(AssessorNotFoundException)
    }

}
