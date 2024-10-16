package carrentalsystem.rentalmanagement.dto;

import carrentalsystem.rentalmanagement.model.enums.PaymentStatus;
import carrentalsystem.rentalmanagement.model.enums.PaymentType;
import carrentalsystem.rentalmanagement.model.enums.RentalStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class RentalResponseDTO {
    private Long id;
    private long reservation_id;
    private PaymentStatus paymentStatus;
    private double amount;
    private LocalDate paymentDate;
    private PaymentType paymentType;
    private String paymentDescription;
    private String paymentCurrency;
//    private String paymentReference;
    private RentalStatus status;
}
