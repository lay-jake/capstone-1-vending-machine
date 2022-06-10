package com.techelevator.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLOutput;

public class InventoryTest {
    private Inventory testInv;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    //makes list
    //adds to list
    //get an correct item from list
    @Before
    public void clearChange(){
        Accounting.giveChange();
        try{
            outputStreamCaptor.flush();
            outputStreamCaptor.reset();
        }catch (IOException ex){
            System.out.println("File not found");
        }
    }
    @Before
    public void makeInv() {
        testInv = new Inventory();
    }
    @Before
    public void setUp(){
        System.setOut( new PrintStream(outputStreamCaptor));
    }

    @Test
    public void shouldCreateInventory() {
        Assert.assertEquals(true, testInv != null);

    }

    @Test
    public void shouldGetItemIfExists() {
        Accounting.feedMoney(15);
        Inventory.getItem("A2");
        String testInput = "Dispensing Stackers for $1.45 ... \r\nCrunch Crunch, Yum!\r\nYou have $13.55 of remaining balance.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
        Accounting.giveChange();

        try{
            outputStreamCaptor.flush();
            outputStreamCaptor.reset();
        }catch (IOException ex){
            System.out.println("File not found");
        }

        Accounting.feedMoney(15);
        Inventory.getItem("D4");
        testInput = "Dispensing Triplemint for $0.75 ... \r\nChew Chew, Yum!\r\nYou have $14.25 of remaining balance.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
        Accounting.giveChange();
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

        try{
            outputStreamCaptor.flush();
            outputStreamCaptor.reset();
        }catch (IOException ex){
            System.out.println("File not found");
        }
        Accounting.feedMoney(15);
        Inventory.getItem("A2");
        String testInput = "Product A2 Stackers is out of stock.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
    }
    @Test
    public void shouldReturnNoIfBroke(){
        Inventory.getItem("A2");
        String testInput = "You do not have enough available balance.";
        Assert.assertEquals(testInput,outputStreamCaptor.toString().trim());
    }

}
