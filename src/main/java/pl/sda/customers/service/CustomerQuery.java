package pl.sda.customers.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.CustomerView;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerQuery {

    @NonNull
    private final CustomerRepository repository;

    public List<CustomerView> listCustomer(){ // metoda przetwarzająca naszego customera z repozytorium na obiekt do wysłania w Query
       return repository.findAll().stream()
                .map(customer -> new CustomerView(customer.getId(),
                        customer.getName(), // w Company nie musimy tworzyć tej metody ponieważ mamy tam name i tworzynam z tego getter czyli getName, potrzebne tylko w Person
                        customer.getEmail(),
                        customer.getCustomerType()))
                .collect(Collectors.toList());
    }
}
