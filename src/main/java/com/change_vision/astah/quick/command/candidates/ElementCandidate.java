package com.change_vision.astah.quick.command.candidates;

import static java.lang.String.format;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.jude.api.inf.model.IAction;
import com.change_vision.jude.api.inf.model.IActivity;
import com.change_vision.jude.api.inf.model.IAnchor;
import com.change_vision.jude.api.inf.model.IArtifact;
import com.change_vision.jude.api.inf.model.IAssociation;
import com.change_vision.jude.api.inf.model.IAssociationClass;
import com.change_vision.jude.api.inf.model.IAttribute;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.ICombinedFragment;
import com.change_vision.jude.api.inf.model.IComment;
import com.change_vision.jude.api.inf.model.IComponent;
import com.change_vision.jude.api.inf.model.IConnector;
import com.change_vision.jude.api.inf.model.IConstraint;
import com.change_vision.jude.api.inf.model.IControlNode;
import com.change_vision.jude.api.inf.model.IDataStore;
import com.change_vision.jude.api.inf.model.IDependency;
import com.change_vision.jude.api.inf.model.IERAttribute;
import com.change_vision.jude.api.inf.model.IERDomain;
import com.change_vision.jude.api.inf.model.IEREntity;
import com.change_vision.jude.api.inf.model.IERIndex;
import com.change_vision.jude.api.inf.model.IERModel;
import com.change_vision.jude.api.inf.model.IERRelationship;
import com.change_vision.jude.api.inf.model.IERSchema;
import com.change_vision.jude.api.inf.model.IERSubtypeRelationship;
import com.change_vision.jude.api.inf.model.IExtend;
import com.change_vision.jude.api.inf.model.IExtentionPoint;
import com.change_vision.jude.api.inf.model.IExternalEntity;
import com.change_vision.jude.api.inf.model.IFinalState;
import com.change_vision.jude.api.inf.model.IFlow;
import com.change_vision.jude.api.inf.model.IGate;
import com.change_vision.jude.api.inf.model.IGeneralization;
import com.change_vision.jude.api.inf.model.IInclude;
import com.change_vision.jude.api.inf.model.IInputPin;
import com.change_vision.jude.api.inf.model.IInstanceSpecification;
import com.change_vision.jude.api.inf.model.IInteraction;
import com.change_vision.jude.api.inf.model.IInteractionOperand;
import com.change_vision.jude.api.inf.model.IInteractionUse;
import com.change_vision.jude.api.inf.model.ILifeline;
import com.change_vision.jude.api.inf.model.ILifelineLink;
import com.change_vision.jude.api.inf.model.ILink;
import com.change_vision.jude.api.inf.model.ILinkEnd;
import com.change_vision.jude.api.inf.model.IMessage;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.INode;
import com.change_vision.jude.api.inf.model.IObjectNode;
import com.change_vision.jude.api.inf.model.IOperation;
import com.change_vision.jude.api.inf.model.IOutputPin;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IParameter;
import com.change_vision.jude.api.inf.model.IPartition;
import com.change_vision.jude.api.inf.model.IPort;
import com.change_vision.jude.api.inf.model.IProcessBox;
import com.change_vision.jude.api.inf.model.IPseudostate;
import com.change_vision.jude.api.inf.model.IRealization;
import com.change_vision.jude.api.inf.model.IRequirement;
import com.change_vision.jude.api.inf.model.ISlot;
import com.change_vision.jude.api.inf.model.IState;
import com.change_vision.jude.api.inf.model.IStateInvariant;
import com.change_vision.jude.api.inf.model.IStateMachine;
import com.change_vision.jude.api.inf.model.ISubsystem;
import com.change_vision.jude.api.inf.model.ITermination;
import com.change_vision.jude.api.inf.model.ITestCase;
import com.change_vision.jude.api.inf.model.ITransition;
import com.change_vision.jude.api.inf.model.IUsage;
import com.change_vision.jude.api.inf.model.IUseCase;
import com.change_vision.jude.api.inf.view.IconDescription;

public class ElementCandidate implements Candidate {

	private INamedElement element;

	public ElementCandidate(INamedElement element) {
		this.element = element;
	}

	@Override
	public String getName() {
		return element.getName();
	}

