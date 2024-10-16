package carrentalsystem.reservationmanagement.controller;


import carrentalsystem.reservationmanagement.dto.ReservationRequestDTO;
import carrentalsystem.reservationmanagement.dto.ReservationResponseDTO;
import carrentalsystem.reservationmanagement.model.enums.ReservationStatus;
import carrentalsystem.reservationmanagement.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import carrentalsystem.reservationmanagement.model.Reservation;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDTO reservationRequestDTO) {
        try{
            ReservationResponseDTO createdReservationRequestDTO = reservationService.createReservation(reservationRequestDTO);
            return new ResponseEntity<>(createdReservationRequestDTO, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to create reservation");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id) {
        try{
            ReservationResponseDTO reservationRequestDTO = reservationService.getReservationById(id);
            return new ResponseEntity<>(reservationRequestDTO.getId(), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservation");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@RequestBody ReservationRequestDTO reservationRequestDTO, @PathVariable Long id) {
        try{
            ReservationResponseDTO updatedReservationResponseDTO = reservationService.updateReservation(reservationRequestDTO, id);
            return new ResponseEntity<>(updatedReservationResponseDTO, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to update reservation");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        try{
            reservationService.deleteReservation(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to delete reservation");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllReservations() {
        try{
            Iterable<ReservationResponseDTO> reservations = reservationService.getAllReservations();
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservations");
        }
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<?> getReservationByVehicleId(@PathVariable Long vehicleId) {
        try{
            Iterable<ReservationResponseDTO> reservations = reservationService.getReservationByVehicleId(vehicleId);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservations");
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getReservationByCustomerId(@PathVariable Long customerId) {
        try{
            Iterable<ReservationResponseDTO> reservations = reservationService.getReservationByCustomerId(customerId);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/startDate/{startDate}")
    public ResponseEntity<?> getReservationByStartDate(@PathVariable LocalDate startDate) {
        try{
            Iterable<ReservationResponseDTO> reservations = reservationService.getReservationByStartDate(startDate);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservations check start date format");
        }
    }

    @GetMapping("/endDate/{endDate}")
    public ResponseEntity<?> getReservationByEndDate(@PathVariable LocalDate endDate) {
        try{
            Iterable<ReservationResponseDTO> reservations = reservationService.getReservationByEndDate(endDate);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservations check end date format");
        }
    }

    @GetMapping("/totalPrice/{totalPrice}")
    public ResponseEntity<?> getReservationByTotalPrice(@PathVariable double totalPrice) {
        try{
            Iterable<ReservationResponseDTO> reservations = reservationService.getReservationByTotalPrice(totalPrice);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservations check total price format");
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getReservationByStatus(@PathVariable ReservationStatus status) {
        try{
            Iterable<ReservationResponseDTO> reservations = reservationService.getReservationByStatus(status);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservations");
        }
    }

    @GetMapping("/{id}/amount")
    public ResponseEntity<?> getReservationAmount(@PathVariable Long id) {
        try{
            ReservationResponseDTO reservationResponseDTO = reservationService.getReservationById(id);
            double amount = reservationResponseDTO.getTotalPrice();
            return new ResponseEntity<>(amount, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservation amount");
        }
    }
    @GetMapping("/{id}/endDate")
    public ResponseEntity<?> getReservationEndDate(@PathVariable Long id) {
        try{
            ReservationResponseDTO reservationResponseDTO = reservationService.getReservationById(id);
            LocalDate endDate = reservationResponseDTO.getEndDate();
            return new ResponseEntity<>(endDate, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to get reservation end date");
        }
    }
    @PutMapping("/{id}/extend")
    public ResponseEntity<?> extendReservation(@PathVariable Long id, @RequestBody LocalDate newEndDate) {
        try{
            ReservationResponseDTO reservationResponseDTO = reservationService.extendReservation(id, newEndDate);
            return new ResponseEntity<>(reservationResponseDTO.getEndDate(), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+" : Failed to extend reservation");
        }
    }
}
