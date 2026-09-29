package org.nova.customer.exception;

public class CustomerPhoneAlreadyExistsException extends RuntimeException {

    public CustomerPhoneAlreadyExistsException(String message) {

        super(message);
    }
}
