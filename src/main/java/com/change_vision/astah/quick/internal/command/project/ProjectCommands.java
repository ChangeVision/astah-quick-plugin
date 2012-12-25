package com.change_vision.astah.quick.internal.command.project;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Command;

public class ProjectCommands {
	
	private static final List<Command> commands = new ArrayList<Command>(); 
	
	static {
		commands.add(new NewProjectCommand());
		commands.add(new OpenProjectCommand());
		commands.add(new CloseProjectCommand());
	}
	
	public static List<Command> commands(){
		return commands;
	}

}
