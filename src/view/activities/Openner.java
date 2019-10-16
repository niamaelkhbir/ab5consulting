/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.activities;

import view.GUI;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Openner extends JFrame
{

    private static final String PARSING_ERROR = "PARSING ERROR";
    public GUI gui;
    private String path;

    public Openner(GUI gui){
        super();
        this.gui = gui;

        this.setSize(250,100);
        this.setTitle("Open");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
        JPanel openAsPanel = new JPanel();
        openAsPanel.setLayout(new BoxLayout(openAsPanel,BoxLayout.LINE_AXIS));
        JPanel openPanel = new JPanel();

        JLabel openAs = new JLabel("  Open :   ");

        final JTextArea dir = new JTextArea(1,10);
        dir.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                return;
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                path = dir.getText();
                return;
            }

        });

        JLabel ser = new JLabel(".ser  ");

        JButton open = new JButton("Open");
        open.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e)
            {
                openGUI();
            }
        });

        openAsPanel.add(openAs);
        openAsPanel.add(dir);
        openAsPanel.add(ser);

        openPanel.add(open);

        contentPane.add(openAsPanel);
        contentPane.add(openPanel);

        this.setContentPane(contentPane);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    private void openGUI()
    {
        if(path.equals("")){
            JOptionPane.showMessageDialog(this, PARSING_ERROR);
        }
        else {
            try {
                FileInputStream fis = new FileInputStream(path+".ser");
                ObjectInputStream in = new ObjectInputStream(fis);
                GUI newGUI = (GUI) in.readObject();
                new GUI(newGUI.nodes,newGUI.sensors);
                this.gui.dispose();
                in.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                dispose();
            }
        }
    }
}
