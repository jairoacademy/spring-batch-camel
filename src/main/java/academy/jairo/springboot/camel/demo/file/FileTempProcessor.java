package academy.jairo.springboot.camel.demo.file;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Log4j2
public class FileTempProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("-> .process | TempProcessor | in body:{}", exchange.getIn().getBody());
    }

}
