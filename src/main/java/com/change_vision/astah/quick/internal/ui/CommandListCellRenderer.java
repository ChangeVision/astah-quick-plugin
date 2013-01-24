package com.change_vision.astah.quick.internal.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.change_vision.astah.quick.command.Command;

final class CommandListCellRenderer implements ListCellRenderer {
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setComponentOrientation(list.getComponentOrientation());
		panel.setBorder(BorderFactory.createEmptyBorder());

		if (isSelected) {
			panel.setBackground(Color.blue.darker());
			panel.setForeground(Color.lightGray.brighter());
		} else {
			panel.setBackground(Color.lightGray.brighter());
			panel.setForeground(Color.DARK_GRAY.darker());
		}

		if (value instanceof Command) {
			Command command = (Command) value;
			JLabel title = createTitleLabel(list, command);
			panel.add(title);
			JLabel description = new JLabel();
			description.setText(command.getDescription());
			if (isSelected) {
				title.setForeground(Color.lightGray.brighter());
				description.setForeground(Color.lightGray.brighter());
			} else {
				title.setForeground(Color.DARK_GRAY.darker());
				description.setForeground(Color.gray);
			}
			panel.add(description);
		}
		return panel;
	}

	private JLabel createTitleLabel(JList list, Command command) {
		JLabel title = new JLabel();
		title.setText(command.getCommandName());
		title.setEnabled(list.isEnabled());
		title.setFont(new Font("Dialog", Font.PLAIN, 20));
		title.setComponentOrientation(list.getComponentOrientation());
		title.setIcon(command.getIconDescription().getIcon());
		return title;
	}
}