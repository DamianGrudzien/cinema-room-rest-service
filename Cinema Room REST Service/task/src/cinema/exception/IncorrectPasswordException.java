package cinema.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("The password is wrong!");
    }
}
