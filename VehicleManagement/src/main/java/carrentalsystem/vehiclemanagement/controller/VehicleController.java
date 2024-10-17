package carrentalsystem.vehiclemanagement.controller;

import carrentalsystem.vehiclemanagement.exceptions.VehicleException;
import carrentalsystem.vehiclemanagement.model.Vehicle;
import carrentalsystem.vehiclemanagement.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicle();
        if (vehicles.isEmpty())
            return new ResponseEntity<String>("No Vehicles found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable Long id) {
        try{
            Vehicle vehicle = vehicleService.getVehicleById(id);
            return new ResponseEntity<>(vehicle.getId(), HttpStatus.OK);
        } catch (VehicleException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableVehicles() {
        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
        if (vehicles.isEmpty())
            return new ResponseEntity<String>("No Vehicles found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<List<Vehicle>>(vehicles, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(@RequestBody Vehicle vehicle) {
        try
        {
            Vehicle savedVehicle = vehicleService.createVehicle(vehicle);
            return new ResponseEntity<Vehicle>(savedVehicle, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable Long id) {
        try {
            Vehicle updated = vehicleService.updateVehicle(vehicle, id);
            return new ResponseEntity<Vehicle>(updated, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}/reserved")
    public ResponseEntity<?> setVehicleBooked(@PathVariable Long id) {
        try {
            Vehicle updated = vehicleService.setVehicleBooked(id);
            return new ResponseEntity<>("Vehicle is booked", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {

        try {
            vehicleService.deleteVehicle(id);
            return new ResponseEntity<String>("Vehicle deleted successfully", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }
}
