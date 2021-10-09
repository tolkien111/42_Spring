package pl.sda.customers.service.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.sda.customers.entity.CustomerType;

import java.util.List;

@Getter
@RequiredArgsConstructor
public abstract class CustomerDetails {

    private final CustomerType type;
    private final String email;
    private final List<AddressView> addresses;

    @Value // w tym przypadku @Value niestety nie tworzy dobrego konstruktora ze wszystkimi polami z clasy i nadklasy
    @EqualsAndHashCode(callSuper = true)// callSuper wywołuje equals i hashcode z klasy nadrzędnej
    public static class PersonCustomerDetails extends CustomerDetails {

        private final String firstName; // @Value podaje te dwie wartości podświetlone na żółto
        String lastName;
        String pesel;

        public PersonCustomerDetails(String email,
                                     String firstName,
                                     String lastName,
                                     String pesel,
                                     List<AddressView> addresses) {
            super(CustomerType.PERSON, email, addresses);
            this.firstName = firstName;
            this.lastName = lastName;
            this.pesel = pesel;
        }
    }

    @Value
    @EqualsAndHashCode(callSuper = true)
    public static class CompanyCustomerDetails extends CustomerDetails {

        String name;
        String vat;

        public CompanyCustomerDetails(String email,
                                      String name,
                                      String vat,
                                      List<AddressView> addresses) {
            super(CustomerType.COMPANY, email, addresses);
            this.name = name;
            this.vat = vat;
        }
    }


}
