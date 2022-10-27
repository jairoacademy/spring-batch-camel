package academy.jairo.springboot.batch.camel;

import academy.jairo.springboot.batch.camel.model.dto.ParameterDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ParameterTest {

    @Autowired
    private ParameterDTO parameters;

    @Test
    void getPropertiesValueWhenSuccess() throws Exception {

        assertThat(parameters.getImportReceivedPath()).isNotEmpty();

        assertThat(parameters.getImportErrorPath()).isNotEmpty();

        assertThat(parameters.getImportProcessedPath()).isNotEmpty();

        assertThat(parameters.getFileStructureHeaderStartField()).isNotEmpty();

        assertThat(parameters.getFileStructureHeaderLength()).isPositive();

        assertThat(parameters.getFileStructureTrailerStartField()).isNotEmpty();

        assertThat(parameters.getFileStructureTrailerLength()).isPositive();

        assertThat(parameters.getFileStructureDataStartField()).isNotEmpty();

        assertThat(parameters.getFileStructureDelimiter()).isNotEmpty();

        assertThat(parameters.getFileStructureDelimiterWithEscapeCharacter()).isNotEmpty();

        assertThat(parameters.getFileStructureLayoutVersion()).isNotEmpty();
    }

}
