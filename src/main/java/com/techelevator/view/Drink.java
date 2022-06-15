package com.techelevator.view;

public class Drink extends Item {
    public Drink(String productName, Double price, int stock) {
        super(productName, price, stock);
    }
    @Override
    //inherited from super, defines the output for purchase confirmation.
    public void purchaseConfirmation(){
        System.out.println("Glug Glug, Yum!");
    }
    @Override
    //getter for name
    public String getName(){
        return "Drink";
    }
}
