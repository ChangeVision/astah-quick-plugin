package com.change_vision.astah.quick.internal.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.command.model.ModelCommands;
import com.change_vision.astah.quick.internal.command.project.ProjectCommands;

public class Commands {
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
	
	private int currentIndex = 0;
	
	private Commands(Command[] commands){
		this.commands = commands;
	}

	public static Commands candidates(String searchKey) {
		logger.trace("candidate searchKey:'{}'",searchKey);
		if (searchKey == null) {
			Command[] currentCommands = allCommands.toArray(new Command[]{});
			return new Commands(currentCommands);
		}
		List<Command> candidates = new ArrayList<Command>();
		for (Command command : allCommands) {
			if (command.getCommandName().startsWith(searchKey) && command.isEnable()) {
				candidates.add(command);
			}
		}
		Command[] currentCommands = candidates.toArray(new Command[]{});
		return new Commands(currentCommands);
	}

	public Command[] getCommands() {
		return commands;
	}

	public void up() {
		currentIndex--;
		if(currentIndex < 0){
			currentIndex = commands.length - 1;
		}
		logger.trace("up currentIndex'{}'",currentIndex);
	}

	public Command current() {
		return commands[currentIndex];
	}

	public void down() {
		currentIndex++;
		if(currentIndex >= commands.length){
			currentIndex = 0;
		}
	}

}
