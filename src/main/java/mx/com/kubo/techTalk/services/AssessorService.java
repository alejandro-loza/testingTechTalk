package mx.com.kubo.techTalk.services;

import mx.com.kubo.assessor.assign.commons.dtos.AssessorDto;
import mx.com.kubo.assessor.assign.commons.validators.AssessorCreateRequestValidator;
import mx.com.kubo.assessor.assign.commons.validators.AssessorUpdateRequestValidator;

import java.util.List;

public interface AssessorService {

  AssessorDto create(AssessorCreateRequestValidator newAssessorTasks);

  AssessorDto findByUserId(String userId);

  AssessorDto update(AssessorUpdateRequestValidator assessorRequestValidator, String userId);

  List<AssessorDto> findAll();

}
