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
    //Defining constants for change and initializing logger so that it won't be closed/reopened every time we need to write to it.
    private static double customerMoney;
    private static final double QUARTER = 25;
    private static final double DIME = 10;
    private static final double NICKLE = 5;
    private static final double DOLLAR_CONVERSION = 100;
    private static PrintWriter logger;
    private static PrintWriter salesLogger;
    private static Map<String, Integer> logRecord = new HashMap<>();
    private static boolean isFirstPurchase = true;
    private static double totalSales =0;

    //Paths to the files for storing data.
    public static final String SAlES_LOG_PATH = "salesLog.txt";
    public static final String TRANS_LOG_PATH = "Log.txt";

    public static void setIsFirstPurchase(boolean isFirstPurchase) {
        Accounting.isFirstPurchase = isFirstPurchase;
    }

    public static Map<String, Integer> getLogRecord() {
        return logRecord;
    }

    public static double getTotalSales() {
        //Update totalSales before printing
        //Reason for this is it may not yet be stored in totalSales
        //So if it's just booted and immediately printed it will print correctly.
        for (String s : logRecord.keySet()) {
            totalSales += (logRecord.get(s) * Inventory.getInvItem(s).getPrice());
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
        customerMoney -= item.getPrice();
        saleslog(item);
        log(item.getProductName() + " " + Inventory.getSlotLocation(item.getProductName()), (customerMoney + item.getPrice()), customerMoney);
    }

    public static int[] giveChange() {
        //Beginning with quarters, going to pennies, finds the amount of each currency required.
        //Converted money to ints so that double trailing decimals do not interfere with the math.
        int retQuarters = (int) ((customerMoney * DOLLAR_CONVERSION) / QUARTER);
        int retDimes = (int) (((customerMoney * DOLLAR_CONVERSION) % QUARTER) / DIME);
        int retNickle = (int) (((customerMoney * DOLLAR_CONVERSION) % DIME) / NICKLE);
        int retPennies = (int) ((customerMoney * DOLLAR_CONVERSION) % NICKLE);
        //records the change to the sales log, transaction log and sets new balance to 0;
        log("GIVE CHANGE: ", customerMoney, 0);
        customerMoney = 0;
        writeReport();
        return new int[]{retQuarters, retDimes, retNickle, retPennies};
    }

    public static void log(String action, double moneyMovement, double moneyEnd) {
        //if logger has been initialized, bypass the initialization otherwise it will initialize the logger
        try {
            if (logger == null) {
                File log = new File(TRANS_LOG_PATH);
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

        //Reads the sales log and saves previous value for item into oldNum
        //Puts the item back into the sales log and adds one to total before saving.
        isFirstPurchase = false;
        int oldNum = logRecord.get(item.getProductName());
        logRecord.put(item.getProductName(), ++oldNum);
    }

    public static void initializeReport(){

        //checks to see if this is the first purchase, if not will not initialize logs as they are updated/correct in mem already
        if (isFirstPurchase) {

            File log = new File(SAlES_LOG_PATH);

            //check to see if the salesLog file exists, if it doesn't we create a new
            //default one and a new hash with all prod names and 0's for sales values.
            //will also check if the sales log got broke while testing and recreate @ 0's.
            if (!log.exists() || isSalesLogBroke(log)) {
                logRecord = createSalesLog(logRecord);
            } else {
                //If the sales log does exist we read and load it into the logRecord variable.
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

    //Method to be called for writing report to the Sales log
    //Records name of item, and how many sold.
    //Ends with total sales in dollar format.
    public static void writeReport(){

        try {
            File log = new File(SAlES_LOG_PATH);

            if (salesLogger == null) {
                salesLogger = new PrintWriter(new FileOutputStream(log,false));
            }
            totalSales=0;
            //loop through stored inventory hashmap, record name/sales to saleslog.
            for (String s : logRecord.keySet()) {
                salesLogger.write(s + "|" + logRecord.get(s)+"\r\n");
                //updates total sales by getting the amount sold, and then the price of the current item s in the hashmap
                totalSales += (logRecord.get(s) * Inventory.getInvItem(s).getPrice());
                salesLogger.flush();
            }
            //record total sales to log
            salesLogger.printf("Total Money Made: $%.2f",totalSales);
            salesLogger.flush();
            //we close the log so that it writes to the file, if we don't do this here
            //it will print all transaction logs created separately instead of over-riding with the most recent.
            salesLogger.close();
        }catch (FileNotFoundException ex){
            System.out.println("File not found");
        }
    }


    public static Map<String,Integer> createSalesLog (Map<String,Integer> logRecord){
       //create a new hashmap for the log with default values of 0 using the logRecord for parameter.
        for (String s : Inventory.getInventory().keySet()) {
            logRecord.put(Inventory.getInventory().get(s).getProductName(), 0);
        }
        return logRecord;
    }

    public static void setLogRecord(Inventory inv) {
        //create a new hashmap for the log with default values of 0 using the Inventory for parameter.
        //mostly used for testing purposes.
        Map <String,Integer> newLog = new HashMap<>();
        for (String s : inv.getInventory().keySet()) {
            newLog.put(inv.getInventory().get(s).getProductName(), 0);
        }
        Accounting.logRecord = newLog;
    }
    //Method that checks to see if the sales log starts with the final line
    //of what should be printed, if that happens it means the sales log got broke somehow.
    //returns true if that is the case.
    public static Boolean isSalesLogBroke(File log){
        try (Scanner read = new Scanner(log)){
            return read.nextLine().contains("Total");
        }catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        return false;
    }
}
