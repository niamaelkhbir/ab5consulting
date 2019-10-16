
package setters;

import model.Calculations;
import view.GUI;

import javax.swing.*;
import java.awt.event.*;

public class Set_nDays extends SetParam
{

    private GUI gui;

    public Set_nDays(GUI gui){

        super();
        this.gui = gui;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        this.setContentPane(panel);

        this.setTitle("Set the number of days simulated");
        this.setSize(350,90);

        ArgSetter nDays = new ArgSetter("N°days", this);

        JButton set = new JButton("Set");

        set.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try{
                    setParameter();
                }
                catch(Exception exp){
                    exp.printStackTrace();
                    showErrorMessage();
                }
                finally
                {
                    return;
                }
            }
        });


        JPanel addPanel = new JPanel();
        addPanel.add(set);

        this.getContentPane().add(addPanel);

        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    @Override
    public void setParameter() throws Exception
    {
        String nDays_str = this.arg_value.get("N°days");
        int nDays = Integer.parseInt(nDays_str);
        if(nDays_str.equals("") || nDays == 0){
            throw new Exception("Parsing error");
        }
        Calculations.setNDays(nDays);
        this.gui.notifyObservers("The number of days simulated was set to : "+nDays_str);
        this.dispose();
    }
}
