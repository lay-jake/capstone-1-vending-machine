package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chip extends Item {
//    private final String POTATO_CRISPS_ICON;
//    private final String STACKERS_ICON;
//    private final String GRAIN_WAVES_ICON;
//    private final String CLOUD_POPCORN_ICON;
    private final String DEFAULT_ICON ="images/CHIP.png";
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
    //Method to set the image for our attempt at Swing implementation it will check the Product name against
    //the switch case and apply the appropriate icon to each item.
    @Override
    public BufferedImage getImage(){
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(DEFAULT_ICON));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
