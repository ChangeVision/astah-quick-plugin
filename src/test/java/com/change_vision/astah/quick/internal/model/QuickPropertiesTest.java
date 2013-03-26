package com.change_vision.astah.quick.internal.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class QuickPropertiesTest {

    private QuickProperties properties;

    @Mock
    private UserConfigDirectory directory;
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        properties = new QuickProperties();
        when(directory.getDirectory()).thenReturn(folder.getRoot());
        properties.setUserConfigDirectory(directory);
    }
    
    @Test
    public void initExists() {
        assertThat(properties.exists(),is(false));
    }
    
    @Test
    public void initGetStroke() throws Exception {
        String keyStroke = properties.getKeyStroke();
        assertThat(keyStroke,is(QuickProperties.INITIAL_KEY_STROKE));
    }
    
    @Test
    public void stored() throws Exception {
        properties.store();
        assertThat(properties.exists(),is(true));
    }
    
    @Test
    public void setKeyStroke() throws Exception {
        properties.setKeyStroke("ESCAPE");
        String keyStroke = properties.getKeyStroke();
        assertThat(keyStroke,is("ESCAPE"));
        assertThat(properties.exists(),is(false));
    }
    
    @Test
    public void setKeyStrokeWithNull() throws Exception {
        properties.setKeyStroke("ESCAPE");
        properties.setKeyStroke(null);
        String keyStroke = properties.getKeyStroke();
        assertThat(keyStroke,is(QuickProperties.INITIAL_KEY_STROKE));
    }
    

}
