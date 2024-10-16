package carrentalsystem.reservationmanagement.dto;

import carrentalsystem.reservationmanagement.model.enums.PaymentStatus;
import carrentalsystem.reservationmanagement.model.enums.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class ReservationRequestDTO {
    private long vehicleId;
    private long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String paymentMethod;
    private PaymentType paymentType;
    private String paymentDescription;
    private String paymentCurrency;
}
