package com.change_vision.astah.quick.internal.ui.installed;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class InstalledWizardDialog extends JDialog {

    public InstalledWizardDialog(JFrame parent){
        super(parent);
        setModalityType(ModalityType.APPLICATION_MODAL);
        InstalledWizardPanel panel = new InstalledWizardPanel(this);
        getContentPane().add(panel);
        pack();
        Rectangle parentBounds = parent.getBounds();
        Point centerPoint = new Point();
        centerPoint.setLocation(parentBounds.getCenterX(), parentBounds.getCenterY());
        Dimension size = getSize();
        centerPoint.translate(-size.width / 2, -size.height / 2);
        setLocation(centerPoint);
    }
}
