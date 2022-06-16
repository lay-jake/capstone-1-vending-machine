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

    public Slot(String loacation, Item item){
        this.item = item;
        this.location = loacation;

        columnNumber = Integer.parseInt(location.substring(1)) - 1;
        rowNumber = 0;
        char rowLetter = loacation.charAt(0);

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

        slotList.add(this);
    }

    public void paint(Graphics2D g){
        final int SLOT_SPACING_X = 100;
        final int SLOT_SPACING_Y = 130;
        x = 15 + 35 + columnNumber * SLOT_SPACING_X;
        y = 130 + rowNumber * SLOT_SPACING_Y;
        g.setColor(Color.lightGray);
        g.fillRoundRect(x, y, 75, 75, 10, 10);

        g.setColor(Color.darkGray);
        g.fillRect(x, y + 80, 75, 20);

        g.setColor(Color.GREEN.darker());
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        g.drawString(location, x + 29, y +94);

        drawItems(g);
    }

    private void drawItems(Graphics2D g){
        for(int i = 0; i < item.getStock(); i++){
            if(!(item.getName().equals("Drink")))
                g.drawImage(item.getImage(), null, x + (10 * i), y + (10 * i));
            else
                g.drawImage(item.getImage(), null, x + (10*i), y + (8 * i));
        }

    }


}