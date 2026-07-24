package com.cw_malabe_tutuk2;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

    private File textFileManager(){
        File INVENTORY_FILE = new File("src/main/java/com/cw_malabe_tutuk/data/inventory_legacy.txt");
        File NEW_INVENTORY_FILE = new File("src/main/java/com/cw_malabe_tutuk/data/newinventory.txt");
        if(NEW_INVENTORY_FILE.exists())
            return NEW_INVENTORY_FILE;
        else if (INVENTORY_FILE.exists()) {
            return INVENTORY_FILE;
        }
        System.out.println("Can't Load the text files");
        return null;
    }


    private  List<String> parseLines(int count ,String line){
        if(count == 8){
            if(line == null || line.trim().isEmpty()) {
                return new ArrayList<>();
            }
            String characters = ";|\\||,(?!(?<=\\b[A-Za-z]{3,9} \\d{1,2},)\\s*\\d{4}\\b)";
            String[] rawFiled = line.split(characters,-1);
            List<String> items = new ArrayList<>();
            for(String field : rawFiled){
                items.add(clean(count,field));
            }return items;
        } else if (count == 4) {
            if(line == null || line.trim().isEmpty()) {
                return new ArrayList<>();
            }
            String characters = "[,;|]";
            String[] rawFiled = line.split(characters,-1);
            List<String> items = new ArrayList<>();
            for(String field : rawFiled){
                items.add(clean(count,field));
            }return items;
        }
        return null;
    }

    private  String clean(int count ,String field){
        if(count == 8){
            String trimmed = field.trim();
            trimmed = trimmed.replaceAll("(?i)^Rs\\.?\\s*","");
            return trimmed.isEmpty() ? null : trimmed;
        }
        else if(count == 4){
            String trimmed = field.trim();
            return trimmed.isEmpty() ? null : trimmed;
        }
        return null;
    }


    private  String DateFormat(String date) {
        String[] dateFormats = {"dd/MM/yyyy", "MM/dd/yyyy", "yyyy/MM/dd",
                "dd-MM-yyyy", "MM-dd-yyyy", "yyyy-MM-dd",
                "dd/MMM/yyyy", "MMM/dd/yyyy", "yyyy/MMM/dd",
                "dd-MMM-yyyy", "MMM-dd-yyyy", "yyyy-MMM-dd",
                "dd MMM, yyyy", "MMM dd, yyyy", "yyyy MMM, dd",
                "dd MM yyyy", "MM dd yyyy", "yyyy MM dd"};
        DateTimeFormatter finalFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (String format : dateFormats) {
            try {
                DateTimeFormatter Formats = DateTimeFormatter.ofPattern(format);
                return LocalDate.parse(date.trim(), Formats).format(finalFormat);

            } catch (DateTimeParseException e) {
            }
        }
        return null;
    }
}
