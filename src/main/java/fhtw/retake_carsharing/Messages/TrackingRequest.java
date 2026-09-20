package fhtw.retake_carsharing.Messages;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrackingRequest {

    @NotNull
    private Long userId;

}
