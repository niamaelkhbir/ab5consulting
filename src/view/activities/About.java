/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view.activities;

import javax.swing.*;

public class About extends JFrame
{
    private static final String text = "\n This software is an property of AB5Consulting.\n\n";

    public About() {
        super();

        setTitle("About");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(320,165);
        setResizable(false);

        JPanel contentPane = new JPanel();
        JLabel about = new JLabel(text);
        JLabel image = new JLabel(new ImageIcon("logo.png"));

        contentPane.add(about);
        contentPane.add(image);

        setContentPane(contentPane);
        setVisible(true);
    }

}
