package com.techelevator.swingImplementation;

import javax.swing.*;
import java.awt.*;

public class VendingPanel extends JPanel {

    private int width = 650;
    private VendingMachine vendingMachine;

    public VendingPanel(){
        setPreferredSize(new Dimension(width, 0));

        vendingMachine = new VendingMachine();
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        //Draw Background
        Color backgroundGradient;

        for(int b = 0; b < 255; b++){
            backgroundGradient = new Color(b/3, 0, b);
            g2D.setColor(backgroundGradient);
            g2D.fillRect(0, 0, getWidth(), (int) (getHeight() - (getHeight() * (b/255.0))));
        }


        //Draw Vending Machine=
        vendingMachine.paintVendingMachine(g2D, getWidth(), getHeight());
    }
}
