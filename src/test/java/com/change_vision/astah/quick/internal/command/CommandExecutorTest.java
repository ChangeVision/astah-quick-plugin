package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateAndArgumentSupportCommand;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.command.model.ModelAPI;
import com.change_vision.astah.quick.internal.command.model.SelectModelCommand;
import com.change_vision.astah.quick.internal.exception.UncommitedCommandExcepition;

public class CommandExecutorTest {

    private static final String COMMAND_SEPARATOR = " ";

    private static final String CANDIDATE_ONE_NAME = "one";

    private static final String CANDIDATE_TWO_NAME = "two";

    private static final String COMMAND_NAME = "create command";

    private CommandExecutor executor;

    @Mock
    private Command command;
    
    @Mock
    private CandidateSupportCommand candidateCommand;

    @Mock
    private CandidateAndArgumentSupportCommand candidateAndArgumentCommand;
    
    @Mock
    private SelectModelCommand selectModelCommand;

    @Mock
    private Candidate one;

    @Mock
    private Candidate two;

    @Mock
    private ModelAPI api;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        executor = new CommandExecutor();
        when(command.getName()).thenReturn(COMMAND_NAME);
        when(candidateCommand.getName()).thenReturn(COMMAND_NAME);
        when(candidateAndArgumentCommand.getName()).thenReturn(COMMAND_NAME);
        when(one.getName()).thenReturn(CANDIDATE_ONE_NAME);
        when(two.getName()).thenReturn(CANDIDATE_TWO_NAME);
        when(selectModelCommand.getName()).thenReturn("Flight Engine");
    }
    
    @Test
    public void initialState() throws Exception {
        Command actual = executor.getCommand();
        assertThat(actual,is(nullValue()));
        boolean commited = executor.isCommited();
        assertThat(commited,is(false));

        String commandText = executor.getCommandText();
        assertThat(commandText,is(""));
        String candidateText = executor.getCandidateText(commandText);
        assertThat(candidateText,is(""));
        Candidate removed = executor.removeCandidate();
        assertThat(removed,is(nullValue()));
    }
    
    @Test
    public void candidateTextWhenUncommitted() throws Exception {
        String candidateText = executor.getCandidateText("hoge");
        assertThat(candidateText,is("hoge"));
    }

    @Test
    public void candidateTextWhenCommitted() throws Exception {
        executor.commit(command);
        String candidateText = executor.getCandidateText(COMMAND_NAME);
        assertThat(candidateText,is(""));
    }
    
    @Test
    public void candidateTextWhenCommittedAndSpace() throws Exception {
        executor.commit(command);
        String candidateText = executor.getCandidateText(COMMAND_NAME + COMMAND_SEPARATOR);
        assertThat(candidateText,is(COMMAND_SEPARATOR));
    }

    @Test
    public void candidateTextAfterCommitted() throws Exception {
        executor.commit(command);
        String candidateText = executor.getCandidateText(COMMAND_NAME + COMMAND_SEPARATOR + "hoge");
        assertThat(candidateText,is("hoge"));
    }
    
    @Test
    public void candidateTextHasSpaceSequence() throws Exception {
        executor.commit(command);
        String candidateText = executor.getCandidateText(COMMAND_NAME + COMMAND_SEPARATOR + COMMAND_SEPARATOR + "hoge");
        assertThat(candidateText,is("hoge"));
    }

    @Test(expected=UncommitedCommandExcepition.class)
    public void throwExceptionWhenExecuteCommandIsNotCommitted() throws UncommitedCommandExcepition, ExecuteCommandException {
        executor.execute("");
    }

    @Test
    public void commitCommand() {
        
        executor.commit(command);

        Command actual = executor.getCommand();
        assertThat(actual,is(notNullValue()));
        boolean commited = executor.isCommited();
        assertThat(commited,is(true));
        assertThat(executor.getCommandText(),is(COMMAND_NAME));
    }
    
    @Test
    public void addArgument() throws Exception {
        executor.commit(candidateCommand);
        executor.add(one);
        assertThat(executor.getCommandText(),is(COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME));
    }
    
    @Test
    public void callExecuteWhenArgumentIsNull() throws Exception {
        executor.commit(command);
        executor.execute(COMMAND_NAME);
        verify(command).execute(new String[]{});
    }
    
    @Test
    public void callExecuteWhenArgumentIsString() throws Exception {
        executor.commit(command);
        executor.execute(COMMAND_NAME + COMMAND_SEPARATOR + "hoge");
        verify(command).execute("hoge");
    }
    
    @Test
    public void callExecuteWhenArgumentIsStringAndHasSpaceSequence() throws Exception {
        executor.commit(command);
        executor.execute(COMMAND_NAME + COMMAND_SEPARATOR + COMMAND_SEPARATOR + "hoge");
        verify(command).execute("hoge");
    }
    
    @Test
    public void callExecuteWhenArgumentIsStringAndHasSpaceTripleSequence() throws Exception {
        executor.commit(command);
        executor.execute(COMMAND_NAME + COMMAND_SEPARATOR + COMMAND_SEPARATOR + COMMAND_SEPARATOR + "hoge");
        verify(command).execute("hoge");
    }

    
    @Test
    public void callExecuteWhenAnArgumentIsSet() throws Exception {
        executor.commit(candidateCommand);
        executor.add(one);
        executor.execute(COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME);
        verify(candidateCommand).execute(new Candidate[]{one});
    }

    @Test
    public void callExecuteWhenAnArgumentAndStringIsSet() throws Exception {
        executor.commit(candidateAndArgumentCommand);
        executor.add(one);
        executor.execute(COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME + COMMAND_SEPARATOR + "hoge");
        verify(candidateAndArgumentCommand).execute(new Candidate[]{one}, new String[]{"hoge"});
    }

    @Test
    public void callExecuteWhenSomergumentIsSet() throws Exception {
        executor.commit(candidateCommand);
        executor.add(one);
        executor.add(two);
        executor.execute(COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME + COMMAND_SEPARATOR + CANDIDATE_TWO_NAME);
        verify(candidateCommand).execute(new Candidate[]{one,two});
    }
    
    @Test
    public void callExecuteWhenFrightEngineSet() throws Exception {
        executor.commit(selectModelCommand);
        executor.execute("F");
        verify(selectModelCommand).execute();
    }
    
    @Test(expected=IllegalStateException.class)
    public void callExecuteWhenCandidateIsAddedButNotSupported() throws Exception {
        executor.commit(command);
        executor.add(one);
        String candidateText = COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME;
        executor.execute(candidateText);
        verify(command).execute(candidateText);
    }
    
    @Test
    public void removeCandidateCommand() throws Exception {
        executor.commit(candidateCommand);
        Candidate removed = executor.removeCandidate();
        assertThat(removed,is(notNullValue()));
    }

    @Test
    public void removeCandidateArgument() throws Exception {
        executor.commit(candidateCommand);
        executor.add(one);
        Candidate removed = executor.removeCandidate();
        assertThat(removed,is(one));
    }
    
    @Test
    public void resetWithCommand() throws Exception {
        executor.commit(candidateCommand);
        assertThat(executor.isCommited(),is(true));
        executor.reset();
        assertThat(executor.isCommited(),is(false));
    }
    
    @Test
    public void resetWithCandidates() throws Exception {
        executor.commit(candidateCommand);
        executor.add(one);
        assertThat(executor.getCandidates().length,is(1));
        executor.reset();
        assertThat(executor.getCandidates().length,is(0));
    }

}
