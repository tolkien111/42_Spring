package pl.sda.customers.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.LatLng;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sda.customers.entity.Address;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GoogleReverseGeocoding implements ReverseGeocoding {

    @NonNull
    private GeoApiContext context;

    @Override
    public Address reverse(double latitude, double longitude) {
        try {
            final var result = GeocodingApi.reverseGeocode(context, new LatLng(latitude, longitude)).await(); //await - czeka na odpowiedź aby stworzyć request
        } catch (Exception ex){
            throw new ReverseGeocodingException("google is falling: ", ex);
        }
        return null;
    }
}
