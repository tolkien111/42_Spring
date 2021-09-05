package pl.sda.customers.entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CustomerTest extends EntityTest {

    @Test
    void shouldSaveCompany(){
        //Given
        final var company = new Company("j@wp.pl", "Comp S.A.", "PL920020222");

        //When
        persist(company);

        //Then
        final var readCompany = em.find(Company.class, company.getId());
        assertEquals(company, readCompany);

    }





}