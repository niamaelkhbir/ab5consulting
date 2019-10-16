package model;

import java.io.*;
import java.util.*;

public class Calculations
{

    private static final double Tearth = 86164.1;

    private static double dt = 60;
    private static int nDays = 2;
    private static int Nmax = (int)(nDays *Tearth/ dt) + 1;
    private static String globals = nDays+" "+dt;

    public static void setNDays(int nDays)
    {
        Calculations.nDays = nDays;
        Calculations.Nmax = (int)(nDays*Tearth/ dt) + 1;
        Calculations.globals = nDays+" "+dt;
        return;
    }

    public static void setDt(double dt)
    {
        Calculations.dt = dt;
        Calculations.Nmax = (int)(nDays*Tearth/ dt) + 1;
        Calculations.globals = nDays+" "+dt;
        return;
    }

    public static int getNmax()
    {
        return Nmax;
    }

    public static double G_850 (double frequency, double D, double phi) throws InterruptedException, IOException
    {
        int code = 5;
        String args = frequency+" "+D+" "+phi;
        Process pr = Runtime.getRuntime().exec("python pyScripts.py "+args+" "+globals+" "+code);
        pr.waitFor();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String ret = bfr.readLine();
        bfr.close();
        pr.destroy();
        double G = Double.parseDouble(ret);
        return G;
    }

    public static double G_850(Node node, Sensor sensor) throws InterruptedException, IOException
    {
        double nodeAlt = node.getAltitude();
        double nodeLat = node.getLatitude();
        double nodeLong = node.getLongitude();
        double sensorAlt = sensor.getAltitude();
        double sensorLat = sensor.getLatitude();
        double sensorLong = sensor.getLongitude();
        int code = 7;
        String args = nodeAlt+" "+nodeLat+" "+nodeLong+" "+sensorAlt+" "+sensorLat+" "+sensorLong;
        Process pr = Runtime.getRuntime().exec("python pyScripts.py "+args+" "+globals+" "+code);
        pr.waitFor();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String d_str = bfr.readLine();
        String phi_str = bfr.readLine();
        bfr.close();
        pr.destroy();
        double d = Double.parseDouble(d_str);
        double phi = Double.parseDouble(phi_str);
        return G_850(node.getFrequency(),d,phi);
    }

    public static double G_645 (double frequency, double D, double phi) throws IOException, InterruptedException
    {
        int code = 4;
        String args = frequency+" "+D+" "+phi;
        Process pr = Runtime.getRuntime().exec("python pyScripts.py "+args+" "+globals+" "+code);
        pr.waitFor();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String ret = bfr.readLine();
        bfr.close();
        pr.destroy();
        double G = Double.parseDouble(ret);
        return G;
    }

    public static double G_645(Node node, Sensor sensor) throws InterruptedException, IOException
    {
        double nodeAlt = node.getAltitude();
        double nodeLat = node.getLatitude();
        double nodeLong = node.getLongitude();
        double sensorAlt = sensor.getAltitude();
        double sensorLat = sensor.getLatitude();
        double sensorLong = sensor.getLongitude();
        int code = 7;
        String args = nodeAlt+" "+nodeLat+" "+nodeLong+" "+sensorAlt+" "+sensorLat+" "+sensorLong;
        Process pr = Runtime.getRuntime().exec("python pyScripts.py "+args+" "+globals+" "+code);
        pr.waitFor();
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String d_str = bfr.readLine();
        String phi_str = bfr.readLine();
        bfr.close();
        pr.destroy();
        double d = Double.parseDouble(d_str);
        double phi = Double.parseDouble(phi_str);
        return G_645(node.getFrequency(),d,phi);
    }

    public static void plotGs(double frequency, double D) throws IOException
    {
        int code = 11;
        String args = frequency+" "+D;
        Runtime.getRuntime().exec("python pyScripts.py "+args+" "+globals+" "+code);
        return;
    }

    public static void plotD_SAT_ES(NonGeoStation ngs, EarthStation es) throws IOException
    {

        double satAlt = ngs.altitude;
        double satAnomaly = ngs.anomaly;
        double satOmega = ngs.omega;
        double satInc = ngs.inc;
        double esLat = es.getLatitude();
        double esLong = es.getLongitude();

        int code = 10;

        String args = satAlt+" "+satAnomaly+" "+satOmega+" "+satInc+" "+esLat+" "+esLong;

        Runtime.getRuntime().exec("python pyScripts.py "+args+" "+globals+" "+code);

        return;

    }

    public static ArrayList<double[]> getPath(Node node) throws IOException, InterruptedException
    {
        ArrayList<double[]> path = new ArrayList<>();
        if(node instanceof EarthStation || node instanceof GeoStation){
            double[] coor = {node.getAltitude(),node.getLatitude(),node.getLongitude()};
            path.add(coor);
        }
        else {

            double altitude = ((NonGeoStation)node).altitude;
            double anomaly = ((NonGeoStation)node).anomaly;
            double omega = ((NonGeoStation)node).omega;
            double inc = ((NonGeoStation)node).inc;
            int code = 20;
            String args = altitude+" "+anomaly+" "+omega+" "+inc;

            String res;

            final Runtime r = Runtime.getRuntime();
            final Process p = r.exec("python pyScripts.py "+args+" "+globals+" "+code);
            try (final BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                res = b.readLine();
            }
            p.waitFor();

            String[] RES = res.split(" ");

            for(int i = 0; i < Nmax; i++){
                String latitude_t_str = RES[2*i];
                String longitude_t_str = RES[2*i+1];
                double[] coor = new double[3];
                coor[0] = altitude;
                coor[1] = Double.parseDouble(latitude_t_str);
                coor[2] = Double.parseDouble(longitude_t_str);
                path.add(coor);
            }

            p.destroy();
        }
        return path;
    }

    public static void plotAz_Elv(NonGeoStation ngs, EarthStation es) throws IOException
    {

        double satAlt = ngs.altitude;
        double satAnomaly = ngs.anomaly;
        double satOmega = ngs.omega;
        double satInc = ngs.inc;
        double esLat = es.getLatitude();
        double esLong = es.getLongitude();

        int code = 9;

        String args = satAlt+" "+satAnomaly+" "+satOmega+" "+satInc+" "+esLat+" "+esLong;

        Runtime.getRuntime().exec("python pyScripts.py "+args+" "+globals+" "+code);

        return;

    }

}
