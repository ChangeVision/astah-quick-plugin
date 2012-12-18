package com.change_vision.astah.quick.internal.ui;

import java.awt.Image;
import java.awt.Point;
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
import javax.swing.JWindow;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class QuickPanel extends JPanel {

    private CommandField commandField;
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
        
        commandField = new CommandField();
        add(commandField, "cell 1 0,growx");
        closeButton = new JButton();
        add(closeButton, "cell 2 0");
    }
    
    public void setCloseAction(Action action){
        this.closeButton.setAction(action);
    }
    
    public void reset(){
        commandField.reset();
    }

    public void setParentWindow(JWindow window) {
        commandField.setParentWindow(window);
    }

}
