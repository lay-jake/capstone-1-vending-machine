package com.techelevator.swingImplementation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LogPanel extends JPanel{
    //SET WIDTH
    private int width = 545;

    //CREATE FILE INSTANCES FOR SALES LOG AND VENDING LOG
    //ALSO ADDS THE TIME LAST MODIFIED FOR THE FILES AS A VARIABLE
    //CREATES SCROLL PANE FOR THE TEXT IN FILES
    private File machineLog = new File("Log.txt");
    private long machineLogLastModified = machineLog.lastModified();
    JScrollPane machineLogScrollPane;

    private File salesLog = new File("salesLog.txt");
    private long salesLogLastModified = salesLog.lastModified();
    JScrollPane salesLogScrollPane;

    public LogPanel(){
        //SET UP LOG PANEL
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(width, 0));
        setLayout(new BorderLayout());

        //SET UP METHODS FOR SALES LOG AND MACHINE LOG DISPLAY
        setUpSalesLogTextArea();
        setUpMachineLogTextArea();

        //CREATE THREAD THAT UPDATES IF FILE IS CHANGED
        createFileWatchThread();
    }

    private void createFileWatchThread(){
        //THIS THREAD CHECKS IF THE CURRENT MODIFIED TIME IS NOT THE SAME AND REMOVES THE PANES
        //AND RECREATES THEM
        Thread fileWatchThread = new Thread(){
            public void run(){
                while(true){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(machineLog.lastModified() != machineLogLastModified){
                        remove(machineLogScrollPane);
                        machineLogLastModified = machineLog.lastModified();
                        setUpMachineLogTextArea();
                        updateUI();
                    }

                    if(salesLog.lastModified() != salesLogLastModified){
                        remove(salesLogScrollPane);
                        salesLogLastModified = salesLog.lastModified();
                        setUpSalesLogTextArea();
                        updateUI();
                    }
                }
            }
        };

        fileWatchThread.start();
    }

    private void setUpMachineLogTextArea(){

        String machineDisplayText = "";

        //READ THROUGH MACHINE LOG FILE AND SAVE INPUT
        try(Scanner fileInput = new Scanner(machineLog)){
            while(fileInput.hasNextLine()){
                String machineLogLine = fileInput.nextLine();
                machineDisplayText += machineLogLine;
                machineDisplayText += "\n";

                if(machineLogLine.contains("GIVE CHANGE:"))
                    machineDisplayText += "----------------------------------------------------------------------------\n";
            }
        } catch (FileNotFoundException e) {
            machineDisplayText = "COULD NOT FIND VENDING LOG";
        }

        //CREATE TEXT AREA FOR THE INPUT FROM MACHINE LOG FILE
        JTextArea machineLogTextArea = new JTextArea(machineDisplayText);

        //SET UP SCROLL PANE AND ADD TEXT AREA TO IT
        machineLogScrollPane = new JScrollPane(machineLogTextArea);
        machineLogScrollPane.setPreferredSize(new Dimension(width/2 + 53, 0));

        //SET SCROLL BAR TO ALWAYS BE AT THE BOTTOM
        JScrollBar scrollBar = machineLogScrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());

        //ADD SCROLL PANE TO LOG PANEL
        add(machineLogScrollPane, BorderLayout.EAST);
    }

    private void setUpSalesLogTextArea(){

        String logDisplayText = "";

        //READS THROUGH SALES LOG AND SAVES INPUT
        try(Scanner fileInput = new Scanner(salesLog)){
            while(fileInput.hasNextLine()){
                logDisplayText += fileInput.nextLine();
                logDisplayText += "\n";

            }
        } catch (FileNotFoundException e) {
                logDisplayText = "UNABLE TO FIND SALES LOG FILE";
        }

        //CREATES TEXT AREA FOR INPUT FROM SALES LOG FILE
        JTextArea salesLogTextArea = new JTextArea(logDisplayText);
        salesLogTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        salesLogTextArea.setBackground(Color.lightGray);

        //SET UP SCROLL PANE AND ADD SALES LOG INFO TO IT
        salesLogScrollPane = new JScrollPane(salesLogTextArea);
        salesLogScrollPane.setPreferredSize(new Dimension(205, 0));

        //ADD SCROLL PANE TO LOG PANEL
        add(salesLogScrollPane, BorderLayout.CENTER);
    }
}
