package carrentalsystem.paymentprocessing.dto;

import carrentalsystem.paymentprocessing.model.enums.PaymentStatus;
import carrentalsystem.paymentprocessing.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {

    private long bookingId;
    private double amount;
    private PaymentType paymentType;
    private String paymentDescription;
    private String paymentCurrency;

}
