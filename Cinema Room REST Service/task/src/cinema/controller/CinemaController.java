package cinema.controller;

import cinema.exception.IncorrectPasswordException;
import cinema.exception.TokenNotFoundException;
import cinema.model.*;
import cinema.service.CinemaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class CinemaController {
    CinemaService cinemaService;
    private static final String SECRET = "super_secret";

    @GetMapping("/seats")
    CinemaDTO seats(){
        return cinemaService.getListOfAvailableSeats();
    }

    @PostMapping("/purchase")
    ResponseEntity<?> purchase(@RequestBody Seat seat){
        return cinemaService.purchaseSeat(seat);
    }

    @PostMapping("/return")
    ResponseEntity<?> returnTicket(@RequestBody Token token){
        return cinemaService.returnTicket(token);
    }

    @PostMapping("/stats")
    Statistics getStats(@RequestParam Optional<String> password){
        password.filter(SECRET::equals)
                .orElseThrow(IncorrectPasswordException::new);

        return cinemaService.getStatistics();
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ErrorDTO exceptionPasswords(IncorrectPasswordException ex){
            return new ErrorDTO(ex.getMessage());
    }


    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDTO exceptionHandler(TokenNotFoundException exception){
        return new ErrorDTO(exception.getMessage());
    }
}
