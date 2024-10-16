package carrentalsystem.rentalmanagement.repository;


import carrentalsystem.rentalmanagement.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
