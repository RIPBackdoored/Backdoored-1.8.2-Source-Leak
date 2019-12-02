package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import java.io.*;
import a.a.g.b.g.*;
import javax.swing.*;
import com.backdoored.setting.*;
import java.nio.file.*;
import com.backdoored.utils.*;
import net.minecraft.util.*;
import java.util.*;
import java.awt.event.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;

@b.a(name = "Spammer", description = "Spam the chat", category = CategoriesInit.PLAYER)
public class Spammer extends BaseHack
{
    private static final char[] tt;
    private static final File tu;
    private r.c tv;
    private Setting<r.c> mode;
    private Setting<Integer> delay;
    private Timer ty;
    private Timer tz;
    private Timer ua;
    
    public Spammer() {
        super();
        this.mode = new EnumSetting<r.c>("Mode", (BaseHack)this, (Enum)r.c.tq);
        this.delay = new IntegerSetting("Delay", this, 2, 1, 60);
        try {
            Spammer.tu.createNewFile();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void onEnabled() {
        if (this.delay == null) {
            this.delay = new IntegerSetting("Delay", this, 2, 1, 60);
        }
        try {
            switch (r.r$b.tp[this.mode.getValInt().ordinal()]) {
                case 1: {
                    final String[] split = new String(Files.readAllBytes(Paths.get(Spammer.tu.getCanonicalPath(), new String[0]))).split("\n");
                    final int length = split.length;
                    if (length == 0 || split[0].isEmpty()) {}
                    (this.ty = new Timer(this.delay.getValInt() * 1000, (ActionListener)new r.r$a(this, length, split))).start();
                    break;
                }
                case 2: {
                    (this.tz = new Timer(this.delay.getValInt() * 1000, this::b)).start();
                    break;
                }
                default: {
                    this.ua = new Timer(this.delay.getValInt() * 1000, r::a);
                    break;
                }
            }
        }
        catch (Exception ex) {
            Utils.printMessage("Disabled spammer due to error: " + ex.toString(), "red");
            ex.printStackTrace();
            this.setEnabled(false);
        }
    }
    
    public void onTick() {
        if (this.getEnabled()) {
            if (this.tv == null) {
                this.tv = this.mode.getValInt();
                return;
            }
            if (!this.tv.equals((Object)this.mode.getValInt())) {
                this.onDisabled();
                this.onEnabled();
            }
            this.tv = this.mode.getValInt();
        }
    }
    
    public void onDisabled() {
        for (final Timer timer : new Timer[] { this.ty, this.tz, this.ua }) {
            try {
                timer.stop();
            }
            catch (Exception ex) {}
        }
    }
    
    private String c() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; ++i) {
            char c;
            for (c = ' '; c == ' ' || !ChatAllowedCharacters.isAllowedCharacter(c); c = Spammer.tt[new Random().nextInt(Spammer.tt.length)]) {}
            sb.append(c);
        }
        return sb.toString();
    }
    
    private static /* synthetic */ void a(final ActionEvent actionEvent) {
        Spammer.mc.player.sendChatMessage(StringUtils.repeat(Spammer.tt[195], 256));
    }
    
    private /* synthetic */ void b(final ActionEvent actionEvent) {
        Spammer.mc.player.sendChatMessage(this.c());
    }
    
    public static /* synthetic */ Minecraft e() {
        return Spammer.mc;
    }
    
    static {
        tt = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u00e2\u0152\u201a\u00c3\u2021\u00c3¼\u00c3©\u00c3¢\u00c3¤\u00c3 \u00c3¥\u00c3§\u00c3ª\u00c3«\u00c3¨\u00c3¯\u00c3®\u00c3¬\u00c3\u201e\u00c3\u2026\u00c3\u2030\u00c3¦\u00c3\u2020\u00c3´\u00c3¶\u00c3²\u00c3»\u00c3¹\u00c3¿\u00c3\u2013\u00c3\u0153\u00c3¸\u00c2£\u00c3\u02dc\u00c3\u2014\u00c6\u2019\u00c3¡\u00c3\u00ad\u00c3³\u00c3º\u00c3±\u00c3\u2018\u00c2ª\u00c2º".toCharArray();
        tu = new File("Backdoored/spammer.txt");
    }
}
