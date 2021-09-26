package pl.sda.customers.service.exception;

import lombok.NonNull;

public class PeselAlreadyExistException extends BusinessServiceException {
    public PeselAlreadyExistException(@NonNull String message) {
        super(message);
    }
}
