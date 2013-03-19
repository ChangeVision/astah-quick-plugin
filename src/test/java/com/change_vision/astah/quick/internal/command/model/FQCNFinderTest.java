package com.change_vision.astah.quick.internal.command.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.jude.api.inf.model.INamedElement;

public class FQCNFinderTest {
    
    @Mock
    public INamedElement element;
    
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void withNull() throws Exception {
        new FQCNFinder(null);
    }

    @Test
    public void foundElement() {
        when(element.getFullName(".")).thenReturn("hoge.Hoge");
        FQCNFinder finder = new FQCNFinder("hoge.Hoge");
        boolean target = finder.isTarget(element);
        assertThat(target,is(true));
    }

}
