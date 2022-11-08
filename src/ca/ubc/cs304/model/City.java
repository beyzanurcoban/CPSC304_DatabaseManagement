package ca.ubc.cs304.model;
/**
 * The intent for this class is to update/store information about a single city
 */
public class City {
    private final String city;
    private final String province;
    private final String longitude;
    private final String latitude;
    private final String country;

    public City(String city, String province, String longitude, String latitude, String country){
        this.city = city;
        this.province = province;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getCountry() {
        return country;
    }
}
