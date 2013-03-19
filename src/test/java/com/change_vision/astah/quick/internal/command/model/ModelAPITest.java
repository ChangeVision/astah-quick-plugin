package com.change_vision.astah.quick.internal.command.model;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.astah.quick.internal.AstahAPIWrapper;
import com.change_vision.astah.quick.internal.modelfinder.ClassOrPackageFinder;
import com.change_vision.astah.quick.internal.modelfinder.FQCNFinder;
import com.change_vision.jude.api.inf.editor.BasicModelEditor;
import com.change_vision.jude.api.inf.editor.IModelEditorFactory;
import com.change_vision.jude.api.inf.editor.ITransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ModelFinder;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class ModelAPITest {

    @Mock
    private ProjectAccessor prjAccessor;

    @Mock
    private AstahAPIWrapper wrapper;

    @Mock
    private ITransactionManager transactionManager;

    @Mock
    private IModelEditorFactory modelEditorFactory;

    @Mock
    private BasicModelEditor basicModelEditor;

    @Mock
    private IModel project;

    private ModelAPI api;

    @Mock
    private IPackage hogePackage;

    @Mock
    private IPackage fugaPackage;

    @Mock
    private IClass clazz;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);
        api = new ModelAPI();
        api.setWrapper(wrapper);
        when(wrapper.getProjectAccessor()).thenReturn(prjAccessor);
        when(prjAccessor.getTransactionManager()).thenReturn(transactionManager);
        when(prjAccessor.getModelEditorFactory()).thenReturn(modelEditorFactory);
        when(modelEditorFactory.getBasicModelEditor()).thenReturn(basicModelEditor);
        when(prjAccessor.getProject()).thenReturn(project);
        when(fugaPackage.getName()).thenReturn("fuga");
        when(hogePackage.getName()).thenReturn("hoge");
        when(project.getOwnedElements()).thenReturn(new INamedElement[0]);
        when(hogePackage.getOwnedElements()).thenReturn(new INamedElement[0]);
        when(fugaPackage.getOwnedElements()).thenReturn(new INamedElement[0]);
        when(prjAccessor.findElements((ModelFinder) any())).thenReturn(
                new INamedElement[] {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void createClassWithNull() throws Exception {
        api.createClass(null);
    }

    @Test
    public void createClassWithSimpleName() throws InvalidEditingException {
        api.createClass("hoge");
        verify(transactionManager).beginTransaction();
        verify(transactionManager).endTransaction();
        verify(basicModelEditor).createClass(project, "hoge");
    }

    @Test
    public void createClassWithPackageName() throws Exception {
        when(basicModelEditor.createPackage(project, "hoge")).thenReturn(hogePackage);
        when(basicModelEditor.createPackage(hogePackage, "fuga")).thenReturn(fugaPackage);
        when(project.getOwnedElements()).thenReturn(new INamedElement[] {});
        when(hogePackage.getOwnedElements()).thenReturn(new INamedElement[] {});
        api.createClass("hoge.fuga.Hoge");
        verify(transactionManager, atLeastOnce()).beginTransaction();
        verify(transactionManager, atLeastOnce()).endTransaction();
        verify(basicModelEditor).createClass(fugaPackage, "Hoge");
    }

    @Test
    public void createClassWithStructuredPackageName() throws Exception {
        when(basicModelEditor.createPackage(project, "hoge")).thenReturn(hogePackage);
        when(project.getOwnedElements()).thenReturn(new INamedElement[] {});
        api.createClass("hoge.Hoge");
        verify(transactionManager, atLeastOnce()).beginTransaction();
        verify(transactionManager, atLeastOnce()).endTransaction();
        verify(basicModelEditor).createClass(hogePackage, "Hoge");
    }

    @Test
    public void createClassWithExistPackageName() throws Exception {
        when(basicModelEditor.createPackage(hogePackage, "fuga")).thenReturn(fugaPackage);
        when(project.getOwnedElements()).thenReturn(new INamedElement[] { hogePackage });
        api.createClass("hoge.fuga.Hoge");
        verify(transactionManager, atLeastOnce()).beginTransaction();
        verify(transactionManager, atLeastOnce()).endTransaction();
        verify(basicModelEditor).createClass(fugaPackage, "Hoge");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createInterfaceWithNull() throws Exception {
        api.createInterface(null);
    }
    
    @Test
    public void createInterfaceWithSimpleName() throws InvalidEditingException {
        api.createInterface("hoge");
        verify(transactionManager).beginTransaction();
        verify(transactionManager).endTransaction();
        verify(basicModelEditor).createInterface(project, "hoge");
    }
    
    @Test
    public void createInterfaceWithPackageName() throws Exception {
        when(basicModelEditor.createPackage(project, "hoge")).thenReturn(hogePackage);
        when(basicModelEditor.createPackage(hogePackage, "fuga")).thenReturn(fugaPackage);
        when(project.getOwnedElements()).thenReturn(new INamedElement[] {});
        when(hogePackage.getOwnedElements()).thenReturn(new INamedElement[] {});
        api.createInterface("hoge.fuga.Hoge");
        verify(transactionManager, atLeastOnce()).beginTransaction();
        verify(transactionManager, atLeastOnce()).endTransaction();
        verify(basicModelEditor).createInterface(fugaPackage, "Hoge");
    }
    
    @Test
    public void createInterfaceWithStructuredPackageName() throws Exception {
        when(basicModelEditor.createPackage(project, "hoge")).thenReturn(hogePackage);
        when(project.getOwnedElements()).thenReturn(new INamedElement[] {});
        api.createInterface("hoge.Hoge");
        verify(transactionManager, atLeastOnce()).beginTransaction();
        verify(transactionManager, atLeastOnce()).endTransaction();
        verify(basicModelEditor).createInterface(hogePackage, "Hoge");
    }
    
    @Test
    public void createInterfaceWithExistPackageName() throws Exception {
        when(basicModelEditor.createPackage(hogePackage, "fuga")).thenReturn(fugaPackage);
        when(project.getOwnedElements()).thenReturn(new INamedElement[] { hogePackage });
        api.createInterface("hoge.fuga.Hoge");
        verify(transactionManager, atLeastOnce()).beginTransaction();
        verify(transactionManager, atLeastOnce()).endTransaction();
        verify(basicModelEditor).createInterface(fugaPackage, "Hoge");
    }
    
    @Test
    public void findClassOrPackage() throws Exception {
        api.findClassOrPackage("");
        ArgumentCaptor<ModelFinder> finderCaptor = ArgumentCaptor.forClass(ModelFinder.class);
        verify(prjAccessor).findElements(finderCaptor.capture());
        ModelFinder finder = finderCaptor.getValue();
        assertThat(finder,is(instanceOf(ClassOrPackageFinder.class)));
        if (finder instanceof ClassOrPackageFinder) {
            ClassOrPackageFinder finderObject = (ClassOrPackageFinder) finder;
            assertThat(finderObject.getSearchKey(),is(""));
        }
    }
    
    @Test
    public void findFQCN() throws Exception {
       api.findByFQCN("hoge.Hoge"); 
       ArgumentCaptor<ModelFinder> finderCaptor = ArgumentCaptor.forClass(ModelFinder.class);
       verify(prjAccessor).findElements(finderCaptor.capture());
       ModelFinder finder = finderCaptor.getValue();
       assertThat(finder,is(instanceOf(FQCNFinder.class)));
       if (finder instanceof FQCNFinder) {
           FQCNFinder finderObject = (FQCNFinder) finder;
           assertThat(finderObject.getSearchKey(),is("hoge.Hoge"));
       }
    }

}
