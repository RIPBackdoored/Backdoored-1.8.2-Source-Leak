package com.backdoored;

import net.minecraftforge.fml.common.*;
import com.google.common.base.*;
import com.google.common.io.*;
import a.a.k.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import com.google.common.hash.*;
import java.nio.charset.*;

public class DrmManager
{
    public static boolean hasCrashed;
    
    public DrmManager() {
        super();
    }
    
    public static boolean loadLicense() {
        FMLLog.log.info("Your hwid: " + c());
        try {
            FMLLog.log.info("Reading license from file");
            final String read = Files.asCharSource(new File("Backdoored/license.txt"), Charsets.UTF_8).read();
            FMLLog.log.info("License Retrieved from file: " + read);
            if (read != null && !read.trim().isEmpty() && b(read.trim())) {
                Backdoored.providedLicense = read.trim();
                return true;
            }
        }
        catch (Exception ex) {
            FMLLog.log.info("Error while reading license from file " + ex.getMessage());
            ex.printStackTrace();
        }
        if (Backdoored.providedLicense == null || Backdoored.providedLicense.trim().isEmpty() || !b(Backdoored.providedLicense.trim())) {
            e.a(c());
        }
        while (Backdoored.providedLicense == null || Backdoored.providedLicense.trim().isEmpty() || !b(Backdoored.providedLicense.trim())) {
            FMLLog.log.info("Hwid is '" + c() + "'");
            final Frame frame = new Frame();
            frame.setAlwaysOnTop(true);
            frame.setState(1);
            final String s = (String)JOptionPane.showInputDialog(frame, "HWID is " + c() + " (copied to clipboard)", "Please enter license", 3, UIManager.getIcon("OptionPane.warningIcon"), null, null);
            if (s == null) {
                return false;
            }
            final String trim = s.trim();
            if (!trim.isEmpty() && b(trim)) {
                FMLLog.log.info("Provided valid license: " + trim);
                a(Backdoored.providedLicense = trim);
                FMLLog.log.info("Saved license " + Backdoored.providedLicense);
                return true;
            }
            FMLLog.log.info("User inputted incorrect license: " + Backdoored.providedLicense);
        }
        return false;
    }
    
    public static void a(final String s) {
        try {
            final FileWriter fileWriter = new FileWriter(new File("Backdoored/license.txt"));
            fileWriter.write(s);
            fileWriter.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String a(final StringBuilder sb) {
        System.out.println("Requested crash report");
        if (!DrmManager.hasCrashed) {
            return sb.toString();
        }
        return "null";
    }
    
    public static void b() {
        try {
            for (final File file : Objects.requireNonNull(new File("mods").listFiles())) {
                if (file.getAbsolutePath().toLowerCase().contains("backdoored")) {
                    try {
                        file.delete();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    file.deleteOnExit();
                }
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }
    
    private static String c() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean b(final String s) {
        final String c = c();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)c, StandardCharsets.UTF_8).toString() + c + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    static {
        DrmManager.hasCrashed = false;
    }
}
