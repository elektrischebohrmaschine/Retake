package fhtw.retake_carsharing.perisistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "list_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotNull
    private Long age;
    @NotBlank
    private String username;
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotBlank
    private String creditCardNr;
    @NotBlank
    private String driversLicenceNr;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(unique = true)
    @JsonIgnore
    private String token;

    @JsonIgnore
    public boolean isAdmin() {
        if (userRole == UserRole.ADMIN) {
            return true;
        }
        return false;
    }
}
