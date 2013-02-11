package com.change_vision.astah.quick.internal.ui.candidatesfield;

import java.awt.Font;

import javax.swing.JTextField;

import com.change_vision.astah.quick.internal.ui.QuickWindow;
import com.change_vision.astah.quick.internal.ui.candidates.CandidatesListWindow;

@SuppressWarnings("serial")
public final class CandidatesField extends JTextField {

	private final CandidatesListWindow candidatesList;

	private final QuickWindow quickWindow;

	public CandidatesField(QuickWindow quickWindow, CandidatesListWindow candidatesList) {
		this.quickWindow = quickWindow;
		this.candidatesList = candidatesList;
		setFont(new Font("Dialog", Font.PLAIN, 32));
		setColumns(16);
		setEditable(true);
		new ExecuteCommandAction(this,this.quickWindow,this.candidatesList);
		new CommitCommandAction(this,this.candidatesList);
		new UpCommandListAction(this,this.candidatesList);
		new DownCommandListAction(this,this.candidatesList);

		CandidatesFieldDocumentListener listener = new CandidatesFieldDocumentListener(this,quickWindow,this.candidatesList);
		getDocument().addDocumentListener(listener);
	}

	public void reset() {
		setText(null);
		if (candidatesList != null) {
			candidatesList.setVisible(false);
		}
	}
}
