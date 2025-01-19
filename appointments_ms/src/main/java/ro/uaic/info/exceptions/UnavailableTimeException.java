package ro.uaic.info.exceptions;

public class UnavailableTimeException extends RuntimeException {
    public UnavailableTimeException() {
        super("The chose time is not available. Please choose another time.");
    }
}

