package com.change_vision.astah.quick.internal.command;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.change_vision.astah.quick.internal.ui.candidatesfield.state.CandidateState;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.SelectCommand;

public class CandidatesTest {
	
	private Candidates candidates;

	@Before
	public void before() throws Exception {
		candidates = new Candidates();
	}

	@Test
	public void initStateIsSelectCommand() {
		CandidateState state = candidates.getState();
		assertThat(state,is(instanceOf(SelectCommand.class)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void filterWithNull() throws Exception {
		candidates.filter(null);
	}

}
