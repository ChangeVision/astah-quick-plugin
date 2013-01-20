package com.change_vision.astah.quick.internal.command.project;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import com.change_vision.astah.quick.command.Command;

public class OpenProjectCommand implements Command{
	
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
			return isAstaFile || isJudeFile;
		}
	}

	private ProjectAPI api = new ProjectAPI();

	@Override
	public String getCommandName() {
		return "open project";
	}

	@Override
	public void execute(String... args) {
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
	public boolean isEnable() {
		return true;
	}
}
