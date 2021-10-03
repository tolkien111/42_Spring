package pl.sda.customers.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.service.CustomerRegistrationService;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisteredCustomerId;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
final class WriteCustomerRestController {

    @NonNull
    private final CustomerRegistrationService service;

    @PostMapping("/companies") // POST -> /api/companies
    ResponseEntity<RegisteredCustomerId> registerCompany(@RequestBody RegisterCompanyForm form){ // ResponseEntity (m.in.kontrolujeny status code, zmieniamy z 200 na 201) i @RequestBody
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registerCompany(form));

    }
    // TODO - add method for registering person
    // POST -> /api/people

    // TODO - add address to customer
    // POST -> /api/customer/{id}/addresses
    // ma zawierać latitude + longitude
}
