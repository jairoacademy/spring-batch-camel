package academy.jairo.springboot.camel.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorFile {

    @Id
    @GeneratedValue(generator = "SEQ_ERROR_FILE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_ERROR_FILE", sequenceName = "SEQ_ERROR_FILE", allocationSize = 1)
    private Long id;

    private Long fileId;

    private Integer lineNumber;

    private String errorMessage;


}
