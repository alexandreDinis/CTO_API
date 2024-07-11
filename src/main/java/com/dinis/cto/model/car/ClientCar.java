package com.dinis.cto.model.car;

import com.dinis.cto.model.os.Work;
import com.dinis.cto.model.person.Client;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCar extends Car {
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Work> works;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}