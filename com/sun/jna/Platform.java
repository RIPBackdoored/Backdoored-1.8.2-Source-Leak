package com.sun.jna;

import java.util.logging.*;
import java.io.*;

public final class Platform
{
    public static final int UNSPECIFIED = -1;
    public static final int MAC = 0;
    public static final int LINUX = 1;
    public static final int WINDOWS = 2;
    public static final int SOLARIS = 3;
    public static final int FREEBSD = 4;
    public static final int OPENBSD = 5;
    public static final int WINDOWSCE = 6;
    public static final int AIX = 7;
    public static final int ANDROID = 8;
    public static final int GNU = 9;
    public static final int KFREEBSD = 10;
    public static final int NETBSD = 11;
    public static final boolean RO_FIELDS;
    public static final boolean HAS_BUFFERS;
    public static final boolean HAS_AWT;
    public static final boolean HAS_JAWT;
    public static final String MATH_LIBRARY_NAME;
    public static final String C_LIBRARY_NAME;
    public static final boolean HAS_DLL_CALLBACKS;
    public static final String RESOURCE_PREFIX;
    private static final int osType;
    public static final String ARCH;
    
    private Platform() {
        super();
    }
    
    public static final int getOSType() {
        return Platform.osType;
    }
    
    public static final boolean isMac() {
        return Platform.osType == 0;
    }
    
    public static final boolean isAndroid() {
        return Platform.osType == 8;
    }
    
    public static final boolean isLinux() {
        return Platform.osType == 1;
    }
    
    public static final boolean isAIX() {
        return Platform.osType == 7;
    }
    
    @Deprecated
    public static final boolean isAix() {
        return isAIX();
    }
    
    public static final boolean isWindowsCE() {
        return Platform.osType == 6;
    }
    
    public static final boolean isWindows() {
        return Platform.osType == 2 || Platform.osType == 6;
    }
    
    public static final boolean isSolaris() {
        return Platform.osType == 3;
    }
    
    public static final boolean isFreeBSD() {
        return Platform.osType == 4;
    }
    
    public static final boolean isOpenBSD() {
        return Platform.osType == 5;
    }
    
    public static final boolean isNetBSD() {
        return Platform.osType == 11;
    }
    
    public static final boolean isGNU() {
        return Platform.osType == 9;
    }
    
    public static final boolean iskFreeBSD() {
        return Platform.osType == 10;
    }
    
    public static final boolean isX11() {
        return !isWindows() && !isMac();
    }
    
    public static final boolean hasRuntimeExec() {
        return !isWindowsCE() || !"J9".equals(System.getProperty("java.vm.name"));
    }
    
    public static final boolean is64Bit() {
        final String v1 = System.getProperty("sun.arch.data.model", System.getProperty("com.ibm.vm.bitmode"));
        if (v1 != null) {
            return "64".equals(v1);
        }
        return "x86-64".equals(Platform.ARCH) || "ia64".equals(Platform.ARCH) || "ppc64".equals(Platform.ARCH) || "ppc64le".equals(Platform.ARCH) || "sparcv9".equals(Platform.ARCH) || "amd64".equals(Platform.ARCH) || Native.POINTER_SIZE == 8;
    }
    
    public static final boolean isIntel() {
        return Platform.ARCH.startsWith("x86");
    }
    
    public static final boolean isPPC() {
        return Platform.ARCH.startsWith("ppc");
    }
    
    public static final boolean isARM() {
        return Platform.ARCH.startsWith("arm");
    }
    
    public static final boolean isSPARC() {
        return Platform.ARCH.startsWith("sparc");
    }
    
    static String getCanonicalArchitecture(String a1, final boolean a2) {
        a1 = a1.toLowerCase().trim();
        if ("powerpc".equals(a1)) {
            a1 = "ppc";
        }
        else if ("powerpc64".equals(a1)) {
            a1 = "ppc64";
        }
        else if ("i386".equals(a1) || "i686".equals(a1)) {
            a1 = "x86";
        }
        else if ("x86_64".equals(a1) || "amd64".equals(a1)) {
            a1 = "x86-64";
        }
        if ("ppc64".equals(a1) && "little".equals(System.getProperty("sun.cpu.endian"))) {
            a1 = "ppc64le";
        }
        if ("arm".equals(a1) && a2) {
            a1 = "armel";
        }
        return a1;
    }
    
