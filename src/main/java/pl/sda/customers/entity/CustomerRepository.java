package pl.sda.customers.entity;

import org.springframework.data.jpa.repository.JpaRepository;
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
    List<Customer> findCustomersInCity(String city);

}
