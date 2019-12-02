package com.backdoored.utils;

import java.util.*;
import java.io.*;
import net.minecraft.entity.player.*;
import com.backdoored.*;
import com.backdoored.natives.*;

public class FriendUtils
{
    private static final String bcm;
    private static ArrayList<String> bcn;
    
    public FriendUtils() {
        super();
    }
    
    public static boolean a() {
        if (FriendUtils.bcn == null) {
            FriendUtils.bcn = new ArrayList<String>() {
                public FriendUtils$a() {
                    super();
                }
            };
        }
        try {
            final PrintWriter printWriter = new PrintWriter(FriendUtils.bcm);
            printWriter.print("");
            printWriter.close();
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FriendUtils.bcm));
            final Iterator<String> iterator = FriendUtils.bcn.iterator();
            while (iterator.hasNext()) {
                bufferedWriter.write(iterator.next());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            return true;
        }
        catch (Exception ex) {
            System.out.println("Could not write friends.txt: " + ex.toString());
            ex.printStackTrace();
            System.out.println(FriendUtils.bcn);
            return false;
        }
    }
    
    public static boolean read() {
        try {
            try {
                FriendUtils.bcn = new ArrayList<String>();
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(FriendUtils.bcm));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    FriendUtils.bcn.add(line);
                }
                bufferedReader.close();
                System.out.println("Successfully read friends: " + FriendUtils.bcn.toString());
                return true;
            }
            catch (FileNotFoundException ex2) {
                new File(FriendUtils.bcm).createNewFile();
                FriendUtils.bcn = new ArrayList<String>() {
                    public FriendUtils$b() {
                        super();
                    }
                };
                return true;
            }
        }
        catch (Exception ex) {
            System.out.println("Could not read friends: " + ex.toString());
            ex.printStackTrace();
            return false;
        }
    }
    
    public static ArrayList<String> c() {
        return FriendUtils.bcn;
    }
    
    public static void a(final String s) {
        if (FriendUtils.bcn == null) {
            return;
        }
        FriendUtils.bcn.add(s);
    }
    
    public static void b(final String s) {
        if (FriendUtils.bcn == null) {
            return;
        }
        FriendUtils.bcn.remove(s);
    }
    
    public static boolean c(final String s) {
        return FriendUtils.bcn != null && FriendUtils.bcn.contains(s);
    }
    
    public static boolean a(final EntityPlayer entityPlayer) {
        return FriendUtils.bcn != null && (Globals.mc.player.getUniqueID().equals(entityPlayer.getUniqueID()) || c(entityPlayer.getName()));
    }
    
    static {
        bcm = EncryptedStringPool.poolGet(42);
    }
}
