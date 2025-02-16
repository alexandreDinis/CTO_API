package com.dinis.cto.model.person;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String number;

    @ManyToOne
    @JoinColumn(name = "contact_id")  // Chave estrangeira na tabela phone
    private Contact contact;

    public Phone(String number, String description) {
        this.number = number;
        this.description = description;
    }
}



