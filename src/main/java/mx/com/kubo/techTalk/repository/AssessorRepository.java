package mx.com.kubo.techTalk.repository;


import mx.com.kubo.techTalk.models.Assessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AssessorRepository extends JpaRepository<Assessor, Long> {
  Optional<Assessor> findByUser(String userId);
}
