package com.change_vision.astah.quick.internal.ui.installed;

import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
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
            }
        }

    }

    private JPanel contentPane;

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
            }
        }

    }

    public InstalledWizardPanel() {
        setLayout(new MigLayout("", "[]push[][]", "[][]"));
        
        contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());
        
        add(contentPane, "cell 0 0 3 1,width 640px,height 480px");
        
        JButton btnCancel = new JButton(new BackAction());
        add(btnCancel, "cell 1 1");
        
        JButton btnNext = new JButton(new NextAction());
        add(btnNext, "cell 2 1");
    }

    
    
}
