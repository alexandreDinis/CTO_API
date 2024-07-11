package com.dinis.cto.model.person;
import com.dinis.cto.model.os.OrderWork;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    private String cpf;
    private String cnpj;
    private LocalDate createDate;

    @ManyToMany
    @JoinTable(
            name = "Client_Contact",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id")
    )
    private List<Contact> contacts;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<OrderWork> orderWorks;
}