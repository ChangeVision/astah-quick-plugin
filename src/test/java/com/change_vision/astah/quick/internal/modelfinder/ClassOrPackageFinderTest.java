package com.change_vision.astah.quick.internal.modelfinder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.internal.modelfinder.ClassOrPackageFinder;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.INamedElement;

public class ClassOrPackageFinderTest {
    
    @Mock
    private INamedElement element;
    
    @Mock
    private IClass clazz;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(clazz.getAlias1()).thenReturn("");
        when(clazz.getAlias2()).thenReturn("");
        when(clazz.getFullName(".")).thenReturn("");
    }
    @Test(expected=IllegalArgumentException.class)
    public void withNull() throws Exception {
        new ClassOrPackageFinder(null);
    }
    
    @Test
    public void emptyNameClass() {
        when(clazz.getName()).thenReturn("");
        ClassOrPackageFinder finder = new ClassOrPackageFinder("");
        boolean target = finder.isTarget(clazz);
        assertThat(target,is(true));
    }

    @Test
    public void emptyNameElement() {
        when(element.getName()).thenReturn("");
        ClassOrPackageFinder finder = new ClassOrPackageFinder("");
        boolean target = finder.isTarget(element);
        assertThat(target,is(false));
    }
    
    @Test
    public void namedClass() throws Exception {
        when(clazz.getName()).thenReturn("hoge");
        when(clazz.getFullName(".")).thenReturn("hoge");
        ClassOrPackageFinder finder = new ClassOrPackageFinder("ho");
        boolean target = finder.isTarget(clazz);
        assertThat(target,is(true));
    }
    
    @Test
    public void namedClassInNamespace() throws Exception {
        when(clazz.getName()).thenReturn("Fuga");
        when(clazz.getFullName(".")).thenReturn("hoge.Fuga");
        ClassOrPackageFinder finder = new ClassOrPackageFinder("ho");
        boolean target = finder.isTarget(clazz);
        assertThat(target,is(true));
    }

}
