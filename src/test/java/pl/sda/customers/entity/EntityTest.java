package pl.sda.customers.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

//tworzenie klasy nadrzędnej dla testów - cel, uniknięcie wielokrotnego pisania tego samego kodu

@SpringBootTest
@Transactional
// w momencie wykonania testu wpis do bazy jest usuwany, aby mieć czystą bazę do kolejnych testów, tylko w testach

public class EntityTest {

    @Autowired
    protected EntityManager em;

    protected void persist(Object entity) {
        //KLUCZ: ID, Wartość: Encja
        em.persist(entity);// nie generuje nam od razu wpisu do bazy danych, dodanie do cache
        em.flush(); // wysłanie cache do db, insert into adresses...
        em.clear(); // czyszczenie cache, chcemy aby em.find nie pobierało obiektu z cache
    }
}
