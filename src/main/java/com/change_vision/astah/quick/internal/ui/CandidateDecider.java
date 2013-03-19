package com.change_vision.astah.quick.internal.ui;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.command.Candidates;
import com.change_vision.astah.quick.internal.command.CommandBuilder;
import com.change_vision.astah.quick.internal.command.CommandExecutor;
import com.change_vision.astah.quick.internal.ui.candidatesfield.CandidatesField;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateWindowState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.ValidState;

public class CandidateDecider {
    
    private final QuickWindow quickWindow;
    private final CandidatesField candidatesField;

    public CandidateDecider(QuickWindow quickWindow,CandidatesField field){
        this.quickWindow = quickWindow;
        this.candidatesField = field;
    }
    
    public void decide() {
        CommandBuilder builder = quickWindow.getBuilder();
        Candidates candidates = quickWindow.getCandidates();
        Candidate candidate = candidates.current();
        if (candidate instanceof ValidState) {
            executeCommand(builder);
            return;
        }
        CommandExecutor executor = quickWindow.getExecutor();
        if (builder.isCommitted()) {
            builder.add(candidate);
            if (isImmidiateCandidate(candidate)) {
                executeCommand(builder);
                return;
            }
            String commandText = builder.getCommandText() + CommandExecutor.SEPARATE_COMMAND_CHAR;
            candidatesField.setText(commandText);
            return;
        }
        if (candidate instanceof Command) {
            Command command = (Command) candidate;
            if (isImmidiateCommand(command)) {
                builder.commit(command);
                executeCommand(builder);
                return;
            }
            builder.commit(command);
            candidatesField.setWindowState(CandidateWindowState.ArgumentWait);
            String commandText = builder.getCommandText() + CommandExecutor.SEPARATE_COMMAND_CHAR;
            candidatesField.setText(commandText);
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
        CommandExecutor executor = quickWindow.getExecutor();
        try {
            executor.execute(builder,candidateText);
        } catch (Exception e) {
            quickWindow.notifyError("Alert", e.getMessage());
        }
        quickWindow.reset();
    }

}
