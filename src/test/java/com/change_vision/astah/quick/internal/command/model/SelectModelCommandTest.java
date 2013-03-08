package com.change_vision.astah.quick.internal.command.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.command.annotations.LooseName;
import com.change_vision.jude.api.inf.model.INamedElement;

public class SelectModelCommandTest {

    @Test
    public void test() {
        INamedElement foundModel = null;
        SelectModelCommand command = new SelectModelCommand(foundModel );
        Class<? extends SelectModelCommand> clazz = command.getClass();
        assertThat(clazz.isAnnotationPresent(Immediate.class),is(true));
        assertThat(clazz.isAnnotationPresent(LooseName.class),is(true));
    }

}
