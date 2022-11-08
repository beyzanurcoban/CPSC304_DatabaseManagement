package ca.ubc.cs304.model;

import java.sql.Date;
/**
 * The intent for this class is to update/store information about a single merchant
 */
public class Merchant {
    private final String merchantID;
    private final String companyName;
    private final java.sql.Date signUpDate;

    public Merchant(String merchantID, String companyName, java.sql.Date signUpDate){
        this.merchantID = merchantID;
        this.companyName = companyName;
        this.signUpDate = signUpDate;
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
}

