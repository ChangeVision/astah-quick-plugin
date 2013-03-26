package com.change_vision.astah.quick.internal.command.model;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;

public class SelectModelCommandFactoryTest {

    private SelectModelCommandFactory factory;

    private static final String dummyKey = "Dummy";

    @Mock
    private INamedElement element;

    @Mock
    private IClass clazz;

    @Mock
    private IClass interfaze;

    @Mock
    private IPackage packaze;

    @Mock
    private ModelAPI api;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        factory = new SelectModelCommandFactory();
        factory.setApi(api);
        when(interfaze.hasStereotype("interface")).thenReturn(true);
        when(clazz.getName()).thenReturn("clazz");
        when(interfaze.getName()).thenReturn("interfaze");
        when(packaze.getName()).thenReturn("packaze");
        when(element.getName()).thenReturn("element");
    }

    @Test
    public void notFoundModels() {
        List<Candidate> candidates = factory.create(null);
        assertThat(candidates.size(), is(0));
    }

    @Test
    public void foundAModel() throws Exception {
        INamedElement[] elements = new INamedElement[] { element };
        when(api.findClassOrPackage(dummyKey)).thenReturn(elements);

        List<Candidate> candidates = factory.create(dummyKey);
        assertThat(candidates.size(), is(1));
        Candidate candidate = candidates.get(0);
        assertThat(candidate, is(instanceOf(SelectModelCommand.class)));
    }

    @Test
    public void foundModels() throws Exception {
        INamedElement[] elements = new INamedElement[] { clazz, interfaze, element, packaze };
        when(api.findClassOrPackage(dummyKey)).thenReturn(elements);

        List<Candidate> candidates = factory.create(dummyKey);
        assertThat(candidates.size(), is(4));
    }
    
    @Test
    public void sortedModels() throws Exception {
        INamedElement[] elements = new INamedElement[] { clazz, interfaze, element, packaze };
        when(api.findClassOrPackage(dummyKey)).thenReturn(elements);

        List<Candidate> candidates = factory.create(dummyKey);
        assertThat(candidates.get(0).getName(),is("clazz"));
        assertThat(candidates.get(1).getName(),is("element"));
        assertThat(candidates.get(2).getName(),is("interfaze"));
        assertThat(candidates.get(3).getName(),is("packaze"));
    }

}
