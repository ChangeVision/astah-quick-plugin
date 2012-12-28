package com.change_vision.astah.quick.internal.ui;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Commands;

@SuppressWarnings("serial")
public class CommandWindowPanel extends JPanel {


	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommandWindowPanel.class);

    private CommandList candidateList;
	private Commands candidates;

    public CommandWindowPanel() {
        JScrollPane scrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        candidateList = new CommandList();
        scrollPane.setViewportView(candidateList);
        scrollPane.setPreferredSize(new Dimension(300,200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);
        setBorder(BorderFactory.createEmptyBorder());
    }
    
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(0, 0,
                getBackground().brighter().brighter(), 0, getHeight(),
                getBackground());

        graphics.setPaint(gp);
        graphics.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(graphics);
	}

	public void updateCandidateText(String commandCandidateText) {
		candidates = Commands.candidates(commandCandidateText);
		candidateList.setListData(candidates.getCommands());
		candidateList.setSelectedIndex(0);
	}

	public void up() {
		candidates.up();
		Command command = candidates.current();
		logger.trace("up : current '{}'",command);
		candidateList.setSelectedValue(command, true);
	}

	public void down() {
		candidates.down();
		Command command = candidates.current();
		logger.trace("down : current '{}'",command);
		candidateList.setSelectedValue(command, true);
	}

	public void execute() {
		Command current = candidates.current();
		logger.trace("execute :'{}'",current.getCommandName());
		current.execute();
	}

}
