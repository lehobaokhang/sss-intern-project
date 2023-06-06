package com.internproject.shippingservice.enumeration;

public enum ShipStatusEnum {
    SHIPPING("SHIPPING"),
    COMPLETE("COMPLETE");

    private final String status;

    ShipStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
