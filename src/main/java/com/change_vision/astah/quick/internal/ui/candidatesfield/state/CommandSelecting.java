package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.command.diagram.DiagramCommands;
import com.change_vision.astah.quick.internal.command.model.ModelCommands;
import com.change_vision.astah.quick.internal.command.project.ProjectCommands;
import com.change_vision.jude.api.inf.model.INamedElement;

public class CommandSelecting implements CandidateState {

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(CommandSelecting.class);

	private static final List<Command> allCommands = new ArrayList<Command>();
	
	static{
		allCommands.addAll(ModelCommands.commands());
		allCommands.addAll(ProjectCommands.commands());
		allCommands.addAll(DiagramCommands.commands());
	}

	private CandidatesSelector<Candidate> selector = new CandidatesSelector<Candidate>();
	
	public CommandSelecting(){
		selector.setCandidates(allCommands.toArray(new Candidate[]{}));
	}

	@Override
	public void candidates(String searchKey) {
		logger.trace("candidates key:{}",searchKey);
		if (searchKey == null || searchKey.isEmpty()) {
			selector.setCandidates(allCommands.toArray(new Command[]{}));
			return;
		}
		List<Candidate> candidates = new ArrayList<Candidate>();
		for (Command command : allCommands) {
			String commandName = command.getName();
			if (command.isEnable() &&
				isCandidate(searchKey, commandName)
					) {
				candidates.add(command);
			}
		}
		INamedElement[] foundModels = ModelCommands.find(searchKey);
		for (INamedElement foundModel : foundModels) {
			Command selectCommand = ModelCommands.createSelectCommand(foundModel);
			candidates.add(selectCommand);
		}

		if (candidates.size() == 0) {
			candidates.add(new NullCandidate());
		}
		selector.setCandidates(candidates.toArray(new Candidate[]{}));
	}

	private boolean isCandidate(String searchKey, String commandName) {
		return commandName.startsWith(searchKey) || searchKey.startsWith(commandName);
	}
	
	@Override
	public Candidate[] getCandidates() {
		return selector.getCandidates();
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
    
	@TestForMethod
	static void add(Command command) {
		allCommands.add(command);
	}

	@TestForMethod
	static void clear() {
		allCommands.clear();
	}
	
	@Override
	public Command currentCommand() {
		return (Command) current();
	}

}
