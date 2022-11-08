package ca.ubc.cs304.model;
/**
 * The intent for this class is to update/store information about a single item
 */
public class Item {
    private final String itemID;
    private final String name;
    private final String description;
    private final float price;
    private final String type;
    private final float weight;
    private final String size;
    private final String orderID;
    private final String merchantID;

    public Item(String itemID, String name, String description, float price, String type, float weight, String size, String orderID, String merchantID){
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.weight = weight;
        this.size = size;
        this.orderID = orderID;
        this.merchantID = merchantID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public float getWeight() {
        return weight;
    }

    public String getSize() {
        return size;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getMerchantID() {
        return merchantID;
    }
}

