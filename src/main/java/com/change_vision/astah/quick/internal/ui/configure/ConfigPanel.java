package com.change_vision.astah.quick.internal.ui.configure;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.model.QuickProperties;

@SuppressWarnings("serial")
public class ConfigPanel extends JPanel {
    
    private static final String LABEL_OF_CURRENT = Messages.getString("ConfigPanel.current_label"); //$NON-NLS-1$
    final private JDialog window;
    private JTextField keyField;
    private final QuickProperties properties = new QuickProperties();
    
    public ConfigPanel(JDialog window){
        this.window = window;
        setLayout(new MigLayout("", "[][300px][][]", "[][]")); //$NON-NLS-1$
        JLabel keyLabel = new JLabel(Messages.getString("ConfigPanel.key_label")); //$NON-NLS-1$
        String keyStroke = properties.getKeyStroke();
        keyField = new KeyConfigField(LABEL_OF_CURRENT + keyStroke);
        Action saveAction = new SaveAction(this);
        JButton saveButton = new JButton(saveAction);
        Action cancelAction = new CancelAction(window);
        JButton cancelButton = new JButton(cancelAction);
        add(keyLabel, "cell 0 0"); //$NON-NLS-1$
        add(keyField, "cell 1 0 3 1,growx"); //$NON-NLS-1$
        add(saveButton, "cell 2 1"); //$NON-NLS-1$
        add(cancelButton, "cell 3 1"); //$NON-NLS-1$
    }

    JDialog getWindow() {
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
