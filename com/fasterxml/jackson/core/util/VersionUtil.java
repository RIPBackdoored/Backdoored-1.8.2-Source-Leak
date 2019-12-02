package com.fasterxml.jackson.core.util;

import java.util.regex.*;
import com.fasterxml.jackson.core.*;
import java.util.*;
import java.io.*;

public class VersionUtil
{
    private static final Pattern V_SEP;
    
    protected VersionUtil() {
        super();
    }
    
    @Deprecated
    public Version version() {
        return Version.unknownVersion();
    }
    
    public static Version versionFor(final Class<?> a1) {
        final Version v1 = packageVersionFor(a1);
        return (v1 == null) ? Version.unknownVersion() : v1;
    }
    
    public static Version packageVersionFor(final Class<?> v-1) {
        Version v0 = null;
        try {
            final String v2 = v-1.getPackage().getName() + ".PackageVersion";
            final Class<?> v3 = Class.forName(v2, true, v-1.getClassLoader());
            try {
                v0 = ((Versioned)v3.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0])).version();
            }
            catch (Exception a1) {
                throw new IllegalArgumentException("Failed to get Versioned out of " + v3);
            }
        }
        catch (Exception ex) {}
        return (v0 == null) ? Version.unknownVersion() : v0;
    }
    
    @Deprecated
    public static Version mavenVersionFor(final ClassLoader v-6, final String v-5, final String v-4) {
        final InputStream resourceAsStream = v-6.getResourceAsStream("META-INF/maven/" + v-5.replaceAll("\\.", "/") + "/" + v-4 + "/pom.properties");
        if (resourceAsStream != null) {
            try {
                final Properties a1 = new Properties();
                a1.load(resourceAsStream);
                final String a2 = a1.getProperty("version");
                final String a3 = a1.getProperty("artifactId");
                final String v1 = a1.getProperty("groupId");
                return parseVersion(a2, v1, a3);
            }
            catch (IOException ex) {}
            finally {
                _close(resourceAsStream);
            }
        }
        return Version.unknownVersion();
    }
    
    public static Version parseVersion(String a2, final String a3, final String v1) {
        if (a2 != null && (a2 = a2.trim()).length() > 0) {
            final String[] a4 = VersionUtil.V_SEP.split(a2);
            return new Version(parseVersionPart(a4[0]), (a4.length > 1) ? parseVersionPart(a4[1]) : 0, (a4.length > 2) ? parseVersionPart(a4[2]) : 0, (a4.length > 3) ? a4[3] : null, a3, v1);
        }
        return Version.unknownVersion();
    }
    
    protected static int parseVersionPart(final String v-1) {
        int v0 = 0;
        for (int v2 = 0, v3 = v-1.length(); v2 < v3; ++v2) {
            final char a1 = v-1.charAt(v2);
            if (a1 > '9') {
                break;
            }
            if (a1 < '0') {
                break;
            }
            v0 = v0 * 10 + (a1 - '0');
        }
        return v0;
    }
    
    private static final void _close(final Closeable v1) {
        try {
            v1.close();
        }
        catch (IOException ex) {}
    }
    
    public static final void throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }
    
    static {
        V_SEP = Pattern.compile("[-_./;:]");
    }
}
