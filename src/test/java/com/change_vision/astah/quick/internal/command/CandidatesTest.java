package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgi.util.tracker.ServiceTracker;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Candidates.SelectCommandFactory;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectArgument;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectCommand;

public class CandidatesTest {

    private static abstract class CandidatesProviderCommand implements Command, CandidatesProvider {

    }
    private Candidates candidates;

    @Mock
    private SelectCommandFactory commandFactory;

    @Mock
    private Command one;

    @Mock
    private Command two;
    
    @Mock
    private CandidatesProviderCommand providerCommand;

    private CommandBuilder commandBuilder;

    @Mock
    private ServiceTracker tracker;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        Commands commands = new Commands(tracker);
        SelectCommand commandState = new SelectCommand(commands);
        commands.clear();
        when(commandFactory.create()).thenReturn(commandState);

        when(one.getName()).thenReturn("new project");
        when(one.isEnabled()).thenReturn(true);
        when(two.getName()).thenReturn("new diagram");
        when(two.isEnabled()).thenReturn(true);
        commands.add(one);
        commands.add(two);

        commandBuilder = new CommandBuilder();
        candidates = new Candidates(commands,commandBuilder);
        candidates.setCommandFactory(commandFactory);
    }

    @Test
    public void initStateIsSelectCommand() {
        CandidateState state = candidates.getState();
        assertThat(state, is(instanceOf(SelectCommand.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void filterWithNull() throws Exception {
        candidates.filter(null);
    }

    @Test
    public void filterWithEmpty() throws Exception {
        candidates.filter("");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual,is(notNullValue()));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectCommand.class)));
    }

    @Test
    public void filterWithFoundSomeCommand() throws Exception {
        candidates.filter("new");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual.length, is(2));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectCommand.class)));
    }

    @Test
    public void filterWitnFoundOnlyOneCommand() throws Exception {
        candidates.filter("new project");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual.length, is(1));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectArgument.class)));
    }
    
    @Test
    public void filterWithNotFoundCommand() throws Exception {
        candidates.filter("not found");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual,is(notNullValue()));
        assertThat(actual.length,is(1));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectCommand.class)));
    }
    
    @Test
    public void filterWithNotFoundArgument() throws Exception {
        commandBuilder.commit(providerCommand);
        candidates.setState(new SelectArgument(commandBuilder));
        candidates.filter("not found");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual,is(notNullValue()));
        assertThat(actual.length,is(1));
    }

    @Test
    public void changeStateWhenRemoveTheNameString() throws Exception {
        candidates.filter("new project");
        commandBuilder.removeCandidate();
        candidates.filter("new");
        Candidate[] actual = candidates.getCandidates();
        System.out.println(actual);
        assertThat(actual.length, is(2));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectCommand.class)));
    }

    @Test
    public void changeStateWhenResetTargetCommandName() throws Exception {
        candidates.filter("new project");
        commandBuilder.removeCandidate();
        candidates.filter("new");
        candidates.filter("new diagram");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual.length, is(1));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectArgument.class)));
    }

    @Test
    public void changeStateWhenPasteIncludeSpaceNameString() throws Exception {
        candidates.filter("new project");
        commandBuilder.removeCandidate();
        candidates.filter("new           ");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual.length, is(2));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectCommand.class)));
    }

    @Test
    public void changeStateWhenPasteIncludeTabNameString() throws Exception {
        candidates.filter("new project");
        commandBuilder.removeCandidate();
        candidates.filter("new\t\t\t");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual.length, is(2));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectCommand.class)));
    }
}
