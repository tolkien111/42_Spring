package pl.sda.customers.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisterPersonForm;
import pl.sda.customers.service.exception.EmailAlreadyExistException;
import pl.sda.customers.service.exception.VatAlreadyExistsException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional // dodajemy aby testy były traktowane jako transakcje i aby wycofać zmiany z bazy danych po wykonaniu testu -> rolled back
class CustomerServiceTest {

    @Autowired
    private  CustomerService service;

    @Autowired
    private CustomerRepository repository;

    // odnośnie Company
    @Test
    void shouldRegisterCompany() {

        //Given
        final var form = new RegisterCompanyForm("Comp S.A", "PL99554499", "fff@wp.pl");

        //When
        final var customerId = service.registerCompany(form);

        //Then
        assertNotNull(customerId);
        assertNotNull(repository.existsById(customerId.getId()));

    }

    @Test
    void shouldNotRegisterCompanyIfEmailExists(){

        //Given
        repository.saveAndFlush(new Company("abc@wp.pl", "test", "PL93554899"));
        final var form = new RegisterCompanyForm("Comp S.A", "PL99554499", "abc@wp.pl");

        //When & Then
        assertThrows(EmailAlreadyExistException.class, () -> service.registerCompany(form));
        //Test przeszedł ponieważ wyjątek został rzucony przy dwóch takich samych mailach
    }

    @Test
    void shouldNotRegisterCompanyIfVatExist(){
        //Given
        repository.saveAndFlush(new Company("abc@wp.pl", "test", "PL93554899"));
        final var form = new RegisterCompanyForm("Comp S.A", "PL93554899", "xyz@wp.pl");

        //When & Then
        assertThrows(VatAlreadyExistsException.class, () -> service.registerCompany(form));
    }

    //odnośnie Person

    @Test
    void shouldRegisterPerson(){
        //Given
        final var person = new RegisterPersonForm("aaa@wp.pl", "Jarosław", "Kaczyński", "50050511258");

        //When
        final var personId = service.registerPerson(person);
        //Then
        assertNotNull(personId);
        assertNotNull((repository.existsById(personId.getId())));
    }


}