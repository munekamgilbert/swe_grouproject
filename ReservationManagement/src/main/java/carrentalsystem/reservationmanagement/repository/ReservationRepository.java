package carrentalsystem.reservationmanagement.repository;



import carrentalsystem.reservationmanagement.dto.ReservationResponseDTO;
import carrentalsystem.reservationmanagement.model.Reservation;
import carrentalsystem.reservationmanagement.model.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByVehicleId(long vehicleId);
    List<Reservation> findByCustomerId(long customerId);
    List<Reservation> findByEndDate(LocalDate endDate);
    List<Reservation> findByTotalPrice(double totalPrice);
    List<Reservation> findByStartDate(LocalDate startDate);
    List<Reservation> findByStatus(ReservationStatus status);




}
