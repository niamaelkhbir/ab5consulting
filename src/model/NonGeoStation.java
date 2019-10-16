/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package model;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.*;
import view.GUI;

import java.util.*;

import static gov.nasa.worldwindx.examples.ApplicationTemplate.insertBeforeCompass;

public class NonGeoStation extends Satellite {

	public double altitude;
	public double anomaly;
	public double omega;
	public double inc;

	public String organisation;
	public String name;
	public String notification_reason;
	public String BR_IFIC;
	private static int counter = 0;

	private int pc;

	public NonGeoStation(String label, double altitude, double anomaly, double omega, double inc){
	    this.label = label;
		this.altitude = altitude;
		this.anomaly = anomaly;
		this.omega = omega;
		this.inc = inc;
	}

	public NonGeoStation(String organisation, String name, String notification_reason, String BR_IFIC)
	{
		this.organisation = organisation;
		this.name = name;
		this.notification_reason = notification_reason;
		this.BR_IFIC = BR_IFIC;
		this.label = name+" (NonGeoStation"+counter+")";
		counter++;
	}

	@Override
	public ArrayList<String> getArgs()
	{
		ArrayList<String> res = new ArrayList<String> ();

		res.add("NULL");
		res.add("NULL");
		res.add("NULL");
		res.add(organisation);
		res.add(name);
		res.add(notification_reason);
		res.add(BR_IFIC);

		return res;
	}

	@Override
	public double getAltitude()
	{
		return altitude;
	}

	@Override
	public double getLatitude()
	{
		return path.get(pc%path.size())[1];
	}

	@Override
	public double getLongitude()
	{
		return path.get(pc%path.size())[2];
	}

	@Override
	public Material getMaterial()
	{
		return Material.RED;
	}

	@Override
	public String getShape()
	{
		return BasicMarkerShape.SPHERE;
	}

	@Override
	public void updatePosition(GUI gui)
	{
	    if(path == null || !(path.size() == Calculations.getNmax())){
	        return;
        }
		double[] newPosition = path.get(pc%path.size());
		List<Marker> markers = new ArrayList<>(1);
		markers.add(new BasicMarker(Position.fromDegrees(newPosition[1],newPosition[2],newPosition[0]), new BasicMarkerAttributes(this.getMaterial(), this.getShape(), 1d, 10, 5)));
		this.markerLayer.setMarkers(markers);
        insertBeforeCompass(gui.wwd, this.markerLayer);
		pc++;
		return;
	}

}
