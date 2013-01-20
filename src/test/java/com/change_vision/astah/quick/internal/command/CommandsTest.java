package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Command;

public class CommandsTest {
	
	@Mock
	private Command newProjectCommand;
	
	@Mock
	private Command createClassCommand;
	
	@Mock
	private Command createPackageCommand;
	
	private int allCommands;
	
	@Before
	public void before(){
		Commands.clear();
		MockitoAnnotations.initMocks(this);
		createCommand("new project", newProjectCommand);		
		createCommand("create class", createClassCommand);		
		createCommand("create package", createPackageCommand);		
	}

	@Test
	public void candidatesWithNull() throws Exception {
		Commands commands = new Commands();
		commands.candidates(null);
		Command[] candidates = commands.getCommands();
		assertThat(candidates.length, is(allCommands));		
	}
	
	@Test
	public void candidatesWithEmpty() throws Exception {
		Commands commands = new Commands();
		commands.candidates("");
		Command[] candidates = commands.getCommands();
		assertThat(candidates.length, is(allCommands));
	}
	
	@Test
	public void candidatesWithNew() throws Throwable{
		Commands commands = new Commands();
		commands.candidates("new");
		Command[] candidates = commands.getCommands();
		assertThat(candidates.length, is(1));
	}
	
	@Test
	public void candidatesWithCreate() throws Exception {
		Commands commands = new Commands();
		commands.candidates("create");
		Command[] candidates = commands.getCommands();
		assertThat(candidates.length, is(2));		
	}

	@Test
	public void current() throws Exception {
		Commands commands = new Commands();
		commands.candidates("");
		Command current = commands.current();
		assertThat(current,is(newProjectCommand));
	}
	
	@Test
	public void down() throws Exception {
		Commands commands = new Commands();
		commands.candidates("");
		commands.down();
		Command current = commands.current();
		assertThat(current,is(createClassCommand));
	}
	
	@Test
	public void rotateWhenUp() throws Exception {
		Commands commands = new Commands();
		commands.candidates("");
		commands.up();
		Command current = commands.current();
		assertThat(current,is(createPackageCommand));		
	}
	
	@Test
	public void downAndUp() throws Exception {
		Commands commands = new Commands();
		commands.candidates("");
		commands.down();
		commands.up();
		Command current = commands.current();
		assertThat(current,is(newProjectCommand));
	}
	
	@Test
	public void rotateWhenUpAndDown() throws Exception {
		Commands commands = new Commands();
		commands.candidates("");
		commands.up();
		commands.down();
		Command current = commands.current();
		assertThat(current,is(newProjectCommand));				
	}
	
	@Test
	public void notHappenedExceptionsWhenCandidatesAreZeroAndUp() throws Exception {
		Commands commands = new Commands();
		commands.candidates("notHappenedExceptionsWhenCandidatesAreZeroUp");
		commands.up();
		Command current = commands.current();
		assertThat(current,is(nullValue()));
		assertThat(commands.currentIndex,is(0));
	}
	
	@Test
	public void notHappenedExceptionsWhenCandidatesAreZeroAndDown() throws Exception {
		Commands commands = new Commands();
		commands.candidates("notHappenedExceptionsWhenCandidatesAreZeroDown");
		commands.down();
		Command current = commands.current();
		assertThat(current,is(nullValue()));
		assertThat(commands.currentIndex,is(0));
	}

	private void createCommand(String commandName, Command command) {
		when(command.getCommandName()).thenReturn(commandName);
		when(command.isEnable()).thenReturn(true);
		Commands.add(command);
		allCommands++;
	}

}
