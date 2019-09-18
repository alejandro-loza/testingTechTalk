package mx.com.kubo.techTalk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
public class AssessorAlreadyExistException extends AssessorException {
  public  AssessorAlreadyExistException(String id) {
    super("Assessor with user Id: " + id + " already exist");
  }
}
