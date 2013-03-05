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
import com.change_vision.astah.quick.command.exception.UncommitedCommandExcepition;

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
    private Candidate one;

    @Mock
    private Candidate two;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        executor = new CommandExecutor();
        when(command.getName()).thenReturn(COMMAND_NAME);
        when(candidateCommand.getName()).thenReturn(COMMAND_NAME);
        when(candidateAndArgumentCommand.getName()).thenReturn(COMMAND_NAME);
        when(one.getName()).thenReturn(CANDIDATE_ONE_NAME);
        when(two.getName()).thenReturn(CANDIDATE_TWO_NAME);
    }
    
    @Test
    public void initialState() throws Exception {
        Command actual = executor.getCommand();
        assertThat(actual,is(nullValue()));
        boolean commited = executor.isCommited();
        assertThat(commited,is(false));
    }
    
    @Test(expected=UncommitedCommandExcepition.class)
    public void throwExceptionWhenExecuteCommandIsNotCommitted() throws UncommitedCommandExcepition {
        executor.execute("");
    }

    @Test
    public void commitCommand() {
        
        executor.commit(command);

        Command actual = executor.getCommand();
        assertThat(actual,is(notNullValue()));
        boolean commited = executor.isCommited();
        assertThat(commited,is(true));
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
    public void removeArgument() throws Exception {
        executor.add(one);
        boolean removed = executor.remove(one);
        assertThat(removed,is(true));
    }

    
}
