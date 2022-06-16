package com.techelevator.swingImplementation;

import com.techelevator.view.Inventory;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel implements ActionListener {
    private int x, y, width, height;
    private JButton A, B, C, D;
    private JButton button1, button2, button3, button4;
    public static String selection = "";
    private long selectionStartTime;
    private long elapsedTime;

    public ButtonPanel(){
        //Set up button panel
        width = 110;
        height = 300;
        x = 460;
        y = 200;
        setPreferredSize(new Dimension(width, height));
        setLocation(x, y);
        setBounds(x, y, width, height);

        //Create grid layout and set it up
        GridLayout layout = new GridLayout(4, 2);
        layout.setHgap(10);
        layout.setVgap(10);
        setLayout(layout);

        setBackground(new Color(0, 0, 0, 0));


        //Instantiate JButtons
        A = new JButton("A");
        B = new JButton("B");
        C = new JButton("C");
        D = new JButton("D");
        button1 = new JButton("1");
        button2 = new JButton("2");
        button3 = new JButton("3");
        button4 = new JButton("4");

        //Add JButtons to pane
        add(A);
        A.addActionListener(this);
        A.setActionCommand("A");

        add(button1);
        button1.addActionListener(this);
        button1.setActionCommand("1");

        add(B);
        B.addActionListener(this);
        B.setActionCommand("B");

        add(button2);
        button2.addActionListener(this);
        button2.setActionCommand("2");

        add(C);
        C.addActionListener(this);
        C.setActionCommand("C");

        add(button3);
        button3.addActionListener(this);
        button3.setActionCommand("3");

        add(D);
        D.addActionListener(this);
        D.setActionCommand("D");

        add(button4);
        button4.addActionListener(this);
        button4.setActionCommand("4");

        revalidate();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        //CHECK THROUGH BUTTONS AND ADD BUTTON TO SELECTION
        switch(action){
            case "A":
                selection += "A";
                break;
            case "B":
                selection += "B";
                break;
            case "C":
                selection += "C";
                break;
            case "D":
                selection += "D";
                break;
            case "1":
                selection += "1";
                break;
            case "2":
                selection += "2";
                break;
            case "3":
                selection += "3";
                break;
            case "4":
                selection += "4";
                break;
        }

        //IF SELECTED START 5 SECOND COUNTER AND RESET CHOICE IF NOTHING ELSE SELECTED
        if(selection.length() == 1) {
            selectionStartTime = System.nanoTime();
            Thread timeCountingThread = new Thread(){
                public void run(){
                    while(selection.length() == 1){
                        elapsedTime += System.nanoTime() - selectionStartTime;
                        elapsedTime /= 1_000_000_000;

                        if(elapsedTime > 5) {
                            selection = "";
                        }
                    }
                }
            };
            timeCountingThread.start();
        }

        //IF VALID LENGTH ATTEMPT TO PURCHASE
        if(selection.length() >= 2){
            Inventory.getItem(selection);
            System.out.println(selection);
            selection = "";
        }
    }
}
