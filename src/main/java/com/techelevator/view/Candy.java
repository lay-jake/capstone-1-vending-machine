package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Candy extends Item {
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

    public BufferedImage getImage(){
        BufferedImage image = null;

        try {
            switch (this.getProductName()){
                case "Big League":
                    image = ImageIO.read(new File("images/Big League.png"));
                    break;
                case "Chiclets":
                    image = ImageIO.read(new File("images/Chiclets.png"));
                    break;
                case "Doubletree":
                    image = ImageIO.read(new File("images/Doubletree.png"));
                    break;
                case "U-Chew":
                    image = ImageIO.read(new File("images/U-Chew.png"));
                    break;
                default:
                    image = ImageIO.read(new File("images/CANDY.png"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
