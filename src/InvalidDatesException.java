public class InvalidDatesException extends RuntimeException{
    public InvalidDatesException(String message) {
        super(message);
    }

    public InvalidDatesException() {
        this("Dates are incorrect");
    }
}
