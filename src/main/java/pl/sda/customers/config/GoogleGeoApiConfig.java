package pl.sda.customers.config;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //za pomocą tej adnotacji możemy w tej klasie tworzyć beany
public class GoogleGeoApiConfig {

    @Bean
    GeoApiContext geocodingApi(@Value("${google.api.key}") String apiKey){ // w application.properties wczucamy zależności, Spring Expression Language-> "${google.api.key}"

        return new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

    }

}
