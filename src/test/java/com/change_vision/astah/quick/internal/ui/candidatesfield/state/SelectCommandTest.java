package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.startsWith;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.Command;

public class SelectCommandTest {

	private SelectCommand state;
	
	@Before
	public void before() throws Exception {
		state = new SelectCommand();
	}

	@Test
	public void filterWithNull() {
		state.filter(null);
		
		Candidate[] candidates = state.getCandidates();
		List<Command> allCommands = SelectCommand.getAllCommands();
		Candidate[] expected = allCommands.toArray(new Candidate[0]);
		assertThat(candidates,is(expected));
	}
	
	@Test
	public void filterWithEmpty() throws Exception {
		state.filter("");
		
		Candidate[] candidates = state.getCandidates();
		List<Command> allCommands = SelectCommand.getAllCommands();
		Candidate[] expected = allCommands.toArray(new Candidate[0]);
		assertThat(candidates,is(expected));		
	}

	@Test
	public void filterWithCreate() throws Exception {
		state.filter("create");
		
		Candidate[] candidates = state.getCandidates();
		for (Candidate candidate : candidates) {
			assertThat(candidate,is(instanceOf(Command.class)));
			String name = candidate.getName();
			assertThat(name,is(startsWith("create")));
		}
	}
}
