package com.change_vision.astah.quick.internal.model;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickProperties {

    public static final String ASTAH_PLUGIN_QUICK_KEY_STROKE = "astah.plugin.quick.keyStroke";

    
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(QuickProperties.class);
    
    private final UserConfigDirectory userConfigDirectory = new UserConfigDirectory();
    
    public void setKeyStroke(String keyStroke){
        Properties properties = getProperties();
        properties.setProperty(ASTAH_PLUGIN_QUICK_KEY_STROKE, keyStroke);
        try {
            properties.store(FileUtils.openOutputStream(getPropertyFile(), false), "");
        } catch (IOException e) {
            logger.error("Error has occurred.",e);
        }
    }
    
    public String getKeyStroke(){
        Properties properties = getProperties();
        return properties.getProperty(ASTAH_PLUGIN_QUICK_KEY_STROKE, "ctrl SPACE");
    }
    
    public boolean exists(){
        File propertyFile = getPropertyFile();
        return propertyFile.exists();
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        File propertyFile = getPropertyFile();
        if(propertyFile.exists()){
            try {
                properties.load(FileUtils.openInputStream(propertyFile));
            } catch (IOException e) {
                logger.error("Error has occurred.",e);
            }
        }
        return properties;
    }
    
    private File getPropertyFile() {
        File directory = userConfigDirectory.getDirectory();
        File propertyFile = new File(directory, "quick.propreties");
        return propertyFile;
    }
}
