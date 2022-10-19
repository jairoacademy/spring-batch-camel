package academy.jairo.springboot.batch.camel.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TB_FILE")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    @Id
    @Column(name = "FILE_ID", nullable = false)
    @GeneratedValue(generator = "SEQ_FILE", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_FILE", sequenceName = "SEQ_FILE", allocationSize = 1)
    private Long id;

    @Column(name = "FILE_NAME_INPUT", nullable = false)
    private String fileNameInput;

    @Column(name = "FILE_NAME_OUTPUT")
    private String fileNameOutput;

    @Column(name = "DATE_INPUT", nullable = false)
    private Date dateInput;

    @Column(name = "DATE_OUTPUT")
    private Date dateOutput;

    @Column(name = "RECORD_QUANTITY")
    private Integer recordQuantity;

    @Column(name = "LAYOUT_VERSION", nullable = false)
    private String layoutVersion;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        File other = (File) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
