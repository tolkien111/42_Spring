package pl.sda.customers.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//strategia pojegająca na tym, że w jednej tabeli mamy różne implementacje
// klasy abstrakcyjnej, Hibernate dodaje kolumnę odnośnie typu implementowanego obiektu
@DiscriminatorColumn(name = "customer_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// PROTECTED ponieważ wszystkie podklasy muszą mieć dostęp do konstruktora z
    // klasy abstrakcyjnej, natomiast inne klasy nie mają dostępu do tego konstruktora
@Getter

public abstract class Customer {

    @Id
    private UUID id;
    private String email;

    @OneToMany //mapowanie jeden do wielu
    @JoinColumn (name = "customer_id")  // nowa kolumna w tabelce z adresami zawierająca klucze obce do łączenia z klientem, jak nie napiszemy to hibernate tworzy
    private List<Address> addresses;    // tabele pośrednią zbędną w tym przypadku

    protected Customer(@NonNull String email) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.addresses = new ArrayList<>();
    }// podobnie jak w annotacji @NoArgsConstructor


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
