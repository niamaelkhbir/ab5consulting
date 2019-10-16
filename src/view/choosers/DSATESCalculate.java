/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.choosers;

import model.*;
import view.GUI;

import java.io.IOException;
import java.util.ArrayList;

public class DSATESCalculate extends SpaceObjectChooser
{

    public GUI gui;

    public DSATESCalculate(GUI gui)
    {
        this.gui = gui;

        ArrayList<NonGeoStation> leftArray = new ArrayList<>();
        ArrayList<EarthStation> rightArray = new ArrayList<>();
        for(Node node: gui.nodes){
            if(node instanceof NonGeoStation){
                leftArray.add((NonGeoStation) node);
            }
            else if (node instanceof EarthStation){
                rightArray.add((EarthStation)node);
            }
        }

        this.title = "Choose a non-geostationnary station and an earth station to perform calculations";

        this.left = new Satellite[leftArray.size()];
        for(int i = 0; i<leftArray.size(); i++){
            this.left[i] = leftArray.get(i);
        }

        this.right = new EarthStation[rightArray.size()];
        for(int i = 0; i<rightArray.size(); i++){
            this.right[i] = rightArray.get(i);
        }

        init();

    }

    @Override
    public void activate(SpaceObject leftObject, SpaceObject rightObject)
    {
        try
        {
            Calculations.plotD_SAT_ES((NonGeoStation) leftObject,(EarthStation)rightObject);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            showErrorMessage();
        }
        finally
        {
            gui.notifyObservers("Distance from the earth station : "+rightObject.getLabel() +" has been plotted for the satellite : "
                +leftObject.getLabel());
            dispose();
        }
    }
}