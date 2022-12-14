package cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SeatTokenDTO {
    UUID token;
    Seat ticket;
}
