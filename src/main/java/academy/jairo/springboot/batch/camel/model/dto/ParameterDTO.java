package academy.jairo.springboot.batch.camel.model.dto;

import academy.jairo.springboot.batch.camel.model.Constants;
import academy.jairo.springboot.batch.camel.validation.ParameterValue;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParameterDTO {

    @ParameterValue(key = "IMPORT_RECEIVED_PATH")
    private String importReceivedPath;

    @ParameterValue(key = "IMPORT_ERROR_PATH")
    private String importErrorPath;

    @ParameterValue(key = "IMPORT_PROCESSED_PATH")
    private String importProcessedPath;

    @ParameterValue(key = "FILE_STRUCTURE_HEADER_START_FIELD")
    private String fileStructureHeaderStartField;

    @ParameterValue(key = "FILE_STRUCTURE_HEADER_LENGTH")
    private Integer fileStructureHeaderLength;

    @ParameterValue(key = "FILE_STRUCTURE_TRAILER_START_FIELD")
    private String fileStructureTrailerStartField;

    @ParameterValue(key = "FILE_STRUCTURE_TRAILER_LENGTH")
    private Integer fileStructureTrailerLength;

    @ParameterValue(key = "FILE_STRUCTURE_DATA_START_FIELD")
    private String fileStructureDataStartField;

    @ParameterValue(key = "FILE_STRUCTURE_DELIMITER")
    private String fileStructureDelimiter;

    @ParameterValue(key = "FILE_STRUCTURE_DELIMITER_WITH_ESCAPE_CHARACTER")
    private String fileStructureDelimiterWithEscapeCharacter;

    @ParameterValue(key = "FILE_STRUCTURE_LAYOUT_VERSION")
    private String fileStructureLayoutVersion;

    private Resource[] resourcesImportReceivedPath;

    public Resource[] getResourcesImportReceivedPath() throws IOException {
        if (resourcesImportReceivedPath == null && StringUtils.isNotBlank(importReceivedPath)) {
            ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

            resourcesImportReceivedPath = patternResolver
                    .getResources("file:" +
                            this.importReceivedPath + "/"+ Constants.CONTACT_PATTERN_FILENAME);
        }
        return resourcesImportReceivedPath;
    }

}
