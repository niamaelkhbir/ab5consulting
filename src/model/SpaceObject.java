package model;

import view.GUI;

import java.io.*;
import java.util.ArrayList;

public interface SpaceObject extends Serializable
{
    String getLabel();
    double getAltitude();
    double getLatitude();
    double getLongitude();
    ArrayList<String> getArgs();
    void showOnGlobe(GUI gui) throws IOException, InterruptedException;
}
