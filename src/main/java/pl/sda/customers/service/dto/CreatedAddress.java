package pl.sda.customers.service.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class CreatedAddress {

    UUID customerId;
    UUID addressId;
    String street;
    String city;
    String zipCode;
    String countryCode;

}
