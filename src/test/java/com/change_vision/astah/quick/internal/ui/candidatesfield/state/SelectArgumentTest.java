package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.CommandExecutor;

public class SelectArgumentTest {

    private static abstract class CandidatesProviderCommand implements Command, CandidatesProvider {

    }

    @Mock
    private Command committed;

    @Mock
    private CandidatesProviderCommand providerCommand;

    @Mock
    private CommandExecutor executor;

    private SelectArgument argument;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(committed.getName()).thenReturn("committed command");
        when(providerCommand.getName()).thenReturn("provider command");
        argument = new SelectArgument(executor);
    }

    @Test
    public void filterCommitted() {
        when(executor.getCommand()).thenReturn(committed);
        Candidate[] candidates = argument.filter("committed command");
        assertThat(candidates.length, is(1));
    }

    @Test
    public void filterProviderCommand() throws Exception {
        when(executor.getCandidates()).thenReturn(new Candidate[0]);
        when(executor.getCommand()).thenReturn(providerCommand);
        argument.filter("hoge");
        verify(providerCommand).candidate(new Candidate[0], "hoge");
    }
    
    @Test
    public void filterProviderCommandWhenNotFound() throws Exception {
        when(executor.getCandidates()).thenReturn(new Candidate[0]);
        when(executor.getCommand()).thenReturn(providerCommand);
        Candidate[] candidates = argument.filter("not found");
        assertThat(candidates,is(notNullValue()));
        assertThat(candidates.length,is(1));
        assertThat(candidates[0],is(instanceOf(NotFound.class)));
    }

}
