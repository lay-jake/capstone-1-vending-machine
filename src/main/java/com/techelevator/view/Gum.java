package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gum extends Item {
    private String CHEW_ICON= "images/Big League.png";
    private String CHIC_ICON= "images/Chiclets.png";
    private String TRIPLEMINT_ICON= "images/Doubletree.png";
    private String UCHEW_ICON= "images/U-Chew.png";
    private String DEFAULT_ICON ="images/GUM.png";
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
    //Method to set the image for our attempt at Swing implementation it will check the Product name against
    //the switch case and apply the appropriate icon to each item.
    @Override
    public BufferedImage getImage(){
        BufferedImage image = null;

        try {
            switch (this.getProductName()){
                case "Little League Chew":
                    image = ImageIO.read(new File(CHEW_ICON));
                    break;
                case "Chiclets":
                    image = ImageIO.read(new File(CHIC_ICON));
                    break;
                case "Triplemint":
                    image = ImageIO.read(new File(TRIPLEMINT_ICON));
                    break;
                case "U-Chews":
                    image = ImageIO.read(new File(UCHEW_ICON));
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
