package carrentalsystem.vehiclemanagement.model;

import carrentalsystem.vehiclemanagement.model.enums.VehicleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String make;            // Make of the vehicle (e.g., "Toyota")
    @NotNull(message = "This field is required")
    private String model;           // Model of the vehicle (e.g., "Camry")
    @NotNull(message = "This field is required")
    private int year;               // Year of manufacture (e.g., 2022)
    @NotNull(message = "This field is required")
    private int mileage;            // Mileage of the vehicle (e.g., 50000)
    @NotNull(message = "This field is required")
    private String vin;             // Vehicle Identification Number (e.g., "1HGCM82633A123456")
    @NotNull(message = "This field is required")
    private String color;           // Color of the vehicle (e.g., "Blue")
    @NotNull(message = "This field is required")
    private String plateNumber;     // License plate number (e.g., "ABC123")
    @NotNull(message = "This field is required")
    private VehicleStatus availability;   // Availability status of the vehicle (e.g., "Rented")
    @NotNull(message = "This field is required")
    private String type;            // Type of vehicle (e.g., "Sedan")
    @NotNull(message = "This field is required")
    @Embedded
    private Location location;        // Location of the vehicle (e.g., "Showroom A")
    @NotNull(message = "This field is required")
    private double rentalRate;           // Price of the vehicle (e.g., 25000.00)
    @NotNull(message = "This field is required")
    private String image;           // URL or path to the image of the vehicle
    @NotNull(message = "This field is required")
    private double lateFee;



}