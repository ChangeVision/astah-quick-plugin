package com.change_vision.astah.quick.internal.ui.candidates;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.BorderLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.internal.command.Candidates;

@SuppressWarnings("serial")
public class CandidatesWindowPanel extends JPanel {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CandidatesWindowPanel.class);

    private CandidatesList candidateList;
    private Candidates candidates;

    private JScrollPane scrollPane;

    public CandidatesWindowPanel(Candidates commands) {
        this.candidates = commands;
        scrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        candidateList = new CandidatesList();
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
            candidateList.setSelectedIndex(0);
        }
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

    public Candidates getCandidates() {
        return candidates;
    }
    
    @Override
    public void setVisible(boolean visible) {
        logger.trace("visible:{}",visible);
        super.setVisible(visible);
    }

}
