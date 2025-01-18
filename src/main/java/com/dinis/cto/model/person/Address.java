package com.dinis.cto.model.person;
import com.dinis.cto.dto.person.DataAddressDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;
    private String complement;

    public Address(DataAddressDTO data) {
        this.street = data.street();
        this.number = data.number();
        this.neighborhood = data.neighborhood();
        this.city = data.city();
        this.state = data.state();
        this.zipCode = data.zipCode();
        this.complement = data.complement();
    }
}
