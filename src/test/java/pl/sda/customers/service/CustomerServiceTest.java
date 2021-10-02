package pl.sda.customers.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.customers.entity.Company;
import pl.sda.customers.entity.CustomerRepository;
import pl.sda.customers.entity.Person;
import pl.sda.customers.service.dto.RegisterCompanyForm;
import pl.sda.customers.service.dto.RegisterPersonForm;
import pl.sda.customers.service.exception.EmailAlreadyExistException;
import pl.sda.customers.service.exception.PeselAlreadyExistException;
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
        assertThrows(VatAlreadyExistsException.class, () -> service.registerCompany(form)); // tutaj piewszy argument to klasa wyjątku jakiego się spodziewamy a druga to kod który na
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

    @Test
    void shouldNotRegisterPersonIfEmailExist(){
        //Given
        repository.saveAndFlush(new Person("aaa@wp.pl", "Jarosław", "Kaczyński", "50050511258"));
        final var form = new RegisterPersonForm("aaa@wp.pl", "Jarek", "Smoleński", "58850511258");

        //When & Then
        assertThrows(EmailAlreadyExistException.class, () -> service.registerPerson(form));
    }

    @Test
    void shouldNotRegisterPersonIfPeselExist(){
        //Given
        repository.saveAndFlush(new Person("aaa@wp.pl", "Jarosław", "Kaczyński", "50050511258"));
        final var form = new RegisterPersonForm("abc@wp.pl", "Jarek", "Smoleński", "50050511258");

        //When & Then
        assertThrows(PeselAlreadyExistException.class, () -> service.registerPerson(form));
    }


}