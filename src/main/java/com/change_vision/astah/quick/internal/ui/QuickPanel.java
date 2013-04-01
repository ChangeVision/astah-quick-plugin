package com.change_vision.astah.quick.internal.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.command.CommandBuilder;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListPanel;
import com.change_vision.astah.quick.internal.ui.candidatesfield.CandidatesField;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateWindowState;

@SuppressWarnings("serial")
public class QuickPanel extends JPanel implements PropertyChangeListener {

    private final class CandidateDoubleClickListener extends MouseAdapter {
        private final CandidateDecider decider;
        private final Candidates candidates;

        private CandidateDoubleClickListener(QuickWindow quickWindow, CandidatesField candidatesField,Candidates candidates) {
            this.decider = new CandidateDecider(quickWindow,candidatesField);
            this.candidates = candidates;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() > 1) {
                decider.decide(candidates);
            }
        }
    }

    private CandidatesField candidatesField;
    private HelpField helpField;
    private JLabel iconLabel;
    private Icon astahIcon;
    private final CandidatesListPanel candidatesList;

    public QuickPanel(final QuickWindow quickWindow,Candidates candidates) {
        setLayout(new MigLayout("", "[32px][grow]", "[][][]"));
        candidatesList = new CandidatesListPanel(candidates);
        
        URL astahIconURL = this.getClass().getResource("/icons/astah_icon_professional.png");
        BufferedImage image;
        try {
            image = ImageIO.read(astahIconURL);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        astahIcon = new ImageIcon(image.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        iconLabel = new JLabel(astahIcon);
        CommandBuilder builder = candidates.getCommandBuilder();
        builder.addPropertyChangeListener(this);
        add(iconLabel, "cell 0 0,left");
        
        candidatesField = new CandidatesField(quickWindow,candidatesList,candidates);
        add(candidatesField, "cell 1 0,growx");
//        helpField = new HelpField();
//        add(helpField, "cell 1 1,growx");
        add(candidatesList,"cell 0 2,span 2,growx");
        
        candidatesList.addMouseListener(new CandidateDoubleClickListener(quickWindow,candidatesField,candidates));
    }
        
    public void opened(){
        candidatesList.setCandidateText("");
        candidatesField.setWindowState(CandidateWindowState.Inputing);
    }
    
    public void reset(){
        iconLabel.setIcon(astahIcon);
        candidatesField.setText("");
        candidatesField.setWindowState(CandidateWindowState.Wait);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(CommandBuilder.PROP_OF_COMMAND)) {
            Object newValue = evt.getNewValue();
            if (newValue instanceof Command) {
                Command command = (Command) newValue;
                updateIcon(command);
                return;
            }
            iconLabel.setIcon(astahIcon);
        }
    }

    private void updateIcon(Command command) {
        CandidateIconDescription iconDescription = command.getIconDescription();
        Icon icon = iconDescription.getIcon();
        BufferedImage bufferedImage = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        icon.paintIcon(null, graphics, 8, 8);
        graphics.dispose();
        iconLabel.setIcon(new ImageIcon(bufferedImage));
    }

    public CandidatesListPanel getCandidatesList() {
        return this.candidatesList;
    }

}
