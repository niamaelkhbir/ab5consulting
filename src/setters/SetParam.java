
package setters;

import javax.swing.*;
import java.util.HashMap;

public abstract class SetParam extends JFrame
{
    private static final String PARSING_ERROR = "PARSING ERROR";
    public HashMap<String,String> arg_value = new HashMap<>();

    public abstract void setParameter() throws Exception;

    protected void showErrorMessage()
    {
        JOptionPane.showMessageDialog(this, PARSING_ERROR);
    }
}
