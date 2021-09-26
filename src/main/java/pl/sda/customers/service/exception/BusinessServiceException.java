package pl.sda.customers.service.exception;

public abstract class BusinessServiceException extends RuntimeException {

    public BusinessServiceException(String message) {
        super(message);
    }

    public BusinessServiceException(String message, Throwable cause) { // czasami wyjątek powoduje wyrzucenie innego wyjątku stąd ten konstruktor
        super(message, cause);
    }
}
