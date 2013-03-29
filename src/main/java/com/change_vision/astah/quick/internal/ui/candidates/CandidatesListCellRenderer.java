package com.change_vision.astah.quick.internal.ui.candidates;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;

final class CandidatesListCellRenderer implements ListCellRenderer {
    
    private final class UnderLineBorder implements Border {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D graphic = (Graphics2D) g.create();
            graphic.setColor(new Color(224,224,224));
            graphic.drawLine(x, y + c.getHeight() - 1, x + c.getWidth(), y + c.getHeight() - 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 1, 0);
        }
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setComponentOrientation(list.getComponentOrientation());
        CompoundBorder border = new CompoundBorder(new UnderLineBorder(),BorderFactory.createEmptyBorder(2, 5, 2, 0));
        
        panel.setBorder(border);

        if (isSelected) {
            panel.setBackground(Color.blue.darker());
            panel.setForeground(Color.lightGray.brighter());
        } else {
            panel.setBackground(Color.lightGray.brighter());
            panel.setForeground(Color.DARK_GRAY.darker());
        }

        if (value instanceof Candidate) {
            Candidate candidate = (Candidate) value;
            JLabel title = createTitleLabel(list, candidate);
            panel.add(title);
            JLabel description = new JLabel();
            description.setText(candidate.getDescription());
            description.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
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

    private JLabel createTitleLabel(JList list, Candidate candidate) {
        JLabel title = new JLabel();
        title.setText(candidate.getName());
        title.setEnabled(list.isEnabled());
        title.setFont(new Font("Dialog", Font.PLAIN, 20));
        title.setComponentOrientation(list.getComponentOrientation());
        CandidateIconDescription iconDescription = candidate.getIconDescription();
        if (iconDescription != null) {
            Icon icon = iconDescription.getIcon();
            if (icon != null) {
                int spaceAroundIcon = 2;
                BufferedImage bi = new BufferedImage(icon.getIconWidth() ,icon.getIconHeight() + ( 2 * spaceAroundIcon), BufferedImage.TYPE_INT_ARGB);
                Graphics g = bi.getGraphics();
                icon.paintIcon(null, g, 0 , spaceAroundIcon * 2);
                g.dispose();
                title.setIcon(new ImageIcon(bi));
            }
        }
        return title;
    }
}