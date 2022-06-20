package com.techelevator.swingImplementation;

import com.techelevator.view.Accounting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DisplayFrame extends JFrame implements MouseListener {
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

        addMouseListener(this);

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
                while(true){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    vendingPanel.repaint();
                    //vendingPanel.updateUI();
                }
            }
        };

        thread.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getX() >= ButtonPanel.changeButtonX && e.getX() <= ButtonPanel.changeButtonX + ButtonPanel.changeButtonWidth + 5
                && e.getY() >= ButtonPanel.changeButtonY && e.getY() <= ButtonPanel.changeButtonY + (ButtonPanel.changeButtonHeight * 2) + 6){
            Accounting.giveChange();
            VendingMachine.soldList.clear();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
