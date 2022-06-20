package com.techelevator.swingImplementation;

import com.techelevator.view.Accounting;
import com.techelevator.view.Inventory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class VendingPanel extends JPanel {

    private int width = 650;
    private VendingMachine vendingMachine;
    public ButtonPanel buttonPanel;
    private MoneySelectionPanel moneySelectionPanel;

    public VendingPanel(){
        //SET UP VENDING PANEL
        setPreferredSize(new Dimension(width, 0));
        setLayout(null);

        //CREATE VENDING MACHINE INSTANCE
        vendingMachine = new VendingMachine();

        //ADD SELECTION PANEL
        buttonPanel = new ButtonPanel();
        add(buttonPanel);

        //ADD MONEY SELECTION PANEL
        moneySelectionPanel = new MoneySelectionPanel();
        add(moneySelectionPanel);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        BufferedImage backgroundImage = null;

        try {
            backgroundImage = ImageIO.read(new File("images/BACKGROUND1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2D.drawImage(backgroundImage, null, 0, 0);

        vendingMachine.paintVendingMachine(g2D, getWidth(), getHeight());
    }
}

//    @Override
//    public void paint(Graphics g){
//        Graphics2D g2D = (Graphics2D) g;
//        //Draw Background
//        BufferedImage backgroundImage = null;
//        try {
//            backgroundImage = ImageIO.read(new File("images/BACKGROUND1.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        g2D.drawImage(backgroundImage, null, 0, 0);
//
////        Color backgroundGradient;
////        for(int b = 0; b < 255; b++){
////            backgroundGradient = new Color(b/3, 0, b);
////            g2D.setColor(backgroundGradient);
////            g2D.fillRect(0, 0, getWidth(), (int) (getHeight() - (getHeight() * (b/255.0))));
////        }
//
//
//        //Draw Vending Machine
////        vendingMachine.paintVendingMachine(g2D, getWidth(), getHeight());
//    }
//}