    private static boolean isSoftFloat() {
        try {
            final File v1 = new File("/proc/self/exe");
            final ELFAnalyser v2 = ELFAnalyser.analyse(v1.getCanonicalPath());
            return v2.isArmSoftFloat();
        }
        catch (IOException v3) {
            Logger.getLogger(Platform.class.getName()).log(Level.FINE, null, v3);
            return false;
        }
    }
    
    static String getNativeLibraryResourcePrefix() {
        final String v1 = System.getProperty("jna.prefix");
        if (v1 != null) {
            return v1;
        }
        return getNativeLibraryResourcePrefix(getOSType(), System.getProperty("os.arch"), System.getProperty("os.name"));
    }
    
    static String getNativeLibraryResourcePrefix(final int a1, final String a2, final String a3) {
        return getNativeLibraryResourcePrefix(a1, a2, a3, isSoftFloat());
    }
    
    static String getNativeLibraryResourcePrefix(final int v-3, String v-2, final String v-1, final boolean v0) {
        v-2 = getCanonicalArchitecture(v-2, v0);
        String v = null;
        switch (v-3) {
            case 8: {
                if (v-2.startsWith("arm")) {
                    v-2 = "arm";
                }
                final String a1 = "android-" + v-2;
                break;
            }
            case 2: {
                final String a2 = "win32-" + v-2;
                break;
            }
            case 6: {
                final String a3 = "w32ce-" + v-2;
                break;
            }
            case 0: {
                final String a4 = "darwin";
                break;
            }
            case 1: {
                v = "linux-" + v-2;
                break;
            }
            case 3: {
                v = "sunos-" + v-2;
                break;
            }
            case 4: {
                v = "freebsd-" + v-2;
                break;
            }
            case 5: {
                v = "openbsd-" + v-2;
                break;
            }
            case 11: {
                v = "netbsd-" + v-2;
                break;
            }
            case 10: {
                v = "kfreebsd-" + v-2;
                break;
            }
            default: {
                v = v-1.toLowerCase();
                final int v2 = v.indexOf(" ");
                if (v2 != -1) {
                    v = v.substring(0, v2);
                }
                v = v + "-" + v-2;
                break;
            }
        }
        return v;
    }
    
    static {
        final String v1 = System.getProperty("os.name");
        if (v1.startsWith("Linux")) {
            if ("dalvik".equals(System.getProperty("java.vm.name").toLowerCase())) {
                osType = 8;
                System.setProperty("jna.nounpack", "true");
            }
            else {
                osType = 1;
            }
        }
        else if (v1.startsWith("AIX")) {
            osType = 7;
        }
        else if (v1.startsWith("Mac") || v1.startsWith("Darwin")) {
            osType = 0;
        }
        else if (v1.startsWith("Windows CE")) {
            osType = 6;
        }
        else if (v1.startsWith("Windows")) {
            osType = 2;
        }
        else if (v1.startsWith("Solaris") || v1.startsWith("SunOS")) {
            osType = 3;
        }
        else if (v1.startsWith("FreeBSD")) {
            osType = 4;
        }
        else if (v1.startsWith("OpenBSD")) {
            osType = 5;
        }
        else if (v1.equalsIgnoreCase("gnu")) {
            osType = 9;
        }
        else if (v1.equalsIgnoreCase("gnu/kfreebsd")) {
            osType = 10;
        }
        else if (v1.equalsIgnoreCase("netbsd")) {
            osType = 11;
        }
        else {
            osType = -1;
        }
        boolean v2 = false;
        try {
            Class.forName("java.nio.Buffer");
            v2 = true;
        }
        catch (ClassNotFoundException ex) {}
        HAS_AWT = (Platform.osType != 6 && Platform.osType != 8 && Platform.osType != 7);
        HAS_JAWT = (Platform.HAS_AWT && Platform.osType != 0);
        HAS_BUFFERS = v2;
        RO_FIELDS = (Platform.osType != 6);
        C_LIBRARY_NAME = ((Platform.osType == 2) ? "msvcrt" : ((Platform.osType == 6) ? "coredll" : "c"));
        MATH_LIBRARY_NAME = ((Platform.osType == 2) ? "msvcrt" : ((Platform.osType == 6) ? "coredll" : "m"));
        HAS_DLL_CALLBACKS = (Platform.osType == 2);
        ARCH = getCanonicalArchitecture(System.getProperty("os.arch"), isSoftFloat());
        RESOURCE_PREFIX = getNativeLibraryResourcePrefix();
    }
}
