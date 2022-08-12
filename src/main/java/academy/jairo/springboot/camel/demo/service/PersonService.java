package academy.jairo.springboot.camel.demo.service;

import academy.jairo.springboot.camel.demo.model.entity.Person;
import academy.jairo.springboot.camel.demo.model.entity.PersonFoo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Bean
    public void simpleExampleOf() {
        Person jairo_power = Person.of("Jairo Power", "32135076368", 51);
        Person jairoWith55 = jairo_power.withAge(55);

        PersonFoo richelle_power = PersonFoo.of("Richelle Power");

        //BeanUtils.copyProperties(entityRequest, entity);
    }

}