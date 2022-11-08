package ca.ubc.cs304.model;

import java.sql.Date;
/**
 * The intent for this class is to update/store information about a single customer
 */
public class Customer {
    private final String customerID;
    private final String name;
    private final String phoneNumber;
    private final String locationId;
    private final java.sql.Date signUpDate;
    private final String note;

    public Customer(String customerID, String name, String phoneNumber, String locationId, java.sql.Date signUpDate,
                    String note){
        this.customerID = customerID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.locationId = locationId;
        this.signUpDate = signUpDate;
        this.note = note;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) {
            return "";
        }
        return phoneNumber;
    }



    public Date getSignUpDate() {
        return signUpDate;
    }

    public String getNote() {
        if (note == null) {
            return "";
        }
        return note;
    }

    public String getLocationId() {
        return locationId;
    }
}
