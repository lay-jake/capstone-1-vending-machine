package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chip extends Item {
    private final String POTATO_CRISPS_ICON = "images/Potato Crisps.png";
    private final String STACKERS_ICON = "images/Stackers.png";
    private final String GRAIN_WAVES_ICON = "images/Grain Waves.png";
    private final String CLOUD_POPCORN_ICON = "images/CLOUD POPCORN.png";
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
            switch (this.getProductName()){
                case "Stackers":
                    image = ImageIO.read(new File(STACKERS_ICON));
                    break;
                case "Potato Crisps":
                    image = ImageIO.read(new File(POTATO_CRISPS_ICON));
                    break;
                case "Grain Waves":
                    image = ImageIO.read(new File(GRAIN_WAVES_ICON));
                    break;
                case "Cloud Popcorn":
                    image = ImageIO.read(new File(CLOUD_POPCORN_ICON));
                    break;
                default:
                    image = ImageIO.read(new File(DEFAULT_ICON));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
