package com.techelevator.view;

public class Candy extends Item {
    public Candy(String slotLocation, String productName, Double price, int stock) {
        super(slotLocation, productName, price, stock);
    }

    @Override
    public void purchaseConfirmation(){
        System.out.println("Munch Munch, Yum!");
    }
    @Override
    public String getName(){
        return "Candy";
    }
}
