package carrentalsystem.rentalmanagement.service.implementation;


import carrentalsystem.rentalmanagement.dto.RentalRequestDTO;
import carrentalsystem.rentalmanagement.dto.RentalResponseDTO;
import carrentalsystem.rentalmanagement.exceptions.PaymentException;
import carrentalsystem.rentalmanagement.exceptions.RentalException;
import carrentalsystem.rentalmanagement.model.Rental;
import carrentalsystem.rentalmanagement.model.enums.PaymentStatus;
import carrentalsystem.rentalmanagement.model.enums.RentalStatus;
import carrentalsystem.rentalmanagement.repository.RentalRepository;
import carrentalsystem.rentalmanagement.service.RentalService;
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
public class RentalServiceImpl implements RentalService {

    @Autowired
    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }
    private final RentalRepository rentalRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${gateway.url}")
    private String apiGatewayUri;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<RentalResponseDTO> getAllRentals() {
        if (rentalRepository.findAll().isEmpty())
             throw new RentalException("No rentals found");
        List<RentalResponseDTO> rentalResponseDTOList = new ArrayList<>();
        List<Rental> rentals = rentalRepository.findAll();
        for (Rental rent : rentals){
            rentalResponseDTOList.add(modelMapper.map(rent, RentalResponseDTO.class));
        }
        return rentalResponseDTOList;
    }

    @Override
    public RentalResponseDTO getRentalById(Long id) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isEmpty())
            throw new RentalException("Rental not found");
        return modelMapper.map(rentalOptional.get(), RentalResponseDTO.class);
    }

    @Override
    public RentalResponseDTO createRental(RentalRequestDTO rental) {
        String reservationUri = apiGatewayUri + "/reservations/" + rental.getReservation_id();
        double amount = 0.0;

        try {
            restTemplate.getForEntity(reservationUri, Long.class);
            Double amountFromReservationService = restTemplate.getForObject(reservationUri + "/amount", Double.class);
            if (amountFromReservationService != null)
                amount = amountFromReservationService;
            else
                throw new RuntimeException("Reservation amount not found");
        } catch (Exception e) {
            throw new RuntimeException("Reservation not found");
        }
        Rental newRental = new Rental();
        newRental.setReservation_id(rental.getReservation_id());
        newRental.setAmount(amount);
        PaymentStatus paymentStatus =  payRental(rental,amount); // Payment occurs here
        newRental.setPaymentStatus(paymentStatus);
        newRental.setPaymentDate(LocalDate.now());
        newRental.setPaymentType(rental.getPaymentType());
        newRental.setPaymentDescription(rental.getPaymentDescription());
        newRental.setPaymentCurrency(rental.getPaymentCurrency());
        newRental.setStatus(RentalStatus.RENTED);
        return modelMapper.map(rentalRepository.save(newRental), RentalResponseDTO.class);
    }

    @Override
    public RentalResponseDTO updateRental(RentalRequestDTO rental, Long id) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isEmpty())
            throw new RuntimeException("Rental not found");
        Rental existingRental = rentalOptional.get();
        // when updating a rental, we can only update the payment status
        // when we update something here it should reflect on payment service
        // so we need to send a message to payment service
        // we use rest template to send a message to payment service

        return null; //for now
    }



    @Override
    public String extendRental(Long id, LocalDate newEndDate) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isEmpty())
            throw new RuntimeException("Rental not found");
        Rental rental = rentalOptional.get();
        // we can only extend a rental if it is not cancelled or returned
        if (rental.getStatus() == RentalStatus.CANCELLED || rental.getStatus() == RentalStatus.RETURNED)
            throw new RuntimeException("Rental is cancelled or returned");
        if (rental.getPaymentStatus() != PaymentStatus.PAID)
            throw new RuntimeException("Rental is not paid");
        if (rental.getPaymentStatus() == PaymentStatus.REFUNDED)
            throw new RuntimeException("Rental is refunded");
        if (rental.getPaymentStatus() == PaymentStatus.CANCELLED)
            throw new RuntimeException("Rental is cancelled");
        if(newEndDate.isBefore(LocalDate.now()))
            throw new RuntimeException("New end date is before today");
        String reservationUri = apiGatewayUri + "/reservations/" + rental.getReservation_id();
        ResponseEntity<?>response;
        response = restTemplate.getForEntity(reservationUri+"/endDate", LocalDate.class);
        LocalDate reservationEndDate = (LocalDate) response.getBody();
        if (newEndDate.isBefore(reservationEndDate))
            throw new RuntimeException("New end date cant be before your reservation end date");
        restTemplate.put(reservationUri+"/extend",newEndDate,LocalDate.class);
        return "Rental extended successfully";
    }

    @Override
    public PaymentStatus payRental(RentalRequestDTO rental, double amount) {
        String paymentUri = apiGatewayUri + "/payments/"+rental.getReservation_id()+"/"+amount+"/"+rental.getPaymentType()+"/"+rental.getPaymentDescription()+"/"+rental.getPaymentCurrency();
        ResponseEntity<?> response;
        try{
            response = restTemplate.postForEntity(paymentUri,null,PaymentStatus.class,rental.getReservation_id()
                    ,amount,rental.getPaymentType(),rental.getPaymentDescription(),rental.getPaymentCurrency());
            return (PaymentStatus) response.getBody();
        }catch (PaymentException e){
            throw new RuntimeException("Payment failed");
        }
    }

    @Override
    public RentalResponseDTO refundRental(Rental rental) {
        // we can only refund a rental if it is not cancelled or returned
        if (rental.getStatus() == RentalStatus.CANCELLED || rental.getStatus() == RentalStatus.RETURNED)
            throw new RuntimeException("Rental is cancelled or returned");

        // we can only refund a rental if the payment status is paid
        if (rental.getPaymentStatus() != PaymentStatus.PAID)
            throw new RuntimeException("Rental is not paid");
        // we can only refund a rental if the payment status is not refunded
        if (rental.getStatus() == RentalStatus.REFUNDED)
            throw new RuntimeException("Rental already refunded");
        // we can only refund a rental if the payment status is not cancelled

        restTemplate.postForEntity(apiGatewayUri+"/payments/"+rental.getReservation_id()+"/cancel",null,String.class,rental.getReservation_id());
        rental.setStatus(RentalStatus.REFUNDED);
        return modelMapper.map(rentalRepository.save(rental), RentalResponseDTO.class);
    }

    @Override
    public RentalResponseDTO cancelRental(Long id) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isEmpty())
            throw new RuntimeException("Rental not found");
        Rental rental = rentalOptional.get();
        return refundRental(rental);
    }

}
