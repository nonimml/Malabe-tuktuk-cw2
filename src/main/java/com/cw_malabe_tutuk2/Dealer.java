package com.cw_malabe_tutuk2;

public class Dealer {
    private String supplierId;
    private String name;
    private String contactInfo;
    private String location;

    public Dealer(String supplierId, String name, String contactInfo,String location) {
        this.setSupplierId(supplierId);
        this.setName(name);
        this.setContactInfo(contactInfo);
        this.setLocation(location);
    }

    public String getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getLocation() {
        return location;
    }
}
