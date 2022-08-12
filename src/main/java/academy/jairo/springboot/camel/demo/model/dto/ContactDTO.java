package academy.jairo.springboot.camel.demo.model.dto;

import academy.jairo.springboot.camel.demo.validation.BatchField;
import lombok.*;
import org.springframework.batch.item.ResourceAware;
import org.springframework.core.io.Resource;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContactDTO implements ResourceAware {

    //Don't forget this, the first token from the line batch file
    @Transient
    @BatchField
    private String startField;

    @NotBlank
    @BatchField
    private String name;

    @NotBlank
    @BatchField
    private String email;

    @BatchField
    private String phoneNumber;


    private Date creationDate;

    private Long fileId;
    private String fileName;

    private Integer lineNumber;

    @Override
    public void setResource(Resource resource) {
        this.fileName = resource.getFilename();
    }

}
