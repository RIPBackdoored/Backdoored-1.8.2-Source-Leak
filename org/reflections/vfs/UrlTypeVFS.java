package org.reflections.vfs;

import com.google.common.base.*;
import java.util.jar.*;
import java.io.*;
import java.net.*;
import org.reflections.*;
import java.util.regex.*;

public class UrlTypeVFS implements Vfs.UrlType
{
    public static final String[] REPLACE_EXTENSION;
    final String VFSZIP = "vfszip";
    final String VFSFILE = "vfsfile";
    Predicate<java.io.File> realFile;
    
    public UrlTypeVFS() {
        super();
        this.realFile = new Predicate<java.io.File>() {
            final /* synthetic */ UrlTypeVFS this$0;
            
            UrlTypeVFS$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            public boolean apply(final java.io.File a1) {
                return a1.exists() && a1.isFile();
            }
            
            @Override
            public /* bridge */ boolean apply(final Object o) {
                return this.apply((java.io.File)o);
            }
        };
    }
    
    @Override
    public boolean matches(final URL a1) {
        return "vfszip".equals(a1.getProtocol()) || "vfsfile".equals(a1.getProtocol());
    }
    
    @Override
    public Vfs.Dir createDir(final URL v-1) {
        try {
            final URL a1 = this.adaptURL(v-1);
            return new ZipDir(new JarFile(a1.getFile()));
        }
        catch (Exception v0) {
            try {
                return new ZipDir(new JarFile(v-1.getFile()));
            }
            catch (IOException v2) {
                if (Reflections.log != null) {
                    Reflections.log.warn("Could not get URL", v0);
                    Reflections.log.warn("Could not get URL", v2);
                }
                return null;
            }
        }
    }
    
    public URL adaptURL(final URL a1) throws MalformedURLException {
        if ("vfszip".equals(a1.getProtocol())) {
            return this.replaceZipSeparators(a1.getPath(), this.realFile);
        }
        if ("vfsfile".equals(a1.getProtocol())) {
            return new URL(a1.toString().replace("vfsfile", "file"));
        }
        return a1;
    }
    
    URL replaceZipSeparators(final String v1, final Predicate<java.io.File> v2) throws MalformedURLException {
        int v3 = 0;
        while (v3 != -1) {
            v3 = this.findFirstMatchOfDeployableExtention(v1, v3);
            if (v3 > 0) {
                final java.io.File a1 = new java.io.File(v1.substring(0, v3 - 1));
                if (v2.apply(a1)) {
                    return this.replaceZipSeparatorStartingFrom(v1, v3);
                }
                continue;
            }
        }
        throw new ReflectionsException("Unable to identify the real zip file in path '" + v1 + "'.");
    }
    
    int findFirstMatchOfDeployableExtention(final String a1, final int a2) {
        final Pattern v1 = Pattern.compile("\\.[ejprw]ar/");
        final Matcher v2 = v1.matcher(a1);
        if (v2.find(a2)) {
            return v2.end();
        }
        return -1;
    }
    
    URL replaceZipSeparatorStartingFrom(final String v2, final int v3) throws MalformedURLException {
        final String v4 = v2.substring(0, v3 - 1);
        String v5 = v2.substring(v3);
        int v6 = 1;
        for (final String a1 : UrlTypeVFS.REPLACE_EXTENSION) {
            while (v5.contains(a1)) {
                v5 = v5.replace(a1, a1.substring(0, 4) + "!");
                ++v6;
            }
        }
        String v7 = "";
        for (int a2 = 0; a2 < v6; ++a2) {
            v7 += "zip:";
        }
        if (v5.trim().length() == 0) {
            return new URL(v7 + "/" + v4);
        }
        return new URL(v7 + "/" + v4 + "!" + v5);
    }
    
    static {
        REPLACE_EXTENSION = new String[] { ".ear/", ".jar/", ".war/", ".sar/", ".har/", ".par/" };
    }
}
