package ca.ubc.cs304.model;

import java.sql.Timestamp;
/**
 * The intent for this class is to update/store information about a single order
 */
public class Order {
    private final String orderID;
    private final String status;
    private final float orderPrice;
    private final java.sql.Timestamp deliveryDate;
    private final java.sql.Timestamp pickUpDate;
    private final java.sql.Timestamp creationTime;
    private final java.sql.Timestamp completionTime;
    private final String paymentMethod;
    private final String pickUpAddress;
    private final String deliveryAddress;
    private final String customerID;
    private final String merchantID;

    public Order(String orderID, String status, float orderPrice, java.sql.Timestamp deliveryDate, java.sql.Timestamp pickUpDate, java.sql.Timestamp creationTime,
                 java.sql.Timestamp completionTime, String paymentMethod, String pickUpAddress, String deliveryAddress, String customerID, String merchantID){
        this.orderID = orderID;
        this.status = status;
        this.orderPrice = orderPrice;
        this.deliveryDate = deliveryDate;
        this.pickUpDate = pickUpDate;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.paymentMethod = paymentMethod;
        this.pickUpAddress = pickUpAddress;
        this.deliveryAddress = deliveryAddress;
        this.customerID = customerID;
        this.merchantID = merchantID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getStatus() {
        if (status == null) {
            return "";
        }
        return status;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public Timestamp getPickUpDate() {
        return pickUpDate;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public Timestamp getCompletionTime() {
        return completionTime;
    }

    public String getPaymentMethod() {
        if (paymentMethod == null) {
            return "";
        }
        return paymentMethod;
    }

    public String getPickUpAddress() {
        if (pickUpAddress == null) {
            return "";
        }
        return pickUpAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getCustomerID() {
        if (customerID == null) {
            return "";
        }
        return customerID;
    }

    public String getMerchantID() {
        if (merchantID == null) {
            return "";
        }
        return merchantID;
    }
}
