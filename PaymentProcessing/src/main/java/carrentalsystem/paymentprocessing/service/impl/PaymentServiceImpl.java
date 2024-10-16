package carrentalsystem.paymentprocessing.service.impl;

import carrentalsystem.paymentprocessing.dto.PaymentRequestDTO;
import carrentalsystem.paymentprocessing.dto.PaymentResponseDTO;
import carrentalsystem.paymentprocessing.model.Payment;
import carrentalsystem.paymentprocessing.model.enums.PaymentStatus;
import carrentalsystem.paymentprocessing.repository.PaymentRepository;
import carrentalsystem.paymentprocessing.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    ModelMapper mapper;
    @Override
    public PaymentResponseDTO savePayment(PaymentRequestDTO payment) {
       Payment payment1 = new Payment();
        payment1.setBookingId(payment.getBookingId());
        payment1.setAmount(payment.getAmount());
        payment1.setPaymentDate(LocalDate.now());
        payment1.setPaymentType(payment.getPaymentType());
        payment1.setPaymentDescription(payment.getPaymentDescription());
        payment1.setPaymentCurrency(payment.getPaymentCurrency());
        String paymentReference = LocalDate.now().getDayOfYear()+payment.getPaymentType().toString()+ payment.getPaymentCurrency();
        payment1.setPaymentReference(paymentReference);
        payment1.setPaymentStatus(PaymentStatus.PAID);
        return mapper.map(paymentRepository.save(payment1),PaymentResponseDTO.class);
    }

    @Override
    public PaymentResponseDTO findPaymentById(Long paymentId) {
        if (paymentRepository.findById(paymentId).isEmpty())
            throw new RuntimeException("Payment with id "+paymentId+" not found");
        return mapper.map(paymentRepository.findById(paymentId),PaymentResponseDTO.class);
    }

    @Override
    public List<PaymentResponseDTO> findAllPayments() {
        if (paymentRepository.findAll().isEmpty())
            throw new RuntimeException("No payments found");
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentResponseDTO> paymentResponseDTOS = new ArrayList<>();
        for (Payment payment:payments){
            paymentResponseDTOS.add(mapper.map(payment,PaymentResponseDTO.class));
        }
        return paymentResponseDTOS;
    }

    @Override
    public PaymentResponseDTO cancelPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()->new RuntimeException("Payment with id "+paymentId+" not found"));
        payment.setPaymentStatus(PaymentStatus.CANCELLED);
        return mapper.map(paymentRepository.save(payment),PaymentResponseDTO.class);
    }

    @Override
    public PaymentResponseDTO refundPayment(Long bookId) {
        Payment payment = paymentRepository.findByBookingId(bookId)
                .orElseThrow(()->new RuntimeException("Payment with booking id "+bookId+" not found"));
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        return mapper.map(paymentRepository.save(payment),PaymentResponseDTO.class);
    }

    @Override
    public PaymentResponseDTO updatePayment(PaymentRequestDTO payment,Long paymentId) {
        Payment existingPayment = paymentRepository.findById(paymentId)
                .orElseThrow(()->new RuntimeException("Payment with id "+paymentId+" not found"));
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setPaymentType(payment.getPaymentType());
        existingPayment.setPaymentDescription(payment.getPaymentDescription());
        existingPayment.setPaymentCurrency(payment.getPaymentCurrency());
        return mapper.map(paymentRepository.save(existingPayment),PaymentResponseDTO.class);

    }

    @Override
    public double updatePaymentPrice(Long id, double amount) {
        //find payment by booking id and update the amount
        Payment payment = paymentRepository.findByBookingId(id)
                .orElseThrow(()->new RuntimeException("Payment with booking id "+id+" not found"));
        payment.setAmount(amount);
        return paymentRepository.save(payment).getAmount();
    }

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
}
