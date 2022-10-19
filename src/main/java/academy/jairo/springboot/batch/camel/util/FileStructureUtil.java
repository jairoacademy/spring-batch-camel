package academy.jairo.springboot.batch.camel.util;

import academy.jairo.springboot.batch.camel.exception.FileStructureException;
import academy.jairo.springboot.batch.camel.model.dto.ParameterDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FileStructureUtil {

    private static final int POS_LINE_HEADER = 0;
    public static Boolean isValid(Resource resource, ParameterDTO parameters) throws IOException {
        Stream<String> file = Files.lines(Paths.get(resource.getURI()));
        List<String> lines = null;
        try {
            lines = file.collect(Collectors.toList());
        } catch (Exception e) {
            throw new FileStructureException("Error with Incorrect Encoding File");
        } finally {
            file.close();
        }

        for (int i = 0; i < lines.size(); i++) {
            String[] fieldsFromLine = lines.get(i).split(parameters.getFileStructureDelimiterWithEscapeCharacter());
            if (i == POS_LINE_HEADER) {
                if (!fieldsFromLine[0].equals(parameters.getFileStructureHeaderStartField())
                        | fieldsFromLine.length != parameters.getFileStructureHeaderLength()) {
                    throw new FileStructureException(
                            String.format("Error with File Structure Header Start Field: line:%s",  lines.get(i)));
                }
            } else if (i == lines.size() - 2) {
                if (!fieldsFromLine[0].equals(parameters.getFileStructureTrailerStartField())
                        | fieldsFromLine.length != parameters.getFileStructureTrailerLength()
                        | !(fieldsFromLine[1].equals(String.valueOf(lines.size() - 3)))) {
                    throw new FileStructureException(
                            String.format("Error File Structure Trailer Start Field: line:%s",  lines.get(i)));
                }
            } else if (i == lines.size() - 1) {
                if (!(fieldsFromLine[0] != null && fieldsFromLine[0].equals(parameters.getFileStructureLayoutVersion()))) {
                    throw new FileStructureException(
                            String.format("Error with File Structure Layout Version: line:%s",  lines.get(i)));
                }
            } else if (!fieldsFromLine[0].equals(parameters.getFileStructureDataStartField())) {
                throw new FileStructureException(
                        String.format("Error with File Structure Data Start Field: line:%s",  lines.get(i)));
            }
        }
        return true;
    }

    public static Integer getRegistryCount(Resource resource, ParameterDTO parameters) throws IOException {
        Stream<String> file = Files.lines(Paths.get(resource.getURI()));
        List<String> lines = file.collect(Collectors.toList());
        file.close();
        String[] field = lines.get(lines.size() - 2)
                .split(parameters.getFileStructureDelimiterWithEscapeCharacter());
        return Integer.parseInt(field[1]);
    }

    public static void moveFile(String moveFrom, String moveTo, String filename) throws IOException {
        String moveFromNormalized = FilenameUtils.normalize(moveFrom);
        String moveToNormalized = FilenameUtils.normalize(moveTo);
        String fileNameNormalized = FilenameUtils.normalize(filename);

        File file = new File(moveFromNormalized);
        if (!file.exists()) {
            file.mkdir();
        }
        Files.move(Paths.get(moveFromNormalized.concat("/").concat(fileNameNormalized)),
                Paths.get(moveToNormalized.concat("/").concat(fileNameNormalized)),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
