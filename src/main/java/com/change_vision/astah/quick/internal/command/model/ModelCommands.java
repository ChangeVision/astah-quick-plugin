package com.change_vision.astah.quick.internal.command.model;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.jude.api.inf.model.INamedElement;

public class ModelCommands {

	private static final List<Command> commands = new ArrayList<Command>(); 
	
	private static final ModelAPI api = new ModelAPI();
	
	static {
		commands.add(new CreateClassCommand());
		commands.add(new CreateInterfaceCommand());
		commands.add(new CreatePackageCommand());
		commands.add(new AddStereotypeCommand());
	}
	
	public static List<Command> commands(){
		return commands;
	}

	public static INamedElement[] find(String searchKey) {
		return api.findClassOrPackage(searchKey);
	}

}

