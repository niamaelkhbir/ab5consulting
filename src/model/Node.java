/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package model;

import view.GUI;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Node implements SpaceObject
{

    protected ArrayList<double[]> path = null;

    public ArrayList<double[]> getPath() throws IOException, InterruptedException
    {
        if(this.path == null || !(this.path.size() == Calculations.getNmax())){
            this.path = Calculations.getPath(this);
        }
        return path;
    }

    public abstract double getFrequency();

    public abstract void updatePosition(GUI gui);

}
