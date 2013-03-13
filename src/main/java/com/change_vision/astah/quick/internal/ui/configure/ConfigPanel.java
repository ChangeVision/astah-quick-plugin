package com.change_vision.astah.quick.internal.ui.configure;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.model.QuickProperties;

@SuppressWarnings("serial")
public class ConfigPanel extends JPanel {
    
    private static final String LABEL_OF_CURRENT = Messages.getString("ConfigPanel.current_label"); //$NON-NLS-1$
    final private JWindow window;
    private JTextField keyField;
    private final QuickProperties properties = new QuickProperties();
    
    public ConfigPanel(JWindow window){
        this.window = window;
        setBackground(Color.white);
        JPanel panel = createKeyFieldPanel();
        add(panel);
    }

    private JPanel createKeyFieldPanel() {
        JLabel configureImageLabel = createConfigImageLabel();
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        JLabel keyLabel = new JLabel(Messages.getString("ConfigPanel.key_label")); //$NON-NLS-1$
        keyLabel.setOpaque(false);
        Font font = getFont().deriveFont(32.0f);
        keyLabel.setFont(font);
        String keyStroke = properties.getKeyStroke();
        keyField = new KeyConfigField(LABEL_OF_CURRENT + keyStroke);
        Action saveAction = new SaveAction(this);
        JButton saveButton = new JButton(saveAction);
        Action cancelAction = new CancelAction(window);
        JButton cancelButton = new JButton(cancelAction);
        Font buttonFont = getFont().deriveFont(24.0f);
        saveButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setAutoCreateGaps(true);
        gl_panel.setAutoCreateContainerGaps(true);
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_panel.createSequentialGroup()
                    .addPreferredGap(ComponentPlacement.RELATED,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(keyLabel))
            .addGroup(gl_panel.createSequentialGroup()
                    .addComponent(configureImageLabel)
                    .addComponent(keyField)
            )
            .addGroup(gl_panel.createSequentialGroup()
                    .addPreferredGap(ComponentPlacement.RELATED,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveButton))
            .addGroup(gl_panel.createSequentialGroup()
                    .addPreferredGap(ComponentPlacement.RELATED,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton))
        );
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup()
            .addGroup(gl_panel.createSequentialGroup()
                .addComponent(configureImageLabel))
            .addGroup(gl_panel.createSequentialGroup()
                .addComponent(keyLabel)
                .addComponent(keyField)
                .addComponent(saveButton)
                .addComponent(cancelButton))
        );
        panel.setLayout(gl_panel);
        return panel;
    }

    private JLabel createConfigImageLabel() {
        URL astahIconURL = this.getClass().getResource(Messages.getString("ConfigPanel.image_path")); //$NON-NLS-1$
        BufferedImage image;
        try {
            image = ImageIO.read(astahIconURL);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        Icon configureImage = new ImageIcon(image);
        
        JLabel configureImageLabel = new JLabel(configureImage);
        return configureImageLabel;
    }

    JWindow getWindow() {
        return this.window;
    }

    public String getKeyStroke() {
        String text = keyField.getText();
        if (text.isEmpty()) {
            return "ctrl SPACE";
        }
        return text;
    }

    public QuickProperties getProperties() {
        return this.properties;
    }

    
}
