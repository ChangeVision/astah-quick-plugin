package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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

    private CommandBuilder builder;

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
        builder = new CommandBuilder();
    }
    
    @Test
    public void candidateTextWhenUncommitted() throws Exception {
        String candidateText = executor.getCandidateText(builder,"hoge");
        assertThat(candidateText,is("hoge"));
    }

    @Test
    public void candidateTextWhenCommitted() throws Exception {
        builder.commit(command);
        String candidateText = executor.getCandidateText(builder,COMMAND_NAME);
        assertThat(candidateText,is(""));
    }
    
    @Test
    public void candidateTextWhenCommittedAndSpace() throws Exception {
        builder.commit(command);
        String candidateText = executor.getCandidateText(builder,COMMAND_NAME + COMMAND_SEPARATOR);
        assertThat(candidateText,is(COMMAND_SEPARATOR));
    }

    @Test
    public void candidateTextAfterCommitted() throws Exception {
        builder.commit(command);
        String candidateText = executor.getCandidateText(builder,COMMAND_NAME + COMMAND_SEPARATOR + "hoge");
        assertThat(candidateText,is("hoge"));
    }
    
    @Test
    public void candidateTextHasSpaceSequence() throws Exception {
        builder.commit(command);
        String candidateText = executor.getCandidateText(builder,COMMAND_NAME + COMMAND_SEPARATOR + COMMAND_SEPARATOR + "hoge");
        assertThat(candidateText,is("hoge"));
    }

    @Test(expected=UncommitedCommandExcepition.class)
    public void throwExceptionWhenExecuteCommandIsNotCommitted() throws UncommitedCommandExcepition, ExecuteCommandException {
        executor.execute(builder,"");
    }

    @Test
    public void commitCommand() {
        
        builder.commit(command);

        Command actual = builder.getCommand();
        assertThat(actual,is(notNullValue()));
        assertThat(executor.getCommandText(builder),is(COMMAND_NAME));
    }
    
    @Test
    public void addArgument() throws Exception {
        builder.commit(candidateCommand);
        builder.add(one);
        assertThat(executor.getCommandText(builder),is(COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME));
    }
    
    @Test
    public void callExecuteWhenArgumentIsNull() throws Exception {
        builder.commit(command);
        executor.execute(builder,COMMAND_NAME);
        verify(command).execute(new String[]{});
    }
    
    @Test
    public void callExecuteWhenArgumentIsString() throws Exception {
        builder.commit(command);
        executor.execute(builder,COMMAND_NAME + COMMAND_SEPARATOR + "hoge");
        verify(command).execute("hoge");
    }
    
    @Test
    public void callExecuteWhenArgumentIsStringAndHasSpaceSequence() throws Exception {
        builder.commit(command);
        executor.execute(builder,COMMAND_NAME + COMMAND_SEPARATOR + COMMAND_SEPARATOR + "hoge");
        verify(command).execute("hoge");
    }
    
    @Test
    public void callExecuteWhenArgumentIsStringAndHasSpaceTripleSequence() throws Exception {
        builder.commit(command);
        executor.execute(builder,COMMAND_NAME + COMMAND_SEPARATOR + COMMAND_SEPARATOR + COMMAND_SEPARATOR + "hoge");
        verify(command).execute("hoge");
    }

    
    @Test
    public void callExecuteWhenAnArgumentIsSet() throws Exception {
        builder.commit(candidateCommand);
        builder.add(one);
        executor.execute(builder,COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME);
        verify(candidateCommand).execute(new Candidate[]{one});
    }

    @Test
    public void callExecuteWhenAnArgumentAndStringIsSet() throws Exception {
        builder.commit(candidateAndArgumentCommand);
        builder.add(one);
        executor.execute(builder,COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME + COMMAND_SEPARATOR + "hoge");
        verify(candidateAndArgumentCommand).execute(new Candidate[]{one}, new String[]{"hoge"});
    }

    @Test
    public void callExecuteWhenSomergumentIsSet() throws Exception {
        builder.commit(candidateCommand);
        builder.add(one);
        builder.add(two);
        executor.execute(builder,COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME + COMMAND_SEPARATOR + CANDIDATE_TWO_NAME);
        verify(candidateCommand).execute(new Candidate[]{one,two});
    }
    
    @Test
    public void callExecuteWhenFrightEngineSet() throws Exception {
        builder.commit(selectModelCommand);
        executor.execute(builder,"F");
        verify(selectModelCommand).execute();
    }
    
    @Test(expected=IllegalStateException.class)
    public void callExecuteWhenCandidateIsAddedButNotSupported() throws Exception {
        builder.commit(command);
        builder.add(one);
        String candidateText = COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME;
        executor.execute(builder,candidateText);
        verify(command).execute(candidateText);
    }

}
