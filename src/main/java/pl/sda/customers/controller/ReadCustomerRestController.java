package pl.sda.customers.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.service.CustomerQuery;
import pl.sda.customers.service.dto.CustomerDetails;
import pl.sda.customers.service.dto.CustomerView;

import java.util.List;
import java.util.UUID;

@RestController //adnotacja tworząca bean springowy o specjalnych właściwościach rest controllera
@RequestMapping("/api/customers") // określa ścieżkę do której rest controller bęcie zapinał się
@RequiredArgsConstructor
final class ReadCustomerRestController {

    @NonNull
    private final CustomerQuery query;

    //GET -> /api/customers
    @GetMapping// jeżeli przyjdzie request na customera to mój request wykona tę metodę
    List<CustomerView> getCustomers(){ // z uwagi, że zwracamy listę, spring przetwarza nam listę klintów do pliku JSON, w prostych obiektach typu String zapisuje do pliku TXT
        return query.listCustomer();
    }

    // TODO - get single customer details
    // /api/customers/{id} ->   JSON {type: "PERSON", "firsName": "Jan", ..., "email": "abv@wp.pl", addresses: []}
    //                          JSON (type: "COMPANY", "name":, "ASSA S.A., "vat"" "PL34234", "email": "...", adresses: []}

    @GetMapping("{customerId}")
    CustomerDetails getCustomerDetails(@PathVariable UUID customerId){
                return query.getById(customerId);
    }

}
