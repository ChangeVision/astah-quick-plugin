package com.change_vision.astah.quick.internal.ui.candidatesfield.state;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidatesProvider;
import com.change_vision.astah.quick.command.Command;

public class SelectArgumentTest {
	
	private static abstract class CandidatesProviderCommand implements Command,CandidatesProvider{
		
	}
	
	@Mock
	private Command committed;
	
	@Mock
	private CandidatesProviderCommand providerCommand;
	
	@Before
	public void before() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		when(committed.getName()).thenReturn("committed command");
		when(providerCommand.getName()).thenReturn("provider command");
	}

	@Test
	public void filterCommitted() {
		SelectArgument argument = new SelectArgument(committed);
		argument.filter("committed command");
		Candidate[] candidates = argument.getCandidates();
		assertThat(candidates.length,is(1));
	}
	
	@Test
	public void filterProviderCommand() throws Exception {
		SelectArgument argument = new SelectArgument(providerCommand);
		argument.filter("provider command hoge");		
		verify(providerCommand).candidate("provider command hoge");
	}

}
