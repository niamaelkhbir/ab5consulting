/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.*;
import view.GUI;

import java.io.IOException;
import java.util.*;

import static gov.nasa.worldwindx.examples.ApplicationTemplate.insertBeforeCompass;

public class Sensor implements SpaceObject
{
    private String label;
    private double altitude;
    private double latitude;
    private double longitude;

    public Sensor(String label, double altitude, double latitude, double longitude)
    {
        this.label = label;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String getLabel()
    {
        return label;
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
    public ArrayList<String> getArgs()
    {
        ArrayList<String> res = new ArrayList<String> ();

        res.add(label);
        res.add(Double.toString(altitude));
        res.add(Double.toString(latitude));
        res.add(Double.toString(longitude));

        return res;
    }

    @Override
    public void showOnGlobe(GUI gui) throws IOException, InterruptedException
    {
        List<Marker> markers = new ArrayList<Marker>(1);
        markers.add(new BasicMarker(Position.fromDegrees(this.getLatitude(), this.getLongitude(),this.getAltitude()),
            new BasicMarkerAttributes(Material.YELLOW,  BasicMarkerShape.CONE, 1d, 10, 2.5)));
        MarkerLayer markerLayer = new MarkerLayer();
        markerLayer.setMarkers(markers);
        insertBeforeCompass(gui.wwd, markerLayer);
        return;
    }
}
