package ca.ubc.cs304.model;

import java.sql.Time;

public class MerchantInfo {
    private final String companyName;
    private final String email;
    private final String phoneNumber;
    private final String openingHour;
    private final String closingHour;
    private final String type;

    public MerchantInfo(String companyName,  String type, String email, String phoneNumber, String openingHour, String closingHour){
        this.companyName = companyName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public String getType() { return type; }
}
