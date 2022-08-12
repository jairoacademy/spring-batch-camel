package academy.jairo.springboot.camel.demo.model.enums;

/**
 * The annotation @ParameterValue, that created, solves this!
 */
@Deprecated
public enum ParameterEnum {

    IMPORT_RECEIVED_PATH("IMPORT_RECEIVED_PATH"),
    IMPORT_ERROR_PATH("IMPORT_ERROR_PATH"),
    IMPORT_PROCESSED_PATH("IMPORT_PROCESSED_PATH"),
    FILE_STRUCTURE_HEADER_START_FIELD("FILE_STRUCTURE_HEADER_START_FIELD"),
    FILE_STRUCTURE_HEADER_LENGTH("FILE_STRUCTURE_HEADER_LENGTH"),
    FILE_STRUCTURE_TRAILER_START_FIELD("FILE_STRUCTURE_TRAILER_START_FIELD"),
    FILE_STRUCTURE_TRAILER_LENGTH("FILE_STRUCTURE_TRAILER_LENGTH"),
    FILE_STRUCTURE_DATA_START_FIELD("FILE_STRUCTURE_DATA_START_FIELD"),
    FILE_STRUCTURE_DELIMITER("FILE_STRUCTURE_DELIMITER"),
    FILE_STRUCTURE_DELIMITER_WITH_ESCAPE_CHARACTER("FILE_STRUCTURE_DELIMITER_WITH_ESCAPE_CHARACTER"),
    FILE_STRUCTURE_LAYOUT_VERSION("FILE_STRUCTURE_LAYOUT_VERSION");

    private String description;

    private ParameterEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
