package pl.sda.customers.service;

import pl.sda.customers.entity.Address;

interface ReverseGeocoding {

    class ReverseGeocodingException extends RuntimeException{  // tworzymy wyjątek na wypadek niedostępności serwera


        public ReverseGeocodingException(String message) {
            super(message);
        }

        public ReverseGeocodingException(String message, Throwable cause) {
            super(message, cause);
        }


    }



    Address reverse (double latitude, double longitude);
}
