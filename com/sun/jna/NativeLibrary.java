package com.sun.jna;

import java.lang.reflect.*;
import java.lang.ref.*;
import java.util.*;
import java.io.*;

public class NativeLibrary
{
    private long handle;
    private final String libraryName;
    private final String libraryPath;
    private final Map<String, Function> functions;
    final int callFlags;
    private String encoding;
    final Map<String, ?> options;
    private static final Map<String, Reference<NativeLibrary>> libraries;
    private static final Map<String, List<String>> searchPaths;
    private static final List<String> librarySearchPath;
    private static final int DEFAULT_OPEN_OPTIONS = -1;
    
    private static String functionKey(final String a1, final int a2, final String a3) {
        return a1 + "|" + a2 + "|" + a3;
    }
    
    private NativeLibrary(final String a3, final String a4, final long v1, final Map<String, ?> v3) {
        super();
        this.functions = new HashMap<String, Function>();
        this.libraryName = this.getLibraryName(a3);
        this.libraryPath = a4;
        this.handle = v1;
        final Object v4 = v3.get("calling-convention");
        final int v5 = (v4 instanceof Number) ? ((Number)v4).intValue() : 0;
        this.callFlags = v5;
        this.options = v3;
        this.encoding = (String)v3.get("string-encoding");
        if (this.encoding == null) {
            this.encoding = Native.getDefaultStringEncoding();
        }
        if (Platform.isWindows() && "kernel32".equals(this.libraryName.toLowerCase())) {
            synchronized (this.functions) {
                final Function a5 = new Function(this, "GetLastError", 63, this.encoding) {
                    final /* synthetic */ NativeLibrary this$0;
                    
                    NativeLibrary$1(final NativeLibrary a2, final String a3, final int a4, final String a5) {
                        this.this$0 = a1;
                        super(a2, a3, a4, a5);
                    }
                    
                    @Override
                    Object invoke(final Object[] a1, final Class<?> a2, final boolean a3, final int a4) {
                        return Native.getLastError();
                    }
                    
                    @Override
                    Object invoke(final Method a1, final Class<?>[] a2, final Class<?> a3, final Object[] a4, final Map<String, ?> a5) {
                        return Native.getLastError();
                    }
                };
                this.functions.put(functionKey("GetLastError", this.callFlags, this.encoding), a5);
            }
        }
    }
    
    private static int openFlags(final Map<String, ?> a1) {
        final Object v1 = a1.get("open-flags");
        if (v1 instanceof Number) {
            return ((Number)v1).intValue();
        }
        return -1;
    }
    
