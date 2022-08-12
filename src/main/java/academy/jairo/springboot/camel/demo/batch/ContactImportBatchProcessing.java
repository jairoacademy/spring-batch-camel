package academy.jairo.springboot.camel.demo.batch;

import academy.jairo.springboot.camel.demo.batch.linemapper.ContactLineMapper;
import academy.jairo.springboot.camel.demo.batch.listener.ContactImportListener;
import academy.jairo.springboot.camel.demo.batch.processor.ContactMapperProcessor;
import academy.jairo.springboot.camel.demo.batch.processor.ContactValidatingItemProcessor;
import academy.jairo.springboot.camel.demo.batch.tasklet.LookupFilesTasklet;
import academy.jairo.springboot.camel.demo.batch.writer.ContactItemWriter;
import academy.jairo.springboot.camel.demo.model.dto.ContactDTO;
import academy.jairo.springboot.camel.demo.model.dto.ParameterDTO;
import academy.jairo.springboot.camel.demo.model.entity.Contact;
import academy.jairo.springboot.camel.demo.repository.ContactRepository;
import academy.jairo.springboot.camel.demo.repository.ErrorFileRepository;
import academy.jairo.springboot.camel.demo.util.FileLayoutGenerator;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class ContactImportBatchProcessing {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private LookupFilesTasklet lookupContactFilesTasklet;

    @Autowired
    private ParameterDTO parameters;

    @Autowired
    private ErrorFileRepository errorFileRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Bean
    public Job contactImportFileJob() throws ParseException, IOException, java.text.ParseException {
        return jobBuilderFactory
                .get("contactImportFileJob")
                .incrementer(new RunIdIncrementer())
                .start(lookupFilesStep())
                .next(contactImportFileStep())
                .build();
    }
    @Bean
    public Step contactImportFileStep() throws ParseException, IOException, java.text.ParseException {
        return stepBuilderFactory
                .get("contactImportFileStep")
                .<ContactDTO, Contact>chunk(100)
                .reader(multiResourceItemReader())
                .processor(compositeItemProcessor())
                .writer(writer())
                .faultTolerant()
                .skipLimit(10)
                .skip(FlatFileParseException.class)
                .skip(ItemStreamException.class)
                .listener(importListener())
                .build();
    }
    @Bean
    public Step lookupFilesStep() throws ParseException, IOException, java.text.ParseException {
        return stepBuilderFactory
                .get("lookupFilesStep")
                .tasklet(lookupContactFilesTasklet)
                .build();
    }

    @Bean
    public ChunkListener importListener() {
        return new ContactImportListener(parameters);
    }

    @Bean
    public ItemWriter<Contact> writer() {
        return new ContactItemWriter(contactRepository);
    }
    @Bean
    public ItemProcessor<ContactDTO, Contact> compositeItemProcessor() {
        return new CompositeItemProcessorBuilder<ContactDTO, Contact>()
                .delegates(validatingProcessor(), mapperProcessor())
                .build();
    }
    @Bean
    @JobScope
    public ItemProcessor<ContactDTO,ContactDTO> validatingProcessor() {
        ContactValidatingItemProcessor beanValidatingItemProcessor = new ContactValidatingItemProcessor(
                errorFileRepository);
        return beanValidatingItemProcessor;
    }

    @Bean
    @JobScope
    public ItemProcessor<ContactDTO, Contact> mapperProcessor() {
        return new ContactMapperProcessor();
    }
    @Bean
    public MultiResourceItemReader<ContactDTO> multiResourceItemReader()
            throws ParseException, IOException, java.text.ParseException {

        MultiResourceItemReader<ContactDTO> multiResourceItemReader = new MultiResourceItemReader<ContactDTO>();
        multiResourceItemReader.setResources(parameters.getResourcesImportReceivedPath());
        multiResourceItemReader.setDelegate(reader(multiResourceItemReader));
        return multiResourceItemReader;

    }

    @Bean
    public FlatFileItemReader<ContactDTO> reader(MultiResourceItemReader<ContactDTO> delegator)
        throws ParseException, java.text.ParseException {

        FlatFileItemReader<ContactDTO> itemReader = new FlatFileItemReader<ContactDTO>();
        itemReader.setLinesToSkip(1);
        ContactLineMapper lineMapper = getLineMapper(delegator);
        itemReader.setLineMapper(lineMapper);

        return itemReader;
    }

    @Bean
    @JobScope
    public ContactLineMapper getLineMapper(MultiResourceItemReader<ContactDTO> delegator)
            throws ParseException, java.text.ParseException {

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(parameters.getFileStructureDelimiter());
        String[] headers = FileLayoutGenerator.getHeadersFromDTOWithoutDefault();
        delimitedLineTokenizer.setNames(headers);
        BeanWrapperFieldSetMapper<ContactDTO> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<ContactDTO>() {
            {
                setTargetType(ContactDTO.class);
            }
        };
        beanWrapperFieldSetMapper.setDistanceLimit(0);
        ContactLineMapper lineMapper = new ContactLineMapper(delegator, errorFileRepository);
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return lineMapper;

    }

}
