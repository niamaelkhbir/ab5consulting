/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package insertion;

import javax.swing.*;

public class ArgGetter extends JPanel
{
    private String arg;
    private InsertSpaceObject whereToInsert;

    public ArgGetter(String arg, InsertSpaceObject whereToInsert){
        super();
        this.arg = arg;
        this.whereToInsert = whereToInsert;

        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));

        JLabel label = new JLabel("  "+arg+" :  ");
        JTextArea textArea = new JTextArea(1,20);

        this.add(label);
        this.add(textArea);

        whereToInsert.getContentPane().add(this);

        textArea.addFocusListener(new InsertionListener(whereToInsert,arg,textArea));
    }
}
