package com.cw_malabe_tutuk2;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class FileHandler {

    public  void itemsData(Inventory inventory){
        final int FIELD_COUNT = 8;
        try(Scanner file_reader = new Scanner(textFileManager())) {
            while (file_reader.hasNextLine()) {
                String line = file_reader.nextLine();
                List<String> Fields = parseLines(FIELD_COUNT ,line);
                if(Fields.size() != FIELD_COUNT){
                    System.out.println("Unexpected field size");
                    continue;
                }

                try{
                    String code = Fields.get(0);

                    String name = Fields.get(1);

                    String brand = Fields.get(2);

                    double price = Double.parseDouble(Fields.get(3));

                    int quantity = Integer.parseInt(Fields.get(4));

                    String type = Fields.get(5).toUpperCase();

                    String date = DateFormat(Fields.get(6));

                    String image = Fields.get(7);

                    Product product = new Product(code,name,brand,price,quantity,type,date,image);
                    inventory.addItems(product);

                }catch (Exception e){
                    System.out.println("can't push data to the List");
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("Inventory_legacy:File Not Found");
        }
    }
}
