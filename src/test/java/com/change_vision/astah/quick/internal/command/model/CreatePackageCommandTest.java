package com.change_vision.astah.quick.internal.command.model;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;

public class CreatePackageCommandTest {
    
    @Mock
    private ModelAPI api;
    
    @Mock
    private INamedElement element;
    
    @Mock
    private IPackage packaze;
    
    private CreatePackageCommand command;
    
    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        command = new CreatePackageCommand();
        command.setAPI(api);
    }

    @Test(expected=IllegalArgumentException.class)
    public void executeWithNull() throws Exception {
        command.execute((String[])null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void executeWithNoArgument() throws Exception {
        command.execute();
    }
    
    @Test
    public void executeWithArgument() throws Exception {
        when(api.findByFQCN("hoge")).thenReturn(new INamedElement[0]);
        command.execute("hoge");
        verify(api).createPackage("hoge");
    }
    
    @Test
    public void executeWithArguments() throws Exception {
        when(api.findByFQCN("hoge")).thenReturn(new INamedElement[0]);
        when(api.findByFQCN("fuga")).thenReturn(new INamedElement[0]);
        
        command.execute("hoge","fuga");

        verify(api).createPackage("hoge");
        verify(api).createPackage("fuga");
    }
    
    @Test
    public void executeWithFoundElementArgument() throws Exception {
        when(api.findByFQCN("hoge")).thenReturn(new INamedElement[]{
                element
        });
        command.execute("hoge");
    }
    
    @Test(expected=ExecuteCommandException.class)
    public void executeWithFoundClassArgument() throws Exception {
        when(api.findByFQCN("hoge")).thenReturn(new INamedElement[]{
                packaze
        });
        command.execute("hoge");
    }}
