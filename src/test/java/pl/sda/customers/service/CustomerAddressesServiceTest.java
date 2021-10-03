package pl.sda.customers.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.customers.entity.Address;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.AddAddressFrom;
import pl.sda.customers.service.dto.CreatedAddress;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class CustomerAddressesServiceTest {

    @Autowired
    private  CustomerAddressesService service;

    @Autowired
    private CustomerRepository repository;

    @MockBean //mockbean to wydmuszka zastępujący inny bean w naszym wypadku, żeby nie używa usług zewnętrznych
    private ReverseGeocoding reverseGeocoding;

    @Test
    void shouldAddAddressToCustomer(){
        //Given
        final var customer = new Company("abc@wp.pl", "test", "PL93554899");
        repository.save(customer);
        final var address = new Address("str", "Warszawa", "81-200", "PL");
        when(reverseGeocoding
                .reverse(anyDouble(), anyDouble())) //dla jakiechkolwiek double zwróć adres
                .thenReturn(address); // otrzymaliśmy adres wydmuszkowy, jakiekolwiek parametry przy reverseGeocoding podamy to zwróci nam adres, uniezależniamy reverseGeocoding od usług zewnętrzych
        final var form = new AddAddressFrom(customer.getId(), 52.242799, 20.979061);


        //When
        final var createdAddress = service.addAddress(form);

        //Then
        assertEquals(new CreatedAddress(customer.getId(),
                address.getId(),
                "str",
                "Warszawa",
                "81-200",
                "PL"), createdAddress);
    }
}