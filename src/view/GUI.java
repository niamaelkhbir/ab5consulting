/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view;

import model.*;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import view.activities.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class GUI extends Observable implements Serializable, Runnable
{

    public static final String TITLE = "AB5Consulting";
    public transient final JFrame mainActivity = new OpeningActivity(this);
    public transient final WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
    public ArrayList<Node> nodes = new ArrayList<Node>();
    public ArrayList<Sensor> sensors = new ArrayList<Sensor>();
    private transient ArrayList<Observer> observers = new ArrayList<Observer>();

    public GUI()
    {
        mainActivity.setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
        new Thread(this).start();
    }

    public GUI(ArrayList<Node> nodes, ArrayList<Sensor> sensors) throws IOException, InterruptedException
    {
        mainActivity.setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
        this.nodes = nodes;
        this.sensors = sensors;
        (this.mainActivity).setContentPane(new NewScenarioActivity(this));
        for(Node node: nodes){
            node.showOnGlobe(this);
            this.notifyObservers(node);
        }
        for(Sensor sensor: sensors){
            sensor.showOnGlobe(this);
            this.notifyObservers(sensor);
        }
        new Thread(this).start();
    }

    @Override
    public void addObserver(Observer o){
        observers.add(o);
    }

    @Override
    public void notifyObservers(Object o){
        for(Observer ob:observers){
            ob.update(this,o);
        }
    }

    public void dispose()
    {
        this.mainActivity.dispose();
    }

    @Override
    public void run()
    {
        while(true){
            synchronized (this)
            {
                for (Node node : this.nodes)
                {
                    node.updatePosition(this);
                }
                try
                {
                    wait(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new GUI();
            }
        });
    }

}
