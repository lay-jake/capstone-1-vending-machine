package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Candy extends Item {
//    private final String MOONPIE_ICON;
//    private final String COWTALES_ICON;
//    private final String WONKA_BAR_ICON;
//    private final String CRUNCHIE_ICON;
    private final String DEFAULT_ICON ="images/CANDY.png";
    public Candy(String productName, Double price, int stock) {
        super(productName, price, stock);
    }

    @Override

    //inherited from super, defines the output for purchase confirmation.
    public void purchaseConfirmation(){
        System.out.println("Munch Munch, Yum!");
    }
    @Override
    //getter for name
    public String getName(){
        return "Candy";
    }
    //Method to set the image for our attempt at Swing implementation it will check the Product name against
    //the switch case and apply the appropriate icon to each item.
    @Override
    public BufferedImage getImage(){
        BufferedImage image = null;

        try {
            switch (this.getProductName()){
                default:
                    image = ImageIO.read(new File(DEFAULT_ICON));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
