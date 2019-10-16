/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package scrapper;

import model.*;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import java.io.IOException;

public class SatScrapper {

    private static final String dataBaseUrl = "https://www.itu.int/snl/freqtab_snl.html";
    private static WebClient client = new WebClient();

    public double minFreq = 2000;
    public double maxFreq = 2200;
    public StationPurpose purpose = StationPurpose.ALL;
    public double minLong = -10;
    public double maxLong = 10;
    public StationType type = StationType.GEOSTATIONARY;
    public SubmissionReason reason = SubmissionReason.ALL;

    private void initClientOptions(){
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
    }

    public SatScrapper() {
        initClientOptions();
    }

    public SatScrapper(double minFreq,double maxFreq,StationPurpose purpose, double minLong,  double maxLong, StationType type, SubmissionReason reason) {
        initClientOptions();
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;
        this.purpose = purpose;
        this.minLong = minLong;
        this.maxLong = maxLong;
        this.type = type;
        this.reason = reason;
    }

    private HtmlPage getPage() {
        try {
            return this.client.getPage(SatScrapper.dataBaseUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HtmlPage getPageResult(){
        HtmlPage page = getPage();
        HtmlForm form = page.getFormByName("x");
        form.getInputByName("freq_low").setValueAttribute(Double.toString(minFreq));
        form.getInputByName("freq_hi").setValueAttribute(Double.toString(maxFreq));
        switch(purpose){
            case EMISSION:
                form.getInputByName("emi").setValueAttribute("E");
                break;
            case RECEPTION:
                form.getInputByName("emi").setValueAttribute("R");
                break;
        }
        form.getInputByName("long_from").setValueAttribute(Double.toString(minLong));
        form.getInputByName("long_to").setValueAttribute(Double.toString(maxLong));
        switch(type){
            case GEOSTATIONARY:
                form.getInputByName("categ").setValueAttribute("G");
                break;
            case NON_GEOSTATIONARY:
                form.getInputByName("categ").setValueAttribute("N");
                break;
            case EARTH_STATION:
                form.getInputByName("categ").setValueAttribute("E");
                break;
        }
        switch(reason){
            case API:
                form.getInputByName("ntf").setValueAttribute("A");
                break;
            case COORDINATION:
                form.getInputByName("ntf").setValueAttribute("C");
                break;
            case NOTIFICATION:
                form.getInputByName("ntf").setValueAttribute("N");
                break;
        }
        final HtmlSubmitInput button = form.getInputByName("sub0");
        try {
            return button.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Node> getResult() {
        ArrayList<Node> res = new ArrayList<Node>();
        NodeList tbody_list = this.getPageResult().getElementsByTagName("tbody");
        org.w3c.dom.Node tbody_desired;
        NodeList tr_list;
        switch (type){
            case GEOSTATIONARY:
                tbody_desired = tbody_list.item(1);
                tr_list = tbody_desired.getChildNodes();
                for (int i = 2 ; i<tr_list.getLength() - 2; i++ ){
                    org.w3c.dom.Node tr = tr_list.item(i);
                    NodeList td_list = tr.getChildNodes();
                    GeoStation gs = new GeoStation(Double.parseDouble(td_list.item(0).getTextContent()), td_list.item(1).getTextContent(),td_list.item(2).getTextContent(),td_list.item(3).getTextContent());
                    res.add(gs);
                }
                break;
            case NON_GEOSTATIONARY:
                tbody_desired = tbody_list.item(1);
                tr_list = tbody_desired.getChildNodes();
                for (int i = 2 ; i<tr_list.getLength() - 2; i++ ){
                    org.w3c.dom.Node tr = tr_list.item(i);
                    NodeList td_list = tr.getChildNodes();
                    NonGeoStation ngs = new NonGeoStation(td_list.item(0).getTextContent(), td_list.item(1).getTextContent(),td_list.item(2).getTextContent(),td_list.item(3).getTextContent());
                    res.add(ngs);

                }
                break;
            case EARTH_STATION:
                tbody_desired = tbody_list.item(0);
                tr_list = tbody_desired.getChildNodes();
                for (int i = 2 ; i<tr_list.getLength() - 2; i++ ){
                    org.w3c.dom.Node tr = tr_list.item(i);
                    NodeList td_list = tr.getChildNodes();
                    EarthStation es = new EarthStation(td_list.item(0).getTextContent(),Double.parseDouble(td_list.item(1).getTextContent()),Double.parseDouble(td_list.item(2).getTextContent()),td_list.item(3).getTextContent(),Double.parseDouble(td_list.item(4).getTextContent()),td_list.item(5).getTextContent(),td_list.item(6).getTextContent(),td_list.item(7).getTextContent());
                    res.add(es);

                }
                break;
        }
        return res;
    }


}