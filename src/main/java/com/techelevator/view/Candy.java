package com.techelevator.view;

public class Candy extends Item {
    public Candy(String slotLocation, String productName, Double price, int stock) {
        super(slotLocation, productName, price, stock);
    }

    @Override

    //inherited from super, defines the output for purchase confirmation.
    public void purchaseConfirmation(){
        System.out.println("Munch Munch, Yum!");
    }
    @Override
    //getter for name
    public String getName(){
        return "Candy";
    }
}
