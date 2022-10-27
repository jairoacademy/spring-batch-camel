package academy.jairo.springboot.batch.camel.tasklet;

import academy.jairo.springboot.batch.camel.batch.tasklet.LookupFilesTasklet;
import academy.jairo.springboot.batch.camel.model.FileCreator;
import academy.jairo.springboot.batch.camel.util.FileStructureUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class LookupFilesTaskletValidTest extends BaseTaskletUtil {

    @InjectMocks
    private LookupFilesTasklet lookupFilesTasklet;

    @Before
    public void setup() throws IOException {

        FileStructureUtil.copyResourceTo(FileCreator.FILE, getReceivedPathValue());

        when(parameters.getResourcesImportReceivedPath()).thenReturn(getResourcesReceivedPath());

    }

    @Test
    public void shouldExecuteValidFile() throws Exception {

        assertTrue(new File(getReceivedPathValue()+ "/" + FileCreator.FILE).exists());

        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution();
        StepContribution stepContribution = new StepContribution(stepExecution);
        ChunkContext chunkContext = new ChunkContext(new StepContext(stepExecution));

        RepeatStatus status = lookupFilesTasklet.execute(stepContribution, chunkContext);

        assertThat(status).isEqualTo(RepeatStatus.FINISHED);

    }


}