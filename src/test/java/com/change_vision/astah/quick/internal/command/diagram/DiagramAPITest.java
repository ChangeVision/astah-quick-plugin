package com.change_vision.astah.quick.internal.command.diagram;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;

public class DiagramAPITest {
    
    private DiagramAPI api;

    @Mock
    private AstahAPIWrapper wrapper;
    
    @Mock
    private IDiagram diagram;

    @Mock
    private IDiagramViewManager diagramViewManager;

    @Mock
    private ProjectAccessor projectAccessor;
    
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        api = new DiagramAPI(wrapper);
        when(wrapper.getDiagramViewManager()).thenReturn(diagramViewManager);
        when(wrapper.getProjectAccessor()).thenReturn(projectAccessor);
    }

    @Test(expected=IllegalArgumentException.class)
    public void openWithNull() {
        api.open(null);
    }
    
    @Test
    public void openWithSpecifiedDiagram() throws Exception {
        api.open(diagram);
        verify(diagramViewManager).open(diagram);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void findWithNull() throws Exception {
        api.find(null);
    }
    
    @Test
    public void findWithName() throws Exception {
        when(projectAccessor.findElements((ModelFinder)any())).thenReturn(new IDiagram[]{diagram});
        IDiagram[] diagrams = api.find("Class");
        assertThat(diagrams.length,is(1));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void closeWithNull() throws Exception {
        api.close(null);
    }
    
    @Test
    public void closeWithDiagram() throws Exception {
        api.close(diagram);
        verify(diagramViewManager).close(diagram);
    }
    
    @Test
    public void closeCurrentDiagram() throws Exception {
        api.closeCurrentDiagram();
        verify(diagramViewManager).closeCurrentDiagramEditor();
    }
    
    @Test
    public void isOpenDiagrams() throws Exception {
        when(diagramViewManager.getCurrentDiagram()).thenReturn(null);
        assertThat(api.isOpenDiagrams(),is(false));
        when(diagramViewManager.getCurrentDiagram()).thenReturn(diagram);
        assertThat(api.isOpenDiagrams(),is(true));
    }

}
