package cinema.model;



public record CinemaError(String error) {
    public static CinemaError getOutOfBandError() {
        return new CinemaError("The number of a row or a column is out of bounds!");
    }

    public static CinemaError getAlreadyPurchasedError() {
        return new CinemaError("The ticket has been already purchased!");
    }

    public static CinemaError getInternalError(String s) {
        return new CinemaError(s);
    }

}
