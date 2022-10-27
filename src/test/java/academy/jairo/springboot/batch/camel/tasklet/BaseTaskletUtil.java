package academy.jairo.springboot.batch.camel.tasklet;

import academy.jairo.springboot.batch.camel.model.Constants;
import academy.jairo.springboot.batch.camel.model.dto.ParameterDTO;
import academy.jairo.springboot.batch.camel.repository.ErrorFileRepository;
import academy.jairo.springboot.batch.camel.repository.FileRepository;
import org.mockito.Mock;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

public class BaseTaskletUtil {

    @Mock
    protected ParameterDTO parameters;

    @Mock
    protected FileRepository fileRepository;

    @Mock
    protected ErrorFileRepository errorFileRepository;

    protected Resource[] getResourcesReceivedPath() throws IOException {

        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

        return patternResolver
                .getResources("file:" + getReceivedPathValue() + Constants.CONTACT_PATTERN_FILENAME);
    }

    protected String getReceivedPathValue() throws IOException {
        return "/home/jairo/apache-camel/file/received/";
    }

    protected String getErrorPathValue() throws IOException {
        return "/home/jairo/apache-camel/file/error";
    }
}
