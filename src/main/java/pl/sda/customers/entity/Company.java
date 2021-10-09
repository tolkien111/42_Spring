package pl.sda.customers.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.sda.customers.service.dto.AddressView;
import pl.sda.customers.service.dto.CustomerDetails;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static pl.sda.customers.service.dto.CustomerDetails.*;

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
    public CustomerDetails mapToDetails() {
        return new CompanyCustomerDetails(getEmail(), name, vat, getAddresses().stream()
        .map(Address::toView) //address -> address.toView()
                .collect(toList()));
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
