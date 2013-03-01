package com.change_vision.astah.quick.internal.command.project;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Properties;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.jude.api.inf.system.SystemPropertyAccessor;

public class ProjectAPITest {

    private ProjectAPI api;

    @Mock
    private AstahAPIWrapper wrapper;

    @Mock
    private SystemPropertyAccessor accessor;
    
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private Properties properties;

    private File recentFile1;
    
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        api = new ProjectAPI();
        api.setWrapper(wrapper);
        when(wrapper.getSystemPropertyAccessor()).thenReturn(accessor);
        properties = new Properties();
        when(accessor.getSystemProperties()).thenReturn(properties);
        recentFile1 = folder.newFile("test1.asta");
    }

    @Test
    public void getRecentFiles_got_no_files() {
        File[] recentFiles = api.getRecentFiles();
        assertThat(recentFiles,is(notNullValue()));
        assertThat(recentFiles.length,is(0));
    }

    @Test
    public void getRecentFiles_got_one_file() {
        properties.setProperty("jude.recent_file_number", "1");
        properties.setProperty("jude.recent_file_0", recentFile1.getAbsolutePath());
        File[] recentFiles = api.getRecentFiles();
        assertThat(recentFiles,is(notNullValue()));
        assertThat(recentFiles.length,is(1));
    }
    
    @Test
    public void getRecentFiles_call_real_method() throws Exception {
        when(wrapper.getSystemPropertyAccessor()).thenCallRealMethod();
        File[] recentFiles = api.getRecentFiles();
        assertThat(recentFiles,is(notNullValue()));
    }

}
