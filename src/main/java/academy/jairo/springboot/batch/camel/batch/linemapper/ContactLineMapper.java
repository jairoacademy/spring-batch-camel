package academy.jairo.springboot.batch.camel.batch.linemapper;

import academy.jairo.springboot.batch.camel.model.dto.ContactDTO;
import academy.jairo.springboot.batch.camel.exception.LineUnparseableException;
import academy.jairo.springboot.batch.camel.model.entity.ErrorFile;
import academy.jairo.springboot.batch.camel.repository.ErrorFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
@Slf4j
public class ContactLineMapper implements LineMapper<ContactDTO> {
    private MultiResourceItemReader<ContactDTO> delegator;
    private BeanWrapperFieldSetMapper<ContactDTO> fieldSetMapper;
    private DelimitedLineTokenizer lineTokenizer;
    private ErrorFileRepository errorFileRepository;
    @Value("#{jobExecution.executionContext}") //Not Work
    private ExecutionContext executionContext;
    public ContactLineMapper(MultiResourceItemReader<ContactDTO> delegator, ErrorFileRepository errorFileRepository) {
        this.delegator = delegator;
        this.errorFileRepository = errorFileRepository;
    }

    @Override
    public ContactDTO mapLine(String line, int i) {

        //Skip Trailing and LayoutVersion
        if (line.startsWith("9") || line.startsWith("V")) {
            return null;
        }

        ContactDTO contactDTO = null;
        Resource currentResource = delegator.getCurrentResource();

        try {
            contactDTO = this.fieldSetMapper.mapFieldSet(this.lineTokenizer.tokenize(line));
            contactDTO.setLineNumber(i);
        } catch (Exception e) {

            log.error(e.getMessage());
            long fileId = executionContext.getLong(currentResource.getFilename());
            this.errorFileRepository.save(ErrorFile.builder()
                    .lineNumber(i).fileId(fileId).errorMessage(e.getMessage()).build());
            throw new LineUnparseableException("Line couldn't be parsed", line);

        }

        return contactDTO;
    }
    public void setFieldSetMapper(BeanWrapperFieldSetMapper<ContactDTO> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }
    public void setLineTokenizer(DelimitedLineTokenizer lineTokenizer) {
        this.lineTokenizer = lineTokenizer;
    }

    public void setErrorFileRepository(ErrorFileRepository errorFileRepository) {
        this.errorFileRepository = errorFileRepository;
    }

}
