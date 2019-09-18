package mx.com.kubo.techTalk.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Assessor {

  @Id
  @GeneratedValue
  private Long id;
  private String user;
  private Boolean assigned;

  public Assessor(String user, boolean assigned) {
    this.user = user;
    this.assigned = assigned;
  }

  public String getUser() {
    return user;
  }

}
