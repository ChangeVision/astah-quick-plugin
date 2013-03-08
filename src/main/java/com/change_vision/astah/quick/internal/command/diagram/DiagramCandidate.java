package com.change_vision.astah.quick.internal.command.diagram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.model.IActivityDiagram;
import com.change_vision.jude.api.inf.model.IClassDiagram;
import com.change_vision.jude.api.inf.model.ICommunicationDiagram;
import com.change_vision.jude.api.inf.model.IComponentDiagram;
import com.change_vision.jude.api.inf.model.ICompositeStructureDiagram;
import com.change_vision.jude.api.inf.model.IDataFlowDiagram;
import com.change_vision.jude.api.inf.model.IDeploymentDiagram;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IERDiagram;
import com.change_vision.jude.api.inf.model.IMatrixDiagram;
import com.change_vision.jude.api.inf.model.IMindMapDiagram;
import com.change_vision.jude.api.inf.model.IRequirementDiagram;
import com.change_vision.jude.api.inf.model.IRequirementTable;
import com.change_vision.jude.api.inf.model.ISequenceDiagram;
import com.change_vision.jude.api.inf.model.IStateMachine;
import com.change_vision.jude.api.inf.model.IUseCaseDiagram;
import com.change_vision.jude.api.inf.view.IconDescription;

@Immediate
class DiagramCandidate implements Candidate{
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(DiagramCandidate.class);
	
	private IDiagram diagram;

	DiagramCandidate(IDiagram diagram){
		this.diagram = diagram;
	}

	@Override
	public String getName() {
		return diagram.getName();
	}

	@Override
	public String getDescription() {
		return diagram.getFullName(".");
	}

	@Override
	public boolean isEnabled() {
		return diagram != null;
	}

	@Override
	public CandidateIconDescription getIconDescription() {
	    if (diagram instanceof IClassDiagram) {
	        return new AstahCommandIconDescription(IconDescription.UML_DGM_CLASS);
        }
	    if (diagram instanceof IUseCaseDiagram) {
	        return new AstahCommandIconDescription(IconDescription.UML_DGM_USECASE);
	    }
        if (diagram instanceof IStateMachine) {
            return new AstahCommandIconDescription(IconDescription.UML_DGM_STATECHART);
        }
        if (diagram instanceof IActivityDiagram) {
            IActivityDiagram activityDiagram = (IActivityDiagram) diagram;
            if (activityDiagram.isFlowChart()) {
                return new AstahCommandIconDescription(IconDescription.FLOW_DGM);
            }
            return new AstahCommandIconDescription(IconDescription.UML_DGM_ACTIVITY);
        }
        if (diagram instanceof ISequenceDiagram) {
            return new AstahCommandIconDescription(IconDescription.UML_DGM_SEQUENCE);
        }
        if (diagram instanceof ICommunicationDiagram) {
            return new AstahCommandIconDescription(IconDescription.UML_DGM_COLLABORATION);
        }
        if (diagram instanceof IComponentDiagram) {
            return new AstahCommandIconDescription(IconDescription.UML_DGM_COMPONENT);
        }
        if (diagram instanceof IDeploymentDiagram) {
            return new AstahCommandIconDescription(IconDescription.UML_DGM_DEPLOYMENT);
        }
        if (diagram instanceof ICompositeStructureDiagram) {
            return new AstahCommandIconDescription(IconDescription.UML_DGM_COMPOSITESTRUCTURE);
        }
        if (diagram instanceof IDataFlowDiagram) {
            return new AstahCommandIconDescription(IconDescription.DATA_FLOW_DGM);
        }
        if (diagram instanceof IERDiagram) {
            return new AstahCommandIconDescription(IconDescription.ER_DGM);
        }
        if (diagram instanceof IMatrixDiagram) {
            return new AstahCommandIconDescription(IconDescription.CRUD_DGM);
        }
        if (diagram instanceof IMindMapDiagram) {
            return new AstahCommandIconDescription(IconDescription.UML_MINDMAP_DGM);
        }
        if (diagram instanceof IRequirementDiagram) {
            return new AstahCommandIconDescription(IconDescription.REQUIREMENT_REQUIREMENT_DIAGRAM);
        }
        if (diagram instanceof IRequirementTable) {
            return new AstahCommandIconDescription(IconDescription.REQUIREMENT_REQUIREMENT_TABLE);
        }
        logger.error("Unknown diagram type:'{}'",diagram);
        return new AstahCommandIconDescription(IconDescription.UML_DGM_CLASS);
	    
	}

	public IDiagram getDiagram() {
		return this.diagram;
	}
	
}
