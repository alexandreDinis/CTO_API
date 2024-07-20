package com.dinis.cto.model.car;

import com.dinis.cto.dto.car.DataCarDTO;
import com.dinis.cto.dto.os.DataWorkDTO;
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
    private List<Maintenance> maintenence;

    public UserCar(DataCarDTO data) {
        super(data.make(), data.model(), data.plate(), data.year(), data.initialKm(), data.createDate());

    }
}
