package com.change_vision.astah.quick.internal.command.model;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.change_vision.jude.api.inf.editor.BasicModelEditor;
import com.change_vision.jude.api.inf.editor.IModelEditorFactory;
import com.change_vision.jude.api.inf.editor.ITransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class ModelAPITest {
	
	@Mock
	private ProjectAccessor prjAccessor;

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
	
	@Before
	public void before() throws Exception {
		MockitoAnnotations.initMocks(this);
		api = new ModelAPI();
		api.setProjectAccessorForTest(prjAccessor);
		when(prjAccessor.getTransactionManager()).thenReturn(transactionManager);
		when(prjAccessor.getModelEditorFactory()).thenReturn(modelEditorFactory);
		when(modelEditorFactory.getBasicModelEditor()).thenReturn(basicModelEditor);
		when(prjAccessor.getProject()).thenReturn(project);
		when(fugaPackage.getName()).thenReturn("fuga");
		when(hogePackage.getName()).thenReturn("hoge");
		when(project.getOwnedElements()).thenReturn(new INamedElement[0]);
		when(hogePackage.getOwnedElements()).thenReturn(new INamedElement[0]);
		when(fugaPackage.getOwnedElements()).thenReturn(new INamedElement[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
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
		when(project.getOwnedElements()).thenReturn(new INamedElement[]{});
		when(hogePackage.getOwnedElements()).thenReturn(new INamedElement[]{});
		api.createClass("hoge.fuga.Hoge");
		verify(transactionManager,atLeastOnce()).beginTransaction();
		verify(transactionManager,atLeastOnce()).endTransaction();
		verify(basicModelEditor).createClass(fugaPackage, "Hoge");
	}

	@Test
	public void createClassWithStructuredPackageName() throws Exception {
		when(basicModelEditor.createPackage(project, "hoge")).thenReturn(hogePackage);
		when(project.getOwnedElements()).thenReturn(new INamedElement[]{});
		api.createClass("hoge.Hoge");
		verify(transactionManager,atLeastOnce()).beginTransaction();
		verify(transactionManager,atLeastOnce()).endTransaction();
		verify(basicModelEditor).createClass(hogePackage, "Hoge");
	}

	@Test
	public void createClassWithExistPackageName() throws Exception {
		when(basicModelEditor.createPackage(hogePackage, "fuga")).thenReturn(fugaPackage);
		when(project.getOwnedElements()).thenReturn(new INamedElement[] {
			hogePackage
		});
		api.createClass("hoge.fuga.Hoge");
		verify(transactionManager,atLeastOnce()).beginTransaction();
		verify(transactionManager,atLeastOnce()).endTransaction();
		verify(basicModelEditor).createClass(fugaPackage, "Hoge");
	}

}
