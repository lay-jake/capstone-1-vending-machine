package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chip extends Item {
    public Chip(String productName, Double price, int stock) {
        super(productName, price, stock);
    }
    @Override
    //inherited from super, defines the output for purchase confirmation.
    public void purchaseConfirmation(){
        System.out.println("Crunch Crunch, Yum!");
    }
    @Override
    //getter for name
    public String getName(){
        return "Chip";
    }

    public BufferedImage getImage(){
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File("images/CHIP.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
