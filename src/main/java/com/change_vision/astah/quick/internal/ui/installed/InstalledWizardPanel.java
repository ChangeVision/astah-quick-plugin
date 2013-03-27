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

import com.change_vision.astah.quick.internal.Messages;

@SuppressWarnings("serial")
public class InstalledWizardPanel extends JPanel {

    private class NextAction extends AbstractAction {
        
        private NextAction() {
            super(Messages.getString("InstalledWizardPanel.next_button_label")); //$NON-NLS-1$
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
            super(Messages.getString("InstalledWizardPanel.back_button_label")); //$NON-NLS-1$
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
            super(Messages.getString("InstalledWizardPanel.finish_button_label")); //$NON-NLS-1$
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }

    private int current = 0;
    private BackAction backAction;
    private NextAction nextAction;
    private FinishAction finishAction;
    private JDialog dialog;

    private static String[] IMAGE_PATHS = Messages.getString("InstalledWizardPanel.contentPath").split(","); //$NON-NLS-1$ //$NON-NLS-2$
    private JLabel imageLabel;
    private ImageIcon[] images = new ImageIcon[IMAGE_PATHS.length];
    
    public InstalledWizardPanel(JDialog dialog) {
        this.dialog = dialog;
        setLayout(new MigLayout("", "[]push[][][]", "[][]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        
        imageLabel = new JLabel();
        ImageIcon image = loadImage(IMAGE_PATHS[0]);
        images[0] = image;
        imageLabel.setIcon(image);
        add(imageLabel, "cell 0 0 4 1,width 480px,height 360px"); //$NON-NLS-1$
        
        backAction = new BackAction();
        backAction.setEnabled(false);
        JButton btnBack = new JButton(backAction);
        add(btnBack, "cell 1 1"); //$NON-NLS-1$
        
        nextAction = new NextAction();
        JButton btnNext = new JButton(nextAction);
        add(btnNext, "cell 2 1"); //$NON-NLS-1$
        
        finishAction = new FinishAction();
        JButton btnFinish = new JButton(finishAction);
        add(btnFinish, "cell 3 1"); //$NON-NLS-1$
      
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
