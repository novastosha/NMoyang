package dev.nova.nmoyang.gui;

import dev.nova.nmoyang.Main;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private final JLabel mainLabel;
    private final JPanel mainPanel;

    public GUI() {
        super("NMoyang");

        this.mainLabel = new JLabel("");
        mainLabel.setVisible(false);

        this.mainPanel = new JPanel();



        this.add(mainPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(650,800));
        setResizable(false);
        setLocationRelativeTo(null);
        loadComponents();
        setVisible(true);
    }

    private void loadComponents() {

        //Add the label.
        mainPanel.add(mainLabel);

        if(!Main.getAPI().isUp()){
            mainLabel.setText("The Mojang API is down right now... Try Again later!");
            mainLabel.setVisible(true);
        }
    }

    public JLabel getMainLabel() {
        return mainLabel;
    }
}