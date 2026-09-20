package fhtw.retake_carsharing.Perisistence.Entities;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coords {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}