package pl.sda.customers.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import pl.sda.customers.service.dto.AddressView;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@EqualsAndHashCode

public final class Address { //błąd inteliJ związany z prywatnym konstruktorem, jest ok

    @Id
    private UUID id;
    private String street;
    private String city;
    private String zipCode;
    private String countryCode;

    //only for hibernate
    private Address() {
    }

    public Address(@NonNull String street,@NonNull String city,@NonNull String zipCode,@NonNull String countryCode) {
        this.id = UUID.randomUUID();
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.countryCode = countryCode;
    }


    public AddressView toView() {
        return new AddressView(id, street, city, zipCode, countryCode);
    }
}
