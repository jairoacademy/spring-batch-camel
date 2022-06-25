package academy.jairo.springboot.camel.demo.file;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;

import java.io.File;

@Log4j2
public class FileValidator {

    public boolean isValid(Exchange exchange) throws Exception {
        File file = exchange.getIn().getBody(File.class);
        boolean isResult = file.getName().indexOf("ok")>0;

        log.info("->.when | FileValidator.isValid:{} | in body:{}, file:{}",
                isResult, exchange.getIn().getBody(), file);
        return isResult;
    }
}
