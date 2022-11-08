package ca.ubc.cs304.model;

public class TransshipTask_PerformContact {
    private final String contactName;
    private final String contactPhone;

    public TransshipTask_PerformContact(String contactName, String contactPhone){
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }
}
