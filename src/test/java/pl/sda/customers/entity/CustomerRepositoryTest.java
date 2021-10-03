package pl.sda.customers.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EntityManager em;

    @Test
    void schouldSave() {
        //Given
        final var customer1 = new Person("fd@wp.pl", "Jan", "Nowak", "98020245653");
        final var customer2 = new Company("dd@wp.pl", "Comp S.A.", "PL05546852");

        //When
        repository.saveAllAndFlush(List.of(customer1, customer2)); //List.of() - tworzy listę z obiektami które chcemy do niej wrzucić

        //Then
        assertEquals(2, repository.count());
    }

    @Test
    void shouldFindPersonByLastName() {
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
    void shouldFindPersonByFirstNameAndLastName() {
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
    void shouldFindPersonByEndingEmailName() {
        //Given
        final var customer1 = new Person("fd@gmail.com", "Jerzy", "Nowak", "98020275653");
        final var customer2 = new Person("fd@wp.pl", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Jarosław", "Nowakiewicz", "97045295653");
        final var customer4 = new Company("d@WP.pl", "Jarosław", "97045295653");

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        //When
        final var result = repository.findByEmailIgnoreCaseEndingWith("WP.pl");

        //Then
        assertEquals(List.of(customer2, customer4), result);
    }

    @Test
    void shouldSearchPersonByFirstNameAndLastName() {
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
    void shouldFindCustomersInCity() {
        //Given
        final var customer1 = new Person("fd@gmail.com", "Jerzy", "Nowak", "98020275653");
        final var customer2 = new Person("fd@wp.pl", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Jarosław", "Nowakiewicz", "97045295653");
        final var customer4 = new Company("d@WP.pl", "Jarosław", "97045295653");


        customer1.addAddress(new Address("Marszałkowska", "Wawa", "04-333", "PL"));
        customer2.addAddress(new Address("Jarosława", "Kraków", "80-303", "PL"));
        customer2.addAddress(new Address("Tadeusza", "Wawa", "04-333", "PL"));
        customer3.addAddress(new Address("Antoniego", "Wrocław", "14-603", "PL"));
        customer4.addAddress(new Address("Mariusza", "Kraków", "80-303", "PL"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));


        //When
        final var result = repository.findCustomersInCity("Kraków");

        //Then
        assertTrue(List.of(customer2, customer4).containsAll(result)); // assertTrue ponieważ lista zwracana może zaiwerać elementy w innej kolejności i assertEquals mógłby nie przejść
    }

    @Test
    void shouldFindCompaniesInCountrySortedByName() {
        // given - utwórz kilka firm wraz z adresami i zapisz poprzez repository
        final var customer1 = new Company("adam@wp.pl", "Adam Company", "95528412244");
        final var customer2 = new Company("d@WP.pl", "Jasuś S.A", "97045295653");
        final var customer3 = new Company("klekot@WP.pl", "Bocian Pożyczki", "97045295653");
        final var customer4 = new Company("rolex@wp.pl", "Uhr GmbH", "98771163654");

        customer1.addAddress(new Address("Aleje Jerozolimskie", "Warszawa", "08-200", "PL"));
        customer2.addAddress(new Address("Tadeusza", "Wawa", "04-333", "PL"));
        customer3.addAddress(new Address("Antoniego", "Wrocław", "14-603", "PL"));
        customer4.addAddress(new Address("Gerard", "Hannover", "32885", "DE"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // when - dodaj metodę w repository, która szuka firm w danym kraju np. PL, a rezultaty są posortowane po nazwie firmy
        // np.
        // final var result = repository.findCompaniesInCountry("PL");

        final var result = repository.findCompaniesInCountry("PL");

        // then - sprawdź czy wyniki się zgadzają z założeniami
        assertEquals(List.of(customer1, customer3, customer2), result);
    }

    @Test
    void shouldFindAllAddressesForLastName() {
        // given - utwórz kilka osób wraz z adresami

        final var customer1 = new Person("fd@gmail.com", "Jerzy", "Nowak", "98020275653");
        final var customer2 = new Person("fd@wp.pl", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Jarosław", "Nowakiewicz", "97045295653");
        final var customer4 = new Person("rr@gmail.com", "Jan-Maria", "Kowalski", "88045295653");

        final var address1 = new Address("Tadeusza", "Kraków", "04-333", "PL");
        final var address2 = new Address("Mariusza", "Wrocław", "09-222", "PL");
        final var address3 = new Address("Gerard", "Hannover", "32885", "DE");

        customer1.addAddress(new Address("Aleje Jerozolimskie", "Warszawa", "08-200", "PL"));
        customer2.addAddress(address1);
        customer2.addAddress(address2);
        customer3.addAddress(new Address("Antoniego", "Kraków", "14-603", "PL"));
        customer4.addAddress(address3);

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // when - dodaj metodę w repository, która zwróci wszystkie adresy pod którymi mieszkają osoby o nazwisku: "Kowalski"
        // np.
        // final var result = repository.findAllAddressesForLastName("Kowalski");
        final var result = repository.findAllAddressesForLastName("Kowalski");

        // then - sprawdź czy wyniki się zgadzają z założeniami
        assertTrue(List.of(address1, address2, address3).containsAll(result));
    }

    @Test
    void shouldCountCustomersByCity() {
        // given - utwórz różnych klientów wraz z adresami

        final var customer1 = new Person("fd@gmail.com", "Jerzy", "Nowak", "98020275653");
        final var customer2 = new Person("fd@wp.pl", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Jarosław", "Nowakiewicz", "97045295653");
        final var customer4 = new Person("rr@gmail.com", "Jan-Maria", "Kowalski", "88045295653");

        customer1.addAddress(new Address("Aleje Jerozolimskie", "Warszawa", "08-200", "PL"));
        customer2.addAddress(new Address("Tadeusza", "Kraków", "04-333", "PL"));
        customer2.addAddress(new Address("Mariusza", "Warszawa", "09-222", "PL"));
        customer3.addAddress(new Address("Antoniego", "Kraków", "14-603", "PL"));
        customer4.addAddress(new Address("Gerard", "Kraków", "32885", "PL"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // when - napisz query, które zwróci miast + liczbę klientów w danym mieście np.
        // city     |  number_of_customers
        // Warszawa |  2
        // Kraków   |  3
        // final var result = repository.countCustomersByCity();
        final var result = repository.countCustomerByCity();

        // then - sprawdź czy wyniki się zgadzają z założeniami
        assertEquals(2, result.size());
        final var row1 = result.get(0); //I metoda
        assertArrayEquals(new Object[]{"Kraków", 3L}, row1);
        assertArrayEquals(new Object[]{"Warszawa", 2L}, result.get(1)); //II metoda
    }

    @Test
    void shouldCountCustomersInCountry() { //II Metoda do wyciągania informacji z Query

        // Given
        final var customer1 = new Person("fd@gmail.com", "Jerzy", "Nowak", "98020275653");
        final var customer2 = new Person("fd@wp.pl", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.com", "Jarosław", "Nowakiewicz", "97045295653");
        final var customer4 = new Person("rr@gmail.com", "Jan-Maria", "Kowalski", "88045295653");

        customer1.addAddress(new Address("Aleje Jerozolimskie", "Berlin", "08-200", "DE"));
        customer2.addAddress(new Address("Tadeusza", "Kraków", "04-333", "PL"));
        customer2.addAddress(new Address("Mariusza", "Berlin", "09-222", "DE"));
        customer3.addAddress(new Address("Antoniego", "Kraków", "14-603", "PL"));
        customer4.addAddress(new Address("Gerard", "Kraków", "32885", "PL"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        //When

        final var result = repository.countCustomerByCountryCode();


        //Then
        assertEquals(2, result.size());
        final var row1 = result.get(0);
        assertEquals("DE", row1.getCountryCode());
        assertEquals(2, row1.getCount());
        final var row2 = result.get(1);
        assertEquals("PL", row2.getCountryCode());
        assertEquals(3, row2.getCount());

    }

    @Test   // znajdź firmy gdzie zipCode zaczyna się od 2 takich samych cyfr
            // III Metoda (na wyciągnięcie czegoś) dla poprzedniego Query

    void shouldFindCompaniesWithZipCode() {

        // Given
        final var customer1 = new Company("adam@wp.pl", "Adam Company", "95528412244");
        final var customer2 = new Company("d@WP.pl", "Jasuś S.A", "97045295653");
        final var customer3 = new Company("klekot@WP.pl", "Bocian Pożyczki", "97045295653");
        final var customer4 = new Company("rolex@wp.pl", "Uhr GmbH", "98771163654");

        customer1.addAddress(new Address("Aleje Jerozolimskie", "Warszawa", "08-200", "PL"));
        customer2.addAddress(new Address("Tadeusza", "Wawa", "08-333", "PL"));
        customer3.addAddress(new Address("Antoniego", "Wrocław", "14-603", "PL"));
        customer4.addAddress(new Address("Gerard", "Hannover", "08885", "DE"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        //When
        final var result = repository.findCompaniesWithZipCode("08%");

        //then
        assertTrue(List.of(
                new CompanyZipCodeView("Adam Company", "95528412244", "08-200"),
                new CompanyZipCodeView("Jasuś S.A", "97045295653", "08-333"),
                new CompanyZipCodeView("Uhr GmbH", "98771163654", "08885")
        ).containsAll(result));

    }

    @Test
    void shouldFindPersonView(){
        //Given
        final var customer1 = new Person("akd@gmail.com", "Jerzy", "Nowak", "98020275653");
        final var customer2 = new Person("fd@wp.pl", "Jan", "Kowalski", "97020295653");
        final var customer3 = new Person("d@gmail.pl", "Jarosław", "Nowakiewicz", "97045295653");
        final var customer4 = new Person("akrr@gmail.com", "Jan-Maria", "Kowalski", "88045295653");

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        //When
        final var result = repository.findPersonViewByEmail("%.pl");

        //Then
        assertTrue(List.of(
                new PersonView(customer2.getId(),customer2.getEmail(), customer2.getPesel()),
                new PersonView(customer3.getId(),customer3.getEmail(), customer3.getPesel())
                ).containsAll(result));
    }

    @Test
    void shouldUpdateCountryCodeForCity(){ //zamiana countryCode na inny, sposób optymalny

        // Given
        final var customer1 = new Company("adam@wp.pl", "Adam Company", "95528412244");
        final var customer2 = new Company("d@WP.pl", "Jasuś S.A", "97045295653");
        final var customer3 = new Company("klekot@WP.pl", "Bocian Pożyczki", "97045295653");
        final var customer4 = new Company("rolex@wp.pl", "Uhr GmbH", "98771163654");

        customer1.addAddress(new Address("Aleje Jerozolimskie", "Warszawa", "08-200", "DE"));
        customer2.addAddress(new Address("Tadeusza", "Warszawa", "08-333", "DE"));
        customer3.addAddress(new Address("Antoniego", "Wrocław", "14-603", "PL"));
        customer4.addAddress(new Address("Gerard", "Hannover", "08885", "DE"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        //When

        final int result = repository.updateCountryCodeForCity("Warszawa", "PL");
        em.clear(); // clear cache

        //Then
        assertEquals(2, result);
        assertEquals(0, repository.countCityWithCountryCode("Warszawa", "DE"));

        final var addresses = repository.findByCity("Warszawa");
        assertEquals(2, addresses.size());
        addresses.forEach(address -> assertEquals("PL", address.getCountryCode()));
    }

    @Test
    void shouldDeleteAllAddressesWithZipCode() {
        // Given - przygotowanie danych testowych

        final var customer1 = new Company("adam@wp.pl", "Adam Company", "95528412244");
        final var customer2 = new Company("d@WP.pl", "Jasuś S.A", "97045295653");
        final var customer3 = new Company("klekot@WP.pl", "Bocian Pożyczki", "97045295653");
        final var customer4 = new Company("rolex@wp.pl", "Uhr GmbH", "98771163654");

        customer1.addAddress(new Address("Aleje Jerozolimskie", "Warszawa", "08-200", "DE"));
        customer2.addAddress(new Address("Tadeusza", "Warszawa", "08-333", "DE"));
        customer3.addAddress(new Address("Antoniego", "Wrocław", "14-603", "PL"));
        customer4.addAddress(new Address("Gerard", "Hannover", "08885", "DE"));

        repository.saveAllAndFlush(List.of(customer1, customer2, customer3, customer4));

        // When - usuwanie adresów o danym zipCode

        final int result = repository.deleteAddressesWithZipCode("08%");
        //em.clear();

        // Then - weryfikacja wyników
        assertEquals(3,result);
        assertEquals(1, repository.countAddressesAfterDelete());
    }



}