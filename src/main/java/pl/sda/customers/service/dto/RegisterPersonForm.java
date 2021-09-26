package pl.sda.customers.service.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class RegisterPersonForm {

    @NonNull
    String email;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    @NonNull
    String pesel;





}
