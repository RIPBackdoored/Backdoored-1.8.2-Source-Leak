package org.reflections.util;

import org.reflections.*;
import javax.servlet.*;
import java.util.jar.*;
import java.net.*;
import java.io.*;
import java.util.*;

public abstract class ClasspathHelper
{
    public ClasspathHelper() {
        super();
    }
    
    public static ClassLoader contextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    public static ClassLoader staticClassLoader() {
        return Reflections.class.getClassLoader();
    }
    
    public static ClassLoader[] classLoaders(final ClassLoader... v-1) {
        if (v-1 != null && v-1.length != 0) {
            return v-1;
        }
        final ClassLoader a1 = contextClassLoader();
        final ClassLoader v1 = staticClassLoader();
        return (a1 != null) ? ((v1 != null && a1 != v1) ? new ClassLoader[] { a1, v1 } : new ClassLoader[] { a1 }) : new ClassLoader[0];
    }
    
    public static Collection<URL> forPackage(final String a1, final ClassLoader... a2) {
        return forResource(resourceName(a1), a2);
    }
    
    public static Collection<URL> forResource(final String v-7, final ClassLoader... v-6) {
        final List<URL> v4 = new ArrayList<URL>();
        final ClassLoader[] classLoaders;
        final ClassLoader[] array = classLoaders = classLoaders(v-6);
        for (final ClassLoader v0 : classLoaders) {
            try {
                final Enumeration<URL> v2 = v0.getResources(v-7);
                while (v2.hasMoreElements()) {
                    final URL a1 = v2.nextElement();
                    final int a2 = a1.toExternalForm().lastIndexOf(v-7);
                    if (a2 != -1) {
                        v4.add(new URL(a1, a1.toExternalForm().substring(0, a2)));
                    }
                    else {
                        v4.add(a1);
                    }
                }
            }
            catch (IOException v3) {
                if (Reflections.log != null) {
                    Reflections.log.error("error getting resources for " + v-7, v3);
                }
            }
        }
        return distinctUrls(v4);
    }
    
    public static URL forClass(final Class<?> v-7, final ClassLoader... v-6) {
        final ClassLoader[] classLoaders = classLoaders(v-6);
        final String string = v-7.getName().replace(".", "/") + ".class";
        for (final ClassLoader v0 : classLoaders) {
            try {
                final URL a2 = v0.getResource(string);
                if (a2 != null) {
                    final String a3 = a2.toExternalForm().substring(0, a2.toExternalForm().lastIndexOf(v-7.getPackage().getName().replace(".", "/")));
                    return new URL(a3);
                }
            }
            catch (MalformedURLException v2) {
                if (Reflections.log != null) {
                    Reflections.log.warn("Could not get URL", v2);
                }
            }
        }
        return null;
    }
    
    public static Collection<URL> forClassLoader() {
        return forClassLoader(classLoaders(new ClassLoader[0]));
    }
    
    public static Collection<URL> forClassLoader(final ClassLoader... v-5) {
        final Collection<URL> v2 = new ArrayList<URL>();
        final ClassLoader[] classLoaders;
        final ClassLoader[] array = classLoaders = classLoaders(v-5);
        for (ClassLoader v1 : classLoaders) {
            while (v1 != null) {
                if (v1 instanceof URLClassLoader) {
                    final URL[] a1 = ((URLClassLoader)v1).getURLs();
                    if (a1 != null) {
                        v2.addAll(Arrays.asList(a1));
                    }
                }
                v1 = v1.getParent();
            }
        }
        return distinctUrls(v2);
    }
    
    public static Collection<URL> forJavaClassPath() {
        final Collection<URL> v3 = new ArrayList<URL>();
        final String property = System.getProperty("java.class.path");
        if (property != null) {
            for (final String v0 : property.split(File.pathSeparator)) {
                try {
                    v3.add(new File(v0).toURI().toURL());
                }
                catch (Exception v2) {
                    if (Reflections.log != null) {
                        Reflections.log.warn("Could not get URL", v2);
                    }
                }
            }
        }
        return distinctUrls(v3);
    }
    
