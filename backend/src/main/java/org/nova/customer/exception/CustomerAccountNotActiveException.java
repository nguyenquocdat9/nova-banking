package org.nova.customer.exception;

public class CustomerAccountNotActiveException extends RuntimeException {
    public CustomerAccountNotActiveException(String message) {
        super(message);
    }
}
