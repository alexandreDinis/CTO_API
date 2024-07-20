package com.dinis.cto.model.car;

import com.dinis.cto.dto.car.DataCarDTO;
import com.dinis.cto.dto.os.DataWorkDTO;
import com.dinis.cto.model.os.Work;
import com.dinis.cto.model.person.Client;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientCar extends Car {
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Work> works;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public ClientCar(DataWorkDTO data) {
        super(data.car().make(), data.car().model(), data.car().plate(), data.car().year(),
                data.car().initialKm(), data.car().createDate());
    }


    public ClientCar(DataCarDTO car) {
        super(car.make(), car.model(),  car.plate(),car.year(), car.initialKm(), car.createDate());
    }
}