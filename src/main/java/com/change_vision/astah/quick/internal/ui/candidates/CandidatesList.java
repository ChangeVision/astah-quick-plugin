package com.change_vision.astah.quick.internal.ui.candidates;

import javax.swing.JList;

@SuppressWarnings("serial")
final class CandidatesList extends JList {
	
	CandidatesList(){
		setCellRenderer(new CandidatesListCellRenderer());
	}
}
