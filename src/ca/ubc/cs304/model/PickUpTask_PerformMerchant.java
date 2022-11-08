package ca.ubc.cs304.model;

public class PickUpTask_PerformMerchant {
    private final String merchantName;
    private final String merchantPhone;

    public PickUpTask_PerformMerchant(String merchantName, String merchantPhone){
        this.merchantName = merchantName;
        this.merchantPhone = merchantPhone;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }
}
