package pl.sda.customers.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("COMPANY") // ustalamy wartość jaka powinna się znaleźć sie w kolumnie po której będziemy rozróżniać firmę od klienta
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Company extends Customer {

    private String name;
    private String vat;

    // domyślnie przy generowaniu konstruktora mamny @NonNull do email, ale nie jest nam potrzebne bo już klasa nadrzędna to weryfikuje
    public Company(String email, @NonNull String name, @NonNull String vat) {
        super(email); // ustawiamy email w konstruktorze, ale id bierze z konstruktora w Customer, podobnie z listą adresów, dziedziczy wszystko z konstr Customer class
        this.name = name;
        this.vat = vat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Company company = (Company) o;
        return name.equals(company.name) && vat.equals(company.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, vat);
    }
}
