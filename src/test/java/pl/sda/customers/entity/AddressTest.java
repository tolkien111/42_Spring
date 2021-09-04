package pl.sda.customers.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressTest {

    @Autowired
    private EntityManager em;

    @Test
    @Transactional // w momencie wykonania testu wpis do bazy jest usuwany, aby mieć czystą bazę do kolejnych testów, tylko w testach
    void shouldSaveAddresses(){
        //Given
        // używamy "var" zamiennie z, w tym przypadku Address
        // zapis równoznaczny z ->
        // final Address adress = new Address();
        final var address = new Address("str", "Wawa", "01-200", "PL");

        //When - save to db
        //KLUCZ: ID, Wartość: Encja
        em.persist(address); // nie generuje nam od razu wpisu do bazy danych, dodanie do cache
        em.flush(); // wysłanie cache do db, insert into adresses...
        em.clear(); // czyszczenie cache, chcemy aby em.find nie pobierało obiektu z cache

        //Then
        final var readAddress = em.find(Address.class, address.getId()); // select a.* from adresses
        assertEquals(address, readAddress);

    }
}