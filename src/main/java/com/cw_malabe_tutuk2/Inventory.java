package com.cw_malabe_tutuk2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void clearProducts(){
        products.clear();
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


    public List<Dealer> RandomDealers(){
        List<Dealer> sortedDealers  = new ArrayList<>();
        Random random = new Random();
        while (sortedDealers.size() < 4 && sortedDealers.size() < this.dealers.size()) {
            int randomIndex = random.nextInt(this.dealers.size());
            Dealer supplier = this.dealers.get(randomIndex);


            boolean isDuplicate = false;
            for (int i = 0; i < sortedDealers.size(); i++) {
                Dealer d = sortedDealers.get(i);
                if (d.getSupplierId().equals(supplier.getSupplierId())) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                sortedDealers.add(supplier);
            }
        }

        for (int j = 0; j < sortedDealers.size() - 1; j++) {
            for (int k = 0; k < sortedDealers.size() - 1 - j; k++) {
                Dealer currentLocation = sortedDealers.get(k);
                Dealer nextLocation = sortedDealers.get(k + 1);


                if (currentLocation.getLocation().compareTo(nextLocation.getLocation()) > 0) {
                    sortedDealers.set(k, nextLocation);
                    sortedDealers.set(k + 1, currentLocation);
                }
            }
        }
        return sortedDealers;
    }
}
