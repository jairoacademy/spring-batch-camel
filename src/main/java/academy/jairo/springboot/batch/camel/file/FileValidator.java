package academy.jairo.springboot.batch.camel.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;

import java.io.File;

@Slf4j
public class FileValidator {

    public boolean isValid(Exchange exchange) throws Exception {
        File file = exchange.getIn().getBody(File.class);
        boolean isResult = true; //file.getName().indexOf("ok")>0;

        log.info("->.when | FileValidator.isValid:{} | in body:{}, file:{}",
                isResult, exchange.getIn().getBody(), file);
        return isResult;
    }
}
