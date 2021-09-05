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
//strategia pojegająca na tym, że tworzona jest jedna tabela i w tej tabeli mamy różne implementacje
// klasy abstrakcyjnej (obiekty z klas dziedziczących wrzucane do tej samej tabeli), Hibernate dodaje kolumnę w tabeli dotyczącą typu implementowanego obiektu
@DiscriminatorColumn(name = "customer_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// PROTECTED ponieważ wszystkie podklasy muszą mieć dostęp do konstruktora z
    // klasy abstrakcyjnej, natomiast inne klasy nie mają dostępu do tego konstruktora
@Getter

public abstract class Customer {

    @Id
    private UUID id;
    private String email;

    @OneToMany (cascade = CascadeType.ALL) // cascade oznacza ze nowe encje niezapisane jeszcze w db zapisuje do bazy danych, u nas brak adresów u klienta i dodajemy je w trakcie dodawania do db
    @JoinColumn (name = "customer_id")  // nowa kolumna w tabelce z adresami zawierająca klucze obce do łączenia z klientem, jak nie napiszemy to hibernate tworzy
    private List<Address> addresses;    // domyślnie tabelę pośrednią zbędną w tym przypadku

    protected Customer(@NonNull String email) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.addresses = new ArrayList<>();
    }// podobnie jak w annotacji @NoArgsConstructor

    public void addAdress(Address address) {
        if(address != null && !addresses.contains(address)){
            addresses.add(address);
        }
    }

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
