package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.util.List;


@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CinemaDTO {
    @JsonProperty("total_rows")
    int totalRows;
    int totalColumns;
    List<Seat> availableSeats;
}
