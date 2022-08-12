package academy.jairo.springboot.camel.demo.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {

    @Id
    @GeneratedValue(generator = "SEQ_CONTACT", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_CONTACT", sequenceName = "SEQ_CONTACT", allocationSize = 1)
    private Long id;

    @NotNull
    private Long fileId;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private String phoneNumber;

}
