package javassist;

import java.net.*;
import java.io.*;

final class DirClassPath implements ClassPath
{
    String directory;
    
    DirClassPath(final String a1) {
        super();
        this.directory = a1;
    }
    
    @Override
    public InputStream openClassfile(final String v-1) {
        try {
            final char a1 = File.separatorChar;
            final String v1 = this.directory + a1 + v-1.replace('.', a1) + ".class";
            return new FileInputStream(v1.toString());
        }
        catch (FileNotFoundException ex) {}
        catch (SecurityException ex2) {}
        return null;
    }
    
    @Override
    public URL find(final String a1) {
        final char v1 = File.separatorChar;
        final String v2 = this.directory + v1 + a1.replace('.', v1) + ".class";
        final File v3 = new File(v2);
        if (v3.exists()) {
            try {
                return v3.getCanonicalFile().toURI().toURL();
            }
            catch (MalformedURLException ex) {}
            catch (IOException ex2) {}
        }
        return null;
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public String toString() {
        return this.directory;
    }
}
