package academy.jairo.springboot.batch.camel.repository;

import academy.jairo.springboot.batch.camel.model.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {

}
