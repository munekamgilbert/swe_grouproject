package carrentalsystem.vehiclemanagement.model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    String street;
    String city;
    String state;
    String zipCode;
    String country;

}
