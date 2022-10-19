package academy.jairo.springboot.batch.camel.repository;

import academy.jairo.springboot.batch.camel.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
