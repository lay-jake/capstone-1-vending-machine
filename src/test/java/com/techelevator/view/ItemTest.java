package com.techelevator.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ItemTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Before
    public void setUp(){
        System.setOut( new PrintStream(outputStreamCaptor));
    }
    @Test
    public void gumSholdChew() {
        Item gum = new Gum("testGum", 3.50, 5);

        Assert.assertEquals(3.50, gum.getPrice(), 0);
        Assert.assertEquals(5, gum.getStock(), 0);
        Assert.assertEquals("testGum", gum.getProductName());
        gum.purchaseConfirmation();
        Assert.assertEquals("Chew Chew, Yum!", outputStreamCaptor.toString().trim());
    }
    @Test
    public void drinkShouldGlug() {
            Item drink = new Drink( "testDrink", 1.99, 5);

            Assert.assertEquals(1.99, drink.getPrice(), 0);
            Assert.assertEquals(5, drink.getStock(), 0);
            Assert.assertEquals("testDrink", drink.getProductName());
            drink.purchaseConfirmation();
            Assert.assertEquals("Glug Glug, Yum!", outputStreamCaptor.toString().trim());
        }
    @Test
    public void candyShouldMunch(){
                Item candy = new Candy("testCandy",2.00,5);

                Assert.assertEquals(2.00,candy.getPrice(),0);
                Assert.assertEquals(5,candy.getStock(),0);
                Assert.assertEquals("testCandy",candy.getProductName());
                candy.purchaseConfirmation();
                Assert.assertEquals("Munch Munch, Yum!",outputStreamCaptor.toString().trim());
}

    @Test
    public void chipsShouldCrunch(){
        Item chip = new Chip("testChip",2.00,5);

        Assert.assertEquals(2.00,chip.getPrice(),0);
        Assert.assertEquals(5,chip.getStock(),0);
        Assert.assertEquals("testChip",chip.getProductName());
        chip.purchaseConfirmation();
        Assert.assertEquals("Crunch Crunch, Yum!",outputStreamCaptor.toString().trim());
    }
}
