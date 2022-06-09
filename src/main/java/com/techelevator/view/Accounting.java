package com.techelevator.view;

public class Accounting {
    private static double customerMoney;

    public static void feedMoney(int money){
        customerMoney += money;
    }

    public  static double getCustomerMoney() {
        return customerMoney;
    }

    public static void purchaseItem(Double price){
        customerMoney-=price;
    }
    public void giveChange(){}

}
