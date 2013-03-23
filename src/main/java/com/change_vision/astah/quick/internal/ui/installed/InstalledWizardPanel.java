package com.change_vision.astah.quick.internal.ui.installed;

import java.awt.CardLayout;
import java.awt.LayoutManager;
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
            LayoutManager layout = contentPane.getLayout();
            if (layout instanceof CardLayout) {
                CardLayout card = (CardLayout) layout;
                card.next(contentPane);
                current++;
                if (current == 6) {
                    setEnabled(false);
                }
                backAction.setEnabled(true);
            }
        }
    }


    private class BackAction extends AbstractAction {
        
        private BackAction() {
            super("back");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LayoutManager layout = contentPane.getLayout();
            if (layout instanceof CardLayout) {
                CardLayout card = (CardLayout) layout;
                card.previous(contentPane);
                current--;
                if(current == 1){
                    setEnabled(false);
                }
                nextAction.setEnabled(true);
            }
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

    private JPanel contentPane;
    private int current = 1;
    private BackAction backAction;
    private NextAction nextAction;
    private FinishAction finishAction;
    private JDialog dialog;

    public InstalledWizardPanel(JDialog dialog) {
        this.dialog = dialog;
        setLayout(new MigLayout("", "[]push[][][]", "[][]"));
        
        contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        
        addContentImage("/slide/en/slide1.png");
        addContentImage("/slide/en/slide2.png");
        addContentImage("/slide/en/slide3.png");
        addContentImage("/slide/en/slide4.png");
        addContentImage("/slide/en/slide5.png");
        addContentImage("/slide/en/slide6.png");
        
        add(contentPane, "cell 0 0 4 1,width 480px,height 360px");
        
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

    private void addContentImage(String contentPath) {
        InputStream stream = getClass().getResourceAsStream(contentPath);
        BufferedImage image;
        try {
            image = ImageIO.read(stream);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        ImageIcon imageIcon = new ImageIcon(image);
        contentPane.add(new JLabel(imageIcon),contentPath);
    }

    
    
}
