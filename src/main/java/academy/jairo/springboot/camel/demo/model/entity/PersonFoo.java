package academy.jairo.springboot.camel.demo.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class PersonFoo {

    private final String name; //of with only final
    private Integer age;

}
