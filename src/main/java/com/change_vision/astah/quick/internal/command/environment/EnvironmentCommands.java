package com.change_vision.astah.quick.internal.command.environment;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Command;

public class EnvironmentCommands {

	private static final List<Command> commands = new ArrayList<Command>(); 
	
	static {
		commands.add(new ConfigCommand());
        commands.add(new QuitCommand());
	}
	
	public static List<Command> commands(){
		return commands;
	}

}

