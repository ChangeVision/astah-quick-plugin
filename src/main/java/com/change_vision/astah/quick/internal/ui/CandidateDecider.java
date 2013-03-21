package com.change_vision.astah.quick.internal.ui;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.command.candidates.InvalidState;
import com.change_vision.astah.quick.command.candidates.ValidState;
import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.command.CommandBuilder;
import com.change_vision.astah.quick.internal.command.CommandExecutor;
import com.change_vision.astah.quick.internal.ui.candidatesfield.CandidatesField;

public class CandidateDecider {
    
    private final QuickWindow quickWindow;
    private final CandidatesField candidatesField;
    private final CommandExecutor executor = new CommandExecutor();

    public CandidateDecider(QuickWindow quickWindow,CandidatesField field){
        this.quickWindow = quickWindow;
        this.candidatesField = field;
    }
    
    public void decide(Candidates candidates) {
        CommandBuilder builder = candidates.getCommandBuilder();
        Candidate candidate = candidates.current();
        if (candidate instanceof InvalidState) {
            return;
        }
        if (candidate instanceof ValidState) {
            executeCommand(builder);
            return;
        }
        if (builder.isCommitted()) {
            builder.add(candidate);
            String commandText = builder.getCommandText() + CommandExecutor.SEPARATE_COMMAND_CHAR;
            candidatesField.setText(commandText);
            if (isImmidiateCandidate(candidate)) {
                executeCommand(builder);
                return;
            }
            return;
        }
        if (candidate instanceof Command) {
            Command command = (Command) candidate;
            builder.commit(command);
            String commandText = builder.getCommandText() + CommandExecutor.SEPARATE_COMMAND_CHAR;
            candidatesField.setText(commandText);
            if (isImmidiateCommand(command)) {
                executeCommand(builder);
                return;
            }
        }
    }

    private boolean isImmidiateCandidate(Candidate candidate) {
        return candidate.getClass().isAnnotationPresent(Immediate.class);
    }

    private boolean isImmidiateCommand(Command command) {
        return command.getClass().isAnnotationPresent(Immediate.class);
    }

    private void executeCommand(CommandBuilder builder) {
        String candidateText = candidatesField.getText();
        quickWindow.close();
        try {
            executor.execute(builder,candidateText);
        } catch (Exception e) {
            quickWindow.notifyError("Alert", e.getMessage());
        }
        quickWindow.reset();
    }

}
