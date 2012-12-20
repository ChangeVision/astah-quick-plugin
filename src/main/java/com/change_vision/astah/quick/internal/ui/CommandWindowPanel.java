package com.change_vision.astah.quick.internal.ui;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class CommandWindowPanel extends JPanel {

    /**
     * Create the panel.
     */
    public CommandWindowPanel() {
        JScrollPane scrollPane = new JScrollPane(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
        JList candidateList = new JList();
        List<String> data = new ArrayList<String>();
        data.add("hogehoge");
        data.add("fugafuga");
        data.add("fugafuga");
        data.add("fugafuga");
        data.add("fugafuga");
        data.add("fugafuga");
        data.add("fugafuga");
        data.add("fugafuga");
        Object[] listData = data.toArray(new String[]{});
        candidateList.setListData(listData);
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

}
