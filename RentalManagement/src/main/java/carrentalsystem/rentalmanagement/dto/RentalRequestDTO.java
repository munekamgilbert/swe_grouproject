package carrentalsystem.rentalmanagement.dto;

import carrentalsystem.rentalmanagement.model.enums.PaymentStatus;
import carrentalsystem.rentalmanagement.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequestDTO {
    private long reservation_id;
    private PaymentType paymentType;
    private String paymentDescription;
    private String paymentCurrency;
}
