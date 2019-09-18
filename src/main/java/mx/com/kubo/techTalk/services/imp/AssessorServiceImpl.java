package mx.com.kubo.techTalk.services.imp;

import mx.com.kubo.assessor.assign.commons.dtos.AssessorDto;
import mx.com.kubo.assessor.assign.commons.validators.AssessorCreateRequestValidator;
import mx.com.kubo.assessor.assign.commons.validators.AssessorUpdateRequestValidator;
import mx.com.kubo.techTalk.exceptions.AssessorAlreadyExistException;
import mx.com.kubo.techTalk.exceptions.AssessorNotFoundException;
import mx.com.kubo.techTalk.models.Assessor;
import mx.com.kubo.techTalk.repository.AssessorRepository;
import mx.com.kubo.techTalk.services.AssessorService;
import mx.com.kubo.techTalk.utils.MapperConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessorServiceImpl implements AssessorService {

  @Autowired
  private MapperConverter mapperConverter;

  @Autowired
  private AssessorRepository assessorRepository;

  @Override
  public AssessorDto create(AssessorCreateRequestValidator assessorRequestValidator) {
    verifyIfAlreadyExist(assessorRequestValidator.getUser());
    return (AssessorDto) mapperConverter.mapTo(
      assessorRepository.save((Assessor) mapperConverter.mapTo(assessorRequestValidator, Assessor.class)),
      AssessorDto.class);
  }

  @Override
  public AssessorDto findByUserId(String userId) {
    return (AssessorDto) mapperConverter.mapTo(assessorRepository.findByUser(userId).orElseThrow(() ->
        new AssessorNotFoundException(userId)),
      AssessorDto.class);
  }

  @Override
  public List<AssessorDto> findAll() {
    return assessorRepository.findAll().stream()
      .map(assessor -> (AssessorDto) mapperConverter.mapTo(assessor, AssessorDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public AssessorDto update(AssessorUpdateRequestValidator assessorRequestValidator, String userId) {
    return (AssessorDto) mapperConverter.mapTo(assessorRepository.findByUser(userId)
        .map(assessorToUpdate -> {
          assessorToUpdate.setUser(
            assessorRequestValidator.getUser() != null ?
              assessorRequestValidator.getUser() : assessorToUpdate.getUser());
          assessorToUpdate.setAssigned(assessorRequestValidator.getAssigned() != null ?
            assessorRequestValidator.getAssigned() : assessorToUpdate.getAssigned());
          return assessorRepository.save(assessorToUpdate);
        })
        .orElseThrow(() -> new AssessorNotFoundException(userId))
      ,
      AssessorDto.class);
  }

  private void verifyIfAlreadyExist(String userId) {
    if (assessorRepository.findByUser(userId).isPresent()) {
      throw new AssessorAlreadyExistException(userId);
    }
  }

}
