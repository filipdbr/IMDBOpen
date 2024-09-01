package Exceptions;

// A generic exception for service-level errors.
public class ServiceException extends RuntimeException {
    public ServiceException(String message, Exception e) {
        super(message);
    }
}