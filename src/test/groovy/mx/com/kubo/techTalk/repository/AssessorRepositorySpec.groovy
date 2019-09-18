package mx.com.kubo.techTalk.repository

import mx.com.kubo.techTalk.models.Assessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

@DataJpaTest
class AssessorRepositorySpec extends Specification {

    @Autowired
    private AssessorRepository assessorRepository //El sujeto del test


    def "Should find an user "() {

        given: 'an string user id' //datos iniciales de la prueba
        String USER_ID = 'assessorEntity'
        Assessor assessorEntity = new Assessor(USER_ID, false)

        and: 'a saved Assessor'
        def savedAssessorTask = assessorRepository.save(assessorEntity)

        when: 'finds by user'
        def assessorAssignEntityFromDb = assessorRepository.findByUser(USER_ID) //Estimulo del test

        then: 'the requested assessor Entity must be the saved one' //La respuesta
        assessorAssignEntityFromDb
                .ifPresent({ assessor ->
                    assert assessor.equals(savedAssessorTask)
                    assert assessor.id
                })

    }

}
