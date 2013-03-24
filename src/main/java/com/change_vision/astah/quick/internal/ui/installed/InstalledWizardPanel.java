package com.change_vision.astah.quick.internal.ui.installed;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class InstalledWizardPanel extends JPanel {

    private class NextAction extends AbstractAction {
        
        private NextAction() {
            super("next");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            current++;
            if (images[current] == null) {
                images[current] = loadImage(IMAGE_PATHS[current]);
            }
            ImageIcon imageIcon = images[current];
            imageLabel.setIcon(imageIcon);
            if (current == IMAGE_PATHS.length - 1) {
                setEnabled(false);
            }
            backAction.setEnabled(true);
        }
    }


    private class BackAction extends AbstractAction {
        
        private BackAction() {
            super("back");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            current--;
            if (images[current] == null) {
                images[current] = loadImage(IMAGE_PATHS[current]);
            }
            ImageIcon imageIcon = images[current];
            imageLabel.setIcon(imageIcon);
            if(current == 0){
                setEnabled(false);
            }
            nextAction.setEnabled(true);
        }
    }
    
    private class FinishAction extends AbstractAction {
        
        private FinishAction() {
            super("finish");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }

    private int current = 1;
    private BackAction backAction;
    private NextAction nextAction;
    private FinishAction finishAction;
    private JDialog dialog;

    private static String[] IMAGE_PATHS = new String[]{
        "/slide/en/slide1.png",
        "/slide/en/slide2.png",
        "/slide/en/slide3.png",
        "/slide/en/slide4.png",
        "/slide/en/slide5.png",
        "/slide/en/slide6.png"        
    };
    private JLabel imageLabel;
    private ImageIcon[] images = new ImageIcon[IMAGE_PATHS.length];
    
    public InstalledWizardPanel(JDialog dialog) {
        this.dialog = dialog;
        setLayout(new MigLayout("", "[]push[][][]", "[][]"));
        
        imageLabel = new JLabel();
        ImageIcon image = loadImage(IMAGE_PATHS[0]);
        images[0] = image;
        imageLabel.setIcon(image);
        add(imageLabel, "cell 0 0 4 1,width 480px,height 360px");
        
        backAction = new BackAction();
        backAction.setEnabled(false);
        JButton btnBack = new JButton(backAction);
        add(btnBack, "cell 1 1");
        
        nextAction = new NextAction();
        JButton btnNext = new JButton(nextAction);
        add(btnNext, "cell 2 1");
        
        finishAction = new FinishAction();
        JButton btnFinish = new JButton(finishAction);
        add(btnFinish, "cell 3 1");
      
    }

    private ImageIcon loadImage(String contentPath) {
        InputStream stream = getClass().getResourceAsStream(contentPath);
        BufferedImage image;
        try {
            image = ImageIO.read(stream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return new ImageIcon(image);
    }
    
}
