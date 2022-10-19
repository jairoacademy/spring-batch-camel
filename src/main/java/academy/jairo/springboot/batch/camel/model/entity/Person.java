package academy.jairo.springboot.batch.camel.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@With
public class Person {

    private String name;
    private String cpf;
    private Integer age;

}
