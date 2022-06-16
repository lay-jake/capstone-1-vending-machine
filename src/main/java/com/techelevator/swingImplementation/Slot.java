package com.techelevator.swingImplementation;

import com.techelevator.view.Item;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Slot{
    int x, y, width, height;
    int columnNumber, rowNumber;
    Item item;
    String location = "";

    static List<Slot> slotList = new ArrayList<>();

    public Slot(String location, Item item){
        this.item = item;
        this.location = location;

        //GET COLUMN NUMBER
        columnNumber = Integer.parseInt(location.substring(1)) - 1;
        rowNumber = 0;
        char rowLetter = location.charAt(0);

        //GET ROW NUMBER BASED OFF LETTER
        switch (rowLetter){
            case 'A':
                rowNumber = 0;
                break;
            case 'B':
                rowNumber = 1;
                break;
            case 'C':
                rowNumber = 2;
                break;
            case 'D':
                rowNumber = 3;
                break;
        }

        //ADD SLOT TO SLOTLIST
        slotList.add(this);
    }

    public void paint(Graphics2D g){
        final int SLOT_SPACING_X = 100;
        final int SLOT_SPACING_Y = 130;
        x = 15 + 35 + columnNumber * SLOT_SPACING_X;
        y = 130 + rowNumber * SLOT_SPACING_Y;

//        //CREATE BACKGROUND FOR SLOT
//        g.setColor(Color.lightGray);
//        g.fillRoundRect(x, y, 75, 75, 10, 10);
//
//        //CREATE SLOT LOCATION DISPLAY
//        g.setColor(Color.darkGray);
//        g.fillRect(x, y + 80, 75, 20);
//
//        //DISPLAY SLOT LOCATION
//        g.setColor(Color.GREEN.darker());
//        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
//        g.drawString(location, x + 29, y +94);

        //DRAW ALL ITEMS INTO THE SLOT
        drawItems(g);
    }

    private void drawItems(Graphics2D g){
        //DRAWS THE ITEMS INTO THE SLOT
        for(int i = 0; i < item.getStock(); i++){
            if((item.getName().equals("Drink")))
                g.drawImage(item.getImage(), null, x + 5 + (10*i), y  + 10 + (8 * i));
            else if((item.getProductName().equals("Cowtales")))
                g.drawImage(item.getImage(), null, x + (8*i), y + 3 + (8 * i));
            else if((item.getProductName().equals("Crunchie")))
                g.drawImage(item.getImage(), null, x + 7 + (12*i), y + 5 + (11 * i));
            else if((item.getProductName().equals("Triplemint")))
                g.drawImage(item.getImage(), null, x - 7 + (11 * i), y - 10 + (9 * i));
            else
                g.drawImage(item.getImage(), null, x + 5  + (10*i), y + 7 + (10 * i));
        }

    }


}
