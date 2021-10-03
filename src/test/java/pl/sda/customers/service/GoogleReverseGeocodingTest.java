package pl.sda.customers.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RequiredArgsConstructor
class GoogleReverseGeocodingTest {

    @Autowired
    private GoogleReverseGeocoding reverseGeocoding;

    @Test
    void shouldFindAddressForCoordinates() {
        //When
        final var address = reverseGeocoding.reverse(52.242799, 20.979061);

        //Then
        assertNotNull(address);
        assertEquals("Dzielna 21", address.getStreet());
        assertEquals("Warszawa", address.getCity());
        assertEquals("00-162", address.getZipCode());
        assertEquals("PL", address.getCountryCode());
    }

    @Test
    void shouldFindAddressForCoordinatesWithoutStreet() {
        //When
        final var address = reverseGeocoding.reverse(52.069757, 14.923789);

        //Then
        assertNotNull(address);
        assertEquals("1", address.getStreet());
        assertEquals("Maszewo", address.getCity());
        assertEquals("66-614", address.getZipCode());
        assertEquals("PL", address.getCountryCode());
    }
}