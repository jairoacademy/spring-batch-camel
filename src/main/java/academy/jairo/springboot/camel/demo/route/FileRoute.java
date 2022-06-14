package academy.jairo.springboot.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

    @Value("${route.path}")
    private String path;

    @Override
    public void configure() throws Exception {
        from("file:" + path + "input")
                .choice()
                    .when(method(ConditionProcessor.class, "isValid"))
                        .log("-> .log | Is valid!")
                        .to("file:" + path + "output")
                    .otherwise()
                        .log("-> .log | Not valid!")
                .end();
    }

    public void _3configure() throws Exception {
        from("file:" + path + "input")
                .to("file:" + path + "output")
                .bean("fileComponent")
                .process(new FileProcessor())
                .log("-> .log | File name: ${file:name}");
    }

    /*
    Caso queira apenas processar o arquivo e enviar para um bean
     */
    public void _2configure() throws Exception {
        from("file:" + path + "input")
                .to("bean:fileComponent");
    }

    /*
    Toma decisao
     */
    public void _1configure2() throws Exception {
        from("file:" + path + "input")
                .choice()
                    .when(simple("header.CamelFileLength}<400"))
                        .to("bean:fileComponent")
                    .otherwise()
                        .process(new FileProcessor());
    }

}
