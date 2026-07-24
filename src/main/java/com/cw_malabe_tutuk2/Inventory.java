package com.cw_malabe_tutuk2;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;
    private  List<Dealer> dealers;
    private  List<Product> cart;

    public Inventory(){
        this.dealers = new ArrayList<>();
        this.products = new ArrayList<>();
        this.cart = new ArrayList<>();
    }
}
