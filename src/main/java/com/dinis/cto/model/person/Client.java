package com.dinis.cto.model.person;
import com.dinis.cto.dto.person.DataClientDTO;
import com.dinis.cto.model.os.OrderWork;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fantasyName;
    private String corporateReason;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private Boolean status;

    @Enumerated(EnumType.STRING)
    private PerformanceEnum performance;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String cnpj;
    private LocalDate createDate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "Client_Contact",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<OrderWork> orderWorks;

    public Client(DataClientDTO data) {
        this.fantasyName = data.fantasyName();
        this.corporateReason = data.corporateReason();
        this.address = new Address(data.address());
        this.cpf = data.cpf();
        this.cnpj = data.cnpj();
        this.contacts = data.contacts().stream()
                .map(Contact::new)
                .collect(Collectors.toList());
        this.createDate = data.createDate();

    }
}