package com.dinis.cto.model.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Maintence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String local;
    private BigDecimal value;
    private int km;
    private Boolean status;
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "user_car_id", referencedColumnName = "id")
    private UserCar userCar;
}

