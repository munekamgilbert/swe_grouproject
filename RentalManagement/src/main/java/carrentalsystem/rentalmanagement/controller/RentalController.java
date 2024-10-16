package carrentalsystem.rentalmanagement.controller;




import carrentalsystem.rentalmanagement.dto.RentalRequestDTO;
import carrentalsystem.rentalmanagement.dto.RentalResponseDTO;
import carrentalsystem.rentalmanagement.exceptions.RentalException;
import carrentalsystem.rentalmanagement.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import carrentalsystem.rentalmanagement.model.Rental;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<?> getAllRentals() {
        try{
            List<RentalResponseDTO> rentals = rentalService.getAllRentals();
            return new ResponseEntity<>(rentals, HttpStatus.OK);
        }catch (RentalException e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get rentals");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRentalById(@PathVariable Long id) {
        try{
            RentalResponseDTO rental = rentalService.getRentalById(id);
            return new ResponseEntity<>(rental, HttpStatus.OK);
        }catch (RentalException e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get rental");
        }
    }

    @PostMapping
    public ResponseEntity<?> createRental(@RequestBody RentalRequestDTO rentalRequestDTO) {
        try{
            RentalResponseDTO createdRental = rentalService.createRental(rentalRequestDTO);
            return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
        }catch (RentalException e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to create rental");
        }
    }

    @PutMapping("/{id}")
    public String updateRental(@PathVariable Long id) {
        return "updateRental";
    }

    @PostMapping("/{id}/extend")
    public ResponseEntity<?>  extendRental(@PathVariable Long id,@RequestBody LocalDate newEndDate) {
        try{
            String result = rentalService.extendRental(id, newEndDate);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (RentalException e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to extend rental");
        }
    }



    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelRental(@PathVariable Long id) {
        try{
            RentalResponseDTO cancelledRental = rentalService.cancelRental(id);
            return new ResponseEntity<>(cancelledRental, HttpStatus.OK);
        }catch (RentalException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/return")
    public String returnRental(@PathVariable Long id) {
        return "returnRental";
    }

}
