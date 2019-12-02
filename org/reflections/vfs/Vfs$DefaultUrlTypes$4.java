package org.reflections.vfs;

import java.net.*;
import org.reflections.util.*;
import java.io.*;
import java.util.jar.*;

enum Vfs$DefaultUrlTypes$4
{
    Vfs$DefaultUrlTypes$4(final String a1, final int a2) {
    }
    
    @Override
    public boolean matches(final URL a1) {
        return a1.getProtocol().equals("vfs");
    }
    
    @Override
    public Dir createDir(final URL a1) throws Exception {
        final Object v1 = a1.openConnection().getContent();
        final Class<?> v2 = ClasspathHelper.contextClassLoader().loadClass("org.jboss.vfs.VirtualFile");
        final java.io.File v3 = (java.io.File)v2.getMethod("getPhysicalFile", (Class<?>[])new Class[0]).invoke(v1, new Object[0]);
        final String v4 = (String)v2.getMethod("getName", (Class<?>[])new Class[0]).invoke(v1, new Object[0]);
        java.io.File v5 = new java.io.File(v3.getParentFile(), v4);
        if (!v5.exists() || !v5.canRead()) {
            v5 = v3;
        }
        return v5.isDirectory() ? new SystemDir(v5) : new ZipDir(new JarFile(v5));
    }
}