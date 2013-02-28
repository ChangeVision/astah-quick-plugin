package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;
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

	@Before
	public void before() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		state = new SelectCommand();
		state.clear();
		state.setCommandFactory(commandFactory);
		
		createMockCommand(closeProjectCommand, "close project", true);
		createMockCommand(createClassCommand, "create class", true);
		createMockCommand(createPackageCommand, "create package", true);
		createMockCommand(notEnabledCommand, "not enabled", false);
		createMockCommand(foundClassModelCommand, "Class", true);
		createMockCommand(foundPackageModelCommand, "Class Package", true);
		
		state.add(closeProjectCommand);
		state.add(createClassCommand);
		state.add(createPackageCommand);
		state.add(notEnabledCommand);
		
		allCommands = new ArrayList<Command>();

		allCommands.add(closeProjectCommand);
		allCommands.add(createClassCommand);
		allCommands.add(createPackageCommand);
		allCommands.add(notEnabledCommand);
	}

	private Command createMockCommand(Command target, String commandName,
			boolean enable) {
		when(target.getName()).thenReturn(commandName);
		when(target.isEnable()).thenReturn(enable);
		return target;
	}

	@Test
	public void filterWithNull() {
		Candidate[] candidates = state.filter(null);
		
		Candidate[] expected = allCommands.toArray(new Candidate[0]);
		assertThat(candidates,is(expected));
	}
	
	@Test
	public void filterWithEmpty() throws Exception {
		Candidate[] candidates = state.filter("");
		
		assertThat(candidates.length,is(4));		
	}
	
	
	@Test
	public void filterWithC() throws Exception {
		Candidate[] candidates = state.filter("c");
		
		assertThat(candidates.length,is(3));		
	}

	@Test
	public void filterWithCreate() throws Exception {
		Candidate[] candidates = state.filter("create");
		
		assertThat(candidates.length,is(2));
	}
	
	@Test
	public void filterWithSpeficiedCommand() throws Exception {
		Candidate[] candidates = state.filter("create class");
		
		assertThat(candidates.length,is(1));
		assertThat(candidates[0].getName(),is("create class"));

		candidates = state.filter("create package");
		
		assertThat(candidates.length,is(1));
		assertThat(candidates[0].getName(),is("create package"));
	}
	
	@Test
	public void filterWithFoundModels() throws Exception {
		ArrayList<Candidate> created = new ArrayList<Candidate>();
		created.add(foundClassModelCommand);
		created.add(foundPackageModelCommand);
		when(commandFactory.create("Cla")).thenReturn(created);
		
		Candidate[] candidates = state.filter("Cla");
		assertThat(candidates.length,is(2));
		assertThat(candidates[0].getName(),is("Class"));
	}
}
