package mx.com.kubo.techTalk.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RequestMapping("/base")
@RestController
public class BaseRestController {

  
  @GetMapping
  public ResponseEntity hello() {
    Map<String, String> hello = new HashMap<>();
    hello.put("message", "hello microservices");
    return new ResponseEntity<>(hello, HttpStatus.OK);
  }
}
