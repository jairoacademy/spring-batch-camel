package academy.jairo.springboot.batch.camel.model;

import academy.jairo.springboot.batch.camel.model.entity.File;

public class FileCreator {

    public static final int PFAC_FILE_STRUCTURE_HEADER_LENGTH = 222;
    public static final String PFAC_FILE_STRUCTURE_HEADER_START_FIELD =  "0";
    public static final String PFAC_FILE_STRUCTURE_DELIMITER_WITH_ESCAPE_CHARACTER = "\\|~\\|";
    public static final String PFAC_FILE_STRUCTURE_LAYOUT_VERSION = "V1.0";
    public static final String PFAC_FILE_STRUCTURE_DATA_START_FIELD = "1";
    public static final String PFAC_FILE_STRUCTURE_TRAILER_START_FIELD = "9";
    public static final Integer PFAC_FILE_STRUCTURE_TRAILER_LENGTH = 2;

    public static final String FILE = "DEMO_CAMEL_CONTACT-VALID_20220809000000.txt";
    public static final String INVALID_FILE = "DEMO_CAMEL_CONTACT-INVALID_20220809000000.txt";

    public static File buildValid() {
        return File.builder()
                .id(1L)
                .fileNameInput(FILE)
                .build();
    }

}
