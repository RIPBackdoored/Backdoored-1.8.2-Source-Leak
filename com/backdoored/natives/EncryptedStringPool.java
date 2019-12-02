package com.backdoored.natives;

import java.util.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;

public class EncryptedStringPool
{
    private static final HashMap<Integer, String> baf;
    
    public EncryptedStringPool() {
        super();
    }
    
    public static String poolGet(final int n) {
        return EncryptedStringPool.baf.get(n);
    }
    
    public static void a() {
        FMLLog.log.info("Encrypting things");
        final long currentTimeMillis = System.currentTimeMillis();
        int n = 0;
        for (final String s : new String[] { "Error initialising class ", "Backdoored tried to load ", " hack, out of which ", " failed", "Failed hack: ", "x", "y", "open", "prefix", "Backdoored startup finished", "Hack with name ", " not found", "Hack of class ", "Logging into an online account with email: ", "session", "field_71449_j", "Logged in successfully:", "Session ID: ", "Username: ", "textures/cape_backdoored.png", "textures/cape_backdoored_dev.png", "http://pastebin.com/raw/ZMZcF3nJ", "Gave capes to: ", "Could not fetch capes", "http://pastebin.com/raw/g4wjzg5U", "Could not fetch dev capes", "dark_red", "red", "gold", "yellow", "dark_green", "green", "aqua", "dark_aqua", "dark_blue", "blue", "light_purple", "dark_purple", "white", "gray", "dark_gray", "black", "Backdoored/friends.txt" }) {
            System.out.print("\nput(" + n + ",\"" + DllManager.encrypt(s) + "\");      // " + s);
            ++n;
        }
        System.out.print("\nTook " + (System.currentTimeMillis() - currentTimeMillis) / 1000.0 + "s");
        System.exit(-1);
    }
    
    static {
        baf = new HashMap<Integer, String>() {
            public EncryptedStringPool$a() {
                super();
            }
            
            {
                this.put(0, "Error initialising class ");
                this.put(1, "Backdoored tried to load ");
                this.put(2, " hack, out of which ");
                this.put(3, " failed");
                this.put(4, "Failed hack: ");
                this.put(5, "x");
                this.put(6, "y");
                this.put(7, "open");
                this.put(8, "prefix");
                this.put(9, "Backdoored startup finished");
                this.put(10, "Hack with name ");
                this.put(11, " not found");
                this.put(12, "Hack of class ");
                this.put(13, "Logging into an online account with email: ");
                this.put(14, "session");
                this.put(15, "field_71449_j");
                this.put(16, "Logged in successfully:");
                this.put(17, "Session ID: ");
                this.put(18, "Username: ");
                this.put(19, "textures/cape_backdoored.png");
                this.put(20, "textures/cape_backdoored_dev.png");
                this.put(21, "http://pastebin.com/raw/ZMZcF3nJ");
                this.put(22, "Gave capes to: ");
                this.put(23, "Could not fetch capes");
                this.put(24, "http://pastebin.com/raw/g4wjzg5U");
                this.put(25, "Could not fetch dev capes");
                this.put(26, "dark_red");
                this.put(27, "red");
                this.put(28, "gold");
                this.put(29, "yellow");
                this.put(30, "dark_green");
                this.put(31, "green");
                this.put(32, "aqua");
                this.put(33, "dark_aqua");
                this.put(34, "dark_blue");
                this.put(35, "blue");
                this.put(36, "light_purple");
                this.put(37, "dark_purple");
                this.put(38, "white");
                this.put(39, "gray");
                this.put(40, "dark_gray");
                this.put(41, "black");
                this.put(42, "Backdoored/friends.txt");
                this.put(43, "http://pastebin.com/raw/sYrfgjsY");
            }
        };
    }
}
