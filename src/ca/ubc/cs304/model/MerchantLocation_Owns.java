package ca.ubc.cs304.model;

public class MerchantLocation_Owns {
    private final String locationID;
    private final String type;
    private final String landLineNumber;
    private final String merchantID;

    public MerchantLocation_Owns(String locationID, String type, String landLineNumber, String merchantID){
        this.locationID = locationID;
        this.type = type;
        this.landLineNumber = landLineNumber;
        this.merchantID = merchantID;
    }

    public String getLocationID() {
        return locationID;
    }

    public String getType() {
        return type;
    }

    public String getLandLineNumber() {
        return landLineNumber;
    }

    public String getMerchantID() {
        return merchantID;
    }
}
