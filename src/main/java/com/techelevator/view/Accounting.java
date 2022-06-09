package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Accounting {
    private static double customerMoney;
    private static final double QUARTER = 25;
    private static final double DIME = 10;
    private static final double NICKLE = 5;
    private static final double DOLLAR_CONVERSION = 100;
    private static PrintWriter logger;

    public static void feedMoney(int money) {
        customerMoney += money;
        log("FEED MONEY: ",money,customerMoney);
    }

    public static double getCustomerMoney() {
        return customerMoney;
    }

    public static void purchaseItem(Item item) {
        customerMoney -= item.price;
        log(item.productName + " "+ item.slotLocation, (customerMoney+item.price), customerMoney);
    }

    public static int[] giveChange() {
        int retQuarters = (int) ((customerMoney * DOLLAR_CONVERSION)  / QUARTER);
        int retDimes =  (int) (((customerMoney * DOLLAR_CONVERSION)  % QUARTER) / DIME);
        int retNickle = (int) (((customerMoney * DOLLAR_CONVERSION)  % DIME) / NICKLE);
        int retPennies = (int) ((customerMoney * DOLLAR_CONVERSION)  % NICKLE);
        log("GIVE CHANGE: ",customerMoney,0);
        customerMoney = 0;
        return new int[]{retQuarters, retDimes, retNickle, retPennies};
    }

    public static void log(String action, double moneyMovement, double moneyEnd){
        try {
            if(logger == null){
                File log = new File("Log.txt");
                logger = new PrintWriter(new FileOutputStream(log,true));
            }
            LocalDateTime localDateTime = LocalDateTime.now();

            String accessed = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(localDateTime);
            logger.printf("%s, %-20s $%.2f  $%.2f \n",accessed,action,moneyMovement,moneyEnd);
            logger.flush();
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}
