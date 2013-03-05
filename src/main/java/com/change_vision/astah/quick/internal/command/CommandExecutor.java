package com.change_vision.astah.quick.internal.command;

import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.UncommitedCommandExcepition;

public class CommandExecutor {

    private Command command;

    public Command getCommand() {
        return command;
    }

    public void commit(Command command) {
        this.command = command;
    }

    public boolean isCommited() {
        return command != null;
    }

    public void execute() throws UncommitedCommandExcepition{
        if (isUncommited()) throw new UncommitedCommandExcepition();
        command.execute(new String[]{});
    }

    private boolean isUncommited() {
        return command == null;
    }

}
