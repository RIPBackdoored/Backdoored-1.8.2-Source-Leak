package a.a.k;

import a.a.*;
import java.awt.*;
import java.util.*;
import com.backdoored.natives.*;

public class a implements e
{
    public static final Map<String, String> bcc;
    
    public a() {
        super();
    }
    
    public static Color b() {
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((System.nanoTime() + 10L) / 1.0E10f % 1.0f, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void a(final String s, final int n, final int n2) {
        a(s, n, n2, false);
    }
    
    public static void a(final String s, final int n, final int n2, final boolean b) {
        final float n3 = System.currentTimeMillis() % 11520L / 11520.0f;
        int n4 = n;
        for (final char c : s.toCharArray()) {
            a.mc.fontRenderer.drawString(String.valueOf(c), (float)n4, (float)n2, Color.HSBtoRGB(n3 + n2 / (float)a.mc.displayHeight + n / (float)a.mc.displayWidth, 1.0f, 1.0f), b);
            n4 += a.mc.fontRenderer.getCharWidth(c);
        }
    }
    
    public static String a(final String s) {
        return a.bcc.getOrDefault(s.replace(" ", "_").trim().toLowerCase(), "§d");
    }
    
    static {
        bcc = new HashMap<String, String>() {
            public a$a() {
                super();
            }
            
            {
                this.put(EncryptedStringPool.poolGet(26), "§4");
                this.put(EncryptedStringPool.poolGet(27), "§c");
                this.put(EncryptedStringPool.poolGet(28), "§6");
                this.put(EncryptedStringPool.poolGet(29), "§e");
                this.put(EncryptedStringPool.poolGet(30), "§2");
                this.put(EncryptedStringPool.poolGet(31), "§a");
                this.put(EncryptedStringPool.poolGet(32), "§b");
                this.put(EncryptedStringPool.poolGet(33), "§3");
                this.put(EncryptedStringPool.poolGet(34), "§1");
                this.put(EncryptedStringPool.poolGet(35), "§9");
                this.put(EncryptedStringPool.poolGet(36), "§d");
                this.put(EncryptedStringPool.poolGet(37), "§5");
                this.put(EncryptedStringPool.poolGet(38), "§f");
                this.put(EncryptedStringPool.poolGet(39), "§7");
                this.put(EncryptedStringPool.poolGet(40), "§8");
                this.put(EncryptedStringPool.poolGet(41), "§0");
            }
        };
    }
    
    public enum b
    {
        bbl("DARKRED", 0, "Dark Red"), 
        bbm("RED", 1, "Red"), 
        bbn("GOLD", 2, "Gold"), 
        bbo("YELLOW", 3, "Yellow"), 
        bbp("DARKGREEN", 4, "Dark Green"), 
        bbq("GREEN", 5, "Green"), 
        bbr("AQUA", 6, "Aqua"), 
        bbs("DARKAQUA", 7, "Dark Aqua"), 
        bbt("DARKBLUE", 8, "Dark Blue"), 
        bbu("BLUE", 9, "Blue"), 
        bbv("LIGHTPURPLE", 10, "Light Purple"), 
        bbw("DARKPURPLE", 11, "Dark Purple"), 
        bbx("WHITE", 12, "White"), 
        bby("GRAY", 13, "Gray"), 
        bbz("DARKGRAY", 14, "Dark Gray"), 
        bca("BLACK", 15, "Black");
        
        private String bcb;
        private static final /* synthetic */ b[] $VALUES;
        
        public static b[] values() {
            return b.$VALUES.clone();
        }
        
        public static b valueOf(final String s) {
            return Enum.valueOf(b.class, s);
        }
        
        private b(final String s, final int n, final String bcb) {
            this.bcb = bcb;
        }
        
        @Override
        public String toString() {
            return this.bcb;
        }
        
        static {
            $VALUES = new b[] { b.bbl, b.bbm, b.bbn, b.bbo, b.bbp, b.bbq, b.bbr, b.bbs, b.bbt, b.bbu, b.bbv, b.bbw, b.bbx, b.bby, b.bbz, b.bca };
        }
    }
}
