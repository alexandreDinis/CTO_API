package com.dinis.cto.model.os;

import com.dinis.cto.dto.os.DataOrderWorkDTO;
import com.dinis.cto.dto.os.DataPartsDTO;
import com.dinis.cto.dto.os.DataWorkDTO;
import com.dinis.cto.model.car.ClientCar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private ClientCar car;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Parts> parts;

    @ManyToOne
    @JoinColumn(name = "order_work_id", referencedColumnName = "id")
    private OrderWork orderWork;

    private String description;
    private BigDecimal value;

    public Work(DataWorkDTO data) {
        this.description = data.description();
        this.parts = data.parts().stream()
                .map(Parts::new)
                .collect(Collectors.toList());
        this.value = calculatePartsValue(data.parts());
    }

    public Work(Work work) {
        this.id = work.getId();
        this.car = work.getCar();
        this.parts = work.getParts();
        this.orderWork = work.getOrderWork();
        this.description = work.getDescription();
    }

    private BigDecimal calculatePartsValue(List<DataPartsDTO> parts) {
        return parts.stream()
                .map(DataPartsDTO::value)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
