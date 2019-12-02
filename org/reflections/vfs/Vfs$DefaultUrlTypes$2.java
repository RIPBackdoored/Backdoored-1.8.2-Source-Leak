package org.reflections.vfs;

import java.util.jar.*;
import java.net.*;
import java.io.*;

enum Vfs$DefaultUrlTypes$2
{
    Vfs$DefaultUrlTypes$2(final String a1, final int a2) {
    }
    
    @Override
    public boolean matches(final URL a1) {
        return "jar".equals(a1.getProtocol()) || "zip".equals(a1.getProtocol()) || "wsjar".equals(a1.getProtocol());
    }
    
    @Override
    public Dir createDir(final URL v2) throws Exception {
        try {
            final URLConnection a1 = v2.openConnection();
            if (a1 instanceof JarURLConnection) {
                return new ZipDir(((JarURLConnection)a1).getJarFile());
            }
        }
        catch (Throwable t) {}
        final java.io.File v3 = Vfs.getFile(v2);
        if (v3 != null) {
            return new ZipDir(new JarFile(v3));
        }
        return null;
    }
}