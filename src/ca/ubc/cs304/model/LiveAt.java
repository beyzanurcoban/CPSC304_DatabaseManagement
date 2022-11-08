package ca.ubc.cs304.model;

import java.util.List;

public class LiveAt {
    private final String customerID;
    private final String locationID;

    public LiveAt(String customerID, String locationID){
        this.customerID = customerID;
        this.locationID = locationID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getLocationID() {
        return locationID;
    }
}
