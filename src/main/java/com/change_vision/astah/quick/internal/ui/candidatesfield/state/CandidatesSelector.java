package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.candidates.NotFound;

public class CandidatesSelector<T extends Candidate> {
	
	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CandidatesSelector.class);
	
	private int currentIndex;
	@SuppressWarnings("unchecked")
	private T[] candidates = (T[]) new Candidate[]{};
	
    public void up() {
		if(candidates.length == 0) return;
		int oldValue = currentIndex;
		currentIndex--;
		if(currentIndex < 0){
			currentIndex = candidates.length - 1;
		}
		firePropertyChange("currentIndex", oldValue, currentIndex);
	}

	@SuppressWarnings("unchecked")
	public T current() {
		if(candidates.length == 0){
		    return (T) new NotFound();
		}
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

	public void setCandidates(T[] candidates) {
		this.candidates = candidates;
		this.currentIndex = 0;
	}

	public Candidate[] getCandidates() {
		return this.candidates;
	}

    public void setCurrentIndex(int index) {
        int oldValue = currentIndex;
        currentIndex = index;
        firePropertyChange("currentIndex", oldValue, currentIndex);
    }
}
