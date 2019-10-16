/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.choosers;

import insertion.*;
import view.GUI;

import javax.swing.*;
import java.awt.event.*;

public class InsertOnGlobe extends JFrame
{

    public GUI gui;

    public InsertOnGlobe(GUI gui)
    {
        super();
        this.gui = gui;

        this.setTitle("Insert new node");
        this.setSize(500,400);

        JPanel pane = new JPanel();
        pane.setLayout(null);

        JButton new_earth_station = new JButton("New earth station");
        new_earth_station.setActionCommand("earthstation");
        new_earth_station.addActionListener(new InnerListener(this));

        JButton new_geostationnary_station = new JButton("New geostationnary station");
        new_geostationnary_station.setActionCommand("geostation");
        new_geostationnary_station.addActionListener(new InnerListener(this));

        JButton new_nongeostationnary_station = new JButton("New non-geostationnary station");
        new_nongeostationnary_station.setActionCommand("nongeostation");
        new_nongeostationnary_station.addActionListener(new InnerListener(this));

        pane.add(new_earth_station);
        pane.add(new_geostationnary_station);
        pane.add(new_nongeostationnary_station);

        new_earth_station.setBounds(100,75,300,50);
        new_geostationnary_station.setBounds(100,150,300,50);
        new_nongeostationnary_station.setBounds(100,225,300,50);

        this.setResizable(false);
        this.setContentPane(pane);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    private class InnerListener implements ActionListener
    {
        InsertOnGlobe outer;

        public InnerListener(InsertOnGlobe outer){
            this.outer = outer;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            if(command.equals("earthstation")){
                outer.dispose();
                new InsertEarthStation(outer.gui);
            }
            else if(command.equals("geostation")){
                outer.dispose();
                new InsertGeoStation(outer.gui);
            }
            else if(command.equals("nongeostation")){
                outer.dispose();
                new InsertNonGeoStation(outer.gui);
            }
            else{
                throw new Error("vérifie les commandes");
            }
        }
    }
}
