package com.change_vision.astah.quick.internal.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.CommandExecutor;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;
import com.change_vision.astah.quick.internal.ui.candidatesfield.CandidatesField;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateWindowState;

@SuppressWarnings("serial")
public class QuickPanel extends JPanel implements PropertyChangeListener {

    private CandidatesField candidatesField;
    private JButton closeButton;
    private HelpField helpField;
    private JLabel iconLabel;
    private Icon astahIcon;

    public QuickPanel(QuickWindow quickWindow,CandidatesListWindow candidatesList) {
        setLayout(new MigLayout("", "[32px][grow][][]", "[]"));
        
        URL astahIconURL = this.getClass().getResource("/icons/astah_icon_professional.png");
        BufferedImage image;
        try {
            image = ImageIO.read(astahIconURL);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        astahIcon = new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconLabel = new JLabel(astahIcon);
        CommandExecutor executor = quickWindow.getExecutor();
        executor.addPropertyChangeListener(this);
        add(iconLabel, "cell 0 0");
        
        candidatesField = new CandidatesField(quickWindow,candidatesList);
        add(candidatesField, "cell 1 0,growx");
        closeButton = new JButton();
        add(closeButton, "cell 2 0");
        helpField = new HelpField();
        add(helpField, "cell 1 1,growx");
    }
    
    public void setCloseAction(Action action){
        this.closeButton.setAction(action);
    }
    
    public void opened(){
        candidatesField.setWindowState(CandidateWindowState.Inputing);
    }
    
    public void reset(){
        iconLabel.setIcon(astahIcon);
        candidatesField.setText("");
        candidatesField.setWindowState(CandidateWindowState.Wait);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(CommandExecutor.PROP_OF_COMMAND)) {
            Object newValue = evt.getNewValue();
            if (newValue instanceof Command) {
                Command command = (Command) newValue;
                CandidateIconDescription iconDescription = command.getIconDescription();
                Icon icon = iconDescription.getIcon();
                BufferedImage bufferedImage = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = bufferedImage.createGraphics();
                icon.paintIcon(null, graphics, 8, 8);
                graphics.dispose();
                iconLabel.setIcon(new ImageIcon(bufferedImage));
            }
        }
    }

}
