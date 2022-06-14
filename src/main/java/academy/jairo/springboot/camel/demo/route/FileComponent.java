package academy.jairo.springboot.camel.demo.route;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Log4j2
public class FileComponent {

    public void log(File file) {
        log.info("-> .bean | FileComponent | Name the file:{}", file.getName());
    }
}
