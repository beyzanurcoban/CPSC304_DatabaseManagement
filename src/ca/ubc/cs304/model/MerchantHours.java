package ca.ubc.cs304.model;

import java.sql.Time;

public class MerchantHours {
    private final String openingHour;
    private final String closingHour;
    private final String openingDuration;

    public MerchantHours(String openingHour, String closingHour, String openingDuration){
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.openingDuration = openingDuration;
    }

    public String getOpeningHour() {
        return openingHour;
    }

    public String getClosingHour() {
        return closingHour;
    }

    public String getOpeningDuration() {
        return openingDuration;
    }
}
