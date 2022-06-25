package academy.jairo.springboot.camel.demo.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {

    private String routePath;
    private String routeInput;
    private String routeOutput;

    private String path;
    private String host;
    private String port;
    private String user;
    private String pass;

}
