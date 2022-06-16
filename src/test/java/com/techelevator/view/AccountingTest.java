package com.techelevator.view;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class AccountingTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Inventory testInv;
    File log = new File("salesLog.txt");
    File logSave = new File("testSave.txt");
    File tranLog = new File("Log.txt");
    File tranLogSave = new File("testLog.txt");

    @Before
    public void saveLogsAndInitialize() {
        //Initialize 4 streams, 2 read 2 write
        // saves the information from Log Text and SalesLog to temp files
        // so that the tests do not overwrite the data.

        try (FileInputStream salesLogRead = new FileInputStream(log);
                  FileOutputStream salesLogWrite = new FileOutputStream(logSave);
                  FileInputStream transLogRead = new FileInputStream(tranLog);
                  FileOutputStream transLogWrite = new FileOutputStream(tranLogSave))
        {
            //byte array for 1kb
            byte[] buffer = new byte[1024];
            //length of next buffer
            int length;
            while ((length = salesLogRead.read(buffer)) > 0) {
                salesLogWrite.write(buffer, 0, length);
                salesLogWrite.flush();
            }
            salesLogWrite.flush();
            while ((length = transLogRead.read(buffer)) > 0){
                transLogWrite.write(buffer,0,length);
                transLogWrite.flush();
            }
            transLogWrite.flush();
        } catch (IOException ex) {
            System.out.println("File not found");
        }
        //initialize inventory, sales log and clear money - created separate methods
        //because they need to run before each method, but in a specific order.
        makeInv();
        createSalesLog();
        clearMoneyCache();
    }
    @After
    public void reloadLogs(){
        //Initialize 4 streams, 2 read 2 write
        // saves the information from Log Text and SalesLog to temp files
        // so that the tests do not overwrite the data.
        try(FileInputStream salesLogRead = new FileInputStream(logSave);
            FileOutputStream salesLogWrite = new FileOutputStream(log);
            FileInputStream transLogRead = new FileInputStream(tranLogSave);
            FileOutputStream transLogWrite = new FileOutputStream(tranLog))
        {
            //byte array for 1kb
            byte[] buffer = new byte[1024];
            //length of next buffer
            int length;
            while ((length = salesLogRead.read(buffer)) > 0){
                salesLogWrite.write(buffer,0,length);
                salesLogWrite.flush();
            }
            while ((length = transLogRead.read(buffer)) > 0){
                transLogWrite.write(buffer,0,length);
                transLogWrite.flush();
            }
            transLogWrite.flush();
        }catch (IOException ex){
            System.out.println("File not found");
        }
        //deleting the temp files.
        logSave.delete();
        tranLogSave.delete();
    }


    public void makeInv() {
        testInv = new Inventory();
    }
    public void clearMoneyCache(){
        clearMoney();
    }

    public void createSalesLog (){
        //create a new hashmap for the log with default values of 0;
        Accounting.setLogRecord(testInv);
        }

    @Test
    public void shouldAddBalance(){
        Accounting.feedMoney(15);
        Assert.assertEquals(15,Accounting.getCustomerMoney(),0);
        clearMoney();

        Accounting.feedMoney(1);
        Assert.assertEquals(1,Accounting.getCustomerMoney(),0);
        clearMoney();

        Accounting.feedMoney(5);
        Assert.assertEquals(5,Accounting.getCustomerMoney(),0);
        clearMoney();

        Accounting.feedMoney(10);
        Assert.assertEquals(10,Accounting.getCustomerMoney(),0);
        clearMoney();

        Accounting.feedMoney(0);
        Assert.assertEquals(0,Accounting.getCustomerMoney(),0);
        clearMoney();
    }
    @Test
    public void doesPurchaseDecreaseBal(){
        Accounting.feedMoney(10);
        Inventory.getItem("C4");
        Assert.assertEquals(8.50,Accounting.getCustomerMoney(),0);
        Inventory.getItem("B1");
        Assert.assertEquals(6.70,Accounting.getCustomerMoney(),0);
        Inventory.getItem("C1");
        Assert.assertEquals(5.45,Accounting.getCustomerMoney(),0);
    }
    @Test
    public void givesCorrectChange() {

        Accounting.feedMoney(5);
        Assert.assertTrue(Arrays.equals(new int[]{20,0,0,0},Accounting.giveChange()));
        clearMoney();

        Accounting.feedMoney(10);
        Inventory.getItem("C4");
        Inventory.getItem("B1");
        Assert.assertTrue(Arrays.equals(new int[]{26,2,0,0},Accounting.giveChange()));
        clearMoney();
    }

    public void clearMoney(){
        Accounting.giveChange();

        try{
            outputStreamCaptor.flush();
            outputStreamCaptor.reset();
        }catch (IOException ex){
            System.out.println("File not found");
        }
    }
    //feed money
    //purchase item
    //give change
}
