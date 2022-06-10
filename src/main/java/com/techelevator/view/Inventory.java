package com.techelevator.view;

import com.techelevator.VendingMachineCLI;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {
    //default restock amount
    private static final int RESTOCK_AMOUNT = 5;
    //list of inventory items in the machine.
    private static final List<Item> inventory = new ArrayList<>();
    public Inventory() {
        //generating inventory based on csv file in memory.
        File file = new File("vendingmachine.csv");
        try(Scanner read = new Scanner(file)){
        while (read.hasNext()){
            String input = read.nextLine();
            String [] splitItem = input.split("\\|");
            //splitting item in file into separate categories//slot/name/type/cost
            inventory.add(createItem(splitItem));
        }
    } catch (FileNotFoundException ex){
            System.out.println("File not found.");
        }
    }
    public static Item getInvItem(String s){
        for (Item item : inventory) {
            if (s.equals(item.productName)) {
                return item;
            }
        }
        return null;
    }

    public static void getItem(String slotNumber) {
        //default item is null
        Item selected = null;
        //looping through inventory to check if the selected item from customer is found in the list of items
        for (Item item : inventory) {
            if (item.slotLocation.equals(slotNumber)) {
                selected = item;
            }
        }
        //if item was not found, selected remains null and prompts invalid selection.
        if (selected == null) {
            System.out.println("Invalid Selection.");
        } else {
            //if item was found, verifies stock is available
            if (selected.stock > 0) {
                //if item is available checks to see if customer can afford it.
                if(selected.price <= Accounting.getCustomerMoney()) {
                    //Dispenses item, removes stock, removes funds from balance, prints all to console.
                    Accounting.purchaseItem(selected);
                    System.out.printf("Dispensing %s for $%.2f ... \r\n", selected.productName, selected.price);
                    selected.purchaseConfirmation();
                    System.out.printf("You have $%.2f of remaining balance. \r\n", Accounting.getCustomerMoney());
                    selected.stock--;
                } else {
                    //prompt if not enough funds.
                    System.out.println("You do not have enough available balance.");
                }
            } else if (selected.stock == 0) {
                //prompt if out of stock and its name/location so customer can verify they input correctly.
                System.out.printf("Product %s %s is out of stock. \n",selected.slotLocation, selected.productName);
            }
        }
    }
    public void printInventory(){
        //loops through inventory, formats output and displays to user
        System.out.printf("%-4s%-8s%-19s%s\n","#","Type","Product","Price");
        System.out.printf("------------------------------------\n");
        for (Item item : inventory) {
            System.out.printf("%-4s%-8s%-20s%.2f\n",item.slotLocation,item.getName(), item.productName,item.price);
        }
    }
    public Item createItem(String [] inputItem){
        //takes item array that was split from reading the CSV, checks the item type and creates an item, matching that item type.
        //if no item is found, returns null..
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
    public static List<Item> getInventory(){
        return inventory;
    }
}
