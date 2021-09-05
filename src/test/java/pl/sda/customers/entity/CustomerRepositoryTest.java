package pl.sda.customers.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void schouldSave(){
        //Given
        final var customer1 = new Person("fd@wp.pl", "Jan", "Nowak", "98020245653");
        final var customer2 = new Company("dd@wp.pl", "Comp S.A.", "PL05546852");

        //When
        repository.saveAllAndFlush(List.of(customer1, customer2)); //List.of() - tworzy listę z obiektami które chcemy do niej wrzucić

        //Then
        assertEquals(2, repository.count());
    }

    @Test
    void shouldFindPersonByLastName(){
        //Given
        final var customer1 = new Person("fd@wp.pl", "Jan", "Nowak", "98020245653");
        final var customer2 = new Person("fd@gmail.com", "Jan", "Kowalski", "97020295653");
        repository.saveAllAndFlush(List.of(customer1, customer2));

        //When
        final var result = repository.findByLastName("Nowak");

        //Then
        assertEquals(List.of(customer1), result);
    }

    @Test
    void shouldFindPersonByFirstNameAndLastName(){
        //Given
        final var customer1 = new Person("fd@wp.pl", "Jan", "Nowak", "98020245653");
        final var customer2 = new Person("fd@gmail.com", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Janek", "Nowakiewicz", "97005295653");
        repository.saveAllAndFlush(List.of(customer1, customer2, customer3));

        //When
        final var result = repository.findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase("ja", "now");

        //Then
        assertEquals(List.of(customer1, customer3), result);
    }

    @Test
    void shouldFindPersonByEndingEmailName(){
        //Given
        final var customer1 = new Person("fd@gmail.com", "Jerzy", "Nowak", "98020275653");
        final var customer2 = new Person("fd@wp.pl", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Jarosław", "Nowakiewicz", "97045295653");
        final var customer4 = new Company ("d@WP.pl", "Jarosław", "97045295653");

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        //When
        final var result = repository.findByEmailIgnoreCaseEndingWith("WP.pl");

        //Then
        assertEquals(List.of(customer2, customer4), result);
    }

    @Test
    void shouldSearchPersonByFirstNameAndLastName(){
        //Given
        final var customer1 = new Person("fd@wp.pl", "Jan", "Nowak", "98020245653");
        final var customer2 = new Person("fd@gmail.com", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Janek", "Nowakiewicz", "97005295653");
        repository.saveAllAndFlush(List.of(customer1, customer2, customer3));

        //When
        final var result = repository.searchPeopleByFirstAndLastName("ja%", "nowa%"); // dodajemy procenty żeby wiedizał że chodzi nam o fragment imienia i nazwiska

        //Then
        assertEquals(List.of(customer1, customer3), result);
    }

    @Test
    void shouldFindCustomersInCity(){
        //Given
        final var customer1 = new Person("fd@gmail.com", "Jerzy", "Nowak", "98020275653");
        final var customer2 = new Person("fd@wp.pl", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Jarosław", "Nowakiewicz", "97045295653");
        final var customer4 = new Company ("d@WP.pl", "Jarosław", "97045295653");


        customer1.addAdress(new Address("Marszałkowska", "Wawa", "04-333", "PL"));
        customer2.addAdress(new Address("Jarosława", "Kraków", "80-303", "PL"));
        customer2.addAdress(new Address("Tadeusza", "Wawa", "04-333", "PL"));
        customer3.addAdress(new Address("Antoniego", "Wrocław", "14-603", "PL"));
        customer4.addAdress(new Address("Mariusza", "Kraków", "80-303", "PL"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));


        //When
        final var result = repository.findCustomersInCity("Kraków");
        //Then
        assertTrue(List.of(customer2, customer4).containsAll(result)); // assertTrue ponieważ lista zwracana może zaiwerać elementy w innej kolejności i assertEquals mógłby nie przejść
    }

}