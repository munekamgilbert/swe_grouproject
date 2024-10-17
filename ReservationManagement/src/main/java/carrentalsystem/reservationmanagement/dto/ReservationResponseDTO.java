package carrentalsystem.reservationmanagement.dto;

import carrentalsystem.reservationmanagement.model.enums.PaymentStatus;
import carrentalsystem.reservationmanagement.model.enums.PaymentType;
import carrentalsystem.reservationmanagement.model.enums.ReservationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationResponseDTO {
    private long id;
    private long vehicleId;
    private long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private ReservationStatus status;
}
