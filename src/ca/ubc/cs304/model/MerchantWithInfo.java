package ca.ubc.cs304.model;

import java.sql.Date;

public class MerchantWithInfo {
    private final String merchantID;
    private final String companyName;
    private final java.sql.Date signUpDate;
    private final String merchantType;
    private final String openingHour;
    private final String closingHour;
    private final String emailAddress;
    private final String phoneNumber;

    public MerchantWithInfo(String merchantID, String companyName, java.sql.Date signUpDate, String merchantType,
                            String openingHour, String closingHour, String emailAddress, String phoneNumber){
        this.merchantID = merchantID;
        this.companyName = companyName;
        this.signUpDate = signUpDate;
        this.merchantType = merchantType;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
    public String getMerchantID() {
        return merchantID;
    }
    public String getCompanyName() {
        return companyName;
    }
    public Date getSignUpDate() {
            return signUpDate;
        }

    public String getMerchantType() {
        if (merchantType == null) {
            return "";
        }
        return merchantType;
    }

    public String getOpeningHour() {
        if (openingHour == null) {
            return "";
        }
        return openingHour;
    }

    public String getClosingHour() {
        if (closingHour == null) {
            return "";
        }
        return closingHour;
    }

    public String getEmailAddress() {
        if (emailAddress == null) {
            return "";
        }
        return emailAddress;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) {
            return "";
        }
        return phoneNumber;
    }
}
