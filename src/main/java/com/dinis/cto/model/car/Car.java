package com.dinis.cto.model.car;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;
    private String place;
    private String year;
    private int initialKm;
    private LocalDate createDate;

    public Car(String make, String model, String place, String year, int initialKm, LocalDate createDate) {
        this.make = make;
        this.model = model;
        this.place = place;
        this.year = year;
        this.initialKm = initialKm;
        this.createDate = createDate;
    }
}

