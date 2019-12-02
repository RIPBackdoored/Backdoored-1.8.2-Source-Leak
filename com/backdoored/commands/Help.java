package com.backdoored.commands;

import com.backdoored.utils.*;
import java.util.*;

public class Help extends CommandBase
{
    public Help() {
        super(new String[] { "help", "commands" });
    }
    
    @Override
    public boolean a(final String[] array) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<CommandBase> iterator = CommandBase.z.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().ba.get(0)).append(", ");
        }
        Utils.printMessage("Commands:\n" + sb.toString());
        return true;
    }
    
    @Override
    public String a() {
        return "-help";
    }
}
