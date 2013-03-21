package com.change_vision.astah.quick.internal.command.model;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.candidates.ElementCandidate;
import com.change_vision.astah.quick.command.candidates.StereotypeCandidate;
import com.change_vision.astah.quick.internal.ui.candidatesfield.state.NotFound;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.INamedElement;

public class AddStereotypeCommandTest {
    
    @Mock
    private ModelAPI api;
    
    @Mock
    private IClass clazz;
    private AddStereotypeCommand command;
    
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        command = new AddStereotypeCommand();
        command.setAPI(api);
        when(clazz.getName()).thenReturn("Hoge");
    }

    @Test
    public void findFirstCandidateIsModel() {
        when(api.findClassOrPackage("")).thenReturn(new INamedElement[0]);
        Candidate[] candidates = command.candidate(new Candidate[0], "");
        assertThat(candidates.length,is(1));
        assertThat(candidates[0],is(instanceOf(NotFound.class)));
    }

    @Test
    public void findSecondCandidateIsStereotype() {
        Candidate[] candidates = command.candidate(new Candidate[]{new ElementCandidate(clazz)}, "Hoge");
        assertThat(candidates.length,is(StereotypeCandidates.DEFINED_CANDIDATES.length));
        for (Candidate candidate : candidates) {
            assertThat(candidate,is(instanceOf(StereotypeCandidate.class)));
        }
    }
    
    @Test
    public void findSecondCandidateIncrementalSearch() {
        Candidate[] candidates = command.candidate(new Candidate[]{new ElementCandidate(clazz)}, "Hoge a");
        assertThat(candidates.length,is(2));
    }

}
