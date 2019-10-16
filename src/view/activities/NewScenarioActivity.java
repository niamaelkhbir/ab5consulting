/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.activities;


import javax.swing.*;

import gov.nasa.worldwind.*;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.util.StatusBar;
import gov.nasa.worldwindx.examples.*;

import model.*;
import view.*;

import java.awt.*;
import java.util.*;

public class NewScenarioActivity extends JPanel
{

    public GUI gui;

    public NewScenarioActivity(GUI gui){

        super();

        this.gui = gui;

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        GUIMenuBar menuBar = new GUIMenuBar(gui);

        (this.gui.mainActivity).setJMenuBar(menuBar);

        JPanel globePanel = new JPanel(new BorderLayout());
        if(gui.wwd == null){
            JOptionPane.showMessageDialog(gui.mainActivity , "Initialisation Error");
        }
        gui.wwd.setPreferredSize(new Dimension(800,600));
        Model m = (Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME);
        gui.wwd.setModel(m);
        gui.wwd.addSelectListener(new ClickAndGoSelectListener(gui.wwd, WorldMapLayer.class));
        StatusBar statusBar = new StatusBar();
        globePanel.add(statusBar, BorderLayout.PAGE_END);
        statusBar.setEventSource(gui.wwd);
        globePanel.add(gui.wwd,BorderLayout.CENTER);

        bottomPanel bottom = new bottomPanel(this.gui);
        bottom.setPreferredSize(new Dimension(800,400));

        this.add(globePanel);
        this.add(bottom);

        this.gui.mainActivity.setSize(800,1000);

    }

    private class bottomPanel extends JTabbedPane implements Observer
    {

        public GUI gui;
        public JPanel results = new JPanel();
        public JPanel data = new JPanel();

        public bottomPanel(GUI gui){
            super();
            this.gui = gui;
            this.gui.addObserver(this);
            results.setLayout(new BoxLayout(results,BoxLayout.PAGE_AXIS));
            data.setLayout(new BoxLayout(data,BoxLayout.PAGE_AXIS));
            JScrollPane scrollResults = new JScrollPane(results);
            JScrollPane scrollData = new JScrollPane(data);
            this.add("results", scrollResults);
            this.add("data", scrollData);
        }

        @Override
        public void update(Observable o, Object arg)
        {
            if(arg instanceof SpaceObject){
                JLabel event = new JLabel("A new node was added: "+((SpaceObject)arg).getLabel());
                this.data.add(event);
                this.data.updateUI();
                return;
            }
            else{
                JLabel result = new JLabel((String)arg);
                this.results.add(result);
                this.results.updateUI();
                return;
            }
        }
    }

}
