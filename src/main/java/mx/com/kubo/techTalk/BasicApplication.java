package mx.com.kubo.techTalk;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
class BasicApplication {
  @Value("${spring.application.name}")
  private String applicationName;

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  public static void main(String[] args) {
    SpringApplication.run(BasicApplication.class, args);
  }

}
