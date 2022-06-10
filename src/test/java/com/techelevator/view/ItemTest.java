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
        Item gum = new Gum("A1", "testGum", 3.50, 5);

        Assert.assertEquals(3.50, gum.price, 0);
        Assert.assertEquals(5, gum.stock, 0);
        Assert.assertEquals("testGum", gum.productName);
        Assert.assertEquals("A1", gum.slotLocation);
        gum.purchaseConfirmation();
        Assert.assertEquals("Chew Chew, Yum!", outputStreamCaptor.toString().trim());
    }
    @Test
    public void drinkShouldGlug() {
            Item drink = new Drink("A5", "testDrink", 1.99, 5);

            Assert.assertEquals(1.99, drink.price, 0);
            Assert.assertEquals(5, drink.stock, 0);
            Assert.assertEquals("testDrink", drink.productName);
            Assert.assertEquals("A5", drink.slotLocation);
            drink.purchaseConfirmation();
            Assert.assertEquals("Glug Glug, Yum!", outputStreamCaptor.toString().trim());
        }
    @Test
    public void candyShouldMunch(){
                Item candy = new Candy("D5","testCandy",2.00,5);

                Assert.assertEquals(2.00,candy.price,0);
                Assert.assertEquals(5,candy.stock,0);
                Assert.assertEquals("testCandy",candy.productName);
                Assert.assertEquals("D5",candy.slotLocation);
                candy.purchaseConfirmation();
                Assert.assertEquals("Munch Munch, Yum!",outputStreamCaptor.toString().trim());
}

    @Test
    public void chipsShouldCrunch(){
        Item chip = new Chip("D2","testChip",2.00,5);

        Assert.assertEquals(2.00,chip.price,0);
        Assert.assertEquals(5,chip.stock,0);
        Assert.assertEquals("testChip",chip.productName);
        Assert.assertEquals("D2",chip.slotLocation);
        chip.purchaseConfirmation();
        Assert.assertEquals("Crunch Crunch, Yum!",outputStreamCaptor.toString().trim());
    }
}
