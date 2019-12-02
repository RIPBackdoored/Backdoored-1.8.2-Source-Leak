package com.backdoored.commands;

import com.backdoored.utils.*;

public class Prefix extends CommandBase
{
    public Prefix() {
        super("prefix");
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length > 0) {
            Command.x = array[0];
            Utils.printMessage("Set cmd prefix to " + Command.x, "red");
            return true;
        }
        return false;
    }
    
    @Override
    public String a() {
        return "Usage: .prefix <new prefix character>";
    }
}
