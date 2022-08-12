package academy.jairo.springboot.camel.demo.batch.writer;

import academy.jairo.springboot.camel.demo.model.entity.Contact;
import academy.jairo.springboot.camel.demo.repository.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class ContactItemWriter implements ItemWriter<Contact> {
    private ContactRepository contactRepository;

    @Override
    public void write(List<? extends Contact> listContact) throws Exception {
        log.info("Total contact to be inserted:{}", listContact.size());
        contactRepository.saveAll(listContact);
    }

}
