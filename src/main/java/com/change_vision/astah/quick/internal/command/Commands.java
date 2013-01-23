package com.change_vision.astah.quick.internal.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.command.model.ModelCommands;
import com.change_vision.astah.quick.internal.command.project.ProjectCommands;
import com.change_vision.jude.api.inf.model.INamedElement;

public class Commands {
    public class NullCommand implements Command {

		@Override
		public String getCommandName() {
			return "Not Found";
		}

		@Override
		public void execute(String... args) {
		}

		@Override
		public String getDescription() {
			return "Candidate are not found.";
		}

		@Override
		public boolean isEnable() {
			return true;
		}

	}

	/**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(Commands.class);

	private static final List<Command> allCommands = new ArrayList<Command>();
	
	static{
		allCommands.addAll(ModelCommands.commands());
		allCommands.addAll(ProjectCommands.commands());
	}

	@TestForMethod
	static void add(Command command) {
		allCommands.add(command);
	}

	@TestForMethod
	static void clear() {
		allCommands.clear();
	}

	private Command[] commands;
	
	int currentIndex = 0;
	
	public Commands(){
		this.commands = allCommands.toArray(new Command[]{});
	}

	public void candidates(String searchKey) {
		logger.trace("candidate searchKey:'{}'",searchKey);
		if (searchKey == null) {
			this.commands = allCommands.toArray(new Command[]{});
			return;
		}
		List<Command> candidates = new ArrayList<Command>();
		for (Command command : allCommands) {
			String commandName = command.getCommandName();
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

	public Command[] getCommands() {
		return commands;
	}

	public void up() {
		if(commands.length == 0) return;
		currentIndex--;
		if(currentIndex < 0){
			currentIndex = commands.length - 1;
		}
		logger.trace("up currentIndex'{}'",currentIndex);
	}

	public Command current() {
		if(commands.length == 0) return new NullCommand();
		return commands[currentIndex];
	}

	public void down() {
		currentIndex++;
		if(currentIndex >= commands.length){
			currentIndex = 0;
		}
	}

}
