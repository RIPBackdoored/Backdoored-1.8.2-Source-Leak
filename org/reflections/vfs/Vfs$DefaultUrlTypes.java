package org.reflections.vfs;

import java.util.jar.*;
import java.net.*;
import java.io.*;
import org.reflections.util.*;

public enum DefaultUrlTypes implements UrlType
{
    jarFile {
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
    }, 
    jarUrl {
        Vfs$DefaultUrlTypes$2(final String a1, final int a2) {
        }
        
        @Override
        public boolean matches(final URL a1) {
            return "jar".equals(a1.getProtocol()) || "zip".equals(a1.getProtocol()) || "wsjar".equals(a1.getProtocol());
        }
        
        @Override
        public Dir createDir(final URL v2) throws Exception {
            try {
                final URLConnection a1 = v2.openConnection();
                if (a1 instanceof JarURLConnection) {
                    return new ZipDir(((JarURLConnection)a1).getJarFile());
                }
            }
            catch (Throwable t) {}
            final java.io.File v3 = Vfs.getFile(v2);
            if (v3 != null) {
                return new ZipDir(new JarFile(v3));
            }
            return null;
        }
    }, 
    directory {
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
    }, 
    jboss_vfs {
        Vfs$DefaultUrlTypes$4(final String a1, final int a2) {
        }
        
        @Override
        public boolean matches(final URL a1) {
            return a1.getProtocol().equals("vfs");
        }
        
        @Override
        public Dir createDir(final URL a1) throws Exception {
            final Object v1 = a1.openConnection().getContent();
            final Class<?> v2 = ClasspathHelper.contextClassLoader().loadClass("org.jboss.vfs.VirtualFile");
            final java.io.File v3 = (java.io.File)v2.getMethod("getPhysicalFile", (Class<?>[])new Class[0]).invoke(v1, new Object[0]);
            final String v4 = (String)v2.getMethod("getName", (Class<?>[])new Class[0]).invoke(v1, new Object[0]);
            java.io.File v5 = new java.io.File(v3.getParentFile(), v4);
            if (!v5.exists() || !v5.canRead()) {
                v5 = v3;
            }
            return v5.isDirectory() ? new SystemDir(v5) : new ZipDir(new JarFile(v5));
        }
    }, 
    jboss_vfsfile {
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
    }, 
    bundle {
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
    }, 
    jarInputStream {
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
    };
    
    private static final /* synthetic */ DefaultUrlTypes[] $VALUES;
    
    public static DefaultUrlTypes[] values() {
        return DefaultUrlTypes.$VALUES.clone();
    }
    
    public static DefaultUrlTypes valueOf(final String a1) {
        return Enum.valueOf(DefaultUrlTypes.class, a1);
    }
    
    DefaultUrlTypes(final String a1, final int a2, final Vfs$1 a3) {
        this();
    }
    
    static {
        $VALUES = new DefaultUrlTypes[] { DefaultUrlTypes.jarFile, DefaultUrlTypes.jarUrl, DefaultUrlTypes.directory, DefaultUrlTypes.jboss_vfs, DefaultUrlTypes.jboss_vfsfile, DefaultUrlTypes.bundle, DefaultUrlTypes.jarInputStream };
    }
}
