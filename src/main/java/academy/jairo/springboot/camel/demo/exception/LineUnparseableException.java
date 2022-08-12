package academy.jairo.springboot.camel.demo.exception;

import org.springframework.batch.item.file.FlatFileParseException;

public class LineUnparseableException extends FlatFileParseException {

    public LineUnparseableException(String message, String input) {
        super(message, input);
    }

}
