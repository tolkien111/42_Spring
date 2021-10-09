package pl.sda.customers.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.customers.service.CustomerAddressesService;
import pl.sda.customers.service.CustomerRegistrationService;
import pl.sda.customers.service.dto.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
final class WriteCustomerRestController {

    @NonNull
    private final CustomerRegistrationService registrationService;
    @NonNull
    private final CustomerAddressesService addressesService;

    @PostMapping("/companies")
        // POST -> /api/companies
        // JSON -> {"name": "Comp S.A", .....} (i tutaj jest ( RequestBody)-> DTO(RegisterCompanyForm)
    ResponseEntity<RegisteredCustomerId> registerCompany(@RequestBody RegisterCompanyForm form) { // ResponseEntity (m.in.kontrolujeny status code, zmieniamy z 200 na 201)
        // i @RequestBody pokazuje na jaki obiekt może zmienić obiekt
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registrationService.registerCompany(form));

    }
    // TODO - add method for registering person
    // POST -> /api/people

    @PostMapping("/peolpe")
    ResponseEntity<RegisteredCustomerId> registerPerson(@RequestBody RegisterPersonForm form) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registrationService.registerPerson(form));
    }
    // TODO - add address to customer
    // ma zawierać latitude + longitude

    @Value
    static class LatLong {
        double latitude;
        double longitude;
    }

    @PostMapping("/customers/{customerId}/addresses")
// POST -> /api/customers/{id}/addresses
        // JSON -> {"latitude": 95052, "longitude:' 96541}
    ResponseEntity<CreatedAddress> addAddress(@PathVariable UUID customerId, @RequestBody LatLong latLong) { //@PathVariable - dodaje id ze ścieżki @PostMapping w
        // naszym przypadku do argumentu metody, ważne: nazwa obiektu musi być taka sama jak w ścieżce
        // jeżeli są inne to @PathVariable ("customerId")
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addressesService.addAddress(new AddAddressFrom(customerId,
                        latLong.getLatitude(),
                        latLong.getLongitude())));
    }
}
