package pl.sda.customers.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.AddAddressFrom;
import pl.sda.customers.service.dto.CreatedAddress;
import pl.sda.customers.service.exception.CustomerNotExistsExeption;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerAddressesService {

    @NonNull
    private final CustomerRepository repository;

    //zmiana współrzędnych na adres
    @NonNull
    private final ReverseGeocoding reverseGeocoding;

    public CreatedAddress addAddress (AddAddressFrom form) {
        if (!repository.existsById(form.getCustomerId())) {
            throw new CustomerNotExistsExeption("customer not exists: " + form.getCustomerId());
        }
        final var address = reverseGeocoding.reverse(form.getLatitude(), form.getLongitude());
        final var customer = repository.getById(form.getCustomerId());
        customer.addAdress(address);
        repository.save(customer);
        return new CreatedAddress(customer.getId(),
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getZipCode(),
                address.getCountryCode());
    }
}
