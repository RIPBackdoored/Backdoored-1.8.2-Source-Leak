package org.reflections.vfs;

import java.net.*;
import java.io.*;

enum Vfs$DefaultUrlTypes$3
{
    Vfs$DefaultUrlTypes$3(final String a1, final int a2) {
    }
    
    @Override
    public boolean matches(final URL v2) {
        if (v2.getProtocol().equals("file") && !Vfs.access$100(v2)) {
            final java.io.File a1 = Vfs.getFile(v2);
            return a1 != null && a1.isDirectory();
        }
        return false;
    }
    
    @Override
    public Dir createDir(final URL a1) throws Exception {
        return new SystemDir(Vfs.getFile(a1));
    }
}