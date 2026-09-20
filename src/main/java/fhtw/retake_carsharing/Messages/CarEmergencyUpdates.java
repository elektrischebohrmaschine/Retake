package fhtw.retake_carsharing.Messages;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarEmergencyUpdates {

    @Valid
    @NotNull
    private CarStatusUpdates statusUpdate;
    @NotNull
    private CarPriority priority;
    @NotBlank
    private String description;



}
