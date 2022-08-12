package academy.jairo.springboot.camel.demo.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_PARAMETER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Parameter {

    @Id
    @Column(name = "PARAMETER_ID", nullable = false)
    @GeneratedValue(generator = "SEQ_PARAMETER", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PARAMETER", sequenceName = "SEQ_PARAMETER", allocationSize = 1)
    private Long id;

    @Column(name = "CREATION_DATE", nullable = false)
    private Date creationDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "ACTIVATED_PARAM", nullable = false)
    private String activated;

    @Column(name = "KEY_PARAM", nullable = false)
    private String key;

    @Column(name = "VALUE_PARAM", nullable = false)
    private String value;

}