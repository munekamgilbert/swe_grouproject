package carrentalsystem.paymentprocessing.dto;

import carrentalsystem.paymentprocessing.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private int paymentId;
    private int bookingId;
    private PaymentStatus paymentStatus;
    private double amount;
    private String paymentDate;
    private String paymentType;
    private String paymentDescription;
    private String paymentCurrency;
    private String paymentReference;
}
