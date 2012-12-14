package com.change_vision.astah.quick.internal.ui;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class QuickPanel extends JPanel {

    private JTextField commandField;
    private JButton closeButton;

    public QuickPanel() {
        setLayout(new MigLayout("", "[32px][grow][][]", "[]"));
        
        URL astahIconURL = this.getClass().getResource("/icons/astah_icon_professional.png");
        BufferedImage image;
        try {
            image = ImageIO.read(astahIconURL);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Icon astahIcon = new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        JLabel astah = new JLabel(astahIcon);
        add(astah, "cell 0 0");
        
        commandField = new JTextField();
        commandField.setFont(new Font("Dialog", Font.PLAIN, 32));
        add(commandField, "cell 1 0,growx");
        commandField.setColumns(16);
        commandField.setEditable(true);
        
        closeButton = new JButton();
        add(closeButton, "cell 2 0");
    }
    
    public void setCloseAction(Action action){
        this.closeButton.setAction(action);
    }
    
    public void reset(){
        commandField.setText(null);
    }

}
