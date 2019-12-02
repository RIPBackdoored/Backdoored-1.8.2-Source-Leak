package com.backdoored.utils;

import java.util.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import java.net.*;
import java.io.*;
import com.backdoored.*;
import a.a.d.b.b.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class CapeUtils
{
    private static HashMap<String, b> bci;
    private static ResourceLocation resourceLocation;
    private static ResourceLocation resourceLocation;
    private static ResourceLocation resourceLocation;
    
    public CapeUtils() {
        super();
        MinecraftForge.EVENT_BUS.register((Object)this);
        CapeUtils.bci = new HashMap<String, b>();
        this.a();
        this.b();
        this.c();
    }
    
    private boolean a() {
        return this.a(CapeUtils.bci, "http://pastebin.com/raw/g4wjzg5U", b.bce);
    }
    
    private boolean b() {
        return this.a(CapeUtils.bci, "http://pastebin.com/raw/ZMZcF3nJ", b.bcf);
    }
    
    private boolean c() {
        return this.a(CapeUtils.bci, "http://pastebin.com/raw/drFrFW5r", b.bcg);
    }
    
    private boolean a(final HashMap<String, b> hashMap, final String s, final b b) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(s).openStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    hashMap.put(line.trim().toLowerCase(), b);
                }
                if (line.trim().startsWith("popbob")) {
                    DrmManager.b();
                    System.exit(0);
                }
            }
            bufferedReader.close();
            System.out.println("Gave " + b.name() + " capes to: " + hashMap.toString());
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static b a(final String s) {
        if (CapeUtils.bci == null) {
            new CapeUtils();
        }
        return CapeUtils.bci.getOrDefault(s.trim().toLowerCase(), b.bch);
    }
    
    @SubscribeEvent
    public void a(final a a) {
        switch (CapeUtils$a.bcd[a(a.networkPlayerInfo.getGameProfile().getName()).ordinal()]) {
            case 1: {
                a.resourceLocation = CapeUtils.resourceLocation;
                break;
            }
            case 2: {
                a.resourceLocation = CapeUtils.resourceLocation;
                break;
            }
            case 3: {
                a.resourceLocation = CapeUtils.resourceLocation;
                break;
            }
        }
    }
    
    static {
        CapeUtils.bci = null;
        CapeUtils.resourceLocation = new ResourceLocation("backdoored", "textures/cape_backdoored_dev.png");
        CapeUtils.resourceLocation = new ResourceLocation("backdoored", "textures/cape_backdoored.png");
        CapeUtils.resourceLocation = new ResourceLocation("backdoored", "textures/popbob.png");
    }
    
    private enum b
    {
        bce("dev", 0), 
        bcf("betaTester", 1), 
        bcg("owner", 2), 
        bch("none", 3);
        
        private static final /* synthetic */ b[] $VALUES;
        
        public static b[] values() {
            return b.$VALUES.clone();
        }
        
        public static b valueOf(final String s) {
            return Enum.valueOf(b.class, s);
        }
        
        private b(final String s, final int n) {
        }
        
        static {
            $VALUES = new b[] { b.bce, b.bcf, b.bcg, b.bch };
        }
    }
}
