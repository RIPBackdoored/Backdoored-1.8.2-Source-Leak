package com.google.api.client.util;

import java.io.*;
import java.lang.reflect.*;

public class IOUtils
{
    public IOUtils() {
        super();
    }
    
    public static void copy(final InputStream a1, final OutputStream a2) throws IOException {
        copy(a1, a2, true);
    }
    
    public static void copy(final InputStream a1, final OutputStream a2, final boolean a3) throws IOException {
        try {
            ByteStreams.copy(a1, a2);
        }
        finally {
            if (a3) {
                a1.close();
            }
        }
    }
    
    public static long computeLength(final StreamingContent a1) throws IOException {
        final ByteCountingOutputStream v1 = new ByteCountingOutputStream();
        try {
            a1.writeTo(v1);
        }
        finally {
            v1.close();
        }
        return v1.count;
    }
    
    public static byte[] serialize(final Object a1) throws IOException {
        final ByteArrayOutputStream v1 = new ByteArrayOutputStream();
        serialize(a1, v1);
        return v1.toByteArray();
    }
    
    public static void serialize(final Object a1, final OutputStream a2) throws IOException {
        try {
            new ObjectOutputStream(a2).writeObject(a1);
        }
        finally {
            a2.close();
        }
    }
    
    public static <S extends java.lang.Object> S deserialize(final byte[] a1) throws IOException {
        if (a1 == null) {
            return null;
        }
        return (S)deserialize((InputStream)new ByteArrayInputStream(a1));
    }
    
    public static <S extends java.lang.Object> S deserialize(final InputStream v0) throws IOException {
        try {
            return (S)new ObjectInputStream(v0).readObject();
        }
        catch (ClassNotFoundException v) {
            final IOException a1 = new IOException("Failed to deserialize object");
            a1.initCause(v);
            throw a1;
        }
        finally {
            v0.close();
        }
    }
    
    public static boolean isSymbolicLink(final File v-1) throws IOException {
        try {
            final Class<?> a1 = Class.forName("java.nio.file.Files");
            final Class<?> v1 = Class.forName("java.nio.file.Path");
            final Object v2 = File.class.getMethod("toPath", (Class<?>[])new Class[0]).invoke(v-1, new Object[0]);
            return (boolean)a1.getMethod("isSymbolicLink", v1).invoke(null, v2);
        }
        catch (InvocationTargetException v4) {
            final Throwable v3 = v4.getCause();
            Throwables.propagateIfPossible(v3, IOException.class);
            throw new RuntimeException(v3);
        }
        catch (ClassNotFoundException ex) {}
        catch (IllegalArgumentException ex2) {}
        catch (SecurityException ex3) {}
        catch (IllegalAccessException ex4) {}
        catch (NoSuchMethodException ex5) {}
        if (File.separatorChar == '\\') {
            return false;
        }
        File v5 = v-1;
        if (v-1.getParent() != null) {
            v5 = new File(v-1.getParentFile().getCanonicalFile(), v-1.getName());
        }
        return !v5.getCanonicalFile().equals(v5.getAbsoluteFile());
    }
}
