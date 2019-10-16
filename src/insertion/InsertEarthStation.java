/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package insertion;

import model.EarthStation;
import view.GUI;

import javax.swing.*;
import java.awt.event.*;

public class InsertEarthStation extends InsertSpaceObject
{
    private GUI gui;

    public InsertEarthStation(GUI gui)
    {
        super();
        this.gui = gui;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        this.setContentPane(panel);

        this.setTitle("Insert new earth station");
        this.setSize(350,120);

        ArgGetter label = new ArgGetter("Label", this);
        ArgGetter latitude = new ArgGetter("Latitude",this);
        ArgGetter longitude = new ArgGetter("Longitude", this);

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
    public void addNode()
    {
        String label = this.arg_value.get("Label");
        double latitude = Double.parseDouble(this.arg_value.get("Latitude"));
        double longitude = Double.parseDouble(this.arg_value.get("Longitude"));
        EarthStation es = new EarthStation(label, latitude, longitude);
        gui.nodes.add(es);
        es.showOnGlobe(gui);
        gui.notifyObservers(es);
        this.dispose();
    }
}
