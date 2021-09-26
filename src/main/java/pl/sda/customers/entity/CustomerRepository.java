package pl.sda.customers.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> { // JpaRepository 2 argumenty obiekt i rodzaj ID po którym szukamy klienta


    List<Person> findByLastName(String lastName); // SELECT * FROM customers c where c.last_name = ?, findBy - jedno ze słów kluczy do tworzenia query

    // przez findBy w nazwie medoda wie co ma robić. DO Mniej skomplikowanych zapytań
    //I metoda po imieniu i nazwisku
    List<Person> findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase(String firstName, String lastName); // nienajlepszy sposób na skomplikowane zapytania

    //Zad. napisz method query, które zwróci wszystkich klientów, którzy mają email kończący się na wp.pl
    List<Customer> findByEmailIgnoreCaseEndingWith(String endingEmailName);

    //II metoda po imieniu i nazwisku
    @Query("FROM Person p WHERE UPPER(p.firstName) LIKE UPPER(?1) AND UPPER (p.lastName) LIKE UPPER(?2)")
    List<Person> searchPeopleByFirstAndLastName(String firstName, String lastName);

    //napisz query które zwraca wszystkich klientów z danego miasta
    //natywny sql poniżej
    //select c.* form customers c
    //inner join addresses a on a.customers_id = c.id
    // where upper(a.city) = upper(?1)
    @Query("FROM Customer c INNER JOIN c.addresses a WHERE UPPER(a.city) = UPPER(:city) ")
    //można używać np. :city zamiast ?1, :city to nazwa parametru z metody
    List<Customer> findCustomersInCity(String city);


    @Query("FROM Company c INNER JOIN c.addresses a WHERE LOWER(a.countryCode) = LOWER(?1) ORDER BY c.name asc")
        //asc i desc - rosnąco i malejąco
    List<Company> findCompaniesInCountry(String countryCode);

    @Query("SELECT p.addresses FROM Person p WHERE UPPER(p.lastName) = UPPER(?1) ")
        //HQL
    List<Address> findAllAddressesForLastName(String lastName);
    // select a.* from addresses a inner join customers c.id = a.customer_id - w SQL


    @Query("SELECT a.city, count (c) FROM Customer c INNER JOIN c.addresses a GROUP BY a.city ORDER BY a.city ASC")
    List<Object[]> countCustomerByCity(); //lista z tabelami obiektów
    // jak wygląda lista z tabelami obiektów
    // Warszawa    |   2    = row[0] ---> Object[0] = Kraków, Object[1] = 3L
    // Kraków      |   3    = row[1] ---> Object[1] = Warszawam Objekt[1] =2L


    //II Metoda (na wyciągnięcie czegoś) dla poprzedniego Query
    //WAŻNE - musimy użyć aliasów w Query np. as countryCode które są w interface jako metody(z przedrostkiem get...) aby hibernate wiedział w jaki sposób ma zaimplementować interface
    //Rozwiązanie typowo ze SPRING DATA
    @Query("SELECT a.countryCode as countryCode, count (c) as count FROM Customer c " +
            "INNER JOIN c.addresses a " +
            "GROUP BY a.city " +
            "ORDER BY a.countryCode ASC")
    List<CustomerCountByCountryCode> countCustomerByCountryCode();




    interface CustomerCountByCountryCode {
        String getCountryCode();
        int getCount();
    }

    //III Metoda (na wyciągnięcie czegoś) dla poprzedniego Query
    @Query("SELECT new pl.sda.customers.entity.CompanyZipCodeView(c.name, c.vat, a.zipCode) " +
            "FROM  Company c INNER JOIN c.addresses a WHERE a.zipCode LIKE ?1")
    List<CompanyZipCodeView> findCompaniesWithZipCode(String zipCode);

    //IV Metoda (na wyciągnięcie czegoś) z poprzedniego query
    @Query("FROM PersonView v WHERE UPPER (v.email) LIKE UPPER(?1)")
    List<PersonView> findPersonViewByEmail(String email);

    // do testu shouldUpdateCountryCodeForCity
    @Modifying // dodajemy ponieważ nawet jak mamy UPDATE w Query to zachowuje się jak Select
    @Query("UPDATE Address SET countryCode = :countryCode WHERE city = :city")
    int updateCountryCodeForCity(String city, String countryCode);

    @Query("SELECT count(a) FROM Address a where a.city = :city and a.countryCode = :countryCode")
    int countCityWithCountryCode(String city, String countryCode);

    @Query ("FROM Address a WHERE a.city = :city")
    List<Address> findByCity(String city);


    @Modifying
    @Query ("DELETE FROM Address a WHERE a.zipCode LIKE :zipCode")
    int deleteAddressesWithZipCode(String zipCode);

    @Query("SELECT count(a) FROM Address a")
    int countAddressesAfterDelete();


}
