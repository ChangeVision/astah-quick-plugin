package com.change_vision.astah.quick.internal.command.project;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.jude.api.inf.view.IconDescription;

public class OpenProjectCommand implements CandidateSupportCommand {
	
	private final class FileChooserCandidate implements Candidate {
        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public String getName() {
            return "Open file chooser";
        }

        @Override
        public CandidateIconDescription getIconDescription() {
            return new AstahCommandIconDescription(IconDescription.PROJECT);
        }

        @Override
        public String getDescription() {
            return "specified by file chooser";
        }
    }

    private final class AstahFileFilter extends FileFilter {
		@Override
		public String getDescription() {
			return "Astah File(*.asta,*.jude)";
		}

		@Override
		public boolean accept(File targetFile) {
			String fileName = targetFile.getName();
			boolean isAstaFile = fileName.endsWith(".asta");
			boolean isJudeFile = fileName.endsWith(".jude");
			return isAstaFile || isJudeFile || targetFile.isDirectory();
		}
	}

	private ProjectAPI api = new ProjectAPI();

	@Override
	public String getName() {
		return "open project";
	}

	@Override
	public void execute(String... args) {
		openProjectByFileChooser();
	}

    protected void openProjectByFileChooser() {
        JFrame mainFrame = api.getMainFrame();

		JFileChooser filechooser = new JFileChooser();
		filechooser.setAcceptAllFileFilterUsed(false);
		filechooser.setFileFilter(new AstahFileFilter());
		int opened = filechooser.showOpenDialog(mainFrame);
		if (opened == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			api.openProject(file);
		}
    }

	@Override
	public String getDescription() {
		return "open project...";
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public CandidateIconDescription getIconDescription() {
		return new ResourceCommandIconDescription("/icons/glyphicons_144_folder_open.png");
	}

    @Override
    public Candidate[] candidate(String searchKey) {
        File[] recentFiles = api.getRecentFiles();
        Candidate[] candidates = new Candidate[recentFiles.length + 1];
        candidates[0] = new FileChooserCandidate();
        for (int i = 0; i < recentFiles.length; i++) {
            File file = recentFiles[i];
            candidates[i + 1] = new FileCandidate(file);
        }
        return candidates;
    }

    @Override
    public void execute(Candidate[] candidates) {
        if (candidates == null) {
            openProjectByFileChooser();
            return;
        }
        // TODO Should this command supports multiple candidates?
        for (Candidate candidate : candidates) {
            if (candidate instanceof FileCandidate) {
                FileCandidate fileCandidate = (FileCandidate) candidate;
                File file = fileCandidate.getFile();
                if (file != null) {
                    api.openProject(file);
                    return;
                }
            }
            if (candidate instanceof FileChooserCandidate) {
                openProjectByFileChooser();
                return;
            }
        }
        openProjectByFileChooser();
    }
}
