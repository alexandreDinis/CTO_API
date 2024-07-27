package com.dinis.cto.model.os;
import com.dinis.cto.dto.os.DataOrderWorkDTO;
import com.dinis.cto.model.person.Client;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Data
@Getter
@Setter
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

    @OneToMany(mappedBy = "orderWork", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Work> works;

    private BigDecimal serviceValue;
    private BigDecimal discountValue;
    private BigDecimal discountPercentage;
    private BigDecimal valueTotal;




    public OrderWork(DataOrderWorkDTO data){
        this.createDate = data.createDate();
        this.initKm = data.initKm();
        this.finalKm = data.finalKm();
        this.works = data.works().stream()
                .map(Work::new)
                .collect(Collectors.toList());
        this.serviceValue = calculateServiceValue();
    }

    private BigDecimal calculateServiceValue() {
        return works.stream()
                .map(Work::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void closeOs() {
        this.status = false;
    }
}
