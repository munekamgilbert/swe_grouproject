package carrentalsystem.rentalmanagement.model;


import carrentalsystem.rentalmanagement.model.enums.ReservationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String vehicle_id;
    private String customer_id;
    private String startDate;
    private String endDate;
    private String totalPrice;
    private ReservationStatus status;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentDate;
    private String paymentAmount;
    private String paymentReference;
    private String paymentType;
    private String paymentDescription;
    private String paymentId;
    private String paymentCardNumber;
    private String paymentCardExpiry;
    private String paymentCardCvv;
    private String paymentCardHolderName;
    private String paymentCardType;
    private String paymentCardIssuer;
    private String paymentCardCountry;
    private String paymentCardPostalCode;
    private String paymentCardStreet;
    private String paymentCardCity;
    private String paymentCardState;
    private String paymentCardPhone;
    private String paymentCardEmail;
    private String paymentCardIpAddress;
    private String paymentCardFingerprint;
    private String paymentCardBin;
    private String paymentCardLast4;
    private String paymentCardExpMonth;
    private String paymentCardExpYear;
    private String paymentCardCustomer;
    private String paymentCardAccount;

}
