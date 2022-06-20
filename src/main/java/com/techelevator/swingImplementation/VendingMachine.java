package com.techelevator.swingImplementation;

import com.techelevator.view.Inventory;
import com.techelevator.view.Item;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendingMachine {
    public int x;
    public int y;
    public int width;
    public int height;

    public static List<Item> soldList= new ArrayList<>();

    public VendingMachine(){
        //Create slots
        for(Map.Entry<String, Item> entry : Inventory.getInventory().entrySet()){
            new Slot(entry.getKey(), entry.getValue());
        }
    }

    public void paintVendingMachine(Graphics2D g, int panelWidth, int panelHeight){
        //get new values
        width = (int) (panelWidth*.7);
        height = panelHeight-30;
        x = 15;
        y = 15;

//        //draw back panel of vending machine
//        g.setColor(Color.YELLOW.darker());
//        g.fillRoundRect(x, y, width, height, 20, 20);

//        //draw stripes
//        g.setColor(Color.BLACK.brighter());
//        for(int stripeY = y + 85; stripeY < height - 100; stripeY += 20){
//            g.fillRect(x, stripeY, width, height/100);
//        }

//        //draw VENIDNG - O - MATIC at top
//        if(System.nanoTime()%4_000_000_000L < 2_000_000_000)
//            g.setColor(Color.YELLOW);
//        else
//            g.setColor(Color.GREEN);
//
//        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 27));
//        g.drawString("V E N D I N G - O - M A T I C", x + 42, y + 50);

//        //Draw Item Dispensing holder part
//        g.setColor(Color.BLACK.brighter());
//        g.fillRoundRect(x + 35, y + 630, 375, 85, 10, 10 );

//        //Draw Selection Panel
//        g.setColor(Color.lightGray);
//        g.fillRect(x + 430, 130, 125, 465);

//        //Draw display panel
//        g.setColor(Color.BLACK);
//        g.fillRect(x + 440, 140, 105, 50);

        //Draw Slots
        for(Slot slot : Slot.slotList){
            slot.paint(g);
        }

        ButtonPanel.drawSelection(g);
        //add Sold items to imageList
//        for(Map.Entry<String, Item> entry: Inventory.getInventory().entrySet()){
//            Item item = entry.getValue();
//            for(int i = item.getStock(); i < 5; i++){
//                if(!imageList.contains(item.getImage()))
//                    imageList.add(item.getImage());
//            }
//        }

        //draw imageList sold items
        int addedItems = 0;
        for(Item item : soldList){
            if(addedItems > 10)
                g.drawImage(item.getImage(), null, x+40+(30* addedItems), y + 670);
            else
                g.drawImage(item.getImage(), null, x + 40 + (30 * addedItems - 10), y + 640);

            addedItems++;
        }
    }
}
