package academy.jairo.springboot.batch.camel.file;

import academy.jairo.springboot.batch.camel.config.SftpProperties;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

    @Autowired
    private SftpProperties properties;

    @Override
    public void configure() throws Exception {
        from("file:" + properties.getRoutePath() + properties.getRouteInput())
                .choice()
                    .when(method(FileValidator.class, "isValid"))
                        .to("file:" + properties.getRoutePath() + properties.getRouteOutput())
                        .process(new FileProcessor(properties))
                    .otherwise()
                        .log("-> .log | Is Not valid!")
                .end();
    }

    public void _3configure() throws Exception {
        from("file:" + properties.getRoutePath() + properties.getRouteOutput())
                .to("file:" + properties.getRoutePath() + "output")
                .bean("fileComponent")
                .process(new FileProcessor(null))
                .log("-> .log | File name: ${file:name}");
    }

    /*
    Caso queira apenas processar o arquivo e enviar para um bean
     */
    public void _2configure() throws Exception {
        from("file:" + properties.getRoutePath() + "input")
                .to("bean:fileComponent");
    }

    /*
    Toma decisao
     */
    public void _1configure2() throws Exception {
        from("file:" + properties.getRoutePath() + "input")
                .choice()
                    .when(simple("header.CamelFileLength}<400"))
                        .to("bean:fileComponent")
                    .otherwise()
                        .process(new FileProcessor(null));
    }

}
