package com.techelevator.view;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class InventoryTest {
    private Inventory testInv;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    AccountingTest test = new AccountingTest();

    @Before
    public void initializeInventoryTestItems(){
        test.saveLogs();
        setUp();
        makeInv();
        Accounting.giveChange();
        flushConsole();
    }
    @After
    public void reloadLogs(){
        test.reloadAndDeletLogs();
    }

    public void makeInv() {
        testInv = new Inventory();
    }
    public void setUp(){
        System.setOut( new PrintStream(outputStreamCaptor));
    }

    @Test
    public void shouldCreateInventory() {
        Assert.assertEquals(true, testInv != null);
        Assert.assertEquals(true, Inventory.getInventory().get("A1").getProductName().equals("Potato Crisps"));
        Assert.assertEquals(true, Inventory.getInventory().get("B2").getProductName().equals("Cowtales"));
        Assert.assertEquals(true, Inventory.getInventory().get("C3").getProductName().equals("Mountain Melter"));
        Assert.assertEquals(true, Inventory.getInventory().get("D4").getProductName().equals("Triplemint"));
    }

    @Test
    public void shouldGetItemIfExists() {
        Accounting.feedMoney(15);
        Inventory.getItem("A2");
        String testInput = "Dispensing Stackers for $1.45 ... \r\nCrunch Crunch, Yum!\r\nYou have $13.55 of remaining balance.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
        Accounting.giveChange();
        flushConsole();

        Accounting.feedMoney(15);
        Inventory.getItem("D4");
        testInput = "Dispensing Triplemint for $0.75 ... \r\nChew Chew, Yum!\r\nYou have $14.25 of remaining balance.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
        Accounting.giveChange();
        flushConsole();

        Accounting.feedMoney(15);
        Inventory.getItem("E4");
        testInput = "Invalid Selection.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
        Accounting.giveChange();
        flushConsole();
    }
    @Test
    public void shouldReturnNoItemIfEmpty(){
        Accounting.feedMoney(15);
        Inventory.getItem("A2");
        Inventory.getItem("A2");
        Inventory.getItem("A2");
        Inventory.getItem("A2");
        Inventory.getItem("A2");
        Accounting.giveChange();

        flushConsole();
        Accounting.feedMoney(15);
        Inventory.getItem("A2");
        String testInput = "Product A2 Stackers is out of stock.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
        flushConsole();
    }
    @Test
    public void shouldReturnNoIfBroke(){
        Inventory.getItem("A2");
        String testInput = "You do not have enough available balance.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
        flushConsole();
    }

    public void flushConsole(){
        try{
            outputStreamCaptor.flush();
            outputStreamCaptor.reset();
        }catch (IOException ex){
            System.out.println("File not found");
        }
    }

}
