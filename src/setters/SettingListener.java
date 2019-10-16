/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package setters;

import javax.swing.*;
import java.awt.event.*;

public class SettingListener implements FocusListener
{

    private SetParam whereToSet;
    private String arg;
    private JTextArea textArea;

    public SettingListener(SetParam whereToSet, String arg, JTextArea textArea)
    {
        this.whereToSet = whereToSet;
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
        whereToSet.arg_value.put(arg,textArea.getText());
        return;
    }
}
