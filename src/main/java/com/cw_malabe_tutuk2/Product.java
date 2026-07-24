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


    public Product(String code,String name,double price,int quantity,String type){
        this.setCode(code);
        this.setName(name);
        this.setPrice(price);
        this.setQuantity(quantity);
        this.setType(type);
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


    public void setCode(String code){
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMinThreshold(int value){
        if(value > 0){
            this.minThreshold = value;
        }else{
            this.minThreshold = 0;
        }
    }
}
