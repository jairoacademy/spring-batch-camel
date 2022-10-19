package academy.jairo.springboot.batch.camel.repository;

import academy.jairo.springboot.batch.camel.model.entity.ErrorFile;
import org.springframework.data.repository.CrudRepository;

public interface ErrorFileRepository extends CrudRepository<ErrorFile, Long> {
}
