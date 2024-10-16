package carrentalsystem.rentalmanagement.service;

import carrentalsystem.rentalmanagement.dto.RentalRequestDTO;
import carrentalsystem.rentalmanagement.dto.RentalResponseDTO;
import carrentalsystem.rentalmanagement.model.Rental;
import carrentalsystem.rentalmanagement.model.enums.PaymentStatus;
import carrentalsystem.rentalmanagement.model.enums.PaymentType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentalService {
    List<RentalResponseDTO> getAllRentals();
    RentalResponseDTO getRentalById(Long id);
    RentalResponseDTO createRental(RentalRequestDTO rental);
    RentalResponseDTO updateRental(RentalRequestDTO rental, Long id);
    String extendRental(Long id, LocalDate newEndDate);
    PaymentStatus payRental(RentalRequestDTO rental, double amount);
    RentalResponseDTO refundRental(Rental id);
    RentalResponseDTO cancelRental(Long id);


}
