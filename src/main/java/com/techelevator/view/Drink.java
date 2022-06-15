package com.techelevator.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Drink extends Item {
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

    public BufferedImage getImage(){
        BufferedImage image = null;

        try {
            switch (this.getProductName()){
                case "Heavy":
                    image = ImageIO.read(new File("images/doctasald.png"));
                    break;
                case "Dr. Salt":
                    image = ImageIO.read(new File("images/Dr.Sprite.png"));
                    break;
                case "Cola":
                    image = ImageIO.read(new File("images/Heavy.png"));
                    break;
                case "Mountain Melter":
                    image = ImageIO.read(new File("images/cola.png"));
                    break;
                default:
                    image = ImageIO.read(new File("images/DRINK.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
