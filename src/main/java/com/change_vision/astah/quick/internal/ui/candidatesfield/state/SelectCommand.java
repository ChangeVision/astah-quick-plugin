package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.candidates.NotFound;
import com.change_vision.astah.quick.internal.annotations.TestForMethod;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.command.model.SelectModelCommandFactory;

public class SelectCommand implements CandidateState {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(SelectCommand.class);

    private SelectModelCommandFactory commandFactory = new SelectModelCommandFactory();
    
    private Commands commands;

    public SelectCommand(Commands commands) {
        this.commands = commands;
    }

    @Override
    public Candidate[] filter(String key) {
        logger.trace("key:{}", key);
        List<Candidate> candidates = new ArrayList<Candidate>();
        if (key == null || key.isEmpty()) {
            for (Command command : commands.getCommands()) {
                if (command.isEnabled()) {
                    candidates.add(command);
                }
            }
            return candidates.toArray(new Candidate[] {});
        }
        for (Command command : commands.getCommands()) {
            String commandName = command.getName();
            if (command.isEnabled() && isCandidate(key, commandName)) {
                candidates.add(command);
            }
        }
        logger.trace("command candidates:{}", candidates);
        List<Candidate> selectCommands = commandFactory.create(key);
        candidates.addAll(selectCommands);

        if (candidates.size() == 0) {
            candidates.add(new NotFound());
        }
        return candidates.toArray(new Candidate[] {});
    }

    private boolean isCandidate(String searchKey, String commandName) {
        return commandName.startsWith(searchKey);
    }

    @TestForMethod
    void setCommandFactory(SelectModelCommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

}
