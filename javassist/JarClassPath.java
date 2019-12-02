package javassist;

import java.io.*;
import java.util.zip.*;
import java.util.jar.*;
import java.net.*;

final class JarClassPath implements ClassPath
{
    JarFile jarfile;
    String jarfileURL;
    
    JarClassPath(final String a1) throws NotFoundException {
        super();
        try {
            this.jarfile = new JarFile(a1);
            this.jarfileURL = new File(a1).getCanonicalFile().toURI().toURL().toString();
        }
        catch (IOException ex) {
            throw new NotFoundException(a1);
        }
    }
    
    @Override
    public InputStream openClassfile(final String v-1) throws NotFoundException {
        try {
            final String a1 = v-1.replace('.', '/') + ".class";
            final JarEntry v1 = this.jarfile.getJarEntry(a1);
            if (v1 != null) {
                return this.jarfile.getInputStream(v1);
            }
            return null;
        }
        catch (IOException ex) {
            throw new NotFoundException("broken jar file?: " + this.jarfile.getName());
        }
    }
    
    @Override
    public URL find(final String a1) {
        final String v1 = a1.replace('.', '/') + ".class";
        final JarEntry v2 = this.jarfile.getJarEntry(v1);
        if (v2 != null) {
            try {
                return new URL("jar:" + this.jarfileURL + "!/" + v1);
            }
            catch (MalformedURLException ex) {}
        }
        return null;
    }
    
    @Override
    public void close() {
        try {
            this.jarfile.close();
            this.jarfile = null;
        }
        catch (IOException ex) {}
    }
    
    @Override
    public String toString() {
        return (this.jarfile == null) ? "<null>" : this.jarfile.toString();
    }
}
