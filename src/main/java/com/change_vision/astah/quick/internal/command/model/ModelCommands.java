package com.change_vision.astah.quick.internal.command.model;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Command;

public class ModelCommands {

	private static final List<Command> commands = new ArrayList<Command>(); 
	
	static {
		commands.add(new CreateClassCommand());
		commands.add(new CreateInterfaceCommand());
		commands.add(new CreatePackageCommand());
		commands.add(new FindClassCommand());
	}
	
	public static List<Command> commands(){
		return commands;
	}
}
