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

public class Saver extends JFrame
{

    private static final String PARSING_ERROR = "PARSING ERROR";
    public GUI gui;
    private String path;

    public Saver(GUI gui)
    {

        super();
        this.gui = gui;

        this.setSize(250,100);
        this.setTitle("Save as");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));
        JPanel saveAsPanel = new JPanel();
        saveAsPanel.setLayout(new BoxLayout(saveAsPanel,BoxLayout.LINE_AXIS));
        JPanel savePanel = new JPanel();

        JLabel saveAs = new JLabel("  Save as :   ");

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

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e)
            {
                saveGUI();
            }
        });

        saveAsPanel.add(saveAs);
        saveAsPanel.add(dir);
        saveAsPanel.add(ser);

        savePanel.add(save);

        contentPane.add(saveAsPanel);
        contentPane.add(savePanel);

        this.setContentPane(contentPane);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);

    }

    private void saveGUI()
    {
        if(path.equals("")){
            JOptionPane.showMessageDialog(this, PARSING_ERROR);
        }
        else {
            try {
                FileOutputStream fos = new FileOutputStream(path+".ser");
                ObjectOutputStream out = new ObjectOutputStream(fos);
                out.writeObject(this.gui);
                out.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                gui.notifyObservers("Scenario has been saved.");
                dispose();
            }
        }
    }

}
