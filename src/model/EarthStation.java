/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package model;

import gov.nasa.worldwind.render.Material;
import view.GUI;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.render.markers.*;

import java.util.*;

import static gov.nasa.worldwindx.examples.ApplicationTemplate.insertBeforeCompass;

public class EarthStation extends Node {

    private String label;
    private double latitude;
    private double longitude;
    private double frequency = 1500000000;

    public String name;
    public String sat_name;
    public double position;
    public String organisation;
    public String BR_IFIC;
    public String category;
    private static int counter;

    public EarthStation(String label, double latitude, double longitude){
        this.label = label;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public EarthStation(String name, double latitude, double longitude, String sat_name, double position,
        String organisation, String BR_IFIC, String category)
    {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.sat_name = sat_name;
        this.position = position;
        this.organisation = organisation;
        this.BR_IFIC = BR_IFIC;
        this.category = category;
        this.label = name+" (EarthStation"+counter+")";
        counter++;
    }

    //Getters
    @Override
    public double getAltitude() {
        return 0;
    }
    @Override
    public double getLatitude() {
        return latitude;
    }
    @Override
    public double getLongitude() {
        return longitude;
    }
    @Override
    public String getLabel(){
        return label;
    }
    @Override
    public double getFrequency() { return frequency; }

    @Override
    public void updatePosition(GUI gui)
    {
        return;
    }

    @Override
    public ArrayList<String> getArgs()
    {
        ArrayList<String> res = new ArrayList<String> ();

        res.add("NULL");
        res.add(name);
        res.add(Double.toString(longitude));
        res.add(Double.toString(latitude));
        res.add(sat_name);
        res.add(Double.toString(position));
        res.add(organisation);
        res.add(BR_IFIC);
        res.add(category);

        return res;
    }

    @Override
    public void showOnGlobe(GUI gui)
    {
        List<Marker> markers = new ArrayList<Marker>(1);
        markers.add(new BasicMarker(Position.fromDegrees(this.getLatitude(), this.getLongitude(),this.getAltitude()),
            new BasicMarkerAttributes(Material.WHITE,  BasicMarkerShape.CUBE, 1d, 10, 5)));
        MarkerLayer markerLayer = new MarkerLayer();
        markerLayer.setMarkers(markers);
        insertBeforeCompass(gui.wwd, markerLayer);

        return;
    }
}
