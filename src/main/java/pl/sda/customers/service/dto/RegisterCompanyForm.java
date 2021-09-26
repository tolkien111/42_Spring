package pl.sda.customers.service.dto;

import lombok.NonNull;
import lombok.Value;

@Value // nie musimy podawać przy klasie final i polach private, adn @Value nam to sama generuje
public class RegisterCompanyForm {

    @NonNull
    String name;
    @NonNull
    String vat;
    @NonNull
    String email;

}
