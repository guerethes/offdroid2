package br.com.guerethes.offdroid.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class PropertiesUtils extends ContextWrapper {

    private String file = "offdroid.properties";
    private static final String PREFS_PRIVATE = "PREFS_PRIVATE";

    public PropertiesUtils(Context base) {
        super(base);
    }

    public void importPropertyApp(Context context) {
        AssetManager assetManager = null;
        try {
            Properties properties = new Properties();
            assetManager = context.getAssets();
            properties.load(assetManager.open(file));

            Set<String> keys = properties.stringPropertyNames();
            PropertiesUtils propertiesUtils = new PropertiesUtils(context);
            for (String key: keys) {
                propertiesUtils.addProperty(key.trim(), ((String) properties.get(key)).trim(), context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public void addProperty(String key, String value, Context context) {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
            sharedPref.edit().remove(key).commit();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key.trim(), value.trim());
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(openFileInput(file));
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

}