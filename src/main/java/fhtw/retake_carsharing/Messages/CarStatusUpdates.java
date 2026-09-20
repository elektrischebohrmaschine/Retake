package fhtw.retake_carsharing.Messages;


import fhtw.retake_carsharing.Perisistence.Entities.CarStatus;
import fhtw.retake_carsharing.Perisistence.Entities.Coords;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarStatusUpdates {

    private Long carId;
    private String currentUser;

    @NotNull
    @Valid
    private Coords coordinates;
    @NotNull
    private LocalDateTime time;
    @NotNull
    private CarStatus status;
    @NotNull
    private Double kilometer;
    @NotNull
    private Long duration;

}
