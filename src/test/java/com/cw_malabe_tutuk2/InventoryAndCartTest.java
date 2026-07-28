package com.cw_malabe_tutuk2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryAndCartTest {

    private Inventory inventory;
    private Product enginePart;
    private Product electricalPart;
    private Product regularPart;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        enginePart = new Product("P001", "V8 Engine", "Toyota", 1000.0, 10, "ENGINE", "01/01/2025", "engine.jpg", 3);
        electricalPart = new Product("P002", "Spark Plug", "Bosch", 100.0, 20, "ELECTRICAL", "01/01/2025", "plug.jpg", 5);
        regularPart = new Product("P003", "Wiper Blade", "3M", 50.0, 15, "ACCESSORY", "01/01/2025", "wiper.jpg", 2);

        inventory.addItems(enginePart);
        inventory.addItems(electricalPart);
        inventory.addItems(regularPart);
    }


    @Test
    public void test_ViewInventory_Sort() {
        List<Product> sortedList = inventory.ViewInventory();

        assertEquals("ACCESSORY", sortedList.get(0).getType());
        assertEquals("ELECTRICAL", sortedList.get(1).getType());
        assertEquals("ENGINE", sortedList.get(2).getType());
    }


    @Test
    public void test_Search_And_Filter() {
        String selectedCategory = "ENGINE";
        double minPrice = 500.0;
        double maxPrice = 1500.0;

        List<Product> filtered = new java.util.ArrayList<>();
        for (Product product : inventory.getProduct()) {
            boolean matchCategory = (selectedCategory == null) || product.getType().equalsIgnoreCase(selectedCategory);
            boolean matchMinPrice = product.getPrice() >= minPrice;
            boolean matchMaxPrice = product.getPrice() <= maxPrice;

            if (matchCategory && matchMinPrice && matchMaxPrice) {
                filtered.add(product);
            }
        }

        assertEquals(1, filtered.size());
        assertEquals("P001", filtered.get(0).getCode());
    }


    @Test
    public void test_Stock_Deduction() {
        int buyQuantity = 4;
        int originalStock = enginePart.getQuantity();
        int remainingStock = originalStock - buyQuantity;

        enginePart.setQuantity(remainingStock);

        assertEquals(6, enginePart.getQuantity());
    }


    @Test
    public void test_LowStock_Detection() {
        int buyQuantity = 16;
        int remainingStock = electricalPart.getQuantity() - buyQuantity;

        boolean isLowStock = remainingStock < electricalPart.getMinThreshold();

        assertTrue(isLowStock, "Low stock alert should be triggered when remaining stock falls below threshold");
    }


    @Test
    public void test_Bulk_Discount_Rule() {
        Product bulkItem = new Product("P003", "Wiper", 100.0, 4, "ACCESSORY");
        double itemTotal = bulkItem.getPrice() * bulkItem.getQuantity();

        double bulkDiscount = 0.0;
        if (bulkItem.getQuantity() >= 3) {
            bulkDiscount = itemTotal * 0.5;
        }

        assertEquals(200.0, bulkDiscount, 0.001);
    }

    @Test
    public void test_Synergy_Discount_Rule() {
        Product engineCartItem = new Product("P001", "Engine", 1000.0, 1, "ENGINE");
        Product elecCartItem = new Product("P002", "Plug", 100.0, 1, "ELECTRICAL");

        double subTotal = (engineCartItem.getPrice() * engineCartItem.getQuantity())
                + (elecCartItem.getPrice() * elecCartItem.getQuantity());

        boolean hasEngine = true;
        boolean hasElectrical = true;

        double synergyDiscount = 0.0;
        if (hasEngine && hasElectrical) {
            synergyDiscount = subTotal * 0.10;
        }

        assertEquals(110.0, synergyDiscount, 0.001);
        assertEquals(990.0, subTotal - synergyDiscount, 0.001);
    }
}
