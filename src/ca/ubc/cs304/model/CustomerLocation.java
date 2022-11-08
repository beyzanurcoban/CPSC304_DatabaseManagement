package ca.ubc.cs304.model;
/**
 * The intent for this class is to update/store information about a single customer location
 */
public class CustomerLocation {
    private final String locationID;
    private final String buzzCode;
    private final String propertyType;

    public CustomerLocation(String locationID, String buzzCode, String propertyType){
        this.locationID = locationID;
        this.buzzCode = buzzCode;
        this.propertyType = propertyType;
    }

    public String getLocationID() {
        return locationID;
    }

    public String getBuzzCode() {
        return buzzCode;
    }

    public String getPropertyType() {
        return propertyType;
    }
}
