package com.change_vision.astah.quick.internal.command.project;

import java.io.File;

import com.change_vision.astah.quick.command.Candidate;
import com.change_vision.astah.quick.command.CandidateIconDescription;
import com.change_vision.astah.quick.internal.command.AstahCommandIconDescription;
import com.change_vision.jude.api.inf.view.IconDescription;

class FileCandidate implements Candidate{
    
    private File file;

    FileCandidate(File file){
        this.file = file;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getDescription() {
        return file.getAbsolutePath();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public CandidateIconDescription getIconDescription() {
        return new AstahCommandIconDescription(IconDescription.PROJECT);
    }
    
    File getFile() {
        return file;
    }

}
