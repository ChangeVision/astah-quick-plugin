package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;

public class CommandCommitted implements CandidateState {
	
	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommandCommitted.class);
	
	public class NullCandidate implements Candidate {

		@Override
		public String getName() {
			return "Not Found";
		}

		@Override
		public String getDescription() {
			return "Candidates are not found.";
		}

		@Override
		public boolean isEnable() {
			return true;
		}

		@Override
		public CandidateIconDescription getIconDescription() {
			return new ResourceCommandIconDescription("/icons/glyphicons_207_remove_2.png");
		}

	}

	private Command committed;
	private int currentIndex;
	private Candidate[] candidates;

	public CommandCommitted(Command committed) {
		this.committed = committed; 
	}

	@Override
	public void candidates(String searchKey) {
		logger.trace("candidates:{}",searchKey);
		if (committed instanceof CandidatesProvider) {
			CandidatesProvider provider = (CandidatesProvider) committed;
			provider.candidate(searchKey);
			this.candidates = provider.getCandidates();
		} else {
			this.candidates = new Candidate[]{
					committed
			};
		}
		this.currentIndex = 0;
	}
	
	@Override
	public Candidate[] getCandidates() {
		if (this.candidates == null) {
			candidates(committed.getName());
		}
		return this.candidates;
	}
	
	public void up() {
		if(candidates.length == 0) return;
		int oldValue = currentIndex;
		currentIndex--;
		if(currentIndex < 0){
			currentIndex = candidates.length - 1;
		}
		firePropertyChange("currentIndex", oldValue, currentIndex);
	}

	public Candidate current() {
		if(candidates.length == 0) return new NullCandidate();
		return candidates[currentIndex];
	}

	public void down() {
		int oldValue = currentIndex;
		currentIndex++;
		if(currentIndex >= candidates.length){
			currentIndex = 0;
		}
		firePropertyChange("currentIndex", oldValue, currentIndex);
	}
	
    public void firePropertyChange(String propertyName, Object oldValue,
            Object newValue) {
		logger.trace("{}: old:'{}' new:'{}'",new Object[]{propertyName,oldValue,newValue});
    }
    
    @Override
    public Command currentCommand() {
    	return committed;
    }

}
