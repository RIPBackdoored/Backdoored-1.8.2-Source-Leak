package com.backdoored;

import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class DllManager
{
    private static Field LIBRARIES;
    
    public DllManager() {
        super();
    }
    
    public static String[] getLoadedLibraries(final ClassLoader classLoader) {
        try {
            return ((Vector)DllManager.LIBRARIES.get(classLoader)).toArray(new String[0]);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new String[0];
        }
    }
    
    public static void loadJarDll(final String s) throws IOException {
        final File file = new File(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(s)).getFile());
        System.out.println(file.getAbsolutePath());
        final FileInputStream fileInputStream = new FileInputStream(file);
        final byte[] array = new byte[1024];
        final File tempFile = File.createTempFile(s, "");
        final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        int read;
        while ((read = fileInputStream.read(array)) != -1) {
            fileOutputStream.write(array, 0, read);
        }
        fileOutputStream.close();
        fileInputStream.close();
        System.out.println(tempFile.getAbsolutePath());
        System.load(tempFile.getAbsolutePath());
    }
    
    public static void loadBackdooredLibrary() {
        try {
            loadJarDll("com_backdoored_DllManager.dll");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String decrypt(final String s) {
        return decrypt(s, Backdoored.providedLicense);
    }
    
    public static native String encrypt(final String p0);
    
    public static native String decrypt(final String p0, final String p1);
    
    public static native String getHwid();
    
    static {
        try {
            (DllManager.LIBRARIES = ClassLoader.class.getDeclaredField("loadedLibraryNames")).setAccessible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            DllManager.LIBRARIES = null;
        }
    }
}
