package carrentalsystem.vehiclemanagement.service;

import carrentalsystem.vehiclemanagement.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
     List<Vehicle> getAllVehicle() ;
     Vehicle getVehicleById(Long id) ;
     Vehicle createVehicle(Vehicle vehicle) ;
     Vehicle updateVehicle(Vehicle vehicle, Long id);
     void deleteVehicle(Long id) ;

    List<Vehicle> getAvailableVehicles();

     Vehicle setVehicleBooked(Long id);
}
