package pl.sda.customers.entity;

import lombok.*;
import pl.sda.customers.service.dto.CustomerView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "customers")
//strategia pojegająca na tym, że tworzona jest jedna tabela i w tej tabeli mamy różne implementacje
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// klasy abstrakcyjnej (obiekty z klas dziedziczących wrzucane do tej samej tabeli), Hibernate dodaje kolumnę w tabeli dotyczącą typu implementowanego obiektu
@DiscriminatorColumn(name = "customer_type")
// PROTECTED ponieważ wszystkie podklasy muszą mieć dostęp do konstruktora z
// klasy abstrakcyjnej, natomiast inne klasy nie mają dostępu do tego konstruktora
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public abstract class Customer {

    @Id
    private UUID id;
    private String email;

    @OneToMany (cascade = CascadeType.ALL) // cascade oznacza ze nowe encje niezapisane jeszcze w db zapisuje do bazy danych, u nas brak adresów u klienta i dodajemy je w trakcie dodawania do db
    @JoinColumn (name = "customer_id")  // nowa kolumna w tabelce z adresami zawierająca klucze obce do łączenia z klientem, jak nie napiszemy to hibernate tworzy
    private List<Address> addresses;    // domyślnie tabelę pośrednią zbędną w tym przypadku

    @Column(name = "customer_type", insertable = false, updatable = false) // to pole jest tylko do odczytu poprzez insertable i updatable na false
    @Enumerated(EnumType.STRING) // ważne aby w bazie danych ten enum oczytywany był jako string, w innym przypadku byłoby zapisane cyframi
    private CustomerType customerType;

    protected Customer(@NonNull String email) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.addresses = new ArrayList<>();
    }// podobnie jak w annotacji @NoArgsConstructor

    public void addAddress(Address address) {
        if(address != null && !addresses.contains(address)){
            addresses.add(address);
        }
    }

    public abstract String getName();



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
