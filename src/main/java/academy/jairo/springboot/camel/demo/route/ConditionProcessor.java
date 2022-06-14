package academy.jairo.springboot.camel.demo.route;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;

import java.io.File;

@Log4j2
public class ConditionProcessor {

    public boolean isValid(Exchange exchange) throws Exception {
        boolean isResult = true;
        File file = exchange.getIn().getBody(File.class);
        log.info("-> file:{}", file);

        log.info("-> ConditionProcessor.isValid:{} | In getBody:{}", isResult, exchange.getIn().getBody());
        return isResult;
    }
}
