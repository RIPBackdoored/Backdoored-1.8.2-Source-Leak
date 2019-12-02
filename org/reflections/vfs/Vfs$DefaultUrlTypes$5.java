package org.reflections.vfs;

import java.net.*;

enum Vfs$DefaultUrlTypes$5
{
    Vfs$DefaultUrlTypes$5(final String a1, final int a2) {
    }
    
    @Override
    public boolean matches(final URL a1) throws Exception {
        return "vfszip".equals(a1.getProtocol()) || "vfsfile".equals(a1.getProtocol());
    }
    
    @Override
    public Dir createDir(final URL a1) throws Exception {
        return new UrlTypeVFS().createDir(a1);
    }
}