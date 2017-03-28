package com.yonyou.iuap.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

    private static String confFileUrl;
    
    public static final String SYSTEM_PROPERTIES_MODE_NEVER = "0";

    public static final String SYSTEM_PROPERTIES_MODE_FALLBACK = "1";

    public static final String SYSTEM_PROPERTIES_MODE_OVERRIDE = "2";

    private static Properties prop = null;
    
	public static void setConfFileUrl(String confFileUrl) {
		PropertyUtil.confFileUrl = confFileUrl;
		init();
	}

	private static String systemPropertyMode;

    static {
    	init();
    }
    
    private static void init(){
    	prop = new Properties();
    	loadData();
    }
    
    public static void reload(){
    	loadData();
    }

    private static void loadData() {
        InputStream in = null;
        try {
            File file = null;
            String confFileUrl = getConfigFilePath();
            if (StringUtils.isNotBlank(confFileUrl)) {
                file = new File(confFileUrl);
            }

            if (file != null && file.exists()) {
                in = new FileInputStream(file);
            } else {
                in = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            }
            prop = new Properties();
			if (in != null) {
				prop.load(in);
				systemPropertyMode = prop.getProperty("uap.system.properties.mode");
			}
            systemPropertyMode =
                    StringUtils.isNotBlank(systemPropertyMode) && Pattern.matches("[012]", systemPropertyMode) ? systemPropertyMode
                            : SYSTEM_PROPERTIES_MODE_OVERRIDE;
        } catch (IOException e) {
            LOGGER.error("Fail to load application.properties", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("Fail to Close inputStream", e);
                }
            }
        }
    }

    private static String getConfigFilePath() {
    	String filePath = null;
    	if(StringUtils.isBlank(filePath)){
    		filePath = System.getProperty("iuap.server.conf.url");
        }
    	if(StringUtils.isBlank(filePath)){
    		filePath = System.getenv("iuap.server.conf.url");
        }
    	
    	if(StringUtils.isBlank(filePath)){
    		filePath = confFileUrl;
        }
        return filePath;
    }

    public static String getPropertyByKey(String key) {
        String value = null;
        if (SYSTEM_PROPERTIES_MODE_OVERRIDE.equals(systemPropertyMode)) {
            value = resolveSystemProperty(key);
        }
        if (StringUtils.isBlank(value)) {
            value = prop.getProperty(key);
        }
        if (StringUtils.isBlank(value) && SYSTEM_PROPERTIES_MODE_FALLBACK.equals(systemPropertyMode)) {
            value = resolveSystemProperty(key);
        }
        return StringUtils.isBlank(value) ? "" : value;
    }

    public static String getPropertyByKey(String key, String defaultValue) {
        String value = null;
        if (SYSTEM_PROPERTIES_MODE_OVERRIDE.equals(systemPropertyMode)) {
            value = resolveSystemProperty(key);
        }
        if (StringUtils.isBlank(value)) {
            value = prop.getProperty(key);
        }
        if (StringUtils.isBlank(value) && SYSTEM_PROPERTIES_MODE_FALLBACK.equals(systemPropertyMode)) {
            value = resolveSystemProperty(key);
        }
        return StringUtils.isBlank(value) ? defaultValue : value;
    }

    private static String resolveSystemProperty(String key) {
        try {
            String value = System.getProperty(key);
            if (StringUtils.isBlank(value)) {
                value = System.getenv(key);
            }
            return value;
        } catch (Exception ex) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Could not access system property '" + key + "': " + ex);
            }
            return null;
        }
    }
}
