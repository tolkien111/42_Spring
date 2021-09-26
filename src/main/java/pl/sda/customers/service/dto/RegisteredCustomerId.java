package pl.sda.customers.service.dto;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class RegisteredCustomerId { // tworzymy tę klasę na wypadek gdyby później była rozwijana i przekazywane byłyby inne dane

    @NonNull
    UUID id;
}
