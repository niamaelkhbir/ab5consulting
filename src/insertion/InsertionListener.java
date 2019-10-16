/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package insertion;

import javax.swing.*;
import java.awt.event.*;

public class InsertionListener implements FocusListener
{
    private InsertSpaceObject whereToInsert;
    private String arg;
    private JTextArea textArea;

    public InsertionListener(InsertSpaceObject whereToInsert, String arg, JTextArea textArea)
    {
        this.whereToInsert = whereToInsert;
        this.arg = arg;
        this.textArea = textArea;
    }

    @Override
    public void focusGained(FocusEvent e)
    {
        return;
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        whereToInsert.arg_value.put(arg,textArea.getText());
        return;
    }
}
