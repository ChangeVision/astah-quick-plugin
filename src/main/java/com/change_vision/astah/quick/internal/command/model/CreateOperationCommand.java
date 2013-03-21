package com.change_vision.astah.quick.internal.command.model;

import static java.lang.String.format;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.CommittedNameTrimer;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.command.candidates.ClassCandidate;
import com.change_vision.astah.quick.command.candidates.PrimitiveCandidate;
import com.change_vision.astah.quick.command.exception.ExecuteCommandException;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.model.IClass;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.view.IconDescription;

public class CreateOperationCommand implements CandidateSupportCommand{
	
	private final class OperationNameCandidate implements Candidate {
        private final String trimed;

        private OperationNameCandidate(String trimed) {
            this.trimed = trimed;
        }

        @Override
        public String getName() {
            return trimed;
        }

        @Override
        public String getDescription() {
            return "Please input operation name";
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public CandidateIconDescription getIconDescription() {
            return new AstahCommandIconDescription(IconDescription.UML_CLASS_OPE);
        }
    }
	
	@Immediate
	private final class ReturnClassCandidate extends ClassCandidate{

        public ReturnClassCandidate(IClass clazz) {
            super(clazz);
        }
	    
	}

    private ModelAPI api = new ModelAPI();
	
    private CommittedNameTrimer trimer = new CommittedNameTrimer();
    
    private final PrimitiveCandidates PRIMITIVES = new PrimitiveCandidates();

    private final Candidate[] VOID = new Candidate[]{
            new PrimitiveCandidate("void")
    };

	@Override
	public void execute(String... args) throws ExecuteCommandException {
	}

	@Override
	public String getName() {
		return "create operation";
	}

	@Override
	public String getDescription() {
		return "create operation [owner class] [name] [return type]";
	}

	@Override
	public boolean isEnabled() {
		return api.isOpenedProject();
	}

	@Override
	public CandidateIconDescription getIconDescription() {
		return new AstahCommandIconDescription(IconDescription.UML_CLASS_OPE);
	}

	@Override
	public Candidate[] candidate(Candidate[] committed, String searchKey) {
        if (committed == null || committed.length == 0) {
            return findClass(searchKey);
        }
        String trimed = trimer.trim(committed, searchKey);
        if (committed.length == 1) {
            return new Candidate[]{
                new OperationNameCandidate(trimed)
            };
        }
        Candidate[] foundClasses = findReturnClass(trimed);
        Candidate[] primitives = PRIMITIVES.find(trimed);
        Candidate[] candidates = new Candidate[foundClasses.length + primitives.length + 1];
		System.arraycopy(VOID, 0, candidates, 0, 1);
        System.arraycopy(primitives, 0, candidates, 1, primitives.length);
        System.arraycopy(foundClasses, 0, candidates, primitives.length + 1, foundClasses.length);
		return candidates;
	}

	private Candidate[] findReturnClass(String searchKey) {
        INamedElement[] found = api.findClass(searchKey);
        Candidate[] foundClasses = new ClassCandidate[found.length];
        for (int i = 0; i < found.length; i++) {
            INamedElement element = found[i];
            if (element instanceof IClass) {
                IClass clazz = (IClass) element;
                foundClasses[i] = new ReturnClassCandidate(clazz);
            }else{
                String message = format("found element doesn't class. found:'%s'", element);
                throw new IllegalStateException(message);
            }
        }
        return foundClasses;
    }

    private Candidate[] findClass(String searchKey) {
		INamedElement[] found = api.findClass(searchKey);
		Candidate[] foundClasses = new ClassCandidate[found.length];
		for (int i = 0; i < found.length; i++) {
			INamedElement element = found[i];
			if (element instanceof IClass) {
                IClass clazz = (IClass) element;
                foundClasses[i] = new ClassCandidate(clazz);
            }else{
                String message = format("found element doesn't class. found:'%s'", element);
                throw new IllegalStateException(message);
            }
		}
		return foundClasses;
	}

	@Override
	public void execute(Candidate[] candidates) throws ExecuteCommandException {
	    if (candidates.length != 3) throw new IllegalArgumentException("illegal argument");
	    if (candidates[0] instanceof ClassCandidate == false) throw new IllegalArgumentException("first candidate needs to be class candidate");
	    ClassCandidate classCandidate = (ClassCandidate) candidates[0];
        if (candidates[1] instanceof OperationNameCandidate == false) throw new IllegalArgumentException("second candidate needs to be operation name candidate");
        OperationNameCandidate operationNameCandidate = (OperationNameCandidate) candidates[1];
        if (candidates[2] instanceof PrimitiveCandidate){
            PrimitiveCandidate primitiveCandidate = (PrimitiveCandidate) candidates[2];
            api.createOperation(classCandidate.getCandidate(), operationNameCandidate.getName(), primitiveCandidate.getName());
            return;
        }
        if (candidates[2] instanceof ReturnClassCandidate) {
            ReturnClassCandidate returnCandidate = (ReturnClassCandidate) candidates[2];
            api.createOperation(classCandidate.getCandidate(), operationNameCandidate.getName(), returnCandidate.getCandidate());          
            return;
        }
        throw new IllegalArgumentException("third candidate needs to be return candidate");
        
	}

}
