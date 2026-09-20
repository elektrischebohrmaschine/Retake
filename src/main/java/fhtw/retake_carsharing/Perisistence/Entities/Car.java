package fhtw.retake_carsharing.Perisistence.Entities;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "list_car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carID;
    @Column(unique = true)
    private String token;
    @Column
    @NotBlank
    private String carName;
    @Column
    @NotBlank
    private String description;
    @Embedded
    @NotNull
    @Valid
    private Coords coordinates;
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    @OneToOne
    private User currentUser;

}
