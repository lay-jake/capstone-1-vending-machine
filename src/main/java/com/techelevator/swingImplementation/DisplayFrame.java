package com.techelevator.swingImplementation;

import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    public static VendingPanel vendingPanel;
    private LogPanel logPanel;

    public DisplayFrame(){
        //CREATE JFRAME, SET PREFERRED SIZE & CLOSE OPERATION
        setPreferredSize(new Dimension(1375, 800));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        //SET UP FUNCTIONALITY FOR FRAME
        setUp();
        createUpdateThread();

        pack();

        //SET LOCATION TO BE CENTER OF SCREEN, SET VISIBLE
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUp(){
        //SET THE LAYOUT OF THE FRAME
        setLayout(new BorderLayout());

        //CREATE VENDING PANEL AND ADD IT TO LAYOUT
        vendingPanel = new VendingPanel();
        add(vendingPanel, BorderLayout.CENTER);

        //CREATE LOG PANEL AND ADD IT TO LAYOUT
        logPanel = new LogPanel();
        add(logPanel, BorderLayout.EAST);
    }

    private void createUpdateThread(){
        //THIS THREAD RUNS IN PARALLEL TO THE PROGRAMS AND UPDATES(REPAINTS) THE VENDING PANEL
        Thread thread = new Thread(){
            public void run(){
//                while(true){
////                    try {
////                        sleep(2000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
//
//                    //vendingPanel.repaint();
//                    //vendingPanel.updateUI();
//                }
            }
        };

        thread.start();
    }
}
