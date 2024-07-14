package com.dinis.cto.model.os;

import com.dinis.cto.model.car.ClientCar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
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


    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parts> parts;

    @ManyToOne
    @JoinColumn(name = "order_work_id", referencedColumnName = "id")
    private OrderWork orderWork;

    private String description;
}
