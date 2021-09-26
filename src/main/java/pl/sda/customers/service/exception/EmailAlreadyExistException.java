package pl.sda.customers.service.exception;

import lombok.NonNull;

public class EmailAlreadyExistException extends BusinessServiceException {

    public EmailAlreadyExistException(@NonNull String message) {
        super(message);
    }
}
