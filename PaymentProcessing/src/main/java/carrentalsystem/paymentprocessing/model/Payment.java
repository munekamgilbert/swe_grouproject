package carrentalsystem.paymentprocessing.model;


import carrentalsystem.paymentprocessing.model.enums.PaymentStatus;
import carrentalsystem.paymentprocessing.model.enums.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int paymentId;

        private long bookingId;
        private PaymentStatus paymentStatus;
        private double amount;
        private LocalDate paymentDate;
        private PaymentType paymentType;
        private String paymentDescription;
        private String paymentCurrency;
        private String paymentReference;
}
