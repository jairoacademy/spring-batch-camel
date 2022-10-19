package academy.jairo.springboot.batch.camel.batch.tasklet;

import academy.jairo.springboot.batch.camel.model.dto.ParameterDTO;
import academy.jairo.springboot.batch.camel.repository.ErrorFileRepository;
import academy.jairo.springboot.batch.camel.repository.FileRepository;
import academy.jairo.springboot.batch.camel.util.FileStructureUtil;
import academy.jairo.springboot.batch.camel.model.entity.File;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Slf4j
public class LookupFilesTasklet implements Tasklet {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private ParameterDTO parameters;

    @Autowired
    private ErrorFileRepository errorFileRepository;
    private String resourcesToBeProcessed;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("-> {} contact files will be processed", parameters.getResourcesImportReceivedPath().length);

        resourcesToBeProcessed = "";

        for (Resource resource : parameters.getResourcesImportReceivedPath()) {
            log.info("-> Validating {} contact files", resource.getFile().getName());
            if (!FileStructureUtil.isValid(resource, parameters)) {

                log.warn("* > The contact file doesn't passed in the structural validation: {}", resource.getFile().getName());

                String moveFrom = FileUtils.getFile(parameters.getImportReceivedPath()).getPath();
                String moveTo = FileUtils.getFile(parameters.getImportErrorPath()).getPath();
                FileStructureUtil.moveFile(moveFrom, moveTo, resource.getFile().getName());

                log.warn("* > The contact file {} was moved to error folder!", resource.getFile().getName());

            } else {
                File file = File.builder()
                        .recordQuantity(FileStructureUtil.getRegistryCount(resource, parameters))
                        .fileNameInput(resource.getFilename())
                        .dateInput(new Date())
                        .layoutVersion(parameters.getFileStructureLayoutVersion())
                        .build();

                file = fileRepository.save(file);
                log.info("-> contact file (id:{}) created: {}", file.getId(), resource.getFilename());

                resourcesToBeProcessed += resource.getFile().getName() + ",";

                chunkContext.getStepContext()
                        .getStepExecution()
                        .getJobExecution()
                        .getExecutionContext()
                        .put(file.getFileNameInput(), file.getId());
            }
        }

        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("resourcesToBeProcessed", resourcesToBeProcessed);


        return RepeatStatus.FINISHED;
    }
}
