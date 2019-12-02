package com.backdoored.commands;

import com.backdoored.hacks.*;
import com.backdoored.utils.*;
import java.util.*;
import a.a.g.b.*;

public class Toggle extends CommandBase
{
    public Toggle() {
        super(new String[] { "toggle", "t" });
    }
    
    @Override
    public boolean a(final String[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
        }
        for (final BaseHack baseHack : c.hacks()) {
            if (sb.toString().equalsIgnoreCase(baseHack.name.replace(" ", ""))) {
                baseHack.setEnabled(!baseHack.getEnabled());
                return true;
            }
        }
        Utils.printMessage(sb.toString() + " not found!", "red");
        return false;
    }
    
    @Override
    public String a() {
        return "-t <hackname>";
    }
}
