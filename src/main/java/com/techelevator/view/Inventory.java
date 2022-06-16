package com.techelevator.view;

import com.techelevator.VendingMachineCLI;
import com.techelevator.swingImplementation.DisplayFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;

public class Inventory {
    //default restock amount
    private static final int RESTOCK_AMOUNT = 5;
    //Tree map of items in the inventory, we use tree map instead of List/Hashmap because
    //tree map is ordered therefore it will print in the correct order, we don't use list
    //So we can associate the item to the slot location.
    private static final Map<String, Item> inventory = new TreeMap<>();
    public final String INVENTORY_PATH = "vendingmachine.csv";

    public Inventory() {
        //generating inventory based on csv file in data folder.
        File file = new File(INVENTORY_PATH);
        try (Scanner read = new Scanner(file)) {
            while (read.hasNext()) {
                String input = read.nextLine();
                String[] splitItem = input.split("\\|");
                //splitting item in file into separate categories//slot/name/type/cost
                inventory.put(splitItem[0], createItem(splitItem));

            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }
    }

    public static Item getInvItem(String s) {
        //getter for the Item object in the Inventory list checks to see if the product name matches and returns that item.
        for (String location : inventory.keySet()) {
           if(Inventory.getInventory().get(location).getProductName().equals(s)){
               return Inventory.getInventory().get(location);
            }
        }
        return null;
    }

    public static void getItem(String slotNumber) {
        //default item is null
        Item selected = null;

        DisplayFrame.vendingPanel.repaint();
        //looping through inventory to check if the selected item from customer is found in the list of items
        for (String s : inventory.keySet()) {
            if (s.equals(slotNumber)){
                selected = Inventory.getInventory().get(s);
            }
        }
        //if item was not found, selected remains null and prompts invalid selection.
        if (selected == null) {
            System.out.println("Invalid Selection.");
        } else {
            //if item was found, verifies stock is available
            if (selected.getStock() > 0) {
                //if item is available checks to see if customer can afford it.
                if (selected.getPrice() <= Accounting.getCustomerMoney()) {
                    //Dispenses item, removes stock, removes funds from balance, prints all to console.
                    Accounting.purchaseItem(selected);
                    System.out.printf("\r\nDispensing %s for $%.2f ... \r\n", selected.getProductName(), selected.getPrice());
                    selected.purchaseConfirmation();
                    System.out.printf("You have $%.2f of remaining balance. \r\n", Accounting.getCustomerMoney());
                    selected.lowerStock();
                } else {
                    //prompt if not enough funds.
                    System.out.println("You do not have enough available balance.");
                }
            } else if (selected.getStock() == 0) {
                //prompt if out of stock and its name/location so customer can verify they input correctly.
                System.out.printf("Product %s %s is out of stock. \n", getSlotLocation(selected.getProductName()), selected.getProductName());
            }
        }
    }

    public void printInventory() {
        //loops through inventory, formats output and displays to user
        System.out.printf("%-4s%-8s%-19s%s\n", "#", "Type", "Product", "Price");
        System.out.printf("------------------------------------\n");
        for (String s : inventory.keySet()) {
            System.out.printf("%-4s%-8s%-20s%.2f\n", s, inventory.get(s).getName(), inventory.get(s).getProductName(), inventory.get(s).getPrice());
        }
    }

    public Item createItem(String[] inputItem) {
        //takes item array that was split from reading the CSV, checks the item type and creates an item, matching that item type.
        //if no item is found, returns null..
        for (int i = 0; i < Item.itemTypes.length; i++) {
            switch (inputItem[3]) {
                case "Candy":
                    return new Candy(inputItem[1], Double.parseDouble(inputItem[2]), RESTOCK_AMOUNT);
                case "Chip":
                    return new Chip(inputItem[1], Double.parseDouble(inputItem[2]), RESTOCK_AMOUNT);
                case "Drink":
                    return new Drink(inputItem[1], Double.parseDouble(inputItem[2]), RESTOCK_AMOUNT);
                case "Gum":
                    return new Gum(inputItem[1], Double.parseDouble(inputItem[2]), RESTOCK_AMOUNT);
            }
        }
        return null;
    }

    public static Map<String,Item> getInventory() {
        return inventory;
    }

    public static String getSlotLocation(String item) {
        //Method to iterate through the inventory map to find the matching Item's product name
        //Returns the key which is the string for the Slot IE A1 B2 etc
        for (String s : inventory.keySet()) {
            if (inventory.get(s).getProductName().equals(item)) {
                return s;
            }
        }
        return null;
    }
}
