package com.change_vision.astah.quick.internal.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.change_vision.astah.quick.command.Command;

final class CommandListCellRenderer implements ListCellRenderer {
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setComponentOrientation(list.getComponentOrientation());

		if (isSelected) {
			label.setBackground(Color.DARK_GRAY.darker());
			label.setForeground(Color.lightGray.brighter());
		} else {
			label.setBackground(Color.lightGray.brighter());
			label.setForeground(Color.DARK_GRAY.darker());
		}

		if (value instanceof Command) {
			Command command = (Command) value;
			label.setText(command.getCommandName());
		}

		label.setEnabled(list.isEnabled());
		label.setFont(new Font("Dialog", Font.PLAIN, 20));

		label.setBorder(BorderFactory.createEmptyBorder());
		return label;
	}
}