package com.change_vision.astah.quick.internal.command.project;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.command.CandidateSupportCommand;
import com.change_vision.astah.quick.command.annotations.Immediate;
import com.change_vision.astah.quick.command.candidates.ProjectCandidate;
import com.change_vision.astah.quick.internal.Messages;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.astah.quick.internal.command.ResourceCommandIconDescription;
import com.change_vision.jude.api.inf.view.IconDescription;

public class OpenProjectCommand implements CandidateSupportCommand {

    @Immediate
    private final class FileChooserCandidate implements Candidate {
        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public String getName() {
            return Messages.getString("OpenProjectCommand.file_chooser_candidate_name"); //$NON-NLS-1$
        }

        @Override
        public CandidateIconDescription getIconDescription() {
            return new AstahCommandIconDescription(IconDescription.PROJECT);
        }

        @Override
        public String getDescription() {
            return Messages.getString("OpenProjectCommand.file_chooser_candidate_description"); //$NON-NLS-1$
        }
    }

    private final class AstahFileFilter extends FileFilter {
        @Override
        public String getDescription() {
            return Messages.getString("OpenProjectCommand.astah_file_filter_description"); //$NON-NLS-1$
        }

        @Override
        public boolean accept(File targetFile) {
            String fileName = targetFile.getName();
            boolean isAstaFile = fileName.endsWith(".asta"); //$NON-NLS-1$
            boolean isJudeFile = fileName.endsWith(".jude"); //$NON-NLS-1$
            return isAstaFile || isJudeFile || targetFile.isDirectory();
        }
    }

    private ProjectAPI api = new ProjectAPI();

    @Override
    public String getName() {
        return "open project"; //$NON-NLS-1$
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
        return Messages.getString("OpenProjectCommand.description"); //$NON-NLS-1$
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new ResourceCommandIconDescription("/icons/glyphicons_144_folder_open.png"); //$NON-NLS-1$
    }

    @Override
    public Candidate[] candidate(Candidate[] committed, String searchKey) {
        File[] recentFiles = api.getRecentFiles();
        Candidate[] candidates = new Candidate[recentFiles.length + 1];
        candidates[0] = new FileChooserCandidate();
        for (int i = 0; i < recentFiles.length; i++) {
            File file = recentFiles[i];
            candidates[i + 1] = new ProjectCandidate(file);
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
            if (candidate instanceof ProjectCandidate) {
                ProjectCandidate fileCandidate = (ProjectCandidate) candidate;
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
