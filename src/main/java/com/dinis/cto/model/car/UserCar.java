package com.dinis.cto.model.car;

import com.dinis.cto.dto.car.DataCarDTO;
import com.dinis.cto.dto.os.DataWorkDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCar extends Car {

    @OneToMany(mappedBy = "userCar", cascade = CascadeType.ALL)
    private List<Maintenance> maintenence;

    @OneToMany(mappedBy = "userCar", cascade = CascadeType.ALL)
    private List<Fuel> fuels;

    public UserCar(DataCarDTO data) {
        super(data.make(), data.model(), data.plate(), data.year(), data.initialKm(), data.createDate());

    }

    public void addFuel(Fuel fuel) {
        fuels.add(fuel);
    }

    public void addMaintenance(Maintenance maintenance) {
        maintenence.add(maintenance);
    }

    public void updateTotalKm(int km) {
        this.setInitialKm(this.getInitialKm() + km);
    }
}
