package com.backdoored.commands;

import com.backdoored.utils.*;
import net.minecraft.util.text.*;

public class Fakemsg extends CommandBase
{
    public Fakemsg() {
        super(new String[] { "fakemsg", "msg", "impersonate" });
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length < 3) {
            return false;
        }
        final String s = array[0];
        switch (s) {
            default: {
                final StringBuilder sb = new StringBuilder();
                sb.append("<").append(array[1]).append("> ");
                for (int i = 2; i < array.length; ++i) {
                    sb.append(array[i]).append(" ");
                }
                Utils.a(sb.toString(), false);
                return true;
            }
            case "whisper": {
                final String s2 = array[1];
                final StringBuilder sb2 = new StringBuilder();
                for (int j = 2; j < array.length; ++j) {
                    sb2.append(array[j]).append(" ");
                }
                this.mc.ingameGUI.addChatMessage(ChatType.CHAT, (ITextComponent)new TextComponentString("§d" + s2 + " whispers: " + sb2.toString()));
                return true;
            }
            case "server": {
                final StringBuilder sb3 = new StringBuilder("§e[SERVER] ");
                for (int k = 1; k < array.length; ++k) {
                    sb3.append(array[k]).append(" ");
                }
                Utils.a(sb3.toString(), false);
                return true;
            }
            case "suicide": {
                final String s3 = array[1];
                final StringBuilder sb4 = new StringBuilder("§4");
                for (int l = 2; l < array.length; ++l) {
                    sb4.append(array[l]).append(" ");
                }
                Utils.a(sb4.toString().replace(" player ", " §3" + s3 + " §4"), false);
                return true;
            }
            case "kill": {
                final String s4 = array[1];
                final String s5 = array[2];
                final StringBuilder sb5 = new StringBuilder("§4");
                for (int n2 = 3; n2 < array.length; ++n2) {
                    sb5.append(array[n2]).append(" ");
                }
                Utils.a(sb5.toString().replace(" player1 ", " §3" + s4 + " §4").replace(" player2 ", " §3" + s5 + " §4"), false);
                return true;
            }
            case "killWeapon": {
                final String s6 = array[1];
                final String s7 = array[2];
                final String s8 = array[3];
                final StringBuilder sb6 = new StringBuilder("§4");
                for (int n3 = 4; n3 < array.length; ++n3) {
                    sb6.append(array[n3]).append(" ");
                }
                Utils.a(sb6.toString().replace(" player1 ", " §3" + s6 + " §4").replace(" player2 ", " §3" + s7 + " §4").replace(" weapon ", " §6" + s8 + " §4"), false);
                return true;
            }
        }
    }
    
    @Override
    public String a() {
        return "-fakemsg chat 4yl im kinda ez ngl\n-fakemsg whisper John200410 Backdoored client on top\n-fakemsg server buy prio pls";
    }
}
