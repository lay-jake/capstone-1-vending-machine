package com.techelevator.swingImplementation;

import com.techelevator.view.Accounting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoneySelectionPanel extends JPanel implements ActionListener {
    private int x, y, width, height;
    private JButton button1, button5, button10, button20;

    public MoneySelectionPanel(){
        //Set up panel
        x = 600;
        y = 103;
        width = 213;
        height = 535;

        setBackground(new Color(0, 0, 0, 0));
        setBounds(x, y, width, height);

        //Set layout
        GridLayout layout = new GridLayout(4, 1);
        layout.setVgap(10);
        setLayout(layout);

        //Instantiate and add buttons
        button1 = new JButton("$1");
        button1.setBackground(Color.GREEN.darker());
        button1.setActionCommand("1");

        button5 = new JButton("$5");
        button5.setBackground(Color.GREEN.darker());
        button5.setActionCommand("5");

        button10 = new JButton("$10");
        button10.setBackground(Color.GREEN.darker());
        button10.setActionCommand("10");

        button20 = new JButton("$20");
        button20.setBackground(Color.GREEN.darker());
        button20.setActionCommand("20");

        add(button1);
        button1.addActionListener(this);

        add(button5);
        button5.addActionListener(this);

        add(button10);
        button10.addActionListener(this);

        add(button20);
        button20.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "1":
                Accounting.feedMoney(1);
                break;
            case "5":
                Accounting.feedMoney(5);
                break;
            case "10":
                Accounting.feedMoney(10);
                break;
            case "20":
                Accounting.feedMoney(20);
                break;
        }
    }
}
