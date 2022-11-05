package cinema.repository;

import cinema.exception.TokenNotFoundException;
import cinema.model.ReturnedTicket;
import cinema.model.Seat;
import cinema.model.SeatTokenDTO;
import cinema.model.Token;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Repository
public class CinemaRepository implements InitializingBean {
    @Value("${cinema.last-rows-price:8}")
    private int lastRowsPrice;
    @Value("${cinema.first-rows-price:10}")
    private int firstRowsPrice;
    @Value("${cinema.first-rows:4}")
    private int firstRows;

    Collection<Seat> seats;
    Collection<SeatTokenDTO> tokens;
    int rows = 9;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    int columns = 9;

    public CinemaRepository() {
    }

    public Collection<Seat> getSeats() {
        return seats;
    }

    public boolean contains(Seat seat){
        return seats.contains(seat);
    }

    public boolean remove(Seat seat){
        return seats.remove(seat);
    }

    public void generateSeatsDTO() {
        seats = new ArrayList<>();
        for (int iRow = 1; iRow <= rows; iRow++) {
            for (int iCol = 1; iCol <= columns; iCol++) {
                Seat seat = new Seat(iRow, iCol, 0);
                seats.add(setPrice(seat));
            }
        }
    }

    public void saveToken(SeatTokenDTO seatTokenDTO) {
        tokens.add(seatTokenDTO);
    }

    public ReturnedTicket returnTicket(Token token) {
        Optional<SeatTokenDTO> tokenMatch = tokens.stream()
                                             .filter(t -> t.getToken().compareTo(token.getToken()) == 0)
                                             .findFirst();
        if(tokenMatch.isPresent()){
            Seat ticket = tokenMatch.get()
                                    .getTicket();
            ticket.setAvailable(true);
            return new ReturnedTicket(ticket);
        } else {
            throw new TokenNotFoundException();
        }
    }

    @Override
    public void afterPropertiesSet() {
        generateSeatsDTO();
        tokens = new ArrayList<>();
    }

    private Seat setPrice(Seat seat){
        int price = seat.getRow() <= firstRows ? firstRowsPrice : lastRowsPrice;
        seat.setPrice(price);
        return seat;
    }
}
