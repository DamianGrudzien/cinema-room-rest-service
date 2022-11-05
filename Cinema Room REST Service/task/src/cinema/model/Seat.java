package cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Objects;

@Data
@Setter
@NoArgsConstructor
public class Seat {
    int row;
    int column;
    int price;
    @JsonIgnore
    boolean isAvailable = true;

    public Seat(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }


    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        return row == seat.row && column == seat.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
