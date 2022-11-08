package ca.ubc.cs304.model;

public class StorageLocation {
    private final String locationID;
    private final String capacity;

    public StorageLocation(String locationID, String capacity){
        this.locationID = locationID;
        this.capacity = capacity;
    }

    public String getLocationID() {
        return locationID;
    }

    public String getCapacity() {
        return capacity;
    }
}
