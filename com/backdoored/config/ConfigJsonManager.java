package com.backdoored.config;

import java.io.*;
import a.a.c.*;

public class ConfigJsonManager
{
    public static final File bq;
    
    public ConfigJsonManager() {
        super();
    }
    
    public static void a() {
        try {
            d.a(ConfigJsonManager.bq);
        }
        catch (Exception ex) {
            System.out.println("Error saving config");
            ex.printStackTrace();
        }
    }
    
    public static void read() {
        try {
            c.a(ConfigJsonManager.bq);
        }
        catch (Exception ex) {
            System.out.println("Error reading config");
            ex.printStackTrace();
        }
    }
    
    static {
        bq = new File("Backdoored/config.json");
        try {
            if (!ConfigJsonManager.bq.exists()) {
                ConfigJsonManager.bq.createNewFile();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
