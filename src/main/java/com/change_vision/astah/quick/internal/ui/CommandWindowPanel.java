package com.change_vision.astah.quick.internal.ui;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.util.ArrayList;
import java.util.List;

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
        add(scrollPane);
        
        JList candidateList = new JList();
        List<String> data = new ArrayList<String>();
        data.add("hogehoge");
        data.add("fugafuga");
        Object[] listData = data.toArray(new String[]{});
        candidateList.setListData(listData );
        candidateList.setOpaque(true);
        scrollPane.add(candidateList);

    }

}
