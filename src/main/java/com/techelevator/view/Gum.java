package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gum extends Item {
    public Gum (String productName, Double price, int stock) {
        super(productName, price, stock);
    }
    @Override
    //inherited from super, defines the output for purchase confirmation.
    public void purchaseConfirmation(){
        System.out.println("Chew Chew, Yum!");
    }
    @Override
    //getter for name
    public String getName(){
        return "Gum";
    }

    public BufferedImage getImage(){
        BufferedImage image = null;

        try {
            switch (this.getProductName()){
                case "Little League Chew":
                    image = ImageIO.read(new File("images/Big League.png"));
                    break;
                case "Chiclets":
                    image = ImageIO.read(new File("images/Chiclets.png"));
                    break;
                case "Triplemint":
                    image = ImageIO.read(new File("images/Doubletree.png"));
                    break;
                case "U-Chews":
                    image = ImageIO.read(new File("images/U-Chew.png"));
                    break;
                default:
                    image = ImageIO.read(new File("images/GUM.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
