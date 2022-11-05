package cinema.service;

import cinema.exception.IncorrectPasswordException;
import cinema.model.*;
import cinema.repository.CinemaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Service
public class CinemaService {
    private static final Logger log = LoggerFactory.getLogger(CinemaService.class);

    CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public CinemaService() {}

    public ResponseEntity<?> purchaseSeat(Seat seatTP) throws NoSuchElementException {

        if (!checkRowsAndColumnsNumber(seatTP)) {
            return new ResponseEntity<>(CinemaError.getOutOfBandError(), HttpStatus.BAD_REQUEST);
        }

        List<Seat> availableSeats = cinemaRepository.getSeats().stream()
                                                    .filter(Seat::isAvailable)
                                                    .toList();

        Optional<Seat> seatOptional = availableSeats.stream()
                                                    .filter(seat -> seat.equals(seatTP))
                                                    .findFirst();
        if (seatOptional.isEmpty()) {
            return new ResponseEntity<>(CinemaError.getAlreadyPurchasedError()
                    , HttpStatus.BAD_REQUEST);
        }
        Seat seatFounded = seatOptional.get();
        seatFounded.setAvailable(false);

        SeatTokenDTO seatTokenDTO = new SeatTokenDTO(UUID.randomUUID(), seatFounded);
        cinemaRepository.saveToken(seatTokenDTO);

        return new ResponseEntity<>(seatTokenDTO,HttpStatus.OK);

    }

    public CinemaDTO getListOfAvailableSeats() {
        List<Seat> seatsAvailable = cinemaRepository.getSeats().stream()
                                                    .filter(Seat::isAvailable)
                                                    .toList();
        return new CinemaDTO(cinemaRepository.getRows()
                , cinemaRepository.getColumns()
                , seatsAvailable);
    }

    public boolean checkRowsAndColumnsNumber(Seat seat) {
        return seat.getRow() <= cinemaRepository.getRows()
                && seat.getRow() > 0
                && seat.getColumn() > 0
                && seat.getColumn() <= cinemaRepository.getColumns();
    }

    public ResponseEntity<?> returnTicket(Token token) {
        ReturnedTicket rt = cinemaRepository.returnTicket(token);
        return new ResponseEntity<>(rt,HttpStatus.OK);
    }

    public Statistics getStatistics() {
        int currentIncome = cinemaRepository.getSeats()
                                     .stream()
                                     .filter(seat -> !seat.isAvailable())
                                     .mapToInt(Seat::getPrice)
                                     .sum();
        long numberOfAvailableSeats = cinemaRepository.getSeats()
                                                      .stream()
                                                      .filter(Seat::isAvailable)
                                                      .count();
        long numberOfPurchasedSeats = cinemaRepository.getSeats().size() - numberOfAvailableSeats;
        return new Statistics(currentIncome, numberOfAvailableSeats, numberOfPurchasedSeats);
    }
}
