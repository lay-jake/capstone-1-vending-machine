package com.techelevator.swingImplementation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LogPanel extends JPanel{
    private int width = 545;

    private File machineLog = new File("Log.txt");
    private long machineLogLastModified = machineLog.lastModified();
    JScrollPane machineLogScrollPane;

    private File salesLog = new File("salesLog.txt");
    private long salesLogLastModified = salesLog.lastModified();
    JScrollPane salesLogScrollPane;

    public LogPanel(){
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(width, 0));
        setLayout(new BorderLayout());

        setUpSalesLogTextArea();
        setUpMachineLogTextArea();

        createFileWatchThread();
    }

    private void createFileWatchThread(){
        Thread fileWatchThread = new Thread(){
            public void run(){
                while(true){
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

        JTextArea machineLogTextArea = new JTextArea(machineDisplayText);

        machineLogScrollPane = new JScrollPane(machineLogTextArea);
        machineLogScrollPane.setPreferredSize(new Dimension(width/2 + 53, 0));
        JScrollBar scrollBar = machineLogScrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());

        add(machineLogScrollPane, BorderLayout.EAST);
    }

    private void setUpSalesLogTextArea(){
        File salesLog = new File("salesLog.txt");

        String logDisplayText = "";

        try(Scanner fileInput = new Scanner(salesLog)){
            while(fileInput.hasNextLine()){
                logDisplayText += fileInput.nextLine();
                logDisplayText += "\n";

            }
        } catch (FileNotFoundException e) {
                logDisplayText = "UNABLE TO FIND SALES LOG FILE";
        }

        JTextArea salesLogTextArea = new JTextArea(logDisplayText);
        salesLogTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        salesLogTextArea.setBackground(Color.lightGray);

        salesLogScrollPane = new JScrollPane(salesLogTextArea);
        salesLogScrollPane.setPreferredSize(new Dimension(205, 0));


        add(salesLogScrollPane, BorderLayout.CENTER);
    }
}
