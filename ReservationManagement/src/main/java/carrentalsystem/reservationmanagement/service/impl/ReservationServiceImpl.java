package carrentalsystem.reservationmanagement.service.impl;


import carrentalsystem.reservationmanagement.dto.ReservationRequestDTO;
import carrentalsystem.reservationmanagement.dto.ReservationResponseDTO;
import carrentalsystem.reservationmanagement.exceptions.VehicleException;
import carrentalsystem.reservationmanagement.model.Reservation;
import carrentalsystem.reservationmanagement.model.enums.ReservationStatus;
import carrentalsystem.reservationmanagement.repository.ReservationRepository;
import carrentalsystem.reservationmanagement.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    int reservationDailyRate = 10; // 10$ per day or can be passed as a parameter
    @Value("${gateway.url}")
    private String gatewayUrl;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    RestTemplate restTemplate;
    @Override
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservation) {
        if(reservation.getStartDate().isAfter(reservation.getEndDate()))
            throw new RuntimeException("Start date cannot be after end date");
        if(reservation.getStartDate().isBefore(LocalDate.now()))
            throw new RuntimeException("Start date cannot be before today");
        String vehicleUrl = gatewayUrl + "vehicles/" + reservation.getVehicleId();
        ResponseEntity<?> responseFromVehicleService;
            try{
                responseFromVehicleService =  restTemplate.getForEntity(vehicleUrl, Long.class);
            } catch (VehicleException e) {
              throw new VehicleException(e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException("Vehicle Service is down or not responding, please try again later"); //finish this when vehicle service is up and running or down
                //throw new VehicleException(e.getMessage());
            }
            if (responseFromVehicleService.getStatusCode().isError())
                throw new RuntimeException("Vehicle with id: " + reservation.getVehicleId() + " not found");
            else {

                String customerUrl = gatewayUrl + "customers/" + reservation.getCustomerId();
                ResponseEntity<?> responseFromCustomerService;
                    try{
                        responseFromCustomerService =  restTemplate.getForEntity(customerUrl, Long.class);
                    }catch (Exception e) {
                        throw new RuntimeException("Customer Service is down or not responding, please try again later");
                    }
                    if (responseFromCustomerService.getStatusCode().isError())
                        throw new RuntimeException("Customer with id: " + reservation.getCustomerId() + " not found");
                    else{
                        Reservation reservationEntity = new Reservation();
                        Reservation savedReservation = setValuesFromDTO(reservation, reservationEntity);
                        restTemplate.put(gatewayUrl + "vehicles/" + reservation.getVehicleId() + "/reserved", String.class);
                        return modelMapper.map(savedReservation, ReservationResponseDTO.class);
                    }

            }


    }

    private Reservation setValuesFromDTO(ReservationRequestDTO reservation, Reservation reservationEntity) {
        reservationEntity.setVehicleId(reservation.getVehicleId());
        reservationEntity.setCustomerId(reservation.getCustomerId());
        reservationEntity.setStartDate(reservation.getStartDate());
        reservationEntity.setEndDate(reservation.getEndDate());
        int dateDifference = reservation.getEndDate().getDayOfMonth() - reservation.getStartDate().getDayOfMonth();
        reservationEntity.setTotalPrice(dateDifference * reservationDailyRate);
        reservationEntity.setStatus(ReservationStatus.BOOKED);
        return reservationRepository.save(reservationEntity);
    }

    @Override
    public ReservationResponseDTO getReservationById(Long id) {
        return reservationRepository.findById(id).map(reservation ->
                modelMapper.map(reservation, ReservationResponseDTO.class))
                .orElseThrow(() -> new RuntimeException("Reservation with id: " + id + " not found"));
    }

    @Override
    public ReservationResponseDTO updateReservation(ReservationRequestDTO reservationRequestDTO, Long id) {
        Reservation reservationOptional = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation with id: " + id + " not found"));
        Reservation updatedReservation = setValuesFromDTO(reservationRequestDTO, reservationOptional);
        return modelMapper.map(updatedReservation, ReservationResponseDTO.class);

    }

    @Override
    public void deleteReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation with id: " + id + " not found");
        }
    }

    @Override
    public List<ReservationResponseDTO> getAllReservations() {
        List<ReservationResponseDTO> reservationResponseDTOList = new ArrayList<>();
        List<Reservation> reservationList = reservationRepository.findAll();
        for (Reservation reservation : reservationList) {
            reservationResponseDTOList.add(modelMapper.map(reservation, ReservationResponseDTO.class));
        }
        return reservationResponseDTOList;
    }


    @Override
    public List<ReservationResponseDTO> getReservationByVehicleId(long vehicleId) {
        List<ReservationResponseDTO> reservationResponseDTOList = new ArrayList<>();
        List<Reservation> reservationList = reservationRepository.findByVehicleId(vehicleId);
        for (Reservation reservation : reservationList) {
            reservationResponseDTOList.add(modelMapper.map(reservation, ReservationResponseDTO.class));
        }
        return reservationResponseDTOList;
    }

    @Override
    public List<ReservationResponseDTO> getReservationByCustomerId(long customerId) {
        List<ReservationResponseDTO> reservationResponseDTOList = new ArrayList<>();
        List<Reservation> reservationList = reservationRepository.findByCustomerId(customerId);
        for (Reservation reservation : reservationList) {
            reservationResponseDTOList.add(modelMapper.map(reservation, ReservationResponseDTO.class));
        }
        return reservationResponseDTOList;
    }

    @Override
    public List<ReservationResponseDTO> getReservationByStartDate(LocalDate startDate) {
        List<ReservationResponseDTO> reservationResponseDTOList = new ArrayList<>();
        List<Reservation> reservationList = reservationRepository.findByStartDate(startDate);
        for (Reservation reservation : reservationList) {
            reservationResponseDTOList.add(modelMapper.map(reservation, ReservationResponseDTO.class));
        }
        return reservationResponseDTOList;
    }

    @Override
    public List<ReservationResponseDTO> getReservationByEndDate(LocalDate endDate) {
        List<ReservationResponseDTO> reservationResponseDTOList = new ArrayList<>();
        List<Reservation> reservationList = reservationRepository.findByEndDate(endDate);
        for (Reservation reservation : reservationList) {
            reservationResponseDTOList.add(modelMapper.map(reservation, ReservationResponseDTO.class));
        }
        return reservationResponseDTOList;
    }

    @Override
    public List<ReservationResponseDTO> getReservationByTotalPrice(double totalPrice) {
        List<ReservationResponseDTO> reservationResponseDTOList = new ArrayList<>();
        List<Reservation> reservationList = reservationRepository.findByTotalPrice(totalPrice);
        for (Reservation reservation : reservationList) {
            reservationResponseDTOList.add(modelMapper.map(reservation, ReservationResponseDTO.class));
        }
        return reservationResponseDTOList;
    }

    @Override
    public List<ReservationResponseDTO> getReservationByStatus(ReservationStatus status) {
        List<ReservationResponseDTO> reservationResponseDTOList = new ArrayList<>();
        List<Reservation> reservationList = reservationRepository.findByStatus(status);
        for (Reservation reservation : reservationList) {
            reservationResponseDTOList.add(modelMapper.map(reservation, ReservationResponseDTO.class));
        }
        return reservationResponseDTOList;
    }

    @Override
    public ReservationResponseDTO extendReservation(Long id, LocalDate newEndDate) {
        Reservation reservationOptional = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation with id: " + id + " not found"));
        reservationOptional.setEndDate(newEndDate);
        int dateDifference = newEndDate.getDayOfMonth() - reservationOptional.getStartDate().getDayOfMonth();
        reservationOptional.setTotalPrice(dateDifference * reservationDailyRate);
        String paymentUrl = gatewayUrl + "payments/" + reservationOptional.getId()+ "/update/price/" + reservationOptional.getTotalPrice();
        return modelMapper.map(reservationOptional, ReservationResponseDTO.class);
    }


}
