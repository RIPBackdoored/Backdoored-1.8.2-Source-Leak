package com.backdoored.commands;

import com.backdoored.utils.*;
import com.backdoored.hacks.*;
import java.util.*;
import a.a.g.b.*;

public class Bind extends CommandBase
{
    public Bind() {
        super(new String[] { "bind", "keybind" });
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length <= 0) {
            Utils.printMessage("Please specify a hack!");
            return false;
        }
        final StringBuilder sb = new StringBuilder();
        for (int length = array.length, i = 0; i < length; ++i) {
            sb.append(array[i]);
        }
        for (final BaseHack baseHack : c.hacks()) {
            if (sb.toString().equalsIgnoreCase(baseHack.name.replace(" ", ""))) {
                Utils.printMessage(baseHack.name + ": " + baseHack.ho.getValInt());
                return true;
            }
        }
        final StringBuilder sb2 = new StringBuilder();
        final String[] array2 = Arrays.copyOf(array, array.length - 1);
        for (int length2 = array2.length, j = 0; j < length2; ++j) {
            sb2.append(array2[j]);
        }
        for (final BaseHack baseHack2 : c.hacks()) {
            if (sb2.toString().equalsIgnoreCase(baseHack2.name.replace(" ", ""))) {
                baseHack2.ho.a(array[array.length - 1].toUpperCase());
                Utils.printMessage("Set keybind of hack '" + baseHack2.name + "' to '" + baseHack2.ho.getValInt() + "'");
                return true;
            }
        }
        Utils.printMessage(sb2.toString() + " not found!", "red");
        return false;
    }
    
    @Override
    public String a() {
        return "-t <hackname>";
    }
}
