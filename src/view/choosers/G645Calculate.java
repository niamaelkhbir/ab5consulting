/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.choosers;

import model.*;
import view.GUI;

import java.io.IOException;

public class G645Calculate extends SpaceObjectChooser
{

    public GUI gui;

    public G645Calculate(GUI gui)
    {
        this.gui = gui;

        this.title = "Choose a node and a sensor to perform calculations";

        this.left = new Node[gui.nodes.size()];
        for(int i = 0; i<gui.nodes.size(); i++){
            this.left[i] = gui.nodes.get(i);
        }

        this.right = new Sensor[gui.sensors.size()];
        for(int i = 0; i<gui.sensors.size(); i++){
            this.right[i] = gui.sensors.get(i);
        }

        init();

    }

    @Override
    public void activate(SpaceObject leftObject, SpaceObject rightObject)
    {
        try
        {
            double res = Calculations.G_645((Node)leftObject,(Sensor)rightObject);
            gui.notifyObservers("Sensor "+rightObject.getLabel()+" detects a signal from node "+leftObject.getLabel()+" with a gain of : "+res+" (Recommendation 645)");
            return;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            showErrorMessage();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            dispose();
        }
    }
}
