package carrentalsystem.vehiclemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull

    private String password;
    @NotNull
    private String phoneNumber;
    @NotNull
    @Embedded
    private Address address;
    @NotNull
    private String drivingLicenseNumber;
    @NotNull
    private String drivingLicenseCountry;



}