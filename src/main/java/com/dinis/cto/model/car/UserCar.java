package com.dinis.cto.model.car;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCar extends Car {
    @OneToMany(mappedBy = "userCar", cascade = CascadeType.ALL)
    private List<Maintence> maintenence;
}
