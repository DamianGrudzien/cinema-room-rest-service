package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@Value
public class ReturnedTicket {
    @JsonProperty("returned_ticket")
    Seat seat;
}
