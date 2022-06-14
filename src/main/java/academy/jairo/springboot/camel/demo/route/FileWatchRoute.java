package academy.jairo.springboot.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//@Component
public class FileWatchRoute extends RouteBuilder {

    @Value("${route.path}")
    private String path;

    @Override
    public void configure() throws Exception {
        from("file-watch:" + path) //?events=CREATE,MODIFY,DELETE
                .bean("fileComponent")
                .process(new FileProcessor())
                .log("-> .log | Event Type:${header.CamelFileEventType} | File:${header:CamelFileName}");
    }



}
