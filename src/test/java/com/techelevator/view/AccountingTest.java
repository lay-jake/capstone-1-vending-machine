package com.techelevator.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;

public class AccountingTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    Inventory testInv;
    @Before
    public void makeInv() {
        testInv = new Inventory();
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
        clearMoney();

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
