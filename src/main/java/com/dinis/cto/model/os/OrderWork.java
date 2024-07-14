package com.dinis.cto.model.os;
import com.dinis.cto.model.person.Client;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BudgetEnum budget;
    private LocalDate createDate;
    private int initKm;
    private int finalKm;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "orderWork", cascade = CascadeType.ALL)
    private List<Work> works;

    private BigDecimal serviceValue;
    private BigDecimal discountValue;
    private BigDecimal discountPercentage;
    private BigDecimal valueTotal;
}
