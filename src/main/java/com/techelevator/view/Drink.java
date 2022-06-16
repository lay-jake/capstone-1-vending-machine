package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Drink extends Item {
    private final String HEAVY_ICON= "images/doctasald.png";
    private final String DR_SALT_ICON= "images/Dr.Sprite.png";
    private final String COLA_ICON= "images/Heavy.png";
    private final String MT_MELTER_ICON= "images/cola.png";
    private final String DEFAULT_ICON ="images/DRINK.png";
    public Drink(String productName, Double price, int stock) {
        super(productName, price, stock);
    }
    @Override
    //inherited from super, defines the output for purchase confirmation.
    public void purchaseConfirmation(){
        System.out.println("Glug Glug, Yum!");
    }
    @Override
    //getter for name
    public String getName(){
        return "Drink";
    }
    //Method to set the image for our attempt at Swing implementation it will check the Product name against
    //the switch case and apply the appropriate icon to each item.
    @Override
    public BufferedImage getImage(){
        BufferedImage image = null;

        try {
            switch (this.getProductName()){
                case "Heavy":
                    image = ImageIO.read(new File(HEAVY_ICON));
                    break;
                case "Dr. Salt":
                    image = ImageIO.read(new File(DR_SALT_ICON));
                    break;
                case "Cola":
                    image = ImageIO.read(new File(COLA_ICON));
                    break;
                case "Mountain Melter":
                    image = ImageIO.read(new File(MT_MELTER_ICON));
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
