package pl.sda.customers.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.LatLng;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customers.entity.Address;
import pl.sda.customers.service.exception.InvalidCoordinatesException;


import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.google.maps.model.AddressComponentType.*;
import static java.util.Arrays.*;
import static java.util.Arrays.asList;

@Component
@RequiredArgsConstructor
public class GoogleReverseGeocoding implements ReverseGeocoding {

    @NonNull
    private GeoApiContext context;

    @Override
    public Address reverse(double latitude, double longitude) {
        try {
            final var results = GeocodingApi.reverseGeocode(context, new LatLng(latitude, longitude)).await(); //await - czeka na odpowiedź aby stworzyć request, request http do google

            if (results.length == 0) { // jak nie znajdzie niczego to ilość danych równa się zero i sypie wyjątkiem
                throw new InvalidCoordinatesException(String.format("invalid lat: %s long: %s", latitude, longitude));
            }

            final var addressComponents = results[0].addressComponents; // jeżeli coś znajdzie to bierzemy pierwszy wynik z góry dlatego results[0]


            //stworzenie zmiennych na potrzeby uzupełnienia adresu, po przeiterowaniu się po tabeli od googla
            final var streetNumber = findValue(addressComponents, List.of(STREET_NUMBER, PREMISE)); // zastosowanie iterowania po tablicy dla każdej zmiennej
            final var street = findOptionalValue(addressComponents, ROUTE);
            final var city = findValue(addressComponents, LOCALITY);
            final var zipCode = findValue(addressComponents, POSTAL_CODE);
            final var countryCode = findValue(addressComponents, COUNTRY);

            //inne podejście do tematu, bardziej wydajne (ale mniej przejrzyste) ale w naszym przypadku niepotrzebne, ponieważ tablica po której iterujemy jest krótka
//            for (final var component : result.addressComponents) {
//                final var types = asList(component.types); // ctrl + alt + v = wyniesienie powtarzającego się ciągu kodu do zmiennej
//                if (types.contains(STREET_NUMBER)) { // dostosowanie do miejscowości bez ulicy
//                    streetNumber = component.shortName;
//                } else if (types.contains(ROUTE)) {
//                    street = component.shortName;
//                } else if (types.contains(LOCALITY)) {
//                    city = component.shortName;
//                } else if (types.contains(POSTAL_CODE)) {
//                    zipCode = component.shortName;
//                } else if (types.contains(COUNTRY)) {
//                    countryCode = component.shortName;
//                }
//            }

            return new Address(
                    street != null ? street + " " + streetNumber : streetNumber,
                    city,
                    zipCode,
                    countryCode);
        } catch (ApiException | InterruptedException | IOException ex) {
            throw new ReverseGeocodingException("google is failing", ex);
        }
    }

    private String findValue(AddressComponent[] components, AddressComponentType type) {
        for (final var component : components) {
            if (asList(component.types).contains(type)) {
                return component.shortName;
            }
        }
        throw new InvalidCoordinatesException("cannot find address part: " + type);
    }

    private String findValue(AddressComponent[] components, Collection<AddressComponentType> types) {
        for (final var component : components) {
            // list 1 contains any element from list 2
            if (stream(component.types).allMatch(types::contains)) { // długa wersja -> asList(component.types).stream().allMatch(type -> types.contains(type)))
                return component.shortName;
            }
        }
        throw new InvalidCoordinatesException("cannot find address part: " + types);
    }

    private String findOptionalValue(AddressComponent[] components, AddressComponentType type) {
        for (final var component : components) {
            if (asList(component.types).contains(type)) {
                return component.shortName;
            }
        }
        return null;
    }
}
