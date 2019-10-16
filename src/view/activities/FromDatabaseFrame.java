/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.activities;

import model.Node;
import scrapper.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FromDatabaseFrame extends JFrame
{

    private static final SatScrapper scrapper = new SatScrapper();
    private static final String PARSING_ERROR = "Ce champ n'accepte que des nombres";
    public GUI gui;


    public FromDatabaseFrame(GUI gui){

        super();
        this.gui = gui;

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Earth stations database");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(8,2));

        final TextArea minFreq = new TextArea("2000");
        minFreq.addFocusListener(new InnerListener(this,minFreq,1));
        JLabel minFreqLabel = new JLabel("Minimum frequency :");

        TextArea maxFreq = new TextArea("2200");
        maxFreq.addFocusListener(new InnerListener(this,maxFreq,2));
        JLabel maxFreqLabel = new JLabel("Maximum frequency :");

        ButtonGroup purposeGroup = new ButtonGroup();
        JRadioButton emission = new JRadioButton("Emission");
        emission.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.purpose = StationPurpose.EMISSION;
            }
        });
        JRadioButton reception = new JRadioButton("Reception");
        reception.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.purpose = StationPurpose.RECEPTION;
            }
        });
        JRadioButton allPurpose = new JRadioButton("All",true);
        allPurpose.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.purpose = StationPurpose.ALL;
            }
        });
        purposeGroup.add(emission);
        purposeGroup.add(reception );
        purposeGroup.add(allPurpose);
        JPanel purpose = new JPanel();
        purpose.setLayout(new BoxLayout(purpose,BoxLayout.PAGE_AXIS));
        purpose.add(emission);
        purpose.add(reception);
        purpose.add(allPurpose);
        JLabel purposeLabel = new JLabel("Purpose :");

        TextArea minLong = new TextArea("-10");
        minLong.addFocusListener(new InnerListener(this,minLong,3));
        JLabel minLongLabel = new JLabel("Minimum longitude :");

        TextArea maxLong = new TextArea("10");
        maxLong.addFocusListener(new InnerListener(this,maxLong,4));
        JLabel maxLongLabel = new JLabel("Maximum longitude :");

        ButtonGroup typeGroup = new ButtonGroup();
        JRadioButton geostation = new JRadioButton("Geostationary",true);
        geostation.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.type = StationType.GEOSTATIONARY;
            }
        });
        JRadioButton nongeostation = new JRadioButton("Non Geostationary");
        nongeostation.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.type = StationType.NON_GEOSTATIONARY;
            }
        });
        JRadioButton earthstation = new JRadioButton("Earth Station");
        earthstation.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.type = StationType.EARTH_STATION;
            }
        });
        typeGroup.add(geostation);
        typeGroup.add(nongeostation);
        typeGroup.add(earthstation);
        JPanel type = new JPanel();
        type.setLayout(new BoxLayout(type,BoxLayout.PAGE_AXIS));
        type.add(geostation);
        type.add(nongeostation);
        type.add(earthstation);
        JLabel typeLabel = new JLabel("Type :");


        ButtonGroup reasonGroup = new ButtonGroup();
        JRadioButton api = new JRadioButton("API");
        api.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.reason = SubmissionReason.API;
            }
        });
        JRadioButton coordination = new JRadioButton("Coordination");
        coordination.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.reason = SubmissionReason.COORDINATION;
            }
        });
        JRadioButton notification = new JRadioButton("Notification");
        notification.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.reason = SubmissionReason.NOTIFICATION;
            }
        });
        JRadioButton allReason = new JRadioButton("All",true);
        allReason.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FromDatabaseFrame.scrapper.reason = SubmissionReason.ALL;
            }
        });
        reasonGroup.add(api);
        reasonGroup.add(coordination);
        reasonGroup.add(notification);
        reasonGroup.add(allReason);
        JPanel reason = new JPanel();
        reason.setLayout(new BoxLayout(reason,BoxLayout.PAGE_AXIS));
        reason.add(api);
        reason.add(coordination);
        reason.add(notification);
        reason.add(allReason);
        JLabel reasonLabel = new JLabel("Reason :");

        JButton ok = new JButton("Ok");
        ok.addActionListener(new InnerListener(this,6));
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new InnerListener(this,5));

        contentPane.add(minFreqLabel);
        contentPane.add(minFreq);
        contentPane.add(maxFreqLabel);
        contentPane.add(maxFreq);
        contentPane.add(purposeLabel);
        contentPane.add(purpose);
        contentPane.add(minLongLabel);
        contentPane.add(minLong);
        contentPane.add(maxLongLabel);
        contentPane.add(maxLong);
        contentPane.add(typeLabel);
        contentPane.add(type);
        contentPane.add(reasonLabel);
        contentPane.add(reason);
        contentPane.add(ok);
        contentPane.add(cancel);

        this.setContentPane(contentPane);
        this.pack();
        this.setVisible(true);

    }

    private class InnerListener implements FocusListener, ActionListener {

        public FromDatabaseFrame outer;
        public TextArea textArea;
        public int field;

        public InnerListener(FromDatabaseFrame outer,TextArea textArea,int field){
            this.outer = outer;
            this.textArea = textArea;
        }

        public InnerListener(FromDatabaseFrame outer,int field){
            this.outer = outer;
            this.field = field;
        }
        
        @Override
        public void focusGained(FocusEvent e)
        {
        }

        @Override
        public void focusLost(FocusEvent e)
        {
            switch(field)
            {
                case(1):
                    try
                    {
                        scrapper.minFreq = Double.parseDouble(textArea.getText());
                    }
                    catch (Exception exp)
                    {
                        exp.printStackTrace();
                        JOptionPane.showMessageDialog(outer, PARSING_ERROR);
                    }
                    break;

                case(2):
                    try
                    {
                        scrapper.maxFreq = Double.parseDouble(textArea.getText());
                    }
                    catch (Exception exp)
                    {
                        exp.printStackTrace();
                        JOptionPane.showMessageDialog(outer, PARSING_ERROR);
                    }
                    break;

                case(3):
                    try
                    {
                        scrapper.minLong = Double.parseDouble(textArea.getText());
                    }
                    catch (Exception exp)
                    {
                        exp.printStackTrace();
                        JOptionPane.showMessageDialog(outer, PARSING_ERROR);
                    }
                    break;

                case(4):
                    try
                    {
                        scrapper.maxLong = Double.parseDouble(textArea.getText());
                    }
                    catch (Exception exp)
                    {
                        exp.printStackTrace();
                        JOptionPane.showMessageDialog(outer, PARSING_ERROR);
                    }
                    break;

                default:
                    break;

            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            switch(field){
                case(5):
                    outer.dispose();
                    break;

                case(6):
                    JPanel resultsPanel = new JPanel();
                    resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.PAGE_AXIS));
                    ArrayList<Node> result = scrapper.getResult();
                    for(int i=0; i<result.size(); i++){
                        resultsPanel.add(new NodeView(result.get(i),outer.gui));
                    }
                    JScrollPane scrollPane = new JScrollPane(resultsPanel);
                    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    outer.setContentPane(scrollPane);
                    outer.pack();
                    outer.setVisible(true);
                    break;

                default:
                    break;

            }
        }
    }

}
