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
		SelectCommand.clear();
		MockitoAnnotations.initMocks(this);
		createCommand("new project", newProjectCommand);		
		createCommand("create class", createClassCommand);		
		createCommand("create package", createPackageCommand);		
	}

	@Test
	public void candidatesWithNull() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter(null);
		Candidate[] candidates = commands.getCandidates();
		assertThat(candidates.length, is(allCommands));		
	}
	
	@Test
	public void candidatesWithEmpty() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("");
		Candidate[] candidates = commands.getCandidates();
		assertThat(candidates.length, is(allCommands));
	}
	
	@Test
	public void candidatesWithNew() throws Throwable{
		SelectCommand commands = new SelectCommand();
		commands.filter("new");
		Candidate[] candidates = commands.getCandidates();
		assertThat(candidates.length, is(1));
	}
	
	@Test
	public void candidatesWithCreate() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("create");
		Candidate[] candidates = commands.getCandidates();
		assertThat(candidates.length, is(2));		
	}

	@Test
	public void current() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("");
		Candidate current = commands.current();
		assertThat(current.getName(),is(newProjectCommand.getName()));
	}
	
	@Test
	public void down() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("");
		commands.down();
		Candidate current = commands.current();
		assertThat(current.getName(),is(createClassCommand.getName()));
	}
	
	@Test
	public void downAndCandidates() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("");
		commands.down();
		commands.filter("");
		Candidate current = commands.current();
		assertThat(current.getName(),is(newProjectCommand.getName()));
	}

	
	@Test
	public void rotateWhenUp() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("");
		commands.up();
		Candidate current = commands.current();
		assertThat(current.getName(),is(createPackageCommand.getName()));		
	}
	
	@Test
	public void downAndUp() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("");
		commands.down();
		commands.up();
		Candidate current = commands.current();
		assertThat(current.getName(),is(newProjectCommand.getName()));
	}
	
	@Test
	public void rotateWhenUpAndDown() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("");
		commands.up();
		commands.down();
		Candidate current = commands.current();
		assertThat(current.getName(),is(newProjectCommand.getName()));				
	}
	
	@Test
	public void notHappenedExceptionsWhenCandidatesAreZeroAndUp() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("notHappenedExceptionsWhenCandidatesAreZeroUp");
		commands.up();
		Candidate current = commands.current();
		assertThat(current,is(instanceOf(NullCandidate.class)));
	}
	
	@Test
	public void notHappenedExceptionsWhenCandidatesAreZeroAndDown() throws Exception {
		SelectCommand commands = new SelectCommand();
		commands.filter("notHappenedExceptionsWhenCandidatesAreZeroDown");
		commands.down();
		Candidate current = commands.current();
		assertThat(current,is(instanceOf(NullCandidate.class)));
	}

	private void createCommand(String commandName, Command command) {
		when(command.getName()).thenReturn(commandName);
		when(command.isEnable()).thenReturn(true);
		SelectCommand.add(command);
		allCommands++;
	}


}
