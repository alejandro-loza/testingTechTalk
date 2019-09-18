package mx.com.kubo.techTalk.controllers;

import mx.com.kubo.assessor.assign.commons.dtos.AssessorDto;
import mx.com.kubo.assessor.assign.commons.validators.AssessorCreateRequestValidator;
import mx.com.kubo.assessor.assign.commons.validators.AssessorUpdateRequestValidator;
import mx.com.kubo.techTalk.services.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/assessor")
@RestController
public class AssessorController {

  @Autowired
  public AssessorService assessorService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public AssessorDto create(@RequestBody @Valid AssessorCreateRequestValidator assessorValidator) {
    return assessorService.create(assessorValidator);
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public List<AssessorDto> findAll() {
    return assessorService.findAll();
  }

  @GetMapping(path = "/{userId}", produces = APPLICATION_JSON_VALUE)
  public AssessorDto findByUserId(@PathVariable("userId") String userId) {
    return assessorService.findByUserId(userId);
  }

  @PutMapping(path = "/{userId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public AssessorDto update(
    @PathVariable("userId") String userId,
    @RequestBody AssessorUpdateRequestValidator assessorValidator) {
    return assessorService.update(assessorValidator, userId);
  }

}
