/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package model;

import view.GUI;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.*;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwind.render.markers.*;
import gov.nasa.worldwind.util.WWUtil;
import gov.nasa.worldwind.render.Material;

import java.io.IOException;
import java.util.*;

import static gov.nasa.worldwindx.examples.ApplicationTemplate.insertBeforeCompass;

public abstract class Satellite extends Node
{

    protected String label;
    protected double frequency = 1500000000;
    protected transient MarkerLayer markerLayer = new MarkerLayer();

    public abstract Material getMaterial();

    public abstract String getShape();

    @Override
    public String getLabel(){ return label; }

    @Override
    public double getFrequency() { return frequency; }

    @Override
    public void showOnGlobe(GUI gui) throws IOException, InterruptedException
    {
        // PATH
        RenderableLayer layer = new RenderableLayer();
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(new Material(WWUtil.makeRandomColor(null)));
        attrs.setOutlineWidth(2d);
        ArrayList<Position> pathPositions = new ArrayList<Position>();
        ArrayList<double[]> nodePath = this.getPath();
        for(double[] coor : nodePath){
            pathPositions.add(Position.fromDegrees(coor[1], coor[2], coor[0]));
        }
        Path path = new Path(pathPositions);
        path.setAttributes(attrs);
        path.setVisible(true);
        path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path.setPathType(AVKey.GREAT_CIRCLE);
        layer.addRenderable(path);
        insertBeforeCompass(gui.wwd, layer);
        // POSITION
        List<Marker> markers = new ArrayList<Marker>(1);
        markers.add(new BasicMarker(Position.fromDegrees(this.getLatitude(), this.getLongitude(),this.getAltitude()),
            new BasicMarkerAttributes(this.getMaterial(), this.getShape(), 1d, 10, 5)));
        if(this.markerLayer == null){
            this.markerLayer = new MarkerLayer();
        }
        this.markerLayer.setMarkers(markers);
        insertBeforeCompass(gui.wwd, this.markerLayer);

        return;

    }
}
