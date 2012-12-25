package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
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
	
	@Before
	public void before(){
		Commands.clear();
		MockitoAnnotations.initMocks(this);
		when(newProjectCommand.getCommandName()).thenReturn("new project");
		when(createClassCommand.getCommandName()).thenReturn("create class");
		Commands.add(newProjectCommand);
		Commands.add(createClassCommand);		
	}
	
	@Test
	public void candidatesWithNull() throws Exception {
		Command[] candidates = Commands.candidates(null);		
		assertThat(candidates.length, is(2));		
	}
	
	@Test
	public void candidatesWithEmpty() throws Exception {
		Command[] candidates = Commands.candidates("");		
		assertThat(candidates.length, is(2));
	}
	
	@Test
	public void candidatesWithNew() throws Throwable{
		Command[] candidates = Commands.candidates("new");
		assertThat(candidates.length, is(1));
	}

}
