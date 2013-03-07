package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Candidates.SelectCommandFactory;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectArgument;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectCommand;

public class CandidatesTest {

    private Candidates candidates;

    @Mock
    private SelectCommandFactory commandFactory;

    @Mock
    private Command one;

    @Mock
    private Command two;

    @Mock
    private CommandExecutor executor;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        SelectCommand commandState = new SelectCommand();
        commandState.clear();
        when(commandFactory.create()).thenReturn(commandState);

        when(one.getName()).thenReturn("new project");
        when(one.isEnabled()).thenReturn(true);
        when(two.getName()).thenReturn("new diagram");
        when(two.isEnabled()).thenReturn(true);
        commandState.add(one);
        commandState.add(two);

        candidates = new Candidates(executor);
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
        System.out.println(actual.length);
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
    public void changeStateWhenRemoveTheNameString() throws Exception {
        candidates.filter("new project");
        candidates.filter("new");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual.length, is(2));
        CandidateState next = candidates.getState();
        assertThat(next, is(instanceOf(SelectCommand.class)));
    }

    @Test
    public void changeStateWhenResetTargetCommandName() throws Exception {
        candidates.filter("new project");
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
        candidates.filter("new           ");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual.length,is(2));
        CandidateState next = candidates.getState();
        assertThat(next,is(instanceOf(SelectCommand.class)));
    }
    

    @Test
    public void changeStateWhenPasteIncludeTabNameString() throws Exception {
        candidates.filter("new project");
        candidates.filter("new\t\t\t");
        Candidate[] actual = candidates.getCandidates();
        assertThat(actual.length,is(2));
        CandidateState next = candidates.getState();
        assertThat(next,is(instanceOf(SelectCommand.class)));
    }
}
