package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

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

public class CommandSelectingTest {

	
	@Mock
	private Command newProjectCommand;
	
	@Mock
	private Command createClassCommand;
	
	@Mock
	private Command createPackageCommand;
	
	private int allCommands;
	
	@Before
	public void before(){
		CommandSelecting.clear();
		MockitoAnnotations.initMocks(this);
		createCommand("new project", newProjectCommand);		
		createCommand("create class", createClassCommand);		
		createCommand("create package", createPackageCommand);		
	}

	@Test
	public void candidatesWithNull() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates(null);
		Candidate[] candidates = commands.getCandidates();
		assertThat(candidates.length, is(allCommands));		
	}
	
	@Test
	public void candidatesWithEmpty() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("");
		Candidate[] candidates = commands.getCandidates();
		assertThat(candidates.length, is(allCommands));
	}
	
	@Test
	public void candidatesWithNew() throws Throwable{
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("new");
		Candidate[] candidates = commands.getCandidates();
		assertThat(candidates.length, is(1));
	}
	
	@Test
	public void candidatesWithCreate() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("create");
		Candidate[] candidates = commands.getCandidates();
		assertThat(candidates.length, is(2));		
	}

	@Test
	public void current() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("");
		Candidate current = commands.current();
		assertThat(current.getName(),is(newProjectCommand.getName()));
	}
	
	@Test
	public void down() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("");
		commands.down();
		Candidate current = commands.current();
		assertThat(current.getName(),is(createClassCommand.getName()));
	}
	
	@Test
	public void downAndCandidates() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("");
		commands.down();
		commands.candidates("");
		Candidate current = commands.current();
		assertThat(current.getName(),is(newProjectCommand.getName()));
	}

	
	@Test
	public void rotateWhenUp() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("");
		commands.up();
		Candidate current = commands.current();
		assertThat(current.getName(),is(createPackageCommand.getName()));		
	}
	
	@Test
	public void downAndUp() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("");
		commands.down();
		commands.up();
		Candidate current = commands.current();
		assertThat(current.getName(),is(newProjectCommand.getName()));
	}
	
	@Test
	public void rotateWhenUpAndDown() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("");
		commands.up();
		commands.down();
		Candidate current = commands.current();
		assertThat(current.getName(),is(newProjectCommand.getName()));				
	}
	
	@Test
	public void notHappenedExceptionsWhenCandidatesAreZeroAndUp() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("notHappenedExceptionsWhenCandidatesAreZeroUp");
		commands.up();
		Candidate current = commands.current();
		assertThat(current,is(instanceOf(NullCandidate.class)));
	}
	
	@Test
	public void notHappenedExceptionsWhenCandidatesAreZeroAndDown() throws Exception {
		CommandSelecting commands = new CommandSelecting();
		commands.candidates("notHappenedExceptionsWhenCandidatesAreZeroDown");
		commands.down();
		Candidate current = commands.current();
		assertThat(current,is(instanceOf(NullCandidate.class)));
	}

	private void createCommand(String commandName, Command command) {
		when(command.getName()).thenReturn(commandName);
		when(command.isEnable()).thenReturn(true);
		CommandSelecting.add(command);
		allCommands++;
	}


}
