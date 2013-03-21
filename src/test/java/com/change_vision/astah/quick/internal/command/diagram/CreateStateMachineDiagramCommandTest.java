package com.change_vision.astah.quick.internal.command.diagram;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.candidates.ElementCandidate;
import com.change_vision.astah.quick.command.candidates.InvalidState;
import com.change_vision.astah.quick.command.candidates.NotFound;
import com.change_vision.astah.quick.internal.command.diagram.CreateStateMachineDiagramCommand.StateMachineDiagramCandidate;
import com.change_vision.jude.api.inf.model.INamedElement;


public class CreateStateMachineDiagramCommandTest {
    
    private CreateStateMachineDiagramCommand command;
    
    @Mock
    public DiagramAPI api;
    
    @Mock
    public ElementCandidate elementCandidate;
    
    @Mock
    public INamedElement elem;
    
    @Mock
    public INamedElement element;
    
    @Mock
    public StateMachineDiagramCandidate diagramCandidate;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        command = new CreateStateMachineDiagramCommand();
        command.setApi(api);
        when(elementCandidate.getElement()).thenReturn(element);
        when(elementCandidate.getName()).thenReturn("element");
        when(element.getName()).thenReturn("element");
        when(diagramCandidate.getName()).thenReturn("hoge");
    }
    
    @Test
    public void candidateWithEmpty() throws Exception {
        when(api.findClassOrPackage("")).thenReturn(new INamedElement[0]);
        Candidate[] candidates = command.candidate(new Candidate[0], "");
        assertThat(candidates.length,is(1));
        assertThat(candidates[0],is(NotFound.class));
    }
    
    @Test
    public void candidateWithFoundElement() throws Exception {
        when(api.findClassOrPackage("element")).thenReturn(new INamedElement[]{
                element
        });
        Candidate[] candidates = command.candidate(new Candidate[0], "element");
        assertThat(candidates.length,is(1));
        assertThat(candidates[0],is(ElementCandidate.class));
    }
    
    @Test
    public void candidateWithFoundElements() throws Exception {
        when(api.findClassOrPackage("elem")).thenReturn(new INamedElement[]{
                elem,
                element
        });
        Candidate[] candidates = command.candidate(new Candidate[0], "elem");
        assertThat(candidates.length,is(2));
    }

    @Test
    public  void candidateWithCommittedOwnerAndEmptyKey() throws Exception {
        Candidate[] candidate = command.candidate(new Candidate[]{
                elementCandidate
        },"element ");
        assertThat(candidate.length,is(1));
        assertThat(candidate[0],is(InvalidState.class));
    }

    @Test(expected=IllegalArgumentException.class)
    public  void executeWithNoCandidate() throws Exception {
        command.execute(new Candidate[0]);
    }
    

    @Test(expected=IllegalArgumentException.class)
    public  void executeWithACandidate() throws Exception {
        command.execute(new Candidate[]{
                elementCandidate
        });
    }

    @Test
    public  void executeWithCorrectCandidate() throws Exception {
        command.execute(new Candidate[]{
                elementCandidate,
                diagramCandidate
        });
        verify(api).createStateMachineDiagram(element, "hoge");
    }
    

}
