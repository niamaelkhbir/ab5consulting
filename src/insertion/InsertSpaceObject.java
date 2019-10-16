/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package insertion;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public abstract class InsertSpaceObject extends JFrame
{
    private static final String PARSING_ERROR = "PARSING ERROR";
    public HashMap<String,String> arg_value = new HashMap<String, String>();
    
    public abstract void addNode() throws IOException, InterruptedException;

    protected void showErrorMessage()
    {
        JOptionPane.showMessageDialog(this, PARSING_ERROR);
    }
}
