package javassist;

import java.io.*;
import java.net.*;

final class JarDirClassPath implements ClassPath
{
    JarClassPath[] jars;
    
    JarDirClassPath(final String v2) throws NotFoundException {
        super();
        final File[] v3 = new File(v2).listFiles(new FilenameFilter() {
            final /* synthetic */ JarDirClassPath this$0;
            
            JarDirClassPath$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public boolean accept(final File a1, String a2) {
                a2 = a2.toLowerCase();
                return a2.endsWith(".jar") || a2.endsWith(".zip");
            }
        });
        if (v3 != null) {
            this.jars = new JarClassPath[v3.length];
            for (int a1 = 0; a1 < v3.length; ++a1) {
                this.jars[a1] = new JarClassPath(v3[a1].getPath());
            }
        }
    }
    
    @Override
    public InputStream openClassfile(final String v0) throws NotFoundException {
        if (this.jars != null) {
            for (int v = 0; v < this.jars.length; ++v) {
                final InputStream a1 = this.jars[v].openClassfile(v0);
                if (a1 != null) {
                    return a1;
                }
            }
        }
        return null;
    }
    
    @Override
    public URL find(final String v0) {
        if (this.jars != null) {
            for (int v = 0; v < this.jars.length; ++v) {
                final URL a1 = this.jars[v].find(v0);
                if (a1 != null) {
                    return a1;
                }
            }
        }
        return null;
    }
    
    @Override
    public void close() {
        if (this.jars != null) {
            for (int v1 = 0; v1 < this.jars.length; ++v1) {
                this.jars[v1].close();
            }
        }
    }
}
