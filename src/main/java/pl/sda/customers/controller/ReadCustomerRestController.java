package pl.sda.customers.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.customers.service.CustomerQuery;
import pl.sda.customers.service.dto.CustomerView;

import java.util.List;

@RestController //adnotacja tworząca bean springowy o specjalnych właściwościach rest controllera
@RequestMapping("/api/customers")
@RequiredArgsConstructor
final class ReadCustomerRestController {

    @NonNull
    private final CustomerQuery query;

    @GetMapping// jeżeli przyjdzie request na customera to mój request wykona tę metodę
    List<CustomerView> getCustomers(){ // z uwagi, że zwracamy listę, spring przetwarza nam listę klintów do pliku JSON, w prostych obiektach typu String zapisuje do pliku TXT
        return query.listCustomer();
    }

    // TODO - get single customer details
    // /api/customers/{id}
}
