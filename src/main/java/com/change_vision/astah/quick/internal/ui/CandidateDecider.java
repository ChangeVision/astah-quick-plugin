package com.change_vision.astah.quick.internal.ui;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.command.Candidates;
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
        Candidates candidates = quickWindow.getCandidates();
        Candidate candidate = candidates.current();
        if (candidate instanceof ValidState) {
            executeCommand();
            return;
        }
        CommandExecutor executor = quickWindow.getExecutor();
        if (executor .isCommited()) {
            executor.add(candidate);
            if (isImmidiateCandidate(candidate)) {
                executeCommand();
                return;
            }
            String commandText = executor.getCommandText() + CommandExecutor.SEPARATE_COMMAND_CHAR;
            candidatesField.setText(commandText);
            return;
        }
        if (candidate instanceof Command) {
            Command command = (Command) candidate;
            if (isImmidiateCommand(command)) {
                executor.commit(command);
                executeCommand();
                return;
            }
            executor.commit(command);
            candidatesField.setWindowState(CandidateWindowState.ArgumentWait);
            String commandText = executor.getCommandText() + CommandExecutor.SEPARATE_COMMAND_CHAR;
            candidatesField.setText(commandText);
        }
    }

    private boolean isImmidiateCandidate(Candidate candidate) {
        return candidate.getClass().isAnnotationPresent(Immediate.class);
    }

    private boolean isImmidiateCommand(Command command) {
        return command.getClass().isAnnotationPresent(Immediate.class);
    }

    private void executeCommand() {
        String candidateText = candidatesField.getText();
        quickWindow.close();
        CommandExecutor executor = quickWindow.getExecutor();
        try {
            executor.execute(candidateText);
        } catch (Exception e) {
            quickWindow.notifyError("Alert", e.getMessage());
        }
        quickWindow.reset();
    }

}
