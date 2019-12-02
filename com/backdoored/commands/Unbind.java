package com.backdoored.commands;

import com.backdoored.utils.*;
import com.backdoored.hacks.*;
import java.util.*;
import a.a.g.b.*;

public class Unbind extends CommandBase
{
    public Unbind() {
        super("unbind");
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length < 1) {
            Utils.printMessage("Invalid args!");
            return false;
        }
        final StringBuilder sb = new StringBuilder();
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
        }
        final String string = sb.toString();
        for (final BaseHack baseHack : c.hacks()) {
            if (string.equalsIgnoreCase(baseHack.name.replace(" ", ""))) {
                baseHack.ho.a("NONE");
                Utils.printMessage("Bound " + string + " to " + baseHack.ho.getValInt());
                return true;
            }
        }
        Utils.printMessage("Could not find hack '" + string + "'");
        return false;
    }
    
    @Override
    public String a() {
        return "-unbind Twerk";
    }
}
