package pl.sda.customers.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void shouldAddCustomerToDb(){
        //Given
        final var customer = new Customer("aaa@wp.pl", "Adam", "Terminator", 668115526);

        //When
        entityManager.persist(customer);
        entityManager.flush();
        entityManager.clear();

        //Then
        final var readCustomer = entityManager.find(Customer.class, customer.getId());
        assertEquals(customer, readCustomer);

    }

}