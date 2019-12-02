package org.reflections.vfs;

import org.reflections.*;
import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;
import javax.annotation.*;
import java.util.jar.*;
import java.net.*;
import org.reflections.util.*;
import java.io.*;

public abstract class Vfs
{
    private static List<UrlType> defaultUrlTypes;
    
    public Vfs() {
        super();
    }
    
    public static List<UrlType> getDefaultUrlTypes() {
        return Vfs.defaultUrlTypes;
    }
    
    public static void setDefaultURLTypes(final List<UrlType> a1) {
        Vfs.defaultUrlTypes = a1;
    }
    
    public static void addDefaultURLTypes(final UrlType a1) {
        Vfs.defaultUrlTypes.add(0, a1);
    }
    
    public static Dir fromURL(final URL a1) {
        return fromURL(a1, Vfs.defaultUrlTypes);
    }
    
    public static Dir fromURL(final URL v-2, final List<UrlType> v-1) {
        for (final UrlType v1 : v-1) {
            try {
                if (!v1.matches(v-2)) {
                    continue;
                }
                final Dir a1 = v1.createDir(v-2);
                if (a1 != null) {
                    return a1;
                }
                continue;
            }
            catch (Throwable a2) {
                if (Reflections.log == null) {
                    continue;
                }
                Reflections.log.warn("could not create Dir using " + v1 + " from url " + v-2.toExternalForm() + ". skipping.", a2);
            }
        }
        throw new ReflectionsException("could not create Vfs.Dir from url, no matching UrlType was found [" + v-2.toExternalForm() + "]\neither use fromURL(final URL url, final List<UrlType> urlTypes) or use the static setDefaultURLTypes(final List<UrlType> urlTypes) or addDefaultURLTypes(UrlType urlType) with your specialized UrlType.");
    }
    
    public static Dir fromURL(final URL a1, final UrlType... a2) {
        return fromURL(a1, Lists.newArrayList(a2));
    }
    
    public static Iterable<File> findFiles(final Collection<URL> a1, final String a2, final Predicate<String> a3) {
        final Predicate<File> v1 = new Predicate<File>() {
            final /* synthetic */ String val$packagePrefix;
            final /* synthetic */ Predicate val$nameFilter;
            
            Vfs$1() {
                super();
            }
            
            @Override
            public boolean apply(final File v2) {
                final String v3 = v2.getRelativePath();
                if (v3.startsWith(a2)) {
                    final String a1 = v3.substring(v3.indexOf(a2) + a2.length());
                    return !Utils.isEmpty(a1) && a3.apply(a1.substring(1));
                }
                return false;
            }
            
            @Override
            public /* bridge */ boolean apply(final Object o) {
                return this.apply((File)o);
            }
        };
        return findFiles(a1, v1);
    }
    
    public static Iterable<File> findFiles(final Collection<URL> v1, final Predicate<File> v2) {
        Iterable<File> v3 = new ArrayList<File>();
        for (final URL a2 : v1) {
            try {
                v3 = Iterables.concat((Iterable<? extends File>)v3, (Iterable<? extends File>)Iterables.filter((Iterable<? extends T>)new Iterable<File>() {
                    final /* synthetic */ URL val$url;
                    
                    Vfs$2() {
                        super();
                    }
                    
                    @Override
                    public Iterator<File> iterator() {
                        return Vfs.fromURL(a2).getFiles().iterator();
                    }
                }, (Predicate<? super T>)v2));
            }
            catch (Throwable a3) {
                if (Reflections.log == null) {
                    continue;
                }
                Reflections.log.error("could not findFiles for url. continuing. [" + a2 + "]", a3);
            }
        }
        return v3;
    }
    
    @Nullable
    public static java.io.File getFile(final URL v-1) {
        try {
            final String v1 = v-1.toURI().getSchemeSpecificPart();
            final java.io.File a1;
            if ((a1 = new java.io.File(v1)).exists()) {
                return a1;
            }
        }
        catch (URISyntaxException ex) {}
        try {
            String v1 = URLDecoder.decode(v-1.getPath(), "UTF-8");
            if (v1.contains(".jar!")) {
                v1 = v1.substring(0, v1.lastIndexOf(".jar!") + ".jar".length());
            }
            final java.io.File v2;
            if ((v2 = new java.io.File(v1)).exists()) {
                return v2;
            }
        }
        catch (UnsupportedEncodingException ex2) {}
        try {
            String v1 = v-1.toExternalForm();
            if (v1.startsWith("jar:")) {
                v1 = v1.substring("jar:".length());
            }
            if (v1.startsWith("wsjar:")) {
                v1 = v1.substring("wsjar:".length());
            }
            if (v1.startsWith("file:")) {
                v1 = v1.substring("file:".length());
            }
            if (v1.contains(".jar!")) {
                v1 = v1.substring(0, v1.indexOf(".jar!") + ".jar".length());
            }
            java.io.File v2;
            if ((v2 = new java.io.File(v1)).exists()) {
                return v2;
            }
            v1 = v1.replace("%20", " ");
            if ((v2 = new java.io.File(v1)).exists()) {
                return v2;
            }
        }
        catch (Exception ex3) {}
        return null;
    }
    
    private static boolean hasJarFileInPath(final URL a1) {
        return a1.toExternalForm().matches(".*\\.jar(\\!.*|$)");
    }
    
    static /* bridge */ boolean access$100(final URL a1) {
        return hasJarFileInPath(a1);
    }
    
    static {
        Vfs.defaultUrlTypes = (List<UrlType>)Lists.newArrayList(DefaultUrlTypes.values());
    }
    
    public enum DefaultUrlTypes implements UrlType
    {
        jarFile {
            Vfs$DefaultUrlTypes$1(final String a1, final int a2) {
            }
            
            @Override
            public boolean matches(final URL a1) {
                return a1.getProtocol().equals("file") && hasJarFileInPath(a1);
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
                if (v2.getProtocol().equals("file") && !hasJarFileInPath(v2)) {
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
    
    public interface UrlType
    {
        boolean matches(final URL p0) throws Exception;
        
        Dir createDir(final URL p0) throws Exception;
    }
    
    public interface Dir
    {
        String getPath();
        
        Iterable<File> getFiles();
        
        void close();
    }
    
    public interface File
    {
        String getName();
        
        String getRelativePath();
        
        InputStream openInputStream() throws IOException;
    }
}
