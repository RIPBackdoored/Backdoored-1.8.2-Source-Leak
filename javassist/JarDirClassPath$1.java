package javassist;

import java.io.*;

class JarDirClassPath$1 implements FilenameFilter {
    final /* synthetic */ JarDirClassPath this$0;
    
    JarDirClassPath$1(final JarDirClassPath a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public boolean accept(final File a1, String a2) {
        a2 = a2.toLowerCase();
        return a2.endsWith(".jar") || a2.endsWith(".zip");
    }
}