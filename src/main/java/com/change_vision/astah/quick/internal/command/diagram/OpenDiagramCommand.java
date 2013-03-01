package com.change_vision.astah.quick.internal.command.diagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.view.IconDescription;

public class OpenDiagramCommand implements Command, CandidatesProvider {

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(OpenDiagramCommand.class);

	private static final DiagramAPI api = new DiagramAPI();
	
	private class DiagramCandidate implements Candidate{
		
		private IDiagram diagram;

		private DiagramCandidate(IDiagram diagram){
			this.diagram = diagram;
		}

		@Override
		public String getName() {
			return diagram.getName();
		}

		@Override
		public String getDescription() {
			return diagram.getFullName(".");
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public CandidateIconDescription getIconDescription() {
			return new AstahCommandIconDescription(IconDescription.UML_DGM_CLASS);
		}

		public IDiagram getDiagram() {
			return this.diagram;
		}
		
	}

	@Override
	public String getName() {
		return "open diagram";
	}

	@Override
	public String getDescription() {
		return "open specified diagram";
	}

	@Override
	public boolean isEnabled() {
		return api.isOpenedProject();
	}

	@Override
	public CandidateIconDescription getIconDescription() {
		return new ResourceCommandIconDescription("/icons/glyphicons_144_folder_open.png");
	}

	@Override
	public void execute(String... args) {
		for (String diagramName : args) {
			IDiagram[] diagrams = api.find(diagramName);
			if (diagrams != null) {
				for (IDiagram diagram : diagrams) {
					api.open(diagram);
				}
			}
		}
	}
	
	@Override
	public void execute(Candidate candidate) {
		DiagramCandidate diagramCandidate = (DiagramCandidate)candidate;
		IDiagram diagram = diagramCandidate.getDiagram();
		api.open(diagram);
	}

	@Override
	public Candidate[] candidate(String searchKey) {
		logger.trace("searchKey:{}",searchKey);
		searchKey = searchKey.substring(getName().length()).trim();
		IDiagram[] found = api.find(searchKey.trim());
		Candidate[] candidates = new DiagramCandidate[found.length];

		for (int i = 0; i < found.length; i++) {
			IDiagram diagram = found[i];
			candidates[i] = new DiagramCandidate(diagram);
		}
		return candidates;
	}

}
