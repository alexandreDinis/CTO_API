package com.dinis.cto.model.os;

import com.dinis.cto.dto.os.DataPartsDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Parts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;

    public Parts(DataPartsDTO data) {
        this.name = data.name();
        this.description = data.description();
        this.value = data.value();
    }

    public Parts(Parts parts) {
        this.name = parts.getName();
        this.description = parts.getDescription();
        this.value = parts.getValue();
    }
}
