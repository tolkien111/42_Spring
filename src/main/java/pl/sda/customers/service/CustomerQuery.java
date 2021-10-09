package pl.sda.customers.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customers.entity.Customer;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.CustomerDetails;
import pl.sda.customers.service.dto.CustomerView;
import pl.sda.customers.service.exception.CustomerNotExistsExeption;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerQuery {

    @NonNull
    private final CustomerRepository repository;

    public List<CustomerView> listCustomer() { // metoda przetwarzająca naszego customera z repozytorium na obiekt do wysłania w Query/nasz endpoint
        return repository.findAll().stream()
                .map(Customer::toView)
                .collect(Collectors.toList());
    }

    public CustomerDetails getById(UUID customerId) {
        return repository.findById(customerId)     // findById zwraca Optional więc jak nie znajdzie to wyrzuci nasz błąd poprzez orElseThrow
                .orElseThrow(() -> new CustomerNotExistsExeption("customer not found: " + customerId))
                .mapToDetails();
    }
}
