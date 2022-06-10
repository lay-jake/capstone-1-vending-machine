package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Accounting {
    //Defining constants for change and initializing logger so it won't be closed/reopened every time we need to write to it.
    private static double customerMoney;
    private static final double QUARTER = 25;
    private static final double DIME = 10;
    private static final double NICKLE = 5;
    private static final double DOLLAR_CONVERSION = 100;
    private static PrintWriter logger;
    private static PrintWriter salesLogger;
    private static Map<String, Integer> logRecord = new HashMap<>();
    private static boolean isFirstPurchase = true;
    private static boolean isFirstCustomer = true;

    public static void setIsFirstPurchase(boolean isFirstPurchase) {
        Accounting.isFirstPurchase = isFirstPurchase;
    }

    public static void setIsFirstCustomer(boolean isFirstCustomer) {
        Accounting.isFirstCustomer = isFirstCustomer;
    }

    public static void feedMoney(int money) {
        //adds money to balance, prints to log money that was added and total balance after.
        customerMoney += money;
        log("FEED MONEY: ", money, customerMoney);
    }

    public static double getCustomerMoney() {
        return customerMoney;
    }

    public static void purchaseItem(Item item) {
        //subtracts money from balance, records the before and after balance to the log, as well as what item and slot was purchased
        customerMoney -= item.price;
        saleslog(item);
        log(item.productName + " " + item.slotLocation, (customerMoney + item.price), customerMoney);
    }

    public static int[] giveChange() {
        //Beginning with quarters, going to pennies, finds the amount of each currency required.
        //Converted money to ints so that double trailing decimals do not interfere with the math.
        int retQuarters = (int) ((customerMoney * DOLLAR_CONVERSION) / QUARTER);
        int retDimes = (int) (((customerMoney * DOLLAR_CONVERSION) % QUARTER) / DIME);
        int retNickle = (int) (((customerMoney * DOLLAR_CONVERSION) % DIME) / NICKLE);
        int retPennies = (int) ((customerMoney * DOLLAR_CONVERSION) % NICKLE);
        //records the change to the log and sets new balance to 0;
        log("GIVE CHANGE: ", customerMoney, 0);
        customerMoney = 0;
        writeReport();
        return new int[]{retQuarters, retDimes, retNickle, retPennies};
    }

    public static void log(String action, double moneyMovement, double moneyEnd) {
        //if logger has been initialized, bypass otherwise intialize the logger
        try {
            if (logger == null) {
                File log = new File("Log.txt");
                logger = new PrintWriter(new FileOutputStream(log, true));
            }
            //setting local date and time
            LocalDateTime localDateTime = LocalDateTime.now();

            //setting string of local time to write to log.
            String accessed = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(localDateTime);
            //formatted input for log.
            logger.printf("%s, %-20s $%.2f  $%.2f \n", accessed, action, moneyMovement, moneyEnd);
            //flushing remaining/stray info in log.
            logger.flush();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saleslog(Item item) {

        //If sales log exists read log, else make new hash with default values.
        isFirstPurchase = false;
        int oldNum = logRecord.get(item.productName);
        logRecord.put(item.productName, ++oldNum);
    }

    public static void initializeReport(){
        if (isFirstPurchase) {
            File log = new File("salesLog.txt");
            if (!log.exists()) {
                logRecord = createSalesLog(logRecord);
            } else {
                try (Scanner read = new Scanner(log)) {
                    while (read.hasNextLine()) {
                        String input = read.nextLine();
                        if (input.contains("Total Money Made")){
                            break;
                        }
                        String[] split = input.split("\\|");
                        logRecord.put(split[0], Integer.parseInt(split[1]));
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("File not found");
                }
            }
        }

    }
    public static void writeReport(){

        try {
            File log = new File("salesLog.txt");
            if(!isFirstCustomer){
                salesLogger.close();
                salesLogger = null;
            }
            if (salesLogger == null) {
                salesLogger = new PrintWriter(new FileOutputStream(log,false));
            }
            double total=0;
            for (String s : logRecord.keySet()) {
                salesLogger.write(s + "|" + logRecord.get(s)+"\r\n");
                total += (logRecord.get(s) * Inventory.getInvItem(s).price);
                salesLogger.flush();
            }
            salesLogger.printf("Total Money Made: $%.2f",total);
            salesLogger.flush();
        }catch (FileNotFoundException ex){
            System.out.println("File not found");
        }
    }


    public static Map<String,Integer> createSalesLog (Map<String,Integer> logRecord){
       //create a new hashmap for the log with default values of 0;
            for (Item item : Inventory.getInventory()) {
                logRecord.put(item.productName, 0);
            }

        return logRecord;
    }
}
