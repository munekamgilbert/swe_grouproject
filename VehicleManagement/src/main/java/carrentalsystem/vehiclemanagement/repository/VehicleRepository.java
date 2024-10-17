package carrentalsystem.vehiclemanagement.repository;


import carrentalsystem.vehiclemanagement.model.Vehicle;
import carrentalsystem.vehiclemanagement.model.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByAvailability(VehicleStatus b);
}
