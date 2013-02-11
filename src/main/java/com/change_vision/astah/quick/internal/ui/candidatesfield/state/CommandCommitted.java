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
	private CandidatesSelector<Candidate> selector = new CandidatesSelector<Candidate>();

	public CommandCommitted(Command committed) {
		this.committed = committed; 
		candidates(committed.getName());
	}

	@Override
	public void candidates(String searchKey) {
		logger.trace("candidates:{}",searchKey);
		Candidate[] candidates;
		if (committed instanceof CandidatesProvider) {
			CandidatesProvider provider = (CandidatesProvider) committed;
			provider.candidate(searchKey);
			candidates = provider.getCandidates();
		} else {
			candidates = new Candidate[]{
					committed
			};
		}
		selector.setCandidates(candidates);
	}
	
	@Override
	public Candidate[] getCandidates() {
		return this.selector.getCandidates();
	}
	
	@Override
	public void up() {
		selector.up();
	}

	@Override
	public Candidate current() {
		return selector.current();
	}

	@Override
	public void down() {
		selector.down();
	}
    
    @Override
    public Command currentCommand() {
    	return committed;
    }

}
