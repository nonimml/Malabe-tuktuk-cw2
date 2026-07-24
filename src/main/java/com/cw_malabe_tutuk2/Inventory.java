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

    public  void addItems(Product product){
        products.add(product);
    }

    public List<Product> getProduct(){
        return products;
    }

    public void addDealers(Dealer dealer){
        dealers.add(dealer);
    }

    public List<Dealer> getDealers(){
        return dealers;
    }

    public  void addToCart(Product product){
        cart.add(product);
    }

    public List<Product> getCartItems(){
        return cart;
    }

    public List<Product> ViewInventory() {
        List<Product> sortedList = new ArrayList<>(this.products);
        int size = sortedList.size();
        boolean swap = true;
        while(swap){
            swap = false;
            for(int i=0;i<size-1;i++){
                Product current = sortedList.get(i);
                Product next = sortedList.get(i+1);

                int category = current.getType().compareTo(next.getType());
                int Code = current.getCode().compareTo(next.getCode());

                if(category >0 || (category==0 && Code >0)){
                    swap = true;
                    sortedList.set(i,next);
                    sortedList.set(i+1,current);
                }

            }
        }
        return sortedList;
    }
}