	@Override
	public String getDescription() {
		String fullName = element.getFullName(".");
		return format("%s", fullName);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public CandidateIconDescription getIconDescription() {
		if (element instanceof IERModel) {
			return new AstahCommandIconDescription(IconDescription.ER_ERMODEL);
		}
		if (element instanceof IModel) {
			return new ResourceCommandIconDescription("/icons/astah_icon_professional.png");
		}
		if (element instanceof ISubsystem) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_SUBSYSTEM);
		}
		if (element instanceof IPackage) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_PACKAGE);
		}
		if (element instanceof IInstanceSpecification) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_OBJECT);
		}
		if (element instanceof ILifeline) {
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_OBJECT);
		}
		if (element instanceof IAssociationClass) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_ASSOCIATION_CLASS);
		}
		if (element instanceof IUseCase) {
			return new AstahCommandIconDescription(IconDescription.UML_USECASE_USECASE);
		}
		if (element instanceof IComponent) {
			return new AstahCommandIconDescription(IconDescription.UML_COMPONENT_COMPONENT);
		}
		if (element instanceof INode) {
			return new AstahCommandIconDescription(IconDescription.UML_DEPLOYMENT_NODE);
		}
		if (element instanceof IArtifact) {
			return new AstahCommandIconDescription(IconDescription.UML_COMPONENT_ARTIFACT);
		}
		if (element instanceof IEREntity) {
			return new AstahCommandIconDescription(IconDescription.ER_ENTITY);
		}
		if (element instanceof IERDomain) {
			return new AstahCommandIconDescription(IconDescription.ER_DOMAIN);
		}
		if (element instanceof IExternalEntity) {
			return new AstahCommandIconDescription(IconDescription.DATA_FLOW_EXTERNAL_ENTITY);
		}
		if (element instanceof IDataStore) {
			return new AstahCommandIconDescription(IconDescription.DATA_FLOW_DATASTORE);
		}
		if (element instanceof IRequirement) {
			return new AstahCommandIconDescription(IconDescription.REQUIREMENT_REQUIREMENT);
		}
		if (element instanceof ITestCase) {
			return new AstahCommandIconDescription(IconDescription.REQUIREMENT_TEST_CASE);
		}
		if (element instanceof IClass) {
			IClass clazz = (IClass) element;
			if (clazz.hasStereotype("interface")) {
				return new AstahCommandIconDescription(IconDescription.UML_CLASS_INTERFACE);				
			}
			if (clazz.hasStereotype("boundary")) {
				return new AstahCommandIconDescription(IconDescription.UML_CLASS_BOUNDARY);				
			}
			if (clazz.hasStereotype("control")) {
				return new AstahCommandIconDescription(IconDescription.UML_CLASS_CONTROL);				
			}
			if (clazz.hasStereotype("entity")) {
				return new AstahCommandIconDescription(IconDescription.UML_CLASS_ENTITY);				
			}
			if (clazz.hasStereotype("actor")) {
				return new AstahCommandIconDescription(IconDescription.UML_USECASE_ACTOR);				
			}
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_CLASS);
		}
		if (element instanceof ILinkEnd) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_ATTR);
		}
		if (element instanceof ILink) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_LINK);
		}
		if (element instanceof IERRelationship) {
			IERRelationship relationship = (IERRelationship) element;
			if (relationship.isMultiToMulti()) {
				return new AstahCommandIconDescription(IconDescription.ER_MANY_TO_MANY_RELATIONSHIP);				
			};
			if (relationship.isIdentifying()) {
				return new AstahCommandIconDescription(IconDescription.ER_IDENTIFYING_RELATIONSHIP);				
			}
			return new AstahCommandIconDescription(IconDescription.ER_NON_IDENTIFYING_RELATIONSHIP);
		}
		if (element instanceof IConnector) {
			return new AstahCommandIconDescription(IconDescription.UML_COMPOSITE_STRUCTURE_CONNECTOR);
		}
		if (element instanceof ILifelineLink) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_ASSOCATION);
		}
		if (element instanceof IAssociation) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_ASSOCATION);
		}
		if (element instanceof IERSubtypeRelationship) {
			return new AstahCommandIconDescription(IconDescription.ER_NON_IDENTIFYING_RELATIONSHIP);				
		}
		if (element instanceof IGeneralization) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_GENERALIZATION);				
		}
		if (element instanceof IUsage) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_DEPENDENCY);				
		}
		if (element instanceof IRealization) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_REALIZATION);				
		}
		if (element instanceof IDependency) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_DEPENDENCY);				
		}
		if (element instanceof IExtend) {
			return new AstahCommandIconDescription(IconDescription.UML_USECASE_EXTEND);				
		}
		if (element instanceof IInclude) {
			return new AstahCommandIconDescription(IconDescription.UML_USECASE_INCLUDE);				
		}
		if (element instanceof IPort) {
			return new AstahCommandIconDescription(IconDescription.UML_COMPOSITE_STRUCTURE_PORT);				
		}
		if (element instanceof IERAttribute) {
			return new AstahCommandIconDescription(IconDescription.ER_ATTRIBUTE);				
		}
		if (element instanceof IAttribute) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_ATTR);				
		}
		if (element instanceof IOperation) {
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_OPE);				
		}
		if (element instanceof IParameter) {
			// Not found
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_CLASS);				
		}
		if (element instanceof IComment) {
			// Not found
			return new ResourceCommandIconDescription("/icons/astah_icon_professional.png");
		}
		if (element instanceof IConstraint) {
			// Not found
			return new ResourceCommandIconDescription("/icons/astah_icon_professional.png");
		}
		if (element instanceof IExtentionPoint) {
			// Not found
			return new AstahCommandIconDescription(IconDescription.UML_USECASE_EXTEND);				
		}
		if (element instanceof IActivity) {
			// Not found
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_PROCESS);				
		}
		if (element instanceof IPartition) {
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_SWIMLANE);				
		}
		if (element instanceof IProcessBox) {
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_PROCESS);				
		}
		if (element instanceof IAction) {
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_ACTION);				
		}
		if (element instanceof IAnchor) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_ACTION);				
		}
		if (element instanceof IControlNode) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_ACTION);				
		}
		if (element instanceof IInputPin) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_ACTION);				
		}
		if (element instanceof IOutputPin) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_ACTION);				
		}
		if (element instanceof IObjectNode) {
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_OBJECTNODE);				
		}
		if (element instanceof IFlow) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_ACTIVITY_PROCESS);				
		}
		if (element instanceof IStateMachine) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_STATECHART_STATE);				
		}
		if (element instanceof ITransition) {
			return new AstahCommandIconDescription(IconDescription.UML_STATECHART_TRANSITION);				
		}
		if (element instanceof IFinalState) {
			return new AstahCommandIconDescription(IconDescription.UML_STATECHART_FINALSTATE);				
		}
		if (element instanceof IPseudostate) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_STATECHART_STATE);				
		}
		if (element instanceof IState) {
			return new AstahCommandIconDescription(IconDescription.UML_STATECHART_STATE);				
		}
		if (element instanceof IInteraction) {
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_INTERACTION_USE);				
		}
		if (element instanceof ITermination) {
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_TERMINATION);				
		}
		if (element instanceof IMessage) {
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_MESSAGE);				
		}
		if (element instanceof ICombinedFragment) {
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_COMBINED_FRAGMENT);				
		}
		if (element instanceof IStateInvariant) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_STATE_INVARIANT);				
		}
		if (element instanceof IInteractionUse) {
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_INTERACTION_USE);				
			}
		if (element instanceof IInteractionOperand) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_INTERACTION_USE);
			}
		if (element instanceof IGate) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_SEQENCE_MESSAGE);
		}
		if (element instanceof IERSchema) {
			// not found
			return new AstahCommandIconDescription(IconDescription.ER_ERMODEL);
		}
		if (element instanceof IERIndex) {
			// not found
			return new AstahCommandIconDescription(IconDescription.ER_ATTRIBUTE);
		}
		if (element instanceof ISlot) {
			// not found
			return new AstahCommandIconDescription(IconDescription.UML_CLASS_OBJECT);
		}
		return new ResourceCommandIconDescription("/icons/astah_icon_professional.png");
	}

	public INamedElement getElement() {
		return element;
	}

}
