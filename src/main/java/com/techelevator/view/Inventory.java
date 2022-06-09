package com.techelevator.view;

import com.techelevator.VendingMachineCLI;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    private static final int RESTOCK_AMOUNT = 5;
    private static List<Item> inventory = new ArrayList<>();
    public Inventory() {
        File file = new File("vendingmachine.csv");
        try(Scanner read = new Scanner(file)){
        while (read.hasNext()){
            String input = read.nextLine();
            String [] splitItem = input.split("\\|");
            // a3 is going to be type of item
            inventory.add(createItem(splitItem));
        }
    } catch (FileNotFoundException ex){
            System.out.println("File not found.");
        }
    }

    public static void getItem(String slotNumber) {
        Item selected = null;

        for (Item item : inventory) {
            if (item.slotLocation.equals(slotNumber)) {
                selected = item;
            }
        }
        if (selected == null) {
            System.out.println("Invalid Selection.");
        } else {
            if (selected.stock > 0) {
                if(selected.price <= Accounting.getCustomerMoney()) {
                    Accounting.purchaseItem(selected);
                    System.out.printf("Dispensing %s for $%.2f ... \n", selected.productName, selected.price);
                    selected.purchaseConfirmation();
                    System.out.printf("You have $%.2f of remaining balance. \n", Accounting.getCustomerMoney());
                    selected.stock--;
                } else {
                    System.out.println("You do not have enough available balance.");
                }
            } else if (selected.stock == 0) {
                System.out.printf("Product %s %s is out of stock. \n",selected.slotLocation, selected.productName);
            }
        }
    }
    public void printInventory(){
        System.out.printf("%-4s%-8s%-19s%s\n","#","Type","Product","Price");
        System.out.printf("------------------------------------\n");
        for (Item item : inventory) {
            System.out.printf("%-4s%-8s%-20s%.2f\n",item.slotLocation,item.getName(), item.productName,item.price);
        }
    }
    public Item createItem(String [] inputItem){
        for (int i = 0; i < Item.itemTypes.length; i++) {
            switch (inputItem[3]){
                case "Candy":
                   return new Candy(inputItem[0],inputItem[1],Double.parseDouble(inputItem[2]), RESTOCK_AMOUNT);
                case "Chip":
                    return new Chip(inputItem[0],inputItem[1],Double.parseDouble(inputItem[2]), RESTOCK_AMOUNT);
                case "Drink":
                    return new Drink(inputItem[0],inputItem[1],Double.parseDouble(inputItem[2]), RESTOCK_AMOUNT);
                case "Gum":
                    return new Gum(inputItem[0],inputItem[1],Double.parseDouble(inputItem[2]), RESTOCK_AMOUNT);
        }
        } return null;
        }

}