    private static NativeLibrary loadLibrary(final String v-10, final Map<String, ?> v-9) {
        if (Native.DEBUG_LOAD) {
            System.out.println("Looking for library '" + v-10 + "'");
        }
        final boolean absolute = new File(v-10).isAbsolute();
        final List<String> list = new ArrayList<String>();
        final int openFlags = openFlags(v-9);
        final String webStartLibraryPath = Native.getWebStartLibraryPath(v-10);
        if (webStartLibraryPath != null) {
            if (Native.DEBUG_LOAD) {
                System.out.println("Adding web start path " + webStartLibraryPath);
            }
            list.add(webStartLibraryPath);
        }
        final List<String> list2 = NativeLibrary.searchPaths.get(v-10);
        if (list2 != null) {
            synchronized (list2) {
                list.addAll(0, list2);
            }
        }
        if (Native.DEBUG_LOAD) {
            System.out.println("Adding paths from jna.library.path: " + System.getProperty("jna.library.path"));
        }
        list.addAll(initPaths("jna.library.path"));
        String a4 = findLibraryPath(v-10, list);
        long v5 = 0L;
        try {
            if (Native.DEBUG_LOAD) {
                System.out.println("Trying " + a4);
            }
            v5 = Native.open(a4, openFlags);
        }
        catch (UnsatisfiedLinkError a3) {
            if (Native.DEBUG_LOAD) {
                System.out.println("Adding system paths: " + NativeLibrary.librarySearchPath);
            }
            list.addAll(NativeLibrary.librarySearchPath);
        }
        try {
            if (v5 == 0L) {
                a4 = findLibraryPath(v-10, list);
                if (Native.DEBUG_LOAD) {
                    System.out.println("Trying " + a4);
                }
                v5 = Native.open(a4, openFlags);
                if (v5 == 0L) {
                    throw new UnsatisfiedLinkError("Failed to load library '" + v-10 + "'");
                }
            }
        }
        catch (UnsatisfiedLinkError v0) {
            if (Platform.isAndroid()) {
                try {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Preload (via System.loadLibrary) " + v-10);
                    }
                    System.loadLibrary(v-10);
                    v5 = Native.open(a4, openFlags);
                }
                catch (UnsatisfiedLinkError a2) {
                    v0 = a2;
                }
            }
            else if (Platform.isLinux() || Platform.isFreeBSD()) {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Looking for version variants");
                }
                a4 = matchLibrary(v-10, list);
                if (a4 != null) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Trying " + a4);
                    }
                    try {
                        v5 = Native.open(a4, openFlags);
                    }
                    catch (UnsatisfiedLinkError v2) {
                        v0 = v2;
                    }
                }
            }
            else if (Platform.isMac() && !v-10.endsWith(".dylib")) {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Looking for matching frameworks");
                }
                a4 = matchFramework(v-10);
                if (a4 != null) {
                    try {
                        if (Native.DEBUG_LOAD) {
                            System.out.println("Trying " + a4);
                        }
                        v5 = Native.open(a4, openFlags);
                    }
                    catch (UnsatisfiedLinkError v2) {
                        v0 = v2;
                    }
                }
            }
            else if (Platform.isWindows() && !absolute) {
                if (Native.DEBUG_LOAD) {
                    System.out.println("Looking for lib- prefix");
                }
                a4 = findLibraryPath("lib" + v-10, list);
                if (a4 != null) {
                    if (Native.DEBUG_LOAD) {
                        System.out.println("Trying " + a4);
                    }
                    try {
                        v5 = Native.open(a4, openFlags);
                    }
                    catch (UnsatisfiedLinkError v2) {
                        v0 = v2;
                    }
                }
            }
            if (v5 == 0L) {
                try {
                    final File v3 = Native.extractFromResourcePath(v-10, (ClassLoader)v-9.get("classloader"));
                    try {
                        v5 = Native.open(v3.getAbsolutePath(), openFlags);
                        a4 = v3.getAbsolutePath();
                    }
                    finally {
                        if (Native.isUnpacked(v3)) {
                            Native.deleteLibrary(v3);
                        }
                    }
                }
                catch (IOException v4) {
                    v0 = new UnsatisfiedLinkError(v4.getMessage());
                }
            }
            if (v5 == 0L) {
                throw new UnsatisfiedLinkError("Unable to load library '" + v-10 + "': " + v0.getMessage());
            }
        }
        if (Native.DEBUG_LOAD) {
            System.out.println("Found library '" + v-10 + "' at " + a4);
        }
        return new NativeLibrary(v-10, a4, v5, v-9);
    }
    
    static String matchFramework(final String v-3) {
        File file = new File(v-3);
        if (file.isAbsolute()) {
            if (v-3.indexOf(".framework") != -1 && file.exists()) {
                return file.getAbsolutePath();
            }
            file = new File(new File(file.getParentFile(), file.getName() + ".framework"), file.getName());
            if (file.exists()) {
                return file.getAbsolutePath();
            }
        }
        else {
            final String[] array = { System.getProperty("user.home"), "", "/System" };
            final String v0 = (v-3.indexOf(".framework") == -1) ? (v-3 + ".framework/" + v-3) : v-3;
            for (int v2 = 0; v2 < array.length; ++v2) {
                final String a1 = array[v2] + "/Library/Frameworks/" + v0;
                if (new File(a1).exists()) {
                    return a1;
                }
            }
        }
        return null;
    }
    
    private String getLibraryName(final String a1) {
        String v1 = a1;
        final String v2 = "---";
        final String v3 = mapSharedLibraryName("---");
        final int v4 = v3.indexOf("---");
        if (v4 > 0 && v1.startsWith(v3.substring(0, v4))) {
            v1 = v1.substring(v4);
        }
        final String v5 = v3.substring(v4 + "---".length());
        final int v6 = v1.indexOf(v5);
        if (v6 != -1) {
            v1 = v1.substring(0, v6);
        }
        return v1;
    }
    
    public static final NativeLibrary getInstance(final String a1) {
        return getInstance(a1, Collections.emptyMap());
    }
    
    public static final NativeLibrary getInstance(final String a1, final ClassLoader a2) {
        return getInstance(a1, Collections.singletonMap("classloader", a2));
    }
    
    public static final NativeLibrary getInstance(String v-4, final Map<String, ?> v-3) {
        final Map<String, Object> v-5 = new HashMap<String, Object>(v-3);
        if (v-5.get("calling-convention") == null) {
            v-5.put("calling-convention", 0);
        }
        if ((Platform.isLinux() || Platform.isFreeBSD() || Platform.isAIX()) && Platform.C_LIBRARY_NAME.equals(v-4)) {
            v-4 = null;
        }
        synchronized (NativeLibrary.libraries) {
            Reference<NativeLibrary> a2 = NativeLibrary.libraries.get(v-4 + v-5);
            NativeLibrary v1 = (a2 != null) ? a2.get() : null;
            if (v1 == null) {
                if (v-4 == null) {
                    v1 = new NativeLibrary("<process>", null, Native.open(null, openFlags(v-5)), v-5);
                }
                else {
                    v1 = loadLibrary(v-4, v-5);
                }
                a2 = new WeakReference<NativeLibrary>(v1);
                NativeLibrary.libraries.put(v1.getName() + v-5, a2);
                final File a3 = v1.getFile();
                if (a3 != null) {
                    NativeLibrary.libraries.put(a3.getAbsolutePath() + v-5, a2);
                    NativeLibrary.libraries.put(a3.getName() + v-5, a2);
                }
            }
            return v1;
        }
    }
    
    public static final synchronized NativeLibrary getProcess() {
        return getInstance(null);
    }
    
    public static final synchronized NativeLibrary getProcess(final Map<String, ?> a1) {
        return getInstance(null, a1);
    }
    
    public static final void addSearchPath(final String a2, final String v1) {
        synchronized (NativeLibrary.searchPaths) {
            List<String> a3 = NativeLibrary.searchPaths.get(a2);
            if (a3 == null) {
                a3 = Collections.synchronizedList(new ArrayList<String>());
                NativeLibrary.searchPaths.put(a2, a3);
            }
            a3.add(v1);
        }
    }
    
    public Function getFunction(final String a1) {
        return this.getFunction(a1, this.callFlags);
    }
    
    Function getFunction(String v1, final Method v2) {
        final FunctionMapper v3 = (FunctionMapper)this.options.get("function-mapper");
        if (v3 != null) {
            v1 = v3.getFunctionName(this, v2);
        }
        final String v4 = System.getProperty("jna.profiler.prefix", "$$YJP$$");
        if (v1.startsWith(v4)) {
            v1 = v1.substring(v4.length());
        }
        int v5 = this.callFlags;
        final Class<?>[] v6 = v2.getExceptionTypes();
        for (int a1 = 0; a1 < v6.length; ++a1) {
            if (LastErrorException.class.isAssignableFrom(v6[a1])) {
                v5 |= 0x40;
            }
        }
        return this.getFunction(v1, v5);
    }
    
    public Function getFunction(final String a1, final int a2) {
        return this.getFunction(a1, a2, this.encoding);
    }
    
    public Function getFunction(final String v1, final int v2, final String v3) {
        if (v1 == null) {
            throw new NullPointerException("Function name may not be null");
        }
        synchronized (this.functions) {
            final String a1 = functionKey(v1, v2, v3);
            Function a2 = this.functions.get(a1);
            if (a2 == null) {
                a2 = new Function(this, v1, v2, v3);
                this.functions.put(a1, a2);
            }
            return a2;
        }
    }
    
    public Map<String, ?> getOptions() {
        return this.options;
    }
    
    public Pointer getGlobalVariableAddress(final String v2) {
        try {
            return new Pointer(this.getSymbolAddress(v2));
        }
        catch (UnsatisfiedLinkError a1) {
            throw new UnsatisfiedLinkError("Error looking up '" + v2 + "': " + a1.getMessage());
        }
    }
    
    long getSymbolAddress(final String a1) {
        if (this.handle == 0L) {
            throw new UnsatisfiedLinkError("Library has been unloaded");
        }
        return Native.findSymbol(this.handle, a1);
    }
    
    @Override
    public String toString() {
        return "Native Library <" + this.libraryPath + "@" + this.handle + ">";
    }
    
    public String getName() {
        return this.libraryName;
    }
    
    public File getFile() {
        if (this.libraryPath == null) {
            return null;
        }
        return new File(this.libraryPath);
    }
    
    @Override
    protected void finalize() {
        this.dispose();
    }
    
    static void disposeAll() {
        final Set<Reference<NativeLibrary>> v1;
        synchronized (NativeLibrary.libraries) {
            v1 = new LinkedHashSet<Reference<NativeLibrary>>(NativeLibrary.libraries.values());
        }
        for (final Reference<NativeLibrary> v2 : v1) {
            final NativeLibrary v3 = v2.get();
            if (v3 != null) {
                v3.dispose();
            }
        }
    }
    
    public void dispose() {
        final Set<String> set = new HashSet<String>();
        synchronized (NativeLibrary.libraries) {
            for (final Map.Entry<String, Reference<NativeLibrary>> v0 : NativeLibrary.libraries.entrySet()) {
                final Reference<NativeLibrary> v2 = v0.getValue();
                if (v2.get() == this) {
                    set.add(v0.getKey());
                }
            }
            for (final String v3 : set) {
                NativeLibrary.libraries.remove(v3);
            }
        }
        synchronized (this) {
            if (this.handle != 0L) {
                Native.close(this.handle);
                this.handle = 0L;
            }
        }
    }
    
    private static List<String> initPaths(final String v1) {
        final String v2 = System.getProperty(v1, "");
        if ("".equals(v2)) {
            return Collections.emptyList();
        }
        final StringTokenizer v3 = new StringTokenizer(v2, File.pathSeparator);
        final List<String> v4 = new ArrayList<String>();
        while (v3.hasMoreTokens()) {
            final String a1 = v3.nextToken();
            if (!"".equals(a1)) {
                v4.add(a1);
            }
        }
        return v4;
    }
    
    private static String findLibraryPath(final String v1, final List<String> v2) {
        if (new File(v1).isAbsolute()) {
            return v1;
        }
        final String v3 = mapSharedLibraryName(v1);
        for (final String a2 : v2) {
            File a3 = new File(a2, v3);
            if (a3.exists()) {
                return a3.getAbsolutePath();
            }
            if (!Platform.isMac() || !v3.endsWith(".dylib")) {
                continue;
            }
            a3 = new File(a2, v3.substring(0, v3.lastIndexOf(".dylib")) + ".jnilib");
            if (a3.exists()) {
                return a3.getAbsolutePath();
            }
        }
        return v3;
    }
    
    static String mapSharedLibraryName(final String v1) {
        if (!Platform.isMac()) {
            if (Platform.isLinux() || Platform.isFreeBSD()) {
                if (isVersionedName(v1) || v1.endsWith(".so")) {
                    return v1;
                }
            }
            else if (Platform.isAIX()) {
                if (v1.startsWith("lib")) {
                    return v1;
                }
            }
            else if (Platform.isWindows() && (v1.endsWith(".drv") || v1.endsWith(".dll"))) {
                return v1;
            }
            return System.mapLibraryName(v1);
        }
        if (v1.startsWith("lib") && (v1.endsWith(".dylib") || v1.endsWith(".jnilib"))) {
            return v1;
        }
        final String a1 = System.mapLibraryName(v1);
        if (a1.endsWith(".jnilib")) {
            return a1.substring(0, a1.lastIndexOf(".jnilib")) + ".dylib";
        }
        return a1;
    }
    
    private static boolean isVersionedName(final String v-1) {
        if (v-1.startsWith("lib")) {
            final int v0 = v-1.lastIndexOf(".so.");
            if (v0 != -1 && v0 + 4 < v-1.length()) {
                for (int v2 = v0 + 4; v2 < v-1.length(); ++v2) {
                    final char a1 = v-1.charAt(v2);
                    if (!Character.isDigit(a1) && a1 != '.') {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    static String matchLibrary(final String v-9, List<String> v-8) {
        final File file = new File(v-9);
        if (file.isAbsolute()) {
            v-8 = Arrays.asList(file.getParent());
        }
        final FilenameFilter filenameFilter = new FilenameFilter() {
            final /* synthetic */ String val$libName;
            
            NativeLibrary$2() {
                super();
            }
            
            @Override
            public boolean accept(final File a1, final String a2) {
                return (a2.startsWith("lib" + v-9 + ".so") || (a2.startsWith(v-9 + ".so") && v-9.startsWith("lib"))) && isVersionedName(a2);
            }
        };
        final Collection<File> collection = new LinkedList<File>();
        for (final String a2 : v-8) {
            final File[] a3 = new File(a2).listFiles(filenameFilter);
            if (a3 != null && a3.length > 0) {
                collection.addAll(Arrays.asList(a3));
            }
        }
        double n = -1.0;
        String s = null;
        for (final File v0 : collection) {
            final String v2 = v0.getAbsolutePath();
            final String v3 = v2.substring(v2.lastIndexOf(".so.") + 4);
            final double v4 = parseVersion(v3);
            if (v4 > n) {
                n = v4;
                s = v2;
            }
        }
        return s;
    }
    
    static double parseVersion(String v-6) {
        double n = 0.0;
        double n2 = 1.0;
        int n3 = v-6.indexOf(".");
        while (v-6 != null) {
            String v0 = null;
            if (n3 != -1) {
                final String a1 = v-6.substring(0, n3);
                v-6 = v-6.substring(n3 + 1);
                n3 = v-6.indexOf(".");
            }
            else {
                v0 = v-6;
                v-6 = null;
            }
            try {
                n += Integer.parseInt(v0) / n2;
            }
            catch (NumberFormatException v2) {
                return 0.0;
            }
            n2 *= 100.0;
        }
        return n;
    }
    
    private static String getMultiArchPath() {
        String v1 = Platform.ARCH;
        final String v2 = Platform.iskFreeBSD() ? "-kfreebsd" : (Platform.isGNU() ? "" : "-linux");
        String v3 = "-gnu";
        if (Platform.isIntel()) {
            v1 = (Platform.is64Bit() ? "x86_64" : "i386");
        }
        else if (Platform.isPPC()) {
            v1 = (Platform.is64Bit() ? "powerpc64" : "powerpc");
        }
        else if (Platform.isARM()) {
            v1 = "arm";
            v3 = "-gnueabi";
        }
        return v1 + v2 + v3;
    }
    
    private static ArrayList<String> getLinuxLdPaths() {
        final ArrayList<String> list = new ArrayList<String>();
        try {
            final Process exec = Runtime.getRuntime().exec("/sbin/ldconfig -p");
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                final int index = line.indexOf(" => ");
                final int v0 = line.lastIndexOf(47);
                if (index != -1 && v0 != -1 && index < v0) {
                    final String v2 = line.substring(index + 4, v0);
                    if (list.contains(v2)) {
                        continue;
                    }
                    list.add(v2);
                }
            }
            bufferedReader.close();
        }
        catch (Exception ex) {}
        return list;
    }
    
    static /* bridge */ boolean access$000(final String a1) {
        return isVersionedName(a1);
    }
    
    static {
        libraries = new HashMap<String, Reference<NativeLibrary>>();
        searchPaths = Collections.synchronizedMap(new HashMap<String, List<String>>());
        librarySearchPath = new ArrayList<String>();
        if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        final String webStartLibraryPath = Native.getWebStartLibraryPath("jnidispatch");
        if (webStartLibraryPath != null) {
            NativeLibrary.librarySearchPath.add(webStartLibraryPath);
        }
        if (System.getProperty("jna.platform.library.path") == null && !Platform.isWindows()) {
            String string = "";
            String pathSeparator = "";
            String string2 = "";
            if (Platform.isLinux() || Platform.isSolaris() || Platform.isFreeBSD() || Platform.iskFreeBSD()) {
                string2 = (Platform.isSolaris() ? "/" : "") + Pointer.SIZE * 8;
            }
            String[] v0 = { "/usr/lib" + string2, "/lib" + string2, "/usr/lib", "/lib" };
            if (Platform.isLinux() || Platform.iskFreeBSD() || Platform.isGNU()) {
                final String v2 = getMultiArchPath();
                v0 = new String[] { "/usr/lib/" + v2, "/lib/" + v2, "/usr/lib" + string2, "/lib" + string2, "/usr/lib", "/lib" };
            }
            if (Platform.isLinux()) {
                final ArrayList<String> v3 = getLinuxLdPaths();
                for (int v4 = v0.length - 1; 0 <= v4; --v4) {
                    final int v5 = v3.indexOf(v0[v4]);
                    if (v5 != -1) {
                        v3.remove(v5);
                    }
                    v3.add(0, v0[v4]);
                }
                v0 = v3.toArray(new String[v3.size()]);
            }
            for (int v6 = 0; v6 < v0.length; ++v6) {
                final File v7 = new File(v0[v6]);
                if (v7.exists() && v7.isDirectory()) {
                    string = string + pathSeparator + v0[v6];
                    pathSeparator = File.pathSeparator;
                }
            }
            if (!"".equals(string)) {
                System.setProperty("jna.platform.library.path", string);
            }
        }
        NativeLibrary.librarySearchPath.addAll(initPaths("jna.platform.library.path"));
    }
}
