package academy.jairo.springboot.camel.demo.repository;

import academy.jairo.springboot.camel.demo.model.entity.Parameter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParameterRepository extends CrudRepository<Parameter, Long> {
    List<Parameter> findByActivated(String activated);

}
