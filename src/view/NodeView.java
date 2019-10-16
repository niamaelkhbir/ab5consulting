/*
 * Copyright (C) 2019 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package view;

import model.Node;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class NodeView extends JPanel
{
    public Node node;
    public GUI gui;

    public NodeView(Node node ,GUI gui){
        this.node = node;
        this.gui = gui;
        JLabel argsLabel = new JLabel();
        String text = "";
        for(String arg: this.node.getArgs()){
            text += arg+" ";
        }
        argsLabel.setText(text);
        JButton add = new JButton("Add");
        add.addActionListener(new InnerListener(this.node,add));
        this.add(argsLabel);
        this.add(add);
    }

    private class InnerListener implements ActionListener {

        private Node outer;
        private JButton add;

        public InnerListener(Node outer, JButton add){
            this.outer = outer;
            this.add = add;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            (gui.nodes).add(outer);
            try
            {
                outer.showOnGlobe(gui);
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
            gui.notifyObservers(outer);
            (this.add).setVisible(false);
            return;
        }

    }

}
