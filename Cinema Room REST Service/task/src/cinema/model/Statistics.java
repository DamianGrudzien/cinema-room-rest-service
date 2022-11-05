package cinema.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Statistics {
    int currentIncome;
    long numberOfAvailableSeats;
    long numberOfPurchasedTickets;
}
