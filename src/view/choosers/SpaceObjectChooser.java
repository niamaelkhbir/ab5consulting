/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.choosers;

import model.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public abstract class SpaceObjectChooser extends JFrame
{
    private static final String PARSING_ERROR = "PARSING ERROR";
    public String title;
    public SpaceObject[] left;
    public SpaceObject[] right;

    protected void init() {

        setTitle(title);

        this.setSize(500,100);

        final HashMap<String, SpaceObject> leftMap = new HashMap<String, SpaceObject>();
        final HashMap<String, SpaceObject> rightMap = new HashMap<String, SpaceObject>();

        for(SpaceObject spaceObject: left){
            leftMap.put(spaceObject.getLabel(),spaceObject);
        }
        for(SpaceObject spaceObject: right){
            rightMap.put(spaceObject.getLabel(),spaceObject);
        }

        Object[] leftLabels = leftMap.keySet().toArray();
        Object[] rightLabels = rightMap.keySet().toArray();

        final JComboBox leftCombo = new JComboBox(leftLabels);
        final JComboBox rightCombo = new JComboBox(rightLabels);

        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new BoxLayout(comboPanel,BoxLayout.LINE_AXIS));

        comboPanel.add(leftCombo);
        comboPanel.add(rightCombo);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.PAGE_AXIS));

        JButton calculate = new JButton("Calculate");
        JPanel calculatePanel = new JPanel();
        calculatePanel.add(calculate);

        calculate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try{
                    SpaceObject leftObject = leftMap.get((String)leftCombo.getSelectedItem());
                    SpaceObject rightObject = rightMap.get((String)rightCombo.getSelectedItem());
                    activate(leftObject, rightObject);
                }
                catch (Exception exp){
                    exp.printStackTrace();
                    showErrorMessage();
                }
            }
        });

        contentPane.add(comboPanel);
        contentPane.add(calculatePanel);

        setContentPane(contentPane);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    protected void showErrorMessage()
    {
        JOptionPane.showMessageDialog(this, PARSING_ERROR);
    }

    protected abstract void activate(SpaceObject leftObject, SpaceObject rightObject);

}
