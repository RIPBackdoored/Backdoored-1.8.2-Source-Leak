package com.backdoored.commands;

import java.io.*;
import a.a.c.*;
import com.backdoored.utils.*;

public class Load extends CommandBase
{
    public Load() {
        super("load");
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length <= 0) {
            return false;
        }
        try {
            String string;
            if (array[0].isEmpty()) {
                string = "Backdoored/config.json";
            }
            else {
                string = "Backdoored/config-" + array[0].toLowerCase() + ".json";
            }
            c.a(new File(string));
            Utils.printMessage("Loaded " + string + " config", "green");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Utils.printMessage("Error when reading config: " + ex.getMessage(), "red");
        }
        return true;
    }
    
    @Override
    public String a() {
        return "-load <config name>";
    }
}
