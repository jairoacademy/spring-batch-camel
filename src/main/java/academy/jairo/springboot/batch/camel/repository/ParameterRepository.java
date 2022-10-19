package academy.jairo.springboot.batch.camel.repository;

import academy.jairo.springboot.batch.camel.model.entity.Parameter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParameterRepository extends CrudRepository<Parameter, Long> {
    List<Parameter> findByActivated(String activated);

}
