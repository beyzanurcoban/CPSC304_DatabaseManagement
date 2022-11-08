package ca.ubc.cs304.model;

public class DeliveryTask_PerformRecipient {
    private final String recipientName;
    private final String recipientPhone;

    public DeliveryTask_PerformRecipient(String recipientName, String recipientPhone){
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }
}
