package com.change_vision.astah.quick.internal.command.model;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;

public class ModelCommands {

	private static final List<Command> commands = new ArrayList<Command>(); 
	
	private static final ModelAPI api = new ModelAPI();
	
	static {
		commands.add(new CreateClassCommand());
		commands.add(new CreateInterfaceCommand());
		commands.add(new CreatePackageCommand());
	}
	
	public static List<Command> commands(){
		return commands;
	}

	public static INamedElement[] find(String searchKey) {
		return api.find(searchKey);
	}

	public static Command createSelectCommand(INamedElement foundModel) {
		if (foundModel instanceof IClass) {
			IClass classModel = (IClass)foundModel;
			boolean isInterface = classModel.hasStereotype("interface");
			if (isInterface) {
				return new SelectInterfaceCommand(classModel);
			}
			return new SelectClassCommand(classModel);
		}
		if (foundModel instanceof IPackage) {
			IPackage packageModel = (IPackage)foundModel;
			return new SelectPackageCommand(packageModel);
		}
		return new SelectModelCommand(foundModel);
	}
}

