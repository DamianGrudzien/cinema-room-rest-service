package cinema.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Wrong token!");
    }
}