    public static Collection<URL> forWebInfLib(final ServletContext v1) {
        final Collection<URL> v2 = new ArrayList<URL>();
        final Set<?> v3 = (Set<?>)v1.getResourcePaths("/WEB-INF/lib");
        if (v3 == null) {
            return v2;
        }
        for (final Object a1 : v3) {
            try {
                v2.add(v1.getResource((String)a1));
            }
            catch (MalformedURLException ex) {}
        }
        return distinctUrls(v2);
    }
    
    public static URL forWebInfClasses(final ServletContext v0) {
        try {
            final String v = v0.getRealPath("/WEB-INF/classes");
            if (v == null) {
                return v0.getResource("/WEB-INF/classes");
            }
            final File a1 = new File(v);
            if (a1.exists()) {
                return a1.toURL();
            }
        }
        catch (MalformedURLException ex) {}
        return null;
    }
    
    public static Collection<URL> forManifest() {
        return forManifest(forClassLoader());
    }
    
    public static Collection<URL> forManifest(final URL v-6) {
        final Collection<URL> v3 = new ArrayList<URL>();
        v3.add(v-6);
        try {
            final String cleanPath = cleanPath(v-6);
            final File file = new File(cleanPath);
            final JarFile jarFile = new JarFile(cleanPath);
            URL url = tryToGetValidUrl(file.getPath(), new File(cleanPath).getParent(), cleanPath);
            if (url != null) {
                v3.add(url);
            }
            final Manifest v0 = jarFile.getManifest();
            if (v0 != null) {
                final String v2 = v0.getMainAttributes().getValue(new Attributes.Name("Class-Path"));
                if (v2 != null) {
                    for (final String a1 : v2.split(" ")) {
                        url = tryToGetValidUrl(file.getPath(), new File(cleanPath).getParent(), a1);
                        if (url != null) {
                            v3.add(url);
                        }
                    }
                }
            }
        }
        catch (IOException ex) {}
        return distinctUrls(v3);
    }
    
    public static Collection<URL> forManifest(final Iterable<URL> v1) {
        final Collection<URL> v2 = new ArrayList<URL>();
        for (final URL a1 : v1) {
            v2.addAll(forManifest(a1));
        }
        return distinctUrls(v2);
    }
    
    static URL tryToGetValidUrl(final String a1, final String a2, final String a3) {
        try {
            if (new File(a3).exists()) {
                return new File(a3).toURI().toURL();
            }
            if (new File(a2 + File.separator + a3).exists()) {
                return new File(a2 + File.separator + a3).toURI().toURL();
            }
            if (new File(a1 + File.separator + a3).exists()) {
                return new File(a1 + File.separator + a3).toURI().toURL();
            }
            if (new File(new URL(a3).getFile()).exists()) {
                return new File(new URL(a3).getFile()).toURI().toURL();
            }
        }
        catch (MalformedURLException ex) {}
        return null;
    }
    
    public static String cleanPath(final URL a1) {
        String v1 = a1.getPath();
        try {
            v1 = URLDecoder.decode(v1, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {}
        if (v1.startsWith("jar:")) {
            v1 = v1.substring("jar:".length());
        }
        if (v1.startsWith("file:")) {
            v1 = v1.substring("file:".length());
        }
        if (v1.endsWith("!/")) {
            v1 = v1.substring(0, v1.lastIndexOf("!/")) + "/";
        }
        return v1;
    }
    
    private static String resourceName(final String v1) {
        if (v1 != null) {
            String a1 = v1.replace(".", "/");
            a1 = a1.replace("\\", "/");
            if (a1.startsWith("/")) {
                a1 = a1.substring(1);
            }
            return a1;
        }
        return null;
    }
    
    private static Collection<URL> distinctUrls(final Collection<URL> v1) {
        final Map<String, URL> v2 = new LinkedHashMap<String, URL>(v1.size());
        for (final URL a1 : v1) {
            v2.put(a1.toExternalForm(), a1);
        }
        return v2.values();
    }
}
