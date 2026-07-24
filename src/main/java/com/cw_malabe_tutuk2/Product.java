package com.cw_malabe_tutuk2;

public class Product {

    private String code;
    private String name;
    private String brand;
    private String type;
    private  String date;
    private double price;
    private int quantity;
    private String image;
    private int  minThreshold;

    public Product(String code, String name, String brand,double price, int quantity,String type,String date,String image) {
        this.setCode(code);
        this.setName(name);
        this.setBrand(brand);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setType(type);
        this.setDate(date);
        this.setImage(image);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity(){return quantity;}

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }
    public int getMinThreshold(){
        return minThreshold;
    }
}
