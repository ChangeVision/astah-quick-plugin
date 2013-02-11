package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
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
	
	public class NullCommand implements Command {

		@Override
		public String getName() {
			return "Not Found";
		}

		@Override
		public void execute(String... args) {
		}

		@Override
		public String getDescription() {
			return "Candidate commands are not found.";
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
	
	static{
		allCommands.addAll(ModelCommands.commands());
		allCommands.addAll(ProjectCommands.commands());
		allCommands.addAll(DiagramCommands.commands());
	}

	private Command[] commands;
	int currentIndex;
	
	public CommandSelecting(){
		this.commands = allCommands.toArray(new Command[]{});
	}

	@Override
	public void candidates(String searchKey) {
		logger.trace("candidates key:{}",searchKey);
		if (searchKey == null) {
			this.commands = allCommands.toArray(new Command[]{});
			return;
		}
		List<Command> candidates = new ArrayList<Command>();
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
			candidates.add(new NullCommand());
		}
		this.commands = candidates.toArray(new Command[]{});
		this.currentIndex = 0;
	}

	private boolean isCandidate(String searchKey, String commandName) {
		return commandName.startsWith(searchKey) || searchKey.startsWith(commandName);
	}
	
	@Override
	public void up() {
		if(commands.length == 0) return;
		int oldValue = currentIndex;
		currentIndex--;
		if(currentIndex < 0){
			currentIndex = commands.length - 1;
		}
		firePropertyChange("currentIndex", oldValue, currentIndex);
	}

	@Override
	public Command current() {
		if(commands.length == 0) return new NullCommand();
		return commands[currentIndex];
	}

	@Override
	public void down() {
		int oldValue = currentIndex;
		currentIndex++;
		if(currentIndex >= commands.length){
			currentIndex = 0;
		}
		firePropertyChange("currentIndex", oldValue, currentIndex);
	}
	
    public void firePropertyChange(String propertyName, Object oldValue,
            Object newValue) {
		logger.trace("{}: old:'{}' new:'{}'",new Object[]{propertyName,oldValue,newValue});
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
	public Candidate[] getCandidates() {
		return this.commands;
	}
	
	@Override
	public Command currentCommand() {
		return current();
	}

}
