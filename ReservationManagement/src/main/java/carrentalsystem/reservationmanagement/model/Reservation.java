package carrentalsystem.reservationmanagement.model;


import carrentalsystem.reservationmanagement.model.enums.PaymentStatus;
import carrentalsystem.reservationmanagement.model.enums.PaymentType;
import carrentalsystem.reservationmanagement.model.enums.ReservationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Vehicle is required")
    private long vehicleId;
    @NotNull(message = "Customer is required")
    private long customerId;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    private double totalPrice;//
    private ReservationStatus status;//


}
