package com.change_vision.astah.quick.internal.ui.configure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.text.Caret;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
final class KeyConfigField extends JTextField {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(KeyConfigField.class);
    
    private String defaultString;

    KeyConfigField(String defaultString) {
        this.defaultString = defaultString;
        setEditable(false);
        setBackground(Color.white);
        setBorder(new LineBorder(Color.lightGray));
        setFocusable(true);
        Caret caret2 = getCaret();
        caret2.setVisible(true);
        setFont(getFont().deriveFont(32.0f));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
                JTextField source = (JTextField) e.getSource();
                String strokeString = keyStroke.toString();
                strokeString = strokeString.replace("pressed ", "");
                logger.trace("pressed:{}",strokeString);
                source.setText(strokeString);
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!this.hasFocus() && this.getText().equals("")) {
            int height = this.getHeight();
            Font prev = g.getFont();
            Font italic = prev.deriveFont(Font.ITALIC);
            Color prevColor = g.getColor();
            g.setFont(italic);
            g.setColor(UIManager.getColor("textInactiveText"));
            int h = g.getFontMetrics().getHeight();
            int textBottom = (height - h) / 2 + h - 2;
            int x = this.getInsets().left + 20;
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints hints = g2d.getRenderingHints();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString(defaultString, x, textBottom);
            g2d.setRenderingHints(hints);
            g.setFont(prev);
            g.setColor(prevColor);
        }
    }
    
}
