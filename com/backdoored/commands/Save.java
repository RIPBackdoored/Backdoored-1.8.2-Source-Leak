package com.backdoored.commands;

import java.io.*;
import a.a.c.*;
import com.backdoored.utils.*;

public class Save extends CommandBase
{
    public Save() {
        super("save");
    }
    
    @Override
    public boolean a(final String[] array) {
        try {
            String string;
            if (array.length <= 0 || array[0].isEmpty()) {
                string = "Backdoored/config.json";
            }
            else {
                string = "Backdoored/config-" + array[0].toLowerCase() + ".json";
            }
            d.a(new File(string));
            Utils.printMessage("Saved " + string + " config", "green");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Utils.printMessage("Error when reading config: " + ex.getMessage(), "red");
        }
        return true;
    }
    
    @Override
    public String a() {
        return "-save <save name>";
    }
}
