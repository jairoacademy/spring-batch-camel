package academy.jairo.springboot.camel.demo.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FileComponent {

    public void log(File file) {
        log.info("-> .bean | FileComponent | Name the file:{}", file.getName());
    }
}
