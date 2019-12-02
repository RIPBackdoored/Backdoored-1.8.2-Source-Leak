package javassist.tools.web;

import java.lang.reflect.*;
import java.net.*;
import java.io.*;

public class Viewer extends ClassLoader
{
    private String server;
    private int port;
    
    public static void main(final String[] v-1) throws Throwable {
        if (v-1.length >= 3) {
            final Viewer a1 = new Viewer(v-1[0], Integer.parseInt(v-1[1]));
            final String[] v1 = new String[v-1.length - 3];
            System.arraycopy(v-1, 3, v1, 0, v-1.length - 3);
            a1.run(v-1[2], v1);
        }
        else {
            System.err.println("Usage: java javassist.tools.web.Viewer <host> <port> class [args ...]");
        }
    }
    
    public Viewer(final String a1, final int a2) {
        super();
        this.server = a1;
        this.port = a2;
    }
    
    public String getServer() {
        return this.server;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void run(final String v1, final String[] v2) throws Throwable {
        final Class v3 = this.loadClass(v1);
        try {
            v3.getDeclaredMethod("main", String[].class).invoke(null, v2);
        }
        catch (InvocationTargetException a1) {
            throw a1.getTargetException();
        }
    }
    
    @Override
    protected synchronized Class loadClass(final String a1, final boolean a2) throws ClassNotFoundException {
        Class v1 = this.findLoadedClass(a1);
        if (v1 == null) {
            v1 = this.findClass(a1);
        }
        if (v1 == null) {
            throw new ClassNotFoundException(a1);
        }
        if (a2) {
            this.resolveClass(v1);
        }
        return v1;
    }
    
    @Override
    protected Class findClass(final String v2) throws ClassNotFoundException {
        Class v3 = null;
        if (v2.startsWith("java.") || v2.startsWith("javax.") || v2.equals("javassist.tools.web.Viewer")) {
            v3 = this.findSystemClass(v2);
        }
        if (v3 == null) {
            try {
                final byte[] a1 = this.fetchClass(v2);
                if (a1 != null) {
                    v3 = this.defineClass(v2, a1, 0, a1.length);
                }
            }
            catch (Exception ex) {}
        }
        return v3;
    }
    
    protected byte[] fetchClass(final String v-6) throws Exception {
        final URL url = new URL("http", this.server, this.port, "/" + v-6.replace('.', '/') + ".class");
        final URLConnection openConnection = url.openConnection();
        openConnection.connect();
        final int contentLength = openConnection.getContentLength();
        final InputStream inputStream = openConnection.getInputStream();
        byte[] array = null;
        if (contentLength <= 0) {
            final byte[] a1 = this.readStream(inputStream);
        }
        else {
            array = new byte[contentLength];
            int v0 = 0;
            do {
                final int v2 = inputStream.read(array, v0, contentLength - v0);
                if (v2 < 0) {
                    inputStream.close();
                    throw new IOException("the stream was closed: " + v-6);
                }
                v0 += v2;
            } while (v0 < contentLength);
        }
        inputStream.close();
        return array;
    }
    
    private byte[] readStream(final InputStream v2) throws IOException {
        byte[] v3 = new byte[4096];
        int v4 = 0;
        int v5 = 0;
        do {
            v4 += v5;
            if (v3.length - v4 <= 0) {
                final byte[] a1 = new byte[v3.length * 2];
                System.arraycopy(v3, 0, a1, 0, v4);
                v3 = a1;
            }
            v5 = v2.read(v3, v4, v3.length - v4);
        } while (v5 >= 0);
        final byte[] v6 = new byte[v4];
        System.arraycopy(v3, 0, v6, 0, v4);
        return v6;
    }
}
