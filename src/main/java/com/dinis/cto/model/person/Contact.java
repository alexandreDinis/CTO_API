package com.dinis.cto.model.person;
import com.dinis.cto.dto.person.DataContactDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    private List<Phone> phones = new ArrayList<>();

    private String email;

    @ManyToMany(mappedBy = "contacts")
    private List<Client> clients;

    public Contact(DataContactDTO data) {
        this.name = data.name();
        this.department = data.department();
        this.phones = data.phones().stream()
                .map(phoneDTO -> new Phone(phoneDTO.number(), phoneDTO.description()))
                .collect(Collectors.toList());
        this.email = data.email();
    }
}

