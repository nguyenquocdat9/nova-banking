package org.nova.customer.exception;

public class CustomerAccountClosedException extends RuntimeException {
    public CustomerAccountClosedException(String message) {
        super(message);
    }
}
