package com.change_vision.astah.quick.internal.modelfinder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.INamedElement;

public class DiagramFinderTest {
    
    @Mock
    private IDiagram diagram;
    
    @Mock
    private INamedElement element;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void emptyNameDiagram() {
        when(diagram.getName()).thenReturn("");
        DiagramFinder finder = new DiagramFinder("");
        boolean target = finder.isTarget(diagram);
        assertThat(target,is(true));
    }

    @Test
    public void emptyNameElement() {
        when(element.getName()).thenReturn("");
        DiagramFinder finder = new DiagramFinder("");
        boolean target = finder.isTarget(element);
        assertThat(target,is(false));
    }
    
    @Test
    public void namedDiagram() throws Exception {
        when(diagram.getName()).thenReturn("hoge");
        when(diagram.getFullName(".")).thenReturn("hoge");
        DiagramFinder finder = new DiagramFinder("ho");
        boolean target = finder.isTarget(diagram);
        assertThat(target,is(true));
    }
    
    @Test
    public void namedDiagramInNamespace() throws Exception {
        when(diagram.getName()).thenReturn("Fuga");
        when(diagram.getFullName(".")).thenReturn("hoge.Fuga");
        DiagramFinder finder = new DiagramFinder("ho");
        boolean target = finder.isTarget(diagram);
        assertThat(target,is(true));
    }
    
}
