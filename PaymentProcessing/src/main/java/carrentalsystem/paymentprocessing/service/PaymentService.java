package carrentalsystem.paymentprocessing.service;

import carrentalsystem.paymentprocessing.dto.PaymentRequestDTO;
import carrentalsystem.paymentprocessing.dto.PaymentResponseDTO;
import carrentalsystem.paymentprocessing.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    PaymentResponseDTO savePayment(PaymentRequestDTO payment);
    PaymentResponseDTO findPaymentById(Long paymentId);
    List<PaymentResponseDTO> findAllPayments();
    PaymentResponseDTO cancelPayment(Long paymentId);
    PaymentResponseDTO refundPayment(Long paymentId);
    PaymentResponseDTO updatePayment(PaymentRequestDTO payment,Long paymentId);

    double updatePaymentPrice(Long id, double amount);
}
