package pl.sda.customers.service.dto;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class AddAddressFrom {

    @NonNull
    UUID customerId;
    double latitude;
    double longitude;

}
