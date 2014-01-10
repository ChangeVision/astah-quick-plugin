package com.change_vision.astah.quick.internal.model;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickProperties {

    public static final String INITIAL_KEY_STROKE = "ctrl SPACE";

    public static final String ASTAH_PLUGIN_QUICK_KEY_STROKE = "astah.plugin.quick.keyStroke";

    
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(QuickProperties.class);
    
    private UserConfigDirectory userConfigDirectory = new UserConfigDirectory();

    private Properties properties = new Properties();

    private static QuickProperties instance;
    
    QuickProperties() {
        loadProperties();
    }

	private void loadProperties() {
		File propertyFile = getPropertyFile();
        if(propertyFile.exists()){
            try {
                properties.load(FileUtils.openInputStream(propertyFile));
            } catch (IOException e) {
                logger.error("Error has occurred.",e);
            }
        }
	}
    
    public static QuickProperties getInstance(){
        if (instance == null) {
            instance = new QuickProperties();
        }
        return instance;
    }
    
    public void setKeyStroke(String keyStroke){
        if (keyStroke == null) {
            properties.setProperty(ASTAH_PLUGIN_QUICK_KEY_STROKE, INITIAL_KEY_STROKE);
        } else {
            properties.setProperty(ASTAH_PLUGIN_QUICK_KEY_STROKE, keyStroke);
        }
    }

    public void store() {
        try {
            properties.store(FileUtils.openOutputStream(getPropertyFile(), false), "");
        } catch (IOException e) {
            logger.error("Error has occurred.",e);
        }
    }
    
    public String getKeyStroke(){
        return properties.getProperty(ASTAH_PLUGIN_QUICK_KEY_STROKE, INITIAL_KEY_STROKE);
    }
    
    public boolean exists(){
        File propertyFile = getPropertyFile();
        return propertyFile.exists();
    }
    
    private File getPropertyFile() {
        File directory = userConfigDirectory.getDirectory();
        File propertyFile = new File(directory, "quick.properties");
        return propertyFile;
    }
    
    void setUserConfigDirectory(UserConfigDirectory userConfigDirectory) {
    	properties.clear();
        this.userConfigDirectory = userConfigDirectory;
        loadProperties();
    }
}
