package org.reflections.vfs;

import java.net.*;
import org.reflections.util.*;

enum Vfs$DefaultUrlTypes$6
{
    Vfs$DefaultUrlTypes$6(final String a1, final int a2) {
    }
    
    @Override
    public boolean matches(final URL a1) throws Exception {
        return a1.getProtocol().startsWith("bundle");
    }
    
    @Override
    public Dir createDir(final URL a1) throws Exception {
        return Vfs.fromURL((URL)ClasspathHelper.contextClassLoader().loadClass("org.eclipse.core.runtime.FileLocator").getMethod("resolve", URL.class).invoke(null, a1));
    }
}