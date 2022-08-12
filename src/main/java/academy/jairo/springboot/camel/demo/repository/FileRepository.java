package academy.jairo.springboot.camel.demo.repository;

import academy.jairo.springboot.camel.demo.model.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {

}
