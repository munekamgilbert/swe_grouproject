package carrentalsystem.vehiclemanagement.service.impl;


import carrentalsystem.vehiclemanagement.exceptions.VehicleException;
import carrentalsystem.vehiclemanagement.model.Vehicle;
import carrentalsystem.vehiclemanagement.model.enums.VehicleStatus;
import carrentalsystem.vehiclemanagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrentalsystem.vehiclemanagement.service.VehicleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    private VehicleRepository vehicleRepository;
    @Override
    public List<Vehicle> getAllVehicle() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleException("Vehicle with id: " + id + " not found"));
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        if (vehicle.getId() != null)
            throw new RuntimeException("Vehicle already exists");
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle, Long id) {
        Vehicle existingVehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle with id: " + id + " not found"));
        existingVehicle.setMake(vehicle.getMake());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setYear(vehicle.getYear());
        existingVehicle.setMileage(vehicle.getMileage());
        existingVehicle.setVin(vehicle.getVin());
        existingVehicle.setColor(vehicle.getColor());
        existingVehicle.setPlateNumber(vehicle.getPlateNumber());
        existingVehicle.setAvailability(vehicle.getAvailability());
        existingVehicle.setType(vehicle.getType());
        existingVehicle.setLocation(vehicle.getLocation());
        existingVehicle.setRentalRate(vehicle.getRentalRate());
        existingVehicle.setLateFee(vehicle.getLateFee());
        existingVehicle.setImage(vehicle.getImage());
        return vehicleRepository.save(existingVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle with id: " + id + " not found"));
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findByAvailability(VehicleStatus.AVAILABLE);
    }

    @Override
    public Vehicle setVehicleBooked(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle with id: " + id + " not found"));
        vehicle.setAvailability(VehicleStatus.BOOKED);
        return vehicleRepository.save(vehicle);
    }

    @Autowired
    public void setVehicleRepository(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
}
