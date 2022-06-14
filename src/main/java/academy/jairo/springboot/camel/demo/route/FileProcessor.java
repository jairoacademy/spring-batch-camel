package academy.jairo.springboot.camel.demo.route;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;

@Log4j2
public class FileProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("-> .process | FileProcessor | In getBody:{}", exchange.getIn().getBody());
    }
}
