package pl.sda.customers.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest extends EntityTest {


    @Test
    void shouldSaveAddresses() {
        //Given
        // używamy "var" zamiennie z, w tym przypadku Address
        // zapis równoznaczny z ->
        // final Address adress = new Address();
        final var address = new Address("str", "Wawa", "01-200", "PL");

        //When - save to db
        //KLUCZ: ID, Wartość: Encja
        persist(address);

        //Then
        final var readAddress = em.find(Address.class, address.getId()); // select a.* from addresses
        assertEquals(address, readAddress);

    }
}