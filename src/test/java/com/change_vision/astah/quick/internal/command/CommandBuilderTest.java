package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;

public class CommandBuilderTest {
    
    private static final String COMMAND_NAME = "create command";
    
    private static final String COMMAND_SEPARATOR = " ";

    private static final String CANDIDATE_ONE_NAME = "one";

    private static final String CANDIDATE_TWO_NAME = "two";
    
    @Mock
    private Command command;
    
    @Mock
    private Candidate one;

    @Mock
    private Candidate two;

    private CommandBuilder builder;

    @Mock
    private PropertyChangeListener propertyChangeListener;
    
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        builder = new CommandBuilder();
        when(command.getName()).thenReturn(COMMAND_NAME);
        when(one.getName()).thenReturn(CANDIDATE_ONE_NAME);
        when(two.getName()).thenReturn(CANDIDATE_TWO_NAME);

    }
    
    @Test
    public void init() throws Exception {
        Command actual = builder.getCommand();
        assertThat(actual,is(nullValue()));
        assertThat(builder.isCommitted(),is(false));
        Candidate[] candidates = builder.getCandidates();
        assertThat(candidates,is(notNullValue()));
        assertThat(candidates.length,is(0));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void commitWithNull() throws Exception {
        builder.commit(null);
    }

    @Test
    public void commitAndGetCommand() {
        builder.commit(command);
        Command actual = builder.getCommand();
        assertThat(actual,is(notNullValue()));
        assertThat(builder.isCommitted(),is(true));
        assertThat(builder.getCommandText(),is(COMMAND_NAME));

    }
    
    @Test
    public void receivePropertyChangeByCommit() throws Exception {
        builder.addPropertyChangeListener(propertyChangeListener);
        builder.commit(command);
        ArgumentCaptor<PropertyChangeEvent> captor = ArgumentCaptor.forClass(PropertyChangeEvent.class);
        verify(propertyChangeListener).propertyChange(captor.capture());
        PropertyChangeEvent event = captor.getValue();
        assertThat(event.getPropertyName(),is(CommandBuilder.PROP_OF_COMMAND));
    }
    
    @Test
    public void addCandidate() throws Exception {
        builder.commit(command);
        builder.add(one);
        Candidate[] candidates = builder.getCandidates();
        assertThat(candidates,is(notNullValue()));
        assertThat(candidates.length,is(1));
        assertThat(builder.getCommandText(),is(COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME));

        builder.add(two);
        candidates = builder.getCandidates();
        assertThat(candidates,is(notNullValue()));
        assertThat(candidates.length,is(2));
        assertThat(builder.getCommandText(),is(COMMAND_NAME + COMMAND_SEPARATOR + CANDIDATE_ONE_NAME + COMMAND_SEPARATOR + CANDIDATE_TWO_NAME));
    }

    @Test
    public void receivePropertyChangeByAdd() throws Exception {
        builder.addPropertyChangeListener(propertyChangeListener);
        builder.add(one);
        ArgumentCaptor<PropertyChangeEvent> captor = ArgumentCaptor.forClass(PropertyChangeEvent.class);
        verify(propertyChangeListener).propertyChange(captor.capture());
        PropertyChangeEvent event = captor.getValue();
        assertThat(event.getPropertyName(),is(CommandBuilder.PROP_OF_CANDIDATE));
    }
    
    @Test
    public void removeACandidate() throws Exception {
        builder.add(one);
        builder.remove(one);
        Candidate[] candidates = builder.getCandidates();
        assertThat(candidates,is(notNullValue()));
        assertThat(candidates.length,is(0));
    }
    
    @Test
    public void removeCandidateNotContained() throws Exception {
        boolean remove = builder.remove(one);
        assertThat(remove,is(false));
    }
    
    @Test
    public void receivePropertyChangeByRemove() throws Exception {
        builder.addPropertyChangeListener(propertyChangeListener);
        builder.add(one);
        builder.remove(one);
        ArgumentCaptor<PropertyChangeEvent> captor = ArgumentCaptor.forClass(PropertyChangeEvent.class);
        verify(propertyChangeListener,times(2)).propertyChange(captor.capture());
        PropertyChangeEvent event = captor.getValue();
        assertThat(event.getPropertyName(),is(CommandBuilder.PROP_OF_CANDIDATE));
    }
    
    @Test
    public void removeCandidate() throws Exception {
        builder.commit(command);
        builder.add(one);
        builder.add(two);
        builder.removeCandidate();
        assertThat(builder.getCommand(),is(notNullValue()));
        Candidate[] candidates = builder.getCandidates();
        assertThat(candidates,is(notNullValue()));
        assertThat(candidates.length,is(1));
        assertThat(candidates[0],is(one));
        builder.removeCandidate();
        candidates = builder.getCandidates();
        assertThat(builder.getCommand(),is(notNullValue()));
        assertThat(candidates,is(notNullValue()));
        assertThat(candidates.length,is(0));
        builder.removeCandidate();
        assertThat(builder.getCommand(),is(nullValue()));
    }
    
    @Test
    public void candidateTextWhenUncommitted() throws Exception {
        String candidateText = builder.getCandidateText("hoge");
        assertThat(candidateText,is("hoge"));
    }

    @Test
    public void candidateTextWhenCommitted() throws Exception {
        builder.commit(command);
        String candidateText = builder.getCandidateText(COMMAND_NAME);
        assertThat(candidateText,is(""));
    }
    
    @Test
    public void candidateTextWhenCommittedAndSpace() throws Exception {
        builder.commit(command);
        String candidateText = builder.getCandidateText(COMMAND_NAME + COMMAND_SEPARATOR);
        assertThat(candidateText,is(COMMAND_SEPARATOR));
    }

    @Test
    public void candidateTextAfterCommitted() throws Exception {
        builder.commit(command);
        String candidateText = builder.getCandidateText(COMMAND_NAME + COMMAND_SEPARATOR + "hoge");
        assertThat(candidateText,is("hoge"));
    }
    
    @Test
    public void candidateTextHasSpaceSequence() throws Exception {
        builder.commit(command);
        String candidateText = builder.getCandidateText(COMMAND_NAME + COMMAND_SEPARATOR + COMMAND_SEPARATOR + "hoge");
        assertThat(candidateText,is("hoge"));
    }


}
