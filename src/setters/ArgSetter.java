
package setters;

import javax.swing.*;

public class ArgSetter extends JPanel
{
    private String arg;
    private SetParam whereToSet;

    public ArgSetter(String arg, SetParam whereToSet){
        super();
        this.arg = arg;
        this.whereToSet = whereToSet;

        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));

        JLabel label = new JLabel("  "+arg+" :  ");
        JTextArea textArea = new JTextArea(1,20);

        this.add(label);
        this.add(textArea);

        whereToSet.getContentPane().add(this);

        textArea.addFocusListener(new SettingListener(whereToSet,arg,textArea));
    }
}
