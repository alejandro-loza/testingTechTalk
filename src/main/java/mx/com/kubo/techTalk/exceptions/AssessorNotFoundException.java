package mx.com.kubo.techTalk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AssessorNotFoundException extends AssessorException {
  public AssessorNotFoundException(String id) {
    super("Could not found assessor with user Id: " + id);
  }
}
