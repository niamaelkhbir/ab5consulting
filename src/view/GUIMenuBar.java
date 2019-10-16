/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view;

import insertion.InsertSensor;
import model.*;
import setters.*;
import view.activities.*;
import view.choosers.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GUIMenuBar extends JMenuBar
{

    private static final String EXAMPLE = "101512624SADFZ446651";
    public GUI gui;

    public GUIMenuBar(GUI gui){

        super();
        this.gui = gui;

        JMenu file = new JMenu("File");
        JMenu set = new JMenu("Set");
        JMenu insert = new JMenu("Insert");
        JMenu calculate = new JMenu("Calculate");
        JMenu help = new JMenu("Help");

        JMenuItem nouveau = new JMenuItem("New scenario");
        nouveau.setActionCommand("new");
        nouveau.addActionListener(new InnerListener(this));

        JMenuItem open = new JMenuItem("Open ...");
        open.setActionCommand("open");
        open.addActionListener(new InnerListener(this));

        JMenuItem save = new JMenuItem("Save");
        save.setActionCommand("save");
        save.addActionListener(new InnerListener(this));

        JMenuItem saveAsAnExample = new JMenuItem("Save as an example");
        saveAsAnExample.setActionCommand("saveAsAnExample");
        saveAsAnExample.addActionListener(new InnerListener(this));

        JMenuItem exit = new JMenuItem("Exit");
        exit.setActionCommand("exit");
        exit.addActionListener(new InnerListener(this));

        JMenuItem dt = new JMenuItem("time-step");
        dt.setActionCommand("dt");
        dt.addActionListener(new InnerListener(this));

        JMenuItem nDays = new JMenuItem("Number of days simulated");
        nDays.setActionCommand("nDays");
        nDays.addActionListener(new InnerListener(this));

        JMenu node = new JMenu("Node");

        JMenuItem sensor = new JMenuItem("Sensor");
        sensor.setActionCommand("sensor");
        sensor.addActionListener(new InnerListener(this));

        JMenuItem g850 = new JMenuItem("Gain (Recommendation 850)");
        g850.setActionCommand("g850");
        g850.addActionListener(new InnerListener(this));

        JMenuItem g645 = new JMenuItem("Gain (Recommendation 645)");
        g645.setActionCommand("g645");
        g645.addActionListener(new InnerListener(this));

        JMenuItem dSAT_ES = new JMenuItem("Distance between a non-geostationnary station and an earth station");
        dSAT_ES.setActionCommand("dsates");
        dSAT_ES.addActionListener(new InnerListener(this));

        JMenuItem plotAz_Elv = new JMenuItem("Plot elevation as a function of azimuth with respect to an earth station");
        plotAz_Elv.setActionCommand("plotazelv");
        plotAz_Elv.addActionListener(new InnerListener(this));

        JMenuItem example = new JMenuItem("Example");
        example.setActionCommand("example");
        example.addActionListener(new InnerListener(this));

        JMenuItem about = new JMenuItem("About");
        about.setActionCommand("about");
        about.addActionListener(new InnerListener(this));

        JMenuItem fromdatabase = new JMenuItem("From database");
        fromdatabase.setActionCommand("fromdatabase");
        fromdatabase.addActionListener(new InnerListener(this));

        JMenuItem insertonglobe = new JMenuItem("Insert on globe");
        insertonglobe.setActionCommand("insertonglobe");
        insertonglobe.addActionListener(new InnerListener(this));

        file.add(nouveau);
        file.add(open);
        file.add(save);
        file.add(saveAsAnExample);
        file.add(exit);

        set.add(dt);
        set.add(nDays);

        insert.add(node);
        insert.add(sensor);

        calculate.add(g850);
        calculate.add(g645);
        calculate.add(dSAT_ES);
        calculate.add(plotAz_Elv);

        help.add(example);
        help.add(about);

        node.add(fromdatabase);
        node.add(insertonglobe);

        this.add(file);
        this.add(set);
        this.add(insert);
        this.add(calculate);
        this.add(help);

    }

    private class InnerListener implements ActionListener
    {

        public GUIMenuBar outer;

        public InnerListener(GUIMenuBar outer){
            this.outer = outer;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            if(command.equals("fromdatabase")){
                new FromDatabaseFrame(outer.gui);
            }
            else if (command.equals("insertonglobe")){
                new InsertOnGlobe(outer.gui);
            }
            else if (command.equals("dsates")){
                new DSATESCalculate(gui);
            }
            else if (command.equals("plotazelv")){
                new PlotAzElvCalculate(gui);
            }
            else if (command.equals("sensor")){
                new InsertSensor(gui);
            }
            else if (command.equals("g850")){
                new G850Calculate(gui);
            }
            else if (command.equals("g645")){
                new G645Calculate(gui);
            }
            else if (command.equals("exit")){
                gui.mainActivity.dispose();
            }
            else if (command.equals("save")){
                new Saver(gui);
            }
            else if (command.equals("saveAsAnExample")){
                try {
                    FileOutputStream fos = new FileOutputStream(EXAMPLE+".ser");
                    ObjectOutputStream out = new ObjectOutputStream(fos);
                    out.writeObject(outer.gui);
                    out.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    gui.notifyObservers("Scenario has been saved as an example.");
                }
            }
            else if (command.equals("open")){
                new Openner(gui);
            }
            else if (command.equals("new")){
                try
                {
                    new GUI(new ArrayList< Node >(),new ArrayList< Sensor >());
                }
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(gui.mainActivity , "An has occured");
                }
            }
            else if (command.equals("example")){
                try {
                    FileInputStream fis = new FileInputStream(EXAMPLE+".ser");
                    ObjectInputStream in = new ObjectInputStream(fis);
                    GUI newGUI = (GUI) in.readObject();
                    new GUI(newGUI.nodes,newGUI.sensors);
                    gui.dispose();
                    in.close();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    gui.dispose();
                }
            }
            else if (command.equals("about")){
                new About();
            }
            else if (command.equals("dt")){
                new Set_dt(gui);
            }
            else if (command.equals("nDays")){
                new Set_nDays(gui);
            }
            else {
                throw new Error("vérifie les commandes");
            }
        }
    }
}
