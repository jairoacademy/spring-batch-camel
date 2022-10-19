package academy.jairo.springboot.batch.camel.file;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

//@Component
public class FileWatch extends RouteBuilder {

    @Value("${route.path}")
    private String path;

    @Override
    public void configure() throws Exception {
        from("file-watch:" + path) //?events=CREATE,MODIFY,DELETE
                .bean("fileComponent")
                .process(new FileProcessor(null))
                .log("-> .log | Event Type:${header.CamelFileEventType} | File:${header:CamelFileName}");
    }



}
