package academy.jairo.springboot.camel.demo.repository;

import academy.jairo.springboot.camel.demo.model.entity.ErrorFile;
import org.springframework.data.repository.CrudRepository;

public interface ErrorFileRepository extends CrudRepository<ErrorFile, Long> {
}
