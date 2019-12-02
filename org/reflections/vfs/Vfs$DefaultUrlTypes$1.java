package org.reflections.vfs;

import java.net.*;
import java.util.jar.*;

enum Vfs$DefaultUrlTypes$1
{
    Vfs$DefaultUrlTypes$1(final String a1, final int a2) {
    }
    
    @Override
    public boolean matches(final URL a1) {
        return a1.getProtocol().equals("file") && Vfs.access$100(a1);
    }
    
    @Override
    public Dir createDir(final URL a1) throws Exception {
        return new ZipDir(new JarFile(Vfs.getFile(a1)));
    }
}