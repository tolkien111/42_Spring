package pl.sda.customers.service.dto;

import lombok.NonNull;
import lombok.Value;

@Value // adn @Value załatwia nam przy klasie final i polach private, sama to generuje, generuje również gettery
public class RegisterCompanyForm {

    @NonNull
    String name;
    @NonNull
    String vat;
    @NonNull
    String email;

}
