package academy.jairo.springboot.camel.demo.batch.processor;

import academy.jairo.springboot.camel.demo.model.dto.ContactDTO;
import academy.jairo.springboot.camel.demo.model.entity.ErrorFile;
import academy.jairo.springboot.camel.demo.repository.ErrorFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class ContactValidatingItemProcessor extends BeanValidatingItemProcessor<ContactDTO> {
    private ErrorFileRepository errorFileRepository;

    @Value("#{jobExecution.executionContext}") //Work
    private ExecutionContext executionContext;

    public ContactValidatingItemProcessor(ErrorFileRepository errorFileRepository) {
        super();
        this.errorFileRepository = errorFileRepository;
    }

    @Override
    public ContactDTO process(ContactDTO contactDTO) throws ValidationException {

        try {
            log.info("-> Converting ContactDTO to Contact");
            super.process(contactDTO);
        } catch (Exception e) {

            log.error("Error in conversion:{} ",  e.getMessage());
            long fileId = executionContext.getLong(contactDTO.getFileName());
            errorFileRepository.save(
                    ErrorFile.builder()
                            .lineNumber(contactDTO.getLineNumber())
                            .fileId(fileId)
                            .errorMessage(e.getLocalizedMessage()).build());
            contactDTO = null;
        }
        return contactDTO;

    }
}
