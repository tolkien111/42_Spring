package pl.sda.customers.service.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class AddressView {

    UUID customerId;
    String street;
    String city;
    String zipCode;
    String countryCode;
}
