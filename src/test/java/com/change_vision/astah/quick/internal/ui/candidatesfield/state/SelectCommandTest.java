package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgi.util.tracker.ServiceTracker;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
import com.change_vision.astah.quick.internal.command.Commands;
import com.change_vision.astah.quick.internal.command.model.SelectModelCommandFactory;

public class SelectCommandTest {

    private SelectCommand state;

    @Mock
    private SelectModelCommandFactory commandFactory;

    @Mock
    private Command closeProjectCommand;

    @Mock
    private Command createClassCommand;

    @Mock
    private Command createPackageCommand;

    @Mock
    private Command notEnabledCommand;

    @Mock
    private Command foundClassModelCommand;

    @Mock
    private Command foundPackageModelCommand;

    private List<Command> allCommands;

    private Commands commands;

    @Mock
    private ServiceTracker tracker;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        commands = new Commands(tracker);
        state = new SelectCommand(commands);
        commands.clear();
        state.setCommandFactory(commandFactory);

        createMockCommand(closeProjectCommand, "close project", true);
        createMockCommand(createClassCommand, "create class", true);
        createMockCommand(createPackageCommand, "create package", true);
        createMockCommand(notEnabledCommand, "not enabled", false);
        createMockCommand(foundClassModelCommand, "Class", true);
        createMockCommand(foundPackageModelCommand, "Class Package", true);

        commands.add(closeProjectCommand);
        commands.add(createClassCommand);
        commands.add(createPackageCommand);
        commands.add(notEnabledCommand);

        allCommands = new ArrayList<Command>();

        allCommands.add(closeProjectCommand);
        allCommands.add(createClassCommand);
        allCommands.add(createPackageCommand);
        allCommands.add(notEnabledCommand);
    }

    @After
    public void after() throws Exception {
        commands.initCommands();
    }

    private Command createMockCommand(Command target, String commandName, boolean enable) {
        when(target.getName()).thenReturn(commandName);
        when(target.isEnabled()).thenReturn(enable);
        return target;
    }

    @Test
    public void filterWithNull() {
        Candidate[] candidates = state.filter(null);

        Candidate[] expected = new Candidate[]{
                closeProjectCommand,
                createClassCommand,
                createPackageCommand
        };
        assertThat(candidates.length, is(3));
        assertThat(candidates, is(expected));
    }

    @Test
    public void filterWithEmpty() throws Exception {
        Candidate[] candidates = state.filter("");

        Candidate[] expected = new Candidate[]{
                closeProjectCommand,
                createClassCommand,
                createPackageCommand
        };
        assertThat(candidates.length, is(3));
        assertThat(candidates, is(expected));
    }

    @Test
    public void filterWithC() throws Exception {
        Candidate[] candidates = state.filter("c");

        assertThat(candidates.length, is(3));
    }

    @Test
    public void filterWithCreate() throws Exception {
        Candidate[] candidates = state.filter("create");

        assertThat(candidates.length, is(2));
    }

    @Test
    public void filterWithSpeficiedCommand() throws Exception {
        Candidate[] candidates = state.filter("create class");

        assertThat(candidates.length, is(1));
        assertThat(candidates[0].getName(), is("create class"));

        candidates = state.filter("create package");

        assertThat(candidates.length, is(1));
        assertThat(candidates[0].getName(), is("create package"));
    }

    @Test
    public void filterWithFoundModels() throws Exception {
        ArrayList<Candidate> created = new ArrayList<Candidate>();
        created.add(foundClassModelCommand);
        created.add(foundPackageModelCommand);
        when(commandFactory.create("Cla")).thenReturn(created);

        Candidate[] candidates = state.filter("Cla");
        assertThat(candidates.length, is(2));
        assertThat(candidates[0].getName(), is("Class"));
    }
}
