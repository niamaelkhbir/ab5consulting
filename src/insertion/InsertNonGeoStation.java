/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package insertion;

import model.NonGeoStation;
import view.GUI;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class InsertNonGeoStation extends InsertSpaceObject
{
    private GUI gui;

    public InsertNonGeoStation(GUI gui)
    {
        super();
        this.gui = gui;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        this.setContentPane(panel);

        this.setTitle("Insert new non-geo-stationnary station");
        this.setSize(400,180);

        ArgGetter label = new ArgGetter("Label", this);
        ArgGetter altitude = new ArgGetter("Altitude", this);
        ArgGetter anomaly = new ArgGetter("Anomaly",this);
        ArgGetter omega = new ArgGetter("Omega", this);
        ArgGetter inc = new ArgGetter("Inc", this);

        JButton add = new JButton("Add");

        add.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try{
                    addNode();
                }
                catch(Exception exp){
                    exp.printStackTrace();
                    showErrorMessage();
                }
                finally
                {
                    return;
                }
            }
        });

        JPanel addPanel = new JPanel();
        addPanel.add(add);

        this.getContentPane().add(addPanel);

        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void addNode() throws IOException, InterruptedException
    {
        String label = this.arg_value.get("Label");
        double altitude = Double.parseDouble(this.arg_value.get("Altitude"));
        double anomaly = Double.parseDouble(this.arg_value.get("Anomaly"));
        double omega = Double.parseDouble(this.arg_value.get("Omega"));
        double inc = Double.parseDouble(this.arg_value.get("Inc"));
        NonGeoStation ngs = new NonGeoStation(label, altitude, anomaly, omega, inc);
        gui.nodes.add(ngs);
        ngs.showOnGlobe(gui);
        gui.notifyObservers(ngs);
        this.dispose();
    }
}
