package academy.jairo.springboot.camel.demo.batch.processor;

import academy.jairo.springboot.camel.demo.model.dto.ContactDTO;
import academy.jairo.springboot.camel.demo.model.entity.Contact;
import academy.jairo.springboot.camel.demo.model.mapper.ContactMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class ContactMapperProcessor implements ItemProcessor<ContactDTO, Contact> {

    @Value("#{jobExecution.executionContext}") //Work
    private ExecutionContext executionContext;

    @Override
    public Contact process(ContactDTO contactDTO) throws Exception {
        log.info("Mapping ContactDTO to Contact");

        ContactMapper instance = ContactMapper.INSTANCE;
        Contact contact = instance.contactDtoToContact(contactDTO);
        contact.setFileId(executionContext.getLong(contactDTO.getFileName()));

        return contact;
    }
}
