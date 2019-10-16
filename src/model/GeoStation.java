/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package model;

import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;
import view.GUI;

import java.util.ArrayList;

public class GeoStation extends Satellite
{

    protected double altitude = 15000000;
    protected double latitude = 52;
    protected double longitude;

    public String organisation;
    public String name;
    public String notification_reason;
    private static int counter;

    public GeoStation(String label, double altitude, double latitude, double longitude){
        this.label = label;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public GeoStation(double longitude, String organisation, String name, String notification_reason)
    {
        this.longitude = longitude;
        this.organisation = organisation;
        this.name = name;
        this.notification_reason = notification_reason;
        this.label = name+" (GeoStation"+counter+")";
        counter++;
    }

    @Override
    public ArrayList<String> getArgs()
    {
        ArrayList<String> res = new ArrayList<String> ();

        res.add("NULL");
        res.add(Double.toString(longitude));
        res.add("NULL");
        res.add(organisation);
        res.add(name);
        res.add(notification_reason);

        return res;
    }

    @Override
    public Material getMaterial()
    {
        return Material.GREEN;
    }

    @Override
    public String getShape()
    {
        return BasicMarkerShape.CYLINDER;
    }

    @Override
    public double getAltitude()
    {
        return altitude;
    }

    @Override
    public double getLatitude()
    {
        return latitude;
    }

    @Override
    public double getLongitude()
    {
        return longitude;
    }

    @Override
    public void updatePosition(GUI gui)
    {
        return;
    }
}
