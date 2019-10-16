
package setters;

import model.Calculations;
import view.GUI;

import javax.swing.*;
import java.awt.event.*;

public class Set_dt extends SetParam
{

    private GUI gui;

    public Set_dt(GUI gui){

        super();
        this.gui = gui;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        this.setContentPane(panel);

        this.setTitle("Set the time-step (in seconds)");
        this.setSize(350,90);

        ArgSetter dt = new ArgSetter("dt", this);

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
        String dt_str = this.arg_value.get("dt");
        double dt = Integer.parseInt(dt_str);
        if(dt_str.equals("") || dt == 0){
            throw new Exception("Parsing error");
        }
        Calculations.setDt(dt);
        this.gui.notifyObservers("The time-step was set to : "+dt_str+"s");
        this.dispose();
    }
}
