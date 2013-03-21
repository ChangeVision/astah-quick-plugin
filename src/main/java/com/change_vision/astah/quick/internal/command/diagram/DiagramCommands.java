package com.change_vision.astah.quick.internal.command.diagram;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.astah.quick.command.Command;

public class DiagramCommands {

    private static final List<Command> commands = new ArrayList<Command>();

    static {
        commands.add(new OpenDiagramCommand());
        commands.add(new CloseDiagramCommand());
        commands.add(new CreateClassDiagramCommand());
        commands.add(new CreateUseCaseDiagramCommand());
    }

    public static List<Command> commands() {
        return commands;
    }

}
