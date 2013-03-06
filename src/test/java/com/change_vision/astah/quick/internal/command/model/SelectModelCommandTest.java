package com.change_vision.astah.quick.internal.command.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.change_vision.astah.quick.internal.annotations.Immidiate;
import com.change_vision.jude.api.inf.model.INamedElement;

public class SelectModelCommandTest {

    @Test
    public void test() {
        INamedElement foundModel = null;
        SelectModelCommand command = new SelectModelCommand(foundModel );
        Class<? extends SelectModelCommand> clazz = command.getClass();
        assertThat(clazz.isAnnotationPresent(Immidiate.class),is(true));
    }

}
