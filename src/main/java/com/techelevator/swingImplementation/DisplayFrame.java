package com.techelevator.swingImplementation;

import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    private VendingPanel vendingPanel;
    private LogPanel logPanel;

    public DisplayFrame(){

        setPreferredSize(new Dimension(1375, 800));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);

        setUp();
        createUpdateThread();

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUp(){
        setLayout(new BorderLayout());

        vendingPanel = new VendingPanel();
        add(vendingPanel, BorderLayout.CENTER);

        logPanel = new LogPanel();
        add(logPanel, BorderLayout.EAST);
    }

    private void createUpdateThread(){
        Thread thread = new Thread(){
            public void run(){
                while(true){
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("REPAINT");
                    vendingPanel.repaint();
//                    if(System.nanoTime()%2_000_000_000 == 0){
//                        vendingPanel.repaint();
//                    }
                }
            }
        };

        thread.start();
    }
}
