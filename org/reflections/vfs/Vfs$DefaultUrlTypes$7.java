package org.reflections.vfs;

import java.net.*;

enum Vfs$DefaultUrlTypes$7
{
    Vfs$DefaultUrlTypes$7(final String a1, final int a2) {
    }
    
    @Override
    public boolean matches(final URL a1) throws Exception {
        return a1.toExternalForm().contains(".jar");
    }
    
    @Override
    public Dir createDir(final URL a1) throws Exception {
        return new JarInputDir(a1);
    }
}