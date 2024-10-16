package carrentalsystem.reservationmanagement.service;

import carrentalsystem.reservationmanagement.dto.ReservationRequestDTO;
import carrentalsystem.reservationmanagement.dto.ReservationResponseDTO;
import carrentalsystem.reservationmanagement.model.Reservation;
import carrentalsystem.reservationmanagement.model.enums.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO);

    ReservationResponseDTO getReservationById(Long id);

    ReservationResponseDTO updateReservation(ReservationRequestDTO reservationRequestDTO, Long id);

    void deleteReservation(Long id);

    List<ReservationResponseDTO> getAllReservations();



    List<ReservationResponseDTO> getReservationByVehicleId(long vehicleId);

    List<ReservationResponseDTO> getReservationByCustomerId(long customerId);

    List<ReservationResponseDTO> getReservationByStartDate(LocalDate startDate);

    List<ReservationResponseDTO> getReservationByEndDate(LocalDate endDate);

    List<ReservationResponseDTO> getReservationByTotalPrice(double totalPrice);

    List<ReservationResponseDTO> getReservationByStatus(ReservationStatus status);

    ReservationResponseDTO extendReservation(Long id, LocalDate newEndDate);
}
