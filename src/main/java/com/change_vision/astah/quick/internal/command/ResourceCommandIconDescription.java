package com.change_vision.astah.quick.internal.command;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.change_vision.astah.quick.command.CandidateIconDescription;

public class ResourceCommandIconDescription implements CandidateIconDescription {
	
	private String path;

	public ResourceCommandIconDescription(String path){
		this.path = path;
	}

	@Override
	public Icon getIcon() {
		URL iconURL = this.getClass().getResource(path);
        BufferedImage image;
        try {
            image = ImageIO.read(iconURL);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return new ImageIcon(image.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
	}

}
