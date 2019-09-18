package mx.com.kubo.techTalk.controllers

import javassist.NotFoundException
import mx.com.kubo.assessor.assign.commons.dtos.AssessorDto
import mx.com.kubo.assessor.assign.commons.validators.AssessorCreateRequestValidator
import mx.com.kubo.techTalk.exceptions.AssessorNotFoundException
import mx.com.kubo.techTalk.services.AssessorService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class AssessorControllerSpec extends Specification {

    @Autowired
    AssessorController assessorController

    AssessorCreateRequestValidator assessorCreateRequestValidator

    void setup() { //antes de cada test se ejecuta primero esto

        assessorController = new AssessorController() //instanciamos el controller a testear
        assessorController.assessorService = Mock(AssessorService)
        // aqui indicamos que el servicio será un mock y no el verdadero
        assessorCreateRequestValidator = new AssessorCreateRequestValidator('aaron', true)
    }

    def 'Should create an assessor'() {
        setup: 'fabricamos la respuesta del servicio notece que es un dto y no un entity'
        AssessorDto assessorDto = new AssessorDto()
        assessorDto.with {
            id = 666
            user = assessorCreateRequestValidator.user
            assigned = true
        }

        when: 'se llama el método create del controller'
        def response = assessorController.create(assessorCreateRequestValidator)

        then: ' una vez debé llamarse  el método create del servicio assessor service con el validador indicado y respondera el dto que hemos generado antes'
        1 * assessorController.assessorService.create(assessorCreateRequestValidator) >> assessorDto //esto es un mock arbitrario

        and: 'la respuesta debe ser el dto que mockeamos si en el controller se procesara la respuesta se debé esperar un resultado distinto'

        response == assessorDto
        response.class.simpleName == 'AssessorDto' // nos sercioramos que responda siempre un assessor dto

    }

    def 'Should find an assessor'() {
        setup: 'fabricamos la respuesta del servicio notece que es un dto y no un entity'
        AssessorDto assessorDto = new AssessorDto()
        assessorDto.with {
            id = 666
            user = assessorCreateRequestValidator.user
            assigned = true
        }

        when: 'se llama el método findByUserId del controller'
        def response = assessorController.findByUserId(assessorCreateRequestValidator.user)

        then: ' una vez debé llamarse  el método create del servicio assessor service con el validador indicado y respondera el dto que hemos generado antes'
        1 * assessorController.assessorService.findByUserId(assessorCreateRequestValidator.user) >> assessorDto //esto es un mock arbitrario

        and: 'la respuesta debe ser el dto que mockeamos si en el controller se procesará la respuesta se debé esperar un resultado distinto'

        response == assessorDto
        response.class.simpleName == 'AssessorDto' // nos sercioramos que responda siempre un assessor dto

    }

    def 'Should not find an assessor and throw not found assessor exception'() {

        when: 'se llama el método findByUserId del controller'
        def response = assessorController.findByUserId('NOT FOUND USER ID')

        then: ' una vez debé llamarse  el método create del servicio assessor service con el validador indicado y respondera el dto que hemos generado antes'
        1 * assessorController.assessorService.findByUserId(_) >> {
            throw new AssessorNotFoundException('NO EXISTO ALV')
        } //esto es un mock arbitrario

        and: 'LA RESPUESTA DEBE SER UNA ASSESSOR NOT FOUND EXCEPTION'

        thrown(AssessorNotFoundException)

    }


}
