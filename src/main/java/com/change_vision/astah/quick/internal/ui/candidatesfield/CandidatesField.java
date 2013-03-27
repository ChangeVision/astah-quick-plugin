package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.command.CommandBuilder;
import com.change_vision.astah.quick.internal.ui.QuickWindow;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListPanel;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateWindowState;

@SuppressWarnings("serial")
public final class CandidatesField extends JTextField implements PropertyChangeListener {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CandidatesField.class);

    private final CandidatesListPanel candidatesList;

    private final QuickWindow quickWindow;

    private boolean settingText;

    private Candidates candidates;

    public CandidatesField(QuickWindow quickWindow, CandidatesListPanel candidatesList,Candidates candidates) {
        this.quickWindow = quickWindow;
        this.candidatesList = candidatesList;
        setFont(new Font("Dialog", Font.PLAIN, 32));
        setColumns(16);
        setEditable(true);
        if (candidatesList == null) {
            return;
        }
        this.candidates = candidates;
        this.candidates.addPropertyChangeListener(this);
        CommitOrExecuteCommandAction commandAction = new CommitOrExecuteCommandAction(this, this.quickWindow,candidates);
        setAction(commandAction);
        new UpCandidatesListAction(this,this.candidatesList);
        new DownCandidatesListAction(this,this.candidatesList);

        CandidatesFieldDocumentListener listener = new CandidatesFieldDocumentListener(this,
                this.candidatesList,candidates);
        
        Document document = getDocument();
        if (document instanceof AbstractDocument) {
            AbstractDocument abstractDocument = (AbstractDocument) document;
            abstractDocument.setDocumentFilter(new CandidatesFieldDocumentFilter());
        }
        document.addDocumentListener(listener);
    }

    public void setWindowState(CandidateWindowState windowState) {
        switch (windowState) {
        case Inputing:
            openCandidatesList();
            break;
        case Wait:
            closeCandidatesListAndReset();
            break;
        case ArgumentInputing:
            openCandidatesList();
            break;
        case ArgumentWait:
            closeCandidatesList();
            break;
        default:
            throw new IllegalStateException("Illegal state of window: '" + windowState.name() + "'");
        }
    }
    
    @Override
    public void setText(String t) {
        settingText = true;
        super.setText(t);
        settingText = false;
    }
    
    public boolean isSettingText() {
        return settingText;
    }

    private void openCandidatesList() {
        if (candidatesList.isVisible() == false) {
            logger.trace("openCandidatesList");
            candidatesList.setVisible(true);
        }
        candidatesList.resetIndex();
    }

    private void closeCandidatesListAndReset() {
        logger.trace("closeCandidatesListAndReset");
        candidates.reset();
    }

    private void closeCandidatesList() {
        logger.trace("closeCandidatesList");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Candidates.PROP_STATE)) {
            evt.getOldValue();
        }
    }

    public String getCandidateText() {
        CommandBuilder builder = candidates.getCommandBuilder();
        return builder.getCandidateText(getText());
    }

}
