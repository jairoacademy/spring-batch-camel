package academy.jairo.springboot.camel.demo.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@Slf4j
public class FileTempProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("-> .process | TempProcessor | in body:{}", exchange.getIn().getBody());
    }

}
