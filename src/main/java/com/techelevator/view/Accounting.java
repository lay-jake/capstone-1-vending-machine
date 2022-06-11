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
    private static double totalSales =0;

    public static void setIsFirstPurchase(boolean isFirstPurchase) {
        Accounting.isFirstPurchase = isFirstPurchase;
    }

    public static Map<String, Integer> getLogRecord() {
        return logRecord;
    }

    public static void setIsFirstCustomer(boolean isFirstCustomer) {
        Accounting.isFirstCustomer = isFirstCustomer;
    }

    public static double getTotalSales() {
        //Update totalSales before printing
        //Reason for this is it may not yet be stored in totalSales
        //if a customer hasn't purchased anything yet
        //so we recalc to provide the most up to date value.
        for (String s : logRecord.keySet()) {
            totalSales += (logRecord.get(s) * Inventory.getInvItem(s).price);
        }
        return totalSales;
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
        //checks to see if this is the first purchase, if not will not initialize logs as they are updated/correct in mem already
        if (isFirstPurchase) {
            File log = new File("salesLog.txt");
            //check to see if the salesLog file exists, if it doesnt we create a new
            //default one and a new hash with all prod names and 0's for sales values.
            if (!log.exists()) {
                logRecord = createSalesLog(logRecord);
            } else {
                //if log exists, load log into a hashmap logRecord.
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
            //if its not the first customer, close the sales logger to write pending changes to log;
            //sets salesLogger to null so it will reinitialize
            if(!isFirstCustomer){
                salesLogger.close();
                salesLogger = null;
            }
            if (salesLogger == null) {
                salesLogger = new PrintWriter(new FileOutputStream(log,false));
            }
            totalSales=0;
            //loop through stored inventory hashmap, record name/sales to saleslog.
            for (String s : logRecord.keySet()) {
                salesLogger.write(s + "|" + logRecord.get(s)+"\r\n");
                //updates total sales by getting the amount sold, and then the price of the current item s in the hashmap
                totalSales += (logRecord.get(s) * Inventory.getInvItem(s).price);
                salesLogger.flush();
            }
            //record total sales to log
            salesLogger.printf("Total Money Made: $%.2f",totalSales);
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
