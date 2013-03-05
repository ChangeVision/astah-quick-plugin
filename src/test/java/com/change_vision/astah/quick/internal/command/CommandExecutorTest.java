package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.command.exception.UncommitedCommandExcepition;

public class CommandExecutorTest {

    private CommandExecutor executor;

    @Mock
    private Command command;
    
    @Mock
    private CandidateSupportCommand candidateCommand;

    @Mock
    private Candidate one;

    @Mock
    private Candidate two;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        executor = new CommandExecutor();
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
        executor.execute();
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
        executor.execute();
        verify(command).execute(new String[]{});
    }
    
    @Test
    public void callExecuteWhenAnArgumentIsSet() throws Exception {
        executor.commit(candidateCommand);
        executor.add(one);
        executor.execute();
        verify(candidateCommand).execute(one);
    }
    
    @Test
    public void callExecuteWhenSomergumentIsSet() throws Exception {
        executor.commit(candidateCommand);
        executor.add(one);
        executor.add(two);
        executor.execute();
        verify(candidateCommand).execute(one,two);
    }

    
}
