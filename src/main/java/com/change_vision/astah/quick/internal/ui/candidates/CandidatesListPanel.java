package com.change_vision.astah.quick.internal.ui.candidates;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.BorderLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.internal.command.Candidates;

@SuppressWarnings("serial")
public class CandidatesListPanel extends JPanel {

    private final class CandidateSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            int index = candidateList.getSelectedIndex();
            candidates.setCurrentIndex(index);
        }
    }

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CandidatesListPanel.class);

    private CandidatesList candidateList;
    private Candidates candidates;

    private JScrollPane scrollPane;

    public CandidatesListPanel(Candidates candidates) {
        this.candidates = candidates;
        scrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setAutoscrolls(true);
        candidateList = new CandidatesList();
        candidateList.addListSelectionListener(new CandidateSelectionListener());
        scrollPane.setViewportView(candidateList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(0, 0, getBackground().brighter().brighter(), 0,
                getHeight(), getBackground());

        graphics.setPaint(gp);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(graphics);
    }

    public void setCandidateText(String commandCandidateText) {
        candidates.filter(commandCandidateText);
        Candidate[] candidatesData = candidates.getCandidates();

        candidateList.setListData(candidatesData);
        if (candidatesData.length > 0) {
            resetCandidateListIndex();
        }
    }

    private void resetCandidateListIndex() {
        candidateList.setSelectedIndex(0);
        scrollPane.getViewport().setViewPosition(new Point(0,0));
    }

    public void up() {
        candidates.up();
        Candidate command = candidates.current();
        logger.trace("up : current '{}'", command);
        candidateList.setSelectedValue(command, true);
    }

    public void down() {
        candidates.down();
        Candidate candidate = candidates.current();
        logger.trace("down : current '{}'", candidate);
        candidateList.setSelectedValue(candidate, true);
    }

    public void resetIndex(){
        logger.trace("reset index");
        resetCandidateListIndex();
    }

    @Override
    public void setVisible(boolean visible) {
        logger.trace("visible:{}",visible);
        super.setVisible(visible);
    }
    
    public void addMouseListener(MouseListener listener){
        candidateList.addMouseListener(listener);
    }

}
