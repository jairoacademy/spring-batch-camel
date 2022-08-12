package academy.jairo.springboot.camel.demo.repository;

import academy.jairo.springboot.camel.demo.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
