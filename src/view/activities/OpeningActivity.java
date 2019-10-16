/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.activities;

import view.*;

import javax.swing.*;
import java.awt.event.*;

public class OpeningActivity extends JFrame
{

    public GUI gui;

    public OpeningActivity (GUI gui) {

        super();
        this.gui = gui;

        this.setTitle(GUI.TITLE);
        this.setSize(400,400);

        JPanel pane = new JPanel();
        pane.setLayout(null);

        JButton newScenarioButton = new JButton("New Scenario");
        newScenarioButton.setActionCommand("NEW");
        newScenarioButton.addActionListener(new InnerListener(gui));

        JButton openExistingScenarioButton = new JButton("Open existing scenario");
        openExistingScenarioButton.setActionCommand("OPEN");
        openExistingScenarioButton.addActionListener(new InnerListener(gui));

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(new InnerListener(gui));

        pane.add(newScenarioButton);
        pane.add(openExistingScenarioButton);
        pane.add(exitButton);

        newScenarioButton.setBounds(100,75,200,50);
        openExistingScenarioButton.setBounds(100,150,200,50);
        exitButton.setBounds(100,225,200,50);

        this.setResizable(false);
        this.setContentPane(pane);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private class InnerListener implements ActionListener
    {
        GUI gui;

        public InnerListener(GUI gui){
            this.gui = gui;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            if(command.equals("NEW")){
                (gui.mainActivity).setContentPane(new NewScenarioActivity(gui));
                return;
            }
            else if(command.equals("OPEN")) {
                new Openner(gui);
                return;
            }
            else if(command.equals("EXIT")){
                (gui.mainActivity).dispose();
                return;
            }
            else{
                return;
            }
        }
    }

}
