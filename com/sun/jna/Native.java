package com.sun.jna;

import java.lang.ref.*;
import java.net.*;
import java.security.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.*;
import java.awt.*;

public final class Native implements Version
{
    public static final String DEFAULT_ENCODING;
    public static boolean DEBUG_LOAD;
    public static boolean DEBUG_JNA_LOAD;
    static String jnidispatchPath;
    private static final Map<Class<?>, Map<String, Object>> typeOptions;
    private static final Map<Class<?>, Reference<?>> libraries;
    private static final String _OPTION_ENCLOSING_LIBRARY = "enclosing-library";
    private static final Callback.UncaughtExceptionHandler DEFAULT_HANDLER;
    private static Callback.UncaughtExceptionHandler callbackExceptionHandler;
    public static final int POINTER_SIZE;
    public static final int LONG_SIZE;
    public static final int WCHAR_SIZE;
    public static final int SIZE_T_SIZE;
    public static final int BOOL_SIZE;
    private static final int TYPE_VOIDP = 0;
    private static final int TYPE_LONG = 1;
    private static final int TYPE_WCHAR_T = 2;
    private static final int TYPE_SIZE_T = 3;
    private static final int TYPE_BOOL = 4;
    static final int MAX_ALIGNMENT;
    static final int MAX_PADDING;
    private static final Object finalizer;
    static final String JNA_TMPLIB_PREFIX = "jna";
    private static Map<Class<?>, long[]> registeredClasses;
    private static Map<Class<?>, NativeLibrary> registeredLibraries;
    static final int CB_HAS_INITIALIZER = 1;
    private static final int CVT_UNSUPPORTED = -1;
    private static final int CVT_DEFAULT = 0;
    private static final int CVT_POINTER = 1;
    private static final int CVT_STRING = 2;
    private static final int CVT_STRUCTURE = 3;
    private static final int CVT_STRUCTURE_BYVAL = 4;
    private static final int CVT_BUFFER = 5;
    private static final int CVT_ARRAY_BYTE = 6;
    private static final int CVT_ARRAY_SHORT = 7;
    private static final int CVT_ARRAY_CHAR = 8;
    private static final int CVT_ARRAY_INT = 9;
    private static final int CVT_ARRAY_LONG = 10;
    private static final int CVT_ARRAY_FLOAT = 11;
    private static final int CVT_ARRAY_DOUBLE = 12;
    private static final int CVT_ARRAY_BOOLEAN = 13;
    private static final int CVT_BOOLEAN = 14;
    private static final int CVT_CALLBACK = 15;
    private static final int CVT_FLOAT = 16;
    private static final int CVT_NATIVE_MAPPED = 17;
    private static final int CVT_NATIVE_MAPPED_STRING = 18;
    private static final int CVT_NATIVE_MAPPED_WSTRING = 19;
    private static final int CVT_WSTRING = 20;
    private static final int CVT_INTEGER_TYPE = 21;
    private static final int CVT_POINTER_TYPE = 22;
    private static final int CVT_TYPE_MAPPER = 23;
    private static final int CVT_TYPE_MAPPER_STRING = 24;
    private static final int CVT_TYPE_MAPPER_WSTRING = 25;
    static final int CB_OPTION_DIRECT = 1;
    static final int CB_OPTION_IN_DLL = 2;
    private static final ThreadLocal<Memory> nativeThreadTerminationFlag;
    private static final Map<Thread, Pointer> nativeThreads;
    
    @Deprecated
    public static float parseVersion(final String a1) {
        return Float.parseFloat(a1.substring(0, a1.lastIndexOf(".")));
    }
    
    static boolean isCompatibleVersion(final String a1, final String a2) {
        final String[] v1 = a1.split("\\.");
        final String[] v2 = a2.split("\\.");
        if (v1.length < 3 || v2.length < 3) {
            return false;
        }
        final int v3 = Integer.parseInt(v1[0]);
        final int v4 = Integer.parseInt(v2[0]);
        final int v5 = Integer.parseInt(v1[1]);
        final int v6 = Integer.parseInt(v2[1]);
        return v3 == v4 && v5 <= v6;
    }
    
    private static void dispose() {
        CallbackReference.disposeAll();
        Memory.disposeAll();
        NativeLibrary.disposeAll();
        unregisterAll();
        Native.jnidispatchPath = null;
        System.setProperty("jna.loaded", "false");
    }
    
    static boolean deleteLibrary(final File a1) {
        if (a1.delete()) {
            return true;
        }
        markTemporaryFile(a1);
        return false;
    }
    
    private Native() {
        super();
    }
    
    private static native void initIDs();
    
    public static synchronized native void setProtected(final boolean p0);
    
    public static synchronized native boolean isProtected();
    
    @Deprecated
    public static void setPreserveLastError(final boolean a1) {
    }
    
    @Deprecated
    public static boolean getPreserveLastError() {
        return true;
    }
    
    public static long getWindowID(final Window a1) throws HeadlessException {
        return AWT.getWindowID(a1);
    }
    
    public static long getComponentID(final Component a1) throws HeadlessException {
        return AWT.getComponentID(a1);
    }
    
    public static Pointer getWindowPointer(final Window a1) throws HeadlessException {
        return new Pointer(AWT.getWindowID(a1));
    }
    
    public static Pointer getComponentPointer(final Component a1) throws HeadlessException {
        return new Pointer(AWT.getComponentID(a1));
    }
    
    static native long getWindowHandle0(final Component p0);
    
    public static Pointer getDirectBufferPointer(final Buffer a1) {
        final long v1 = _getDirectBufferPointer(a1);
        return (v1 == 0L) ? null : new Pointer(v1);
    }
    
    private static native long _getDirectBufferPointer(final Buffer p0);
    
    public static String toString(final byte[] a1) {
        return toString(a1, getDefaultStringEncoding());
    }
    
    public static String toString(final byte[] v1, final String v2) {
        int v3 = v1.length;
        for (int a1 = 0; a1 < v3; ++a1) {
            if (v1[a1] == 0) {
                v3 = a1;
                break;
            }
        }
        if (v3 == 0) {
            return "";
        }
        if (v2 != null) {
            try {
                return new String(v1, 0, v3, v2);
            }
            catch (UnsupportedEncodingException a2) {
                System.err.println("JNA Warning: Encoding '" + v2 + "' is unsupported");
            }
        }
        System.err.println("JNA Warning: Decoding with fallback " + System.getProperty("file.encoding"));
        return new String(v1, 0, v3);
    }
    
    public static String toString(final char[] v1) {
        int v2 = v1.length;
        for (int a1 = 0; a1 < v2; ++a1) {
            if (v1[a1] == '\0') {
                v2 = a1;
                break;
            }
        }
        if (v2 == 0) {
            return "";
        }
        return new String(v1, 0, v2);
    }
    
    public static List<String> toStringList(final char[] a1) {
        return toStringList(a1, 0, a1.length);
    }
    
    public static List<String> toStringList(final char[] v1, final int v2, final int v3) {
        final List<String> v4 = new ArrayList<String>();
        int v5 = v2;
        final int v6 = v2 + v3;
        for (int a2 = v2; a2 < v6; ++a2) {
            if (v1[a2] == '\0') {
                if (v5 == a2) {
                    return v4;
                }
                final String a3 = new String(v1, v5, a2 - v5);
                v4.add(a3);
                v5 = a2 + 1;
            }
        }
        if (v5 < v6) {
            final String a4 = new String(v1, v5, v6 - v5);
            v4.add(a4);
        }
        return v4;
    }
    
    public static <T> T loadLibrary(final Class<T> a1) {
        return loadLibrary(null, a1);
    }
    
    public static <T> T loadLibrary(final Class<T> a1, final Map<String, ?> a2) {
        return loadLibrary(null, a1, a2);
    }
    
    public static <T> T loadLibrary(final String a1, final Class<T> a2) {
        return loadLibrary(a1, a2, Collections.emptyMap());
    }
    
    public static <T> T loadLibrary(final String a1, final Class<T> a2, final Map<String, ?> a3) {
        if (!Library.class.isAssignableFrom(a2)) {
            throw new IllegalArgumentException("Interface (" + a2.getSimpleName() + ") of library=" + a1 + " does not extend " + Library.class.getSimpleName());
        }
        final Library.Handler v1 = new Library.Handler(a1, a2, a3);
        final ClassLoader v2 = a2.getClassLoader();
        final Object v3 = Proxy.newProxyInstance(v2, new Class[] { a2 }, v1);
        cacheOptions(a2, a3, v3);
        return a2.cast(v3);
    }
    
    private static void loadLibraryInstance(final Class<?> v-2) {
        synchronized (Native.libraries) {
            if (v-2 != null && !Native.libraries.containsKey(v-2)) {
                try {
                    final Field[] v0 = v-2.getFields();
                    for (int v2 = 0; v2 < v0.length; ++v2) {
                        final Field a1 = v0[v2];
                        if (a1.getType() == v-2 && Modifier.isStatic(a1.getModifiers())) {
                            Native.libraries.put(v-2, new WeakReference<Object>(a1.get(null)));
                            break;
                        }
                    }
                }
                catch (Exception v3) {
                    throw new IllegalArgumentException("Could not access instance of " + v-2 + " (" + v3 + ")");
                }
            }
        }
    }
    
    static Class<?> findEnclosingLibraryClass(Class<?> v-2) {
        if (v-2 == null) {
            return null;
        }
        synchronized (Native.libraries) {
            if (Native.typeOptions.containsKey(v-2)) {
                final Map<String, ?> a1 = Native.typeOptions.get(v-2);
                final Class<?> v1 = (Class<?>)a1.get("enclosing-library");
                if (v1 != null) {
                    return v1;
                }
                return v-2;
            }
        }
        if (Library.class.isAssignableFrom(v-2)) {
            return v-2;
        }
        if (Callback.class.isAssignableFrom(v-2)) {
            v-2 = CallbackReference.findCallbackClass(v-2);
        }
        final Class<?> declaringClass = v-2.getDeclaringClass();
        final Class<?> v2 = findEnclosingLibraryClass(declaringClass);
        if (v2 != null) {
            return v2;
        }
        return findEnclosingLibraryClass(v-2.getSuperclass());
    }
    
    public static Map<String, Object> getLibraryOptions(final Class<?> v-3) {
        synchronized (Native.libraries) {
            final Map<String, Object> a1 = Native.typeOptions.get(v-3);
            if (a1 != null) {
                return a1;
            }
        }
        Class<?> enclosingLibraryClass = findEnclosingLibraryClass(v-3);
        if (enclosingLibraryClass != null) {
            loadLibraryInstance(enclosingLibraryClass);
        }
        else {
            enclosingLibraryClass = v-3;
        }
        synchronized (Native.libraries) {
            Map<String, Object> v4 = Native.typeOptions.get(enclosingLibraryClass);
            if (v4 != null) {
                Native.typeOptions.put(v-3, v4);
                return v4;
            }
            try {
                final Field v1 = enclosingLibraryClass.getField("OPTIONS");
                v1.setAccessible(true);
                v4 = (Map<String, Object>)v1.get(null);
                if (v4 == null) {
                    throw new IllegalStateException("Null options field");
                }
            }
            catch (NoSuchFieldException v3) {
                v4 = Collections.emptyMap();
            }
            catch (Exception v2) {
                throw new IllegalArgumentException("OPTIONS must be a public field of type java.util.Map (" + v2 + "): " + enclosingLibraryClass);
            }
            v4 = new HashMap<String, Object>(v4);
            if (!v4.containsKey("type-mapper")) {
                v4.put("type-mapper", lookupField(enclosingLibraryClass, "TYPE_MAPPER", TypeMapper.class));
            }
            if (!v4.containsKey("structure-alignment")) {
                v4.put("structure-alignment", lookupField(enclosingLibraryClass, "STRUCTURE_ALIGNMENT", Integer.class));
            }
            if (!v4.containsKey("string-encoding")) {
                v4.put("string-encoding", lookupField(enclosingLibraryClass, "STRING_ENCODING", String.class));
            }
            v4 = cacheOptions(enclosingLibraryClass, v4, null);
            if (v-3 != enclosingLibraryClass) {
                Native.typeOptions.put(v-3, v4);
            }
            return v4;
        }
    }
    
    private static Object lookupField(final Class<?> v1, final String v2, final Class<?> v3) {
        try {
            final Field a1 = v1.getField(v2);
            a1.setAccessible(true);
            return a1.get(null);
        }
        catch (NoSuchFieldException a3) {
            return null;
        }
        catch (Exception a2) {
            throw new IllegalArgumentException(v2 + " must be a public field of type " + v3.getName() + " (" + a2 + "): " + v1);
        }
    }
    
    public static TypeMapper getTypeMapper(final Class<?> a1) {
        final Map<String, ?> v1 = getLibraryOptions(a1);
        return (TypeMapper)v1.get("type-mapper");
    }
    
    public static String getStringEncoding(final Class<?> a1) {
        final Map<String, ?> v1 = getLibraryOptions(a1);
        final String v2 = (String)v1.get("string-encoding");
        return (v2 != null) ? v2 : getDefaultStringEncoding();
    }
    
    public static String getDefaultStringEncoding() {
        return System.getProperty("jna.encoding", Native.DEFAULT_ENCODING);
    }
    
    public static int getStructureAlignment(final Class<?> a1) {
        final Integer v1 = getLibraryOptions(a1).get("structure-alignment");
        return (v1 == null) ? 0 : v1;
    }
    
    static byte[] getBytes(final String a1) {
        return getBytes(a1, getDefaultStringEncoding());
    }
    
    static byte[] getBytes(final String a2, final String v1) {
        if (v1 != null) {
            try {
                return a2.getBytes(v1);
            }
            catch (UnsupportedEncodingException a3) {
                System.err.println("JNA Warning: Encoding '" + v1 + "' is unsupported");
            }
        }
        System.err.println("JNA Warning: Encoding with fallback " + System.getProperty("file.encoding"));
        return a2.getBytes();
    }
    
    public static byte[] toByteArray(final String a1) {
        return toByteArray(a1, getDefaultStringEncoding());
    }
    
    public static byte[] toByteArray(final String a1, final String a2) {
        final byte[] v1 = getBytes(a1, a2);
        final byte[] v2 = new byte[v1.length + 1];
        System.arraycopy(v1, 0, v2, 0, v1.length);
        return v2;
    }
    
    public static char[] toCharArray(final String a1) {
        final char[] v1 = a1.toCharArray();
        final char[] v2 = new char[v1.length + 1];
        System.arraycopy(v1, 0, v2, 0, v1.length);
        return v2;
    }
    
    private static void loadNativeDispatchLibrary() {
        if (!Boolean.getBoolean("jna.nounpack")) {
            try {
                removeTemporaryFiles();
            }
            catch (IOException v1) {
                System.err.println("JNA Warning: IOException removing temporary files: " + v1.getMessage());
            }
        }
        final String v2 = System.getProperty("jna.boot.library.name", "jnidispatch");
        final String v3 = System.getProperty("jna.boot.library.path");
        if (v3 != null) {
            final StringTokenizer v4 = new StringTokenizer(v3, File.pathSeparator);
            while (v4.hasMoreTokens()) {
                final String v5 = v4.nextToken();
                final File v6 = new File(new File(v5), System.mapLibraryName(v2).replace(".dylib", ".jnilib"));
                String v7 = v6.getAbsolutePath();
                if (Native.DEBUG_JNA_LOAD) {
                    System.out.println("Looking in " + v7);
                }
                if (v6.exists()) {
                    try {
                        if (Native.DEBUG_JNA_LOAD) {
                            System.out.println("Trying " + v7);
                        }
                        System.setProperty("jnidispatch.path", v7);
                        System.load(v7);
                        Native.jnidispatchPath = v7;
                        if (Native.DEBUG_JNA_LOAD) {
                            System.out.println("Found jnidispatch at " + v7);
                        }
                        return;
                    }
                    catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
                }
                if (Platform.isMac()) {
                    String v8;
                    String v9;
                    if (v7.endsWith("dylib")) {
                        v8 = "dylib";
                        v9 = "jnilib";
                    }
                    else {
                        v8 = "jnilib";
                        v9 = "dylib";
                    }
                    v7 = v7.substring(0, v7.lastIndexOf(v8)) + v9;
                    if (Native.DEBUG_JNA_LOAD) {
                        System.out.println("Looking in " + v7);
                    }
                    if (!new File(v7).exists()) {
                        continue;
                    }
                    try {
                        if (Native.DEBUG_JNA_LOAD) {
                            System.out.println("Trying " + v7);
                        }
                        System.setProperty("jnidispatch.path", v7);
                        System.load(v7);
                        Native.jnidispatchPath = v7;
                        if (Native.DEBUG_JNA_LOAD) {
                            System.out.println("Found jnidispatch at " + v7);
                        }
                        return;
                    }
                    catch (UnsatisfiedLinkError v10) {
                        System.err.println("File found at " + v7 + " but not loadable: " + v10.getMessage());
                    }
                }
            }
        }
        if (!Boolean.getBoolean("jna.nosys")) {
            try {
                if (Native.DEBUG_JNA_LOAD) {
                    System.out.println("Trying (via loadLibrary) " + v2);
                }
                System.loadLibrary(v2);
                if (Native.DEBUG_JNA_LOAD) {
                    System.out.println("Found jnidispatch on system path");
                }
                return;
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError2) {}
        }
        if (!Boolean.getBoolean("jna.noclasspath")) {
            loadNativeDispatchLibraryFromClasspath();
            return;
        }
        throw new UnsatisfiedLinkError("Unable to locate JNA native support library");
    }
    
    private static void loadNativeDispatchLibraryFromClasspath() {
        try {
            final String v1 = "/com/sun/jna/" + Platform.RESOURCE_PREFIX + "/" + System.mapLibraryName("jnidispatch").replace(".dylib", ".jnilib");
            final File v2 = extractFromResourcePath(v1, Native.class.getClassLoader());
            if (v2 == null && v2 == null) {
                throw new UnsatisfiedLinkError("Could not find JNA native support");
            }
            if (Native.DEBUG_JNA_LOAD) {
                System.out.println("Trying " + v2.getAbsolutePath());
            }
            System.setProperty("jnidispatch.path", v2.getAbsolutePath());
            System.load(v2.getAbsolutePath());
            Native.jnidispatchPath = v2.getAbsolutePath();
            if (Native.DEBUG_JNA_LOAD) {
                System.out.println("Found jnidispatch at " + Native.jnidispatchPath);
            }
            if (isUnpacked(v2) && !Boolean.getBoolean("jnidispatch.preserve")) {
                deleteLibrary(v2);
            }
        }
        catch (IOException v3) {
            throw new UnsatisfiedLinkError(v3.getMessage());
        }
    }
    
    static boolean isUnpacked(final File a1) {
        return a1.getName().startsWith("jna");
    }
    
    public static File extractFromResourcePath(final String a1) throws IOException {
        return extractFromResourcePath(a1, null);
    }
    
    public static File extractFromResourcePath(final String v-8, ClassLoader v-7) throws IOException {
        final boolean b = Native.DEBUG_LOAD || (Native.DEBUG_JNA_LOAD && v-8.indexOf("jnidispatch") != -1);
        if (v-7 == null) {
            v-7 = Thread.currentThread().getContextClassLoader();
            if (v-7 == null) {
                v-7 = Native.class.getClassLoader();
            }
        }
        if (b) {
            System.out.println("Looking in classpath from " + v-7 + " for " + v-8);
        }
        final String s = v-8.startsWith("/") ? v-8 : NativeLibrary.mapSharedLibraryName(v-8);
        String substring = v-8.startsWith("/") ? v-8 : (Platform.RESOURCE_PREFIX + "/" + s);
        if (substring.startsWith("/")) {
            substring = substring.substring(1);
        }
        URL url = v-7.getResource(substring);
        if (url == null && substring.startsWith(Platform.RESOURCE_PREFIX)) {
            url = v-7.getResource(s);
        }
        if (url == null) {
            String a1 = System.getProperty("java.class.path");
            if (v-7 instanceof URLClassLoader) {
                a1 = Arrays.asList(((URLClassLoader)v-7).getURLs()).toString();
            }
            throw new IOException("Native library (" + substring + ") not found in resource path (" + a1 + ")");
        }
        if (b) {
            System.out.println("Found library resource at " + url);
        }
        File tempFile = null;
        if (url.getProtocol().toLowerCase().equals("file")) {
            try {
                tempFile = new File(new URI(url.toString()));
            }
            catch (URISyntaxException a2) {
                tempFile = new File(url.getPath());
            }
            if (b) {
                System.out.println("Looking in " + tempFile.getAbsolutePath());
            }
            if (!tempFile.exists()) {
                throw new IOException("File URL " + url + " could not be properly decoded");
            }
        }
        else if (!Boolean.getBoolean("jna.nounpack")) {
            final InputStream resourceAsStream = v-7.getResourceAsStream(substring);
            if (resourceAsStream == null) {
                throw new IOException("Can't obtain InputStream for " + substring);
            }
            FileOutputStream v0 = null;
            try {
                final File v2 = getTempDir();
                tempFile = File.createTempFile("jna", Platform.isWindows() ? ".dll" : null, v2);
                if (!Boolean.getBoolean("jnidispatch.preserve")) {
                    tempFile.deleteOnExit();
                }
                v0 = new FileOutputStream(tempFile);
                final byte[] v3 = new byte[1024];
                int v4;
                while ((v4 = resourceAsStream.read(v3, 0, v3.length)) > 0) {
                    v0.write(v3, 0, v4);
                }
            }
            catch (IOException v5) {
                throw new IOException("Failed to create temporary file for " + v-8 + " library: " + v5.getMessage());
            }
            finally {
                try {
                    resourceAsStream.close();
                }
                catch (IOException ex) {}
                if (v0 != null) {
                    try {
                        v0.close();
                    }
                    catch (IOException ex2) {}
                }
            }
        }
        return tempFile;
    }
    
    private static native int sizeof(final int p0);
    
    private static native String getNativeVersion();
    
    private static native String getAPIChecksum();
    
    public static native int getLastError();
    
    public static native void setLastError(final int p0);
    
    public static Library synchronizedLibrary(final Library a1) {
        final Class<?> v1 = a1.getClass();
        if (!Proxy.isProxyClass(v1)) {
            throw new IllegalArgumentException("Library must be a proxy class");
        }
        final InvocationHandler v2 = Proxy.getInvocationHandler(a1);
        if (!(v2 instanceof Library.Handler)) {
            throw new IllegalArgumentException("Unrecognized proxy handler: " + v2);
        }
        final Library.Handler v3 = (Library.Handler)v2;
        final InvocationHandler v4 = new InvocationHandler() {
            final /* synthetic */ Library.Handler val$handler;
            final /* synthetic */ Library val$library;
            
            Native$3() {
                super();
            }
            
            @Override
            public Object invoke(final Object a1, final Method a2, final Object[] a3) throws Throwable {
                synchronized (v3.getNativeLibrary()) {
                    return v3.invoke(a1, a2, a3);
                }
            }
        };
        return (Library)Proxy.newProxyInstance(v1.getClassLoader(), v1.getInterfaces(), v4);
    }
    
    public static String getWebStartLibraryPath(final String v-1) {
        if (System.getProperty("javawebstart.version") == null) {
            return null;
        }
        try {
            final ClassLoader a1 = Native.class.getClassLoader();
            final Method v1 = AccessController.doPrivileged((PrivilegedAction<Method>)new PrivilegedAction<Method>() {
                Native$4() {
                    super();
                }
                
                @Override
                public Method run() {
                    try {
                        final Method v1 = ClassLoader.class.getDeclaredMethod("findLibrary", String.class);
                        v1.setAccessible(true);
                        return v1;
                    }
                    catch (Exception v2) {
                        return null;
                    }
                }
                
                @Override
                public /* bridge */ Object run() {
                    return this.run();
                }
            });
            final String v2 = (String)v1.invoke(a1, v-1);
            if (v2 != null) {
                return new File(v2).getParent();
            }
            return null;
        }
        catch (Exception v3) {
            return null;
        }
    }
    
    static void markTemporaryFile(final File v0) {
        try {
            final File a1 = new File(v0.getParentFile(), v0.getName() + ".x");
            a1.createNewFile();
        }
        catch (IOException v) {
            v.printStackTrace();
        }
    }
    
    static File getTempDir() throws IOException {
        final String v2 = System.getProperty("jna.tmpdir");
        File v3;
        if (v2 != null) {
            v3 = new File(v2);
            v3.mkdirs();
        }
        else {
            final File v4 = new File(System.getProperty("java.io.tmpdir"));
            v3 = new File(v4, "jna-" + System.getProperty("user.name").hashCode());
            v3.mkdirs();
            if (!v3.exists() || !v3.canWrite()) {
                v3 = v4;
            }
        }
        if (!v3.exists()) {
            throw new IOException("JNA temporary directory '" + v3 + "' does not exist");
        }
        if (!v3.canWrite()) {
            throw new IOException("JNA temporary directory '" + v3 + "' is not writable");
        }
        return v3;
    }
    
    static void removeTemporaryFiles() throws IOException {
        final File tempDir = getTempDir();
        final FilenameFilter filenameFilter = new FilenameFilter() {
            Native$5() {
                super();
            }
            
            @Override
            public boolean accept(final File a1, final String a2) {
                return a2.endsWith(".x") && a2.startsWith("jna");
            }
        };
        final File[] listFiles = tempDir.listFiles(filenameFilter);
        for (int v0 = 0; listFiles != null && v0 < listFiles.length; ++v0) {
            final File v2 = listFiles[v0];
            String v3 = v2.getName();
            v3 = v3.substring(0, v3.length() - 2);
            final File v4 = new File(v2.getParentFile(), v3);
            if (!v4.exists() || v4.delete()) {
                v2.delete();
            }
        }
    }
    
    public static int getNativeSize(final Class<?> v-1, final Object v0) {
        if (v-1.isArray()) {
            final int a2 = Array.getLength(v0);
            if (a2 > 0) {
                final Object a3 = Array.get(v0, 0);
                return a2 * getNativeSize(v-1.getComponentType(), a3);
            }
            throw new IllegalArgumentException("Arrays of length zero not allowed: " + v-1);
        }
        else {
            if (Structure.class.isAssignableFrom(v-1) && !Structure.ByReference.class.isAssignableFrom(v-1)) {
                return Structure.size(v-1, (Structure)v0);
            }
            try {
                return getNativeSize(v-1);
            }
            catch (IllegalArgumentException v) {
                throw new IllegalArgumentException("The type \"" + v-1.getName() + "\" is not supported: " + v.getMessage());
            }
        }
    }
    
    public static int getNativeSize(Class<?> a1) {
        if (NativeMapped.class.isAssignableFrom(a1)) {
            a1 = NativeMappedConverter.getInstance(a1).nativeType();
        }
        if (a1 == Boolean.TYPE || a1 == Boolean.class) {
            return 4;
        }
        if (a1 == Byte.TYPE || a1 == Byte.class) {
            return 1;
        }
        if (a1 == Short.TYPE || a1 == Short.class) {
            return 2;
        }
        if (a1 == Character.TYPE || a1 == Character.class) {
            return Native.WCHAR_SIZE;
        }
        if (a1 == Integer.TYPE || a1 == Integer.class) {
            return 4;
        }
        if (a1 == Long.TYPE || a1 == Long.class) {
            return 8;
        }
        if (a1 == Float.TYPE || a1 == Float.class) {
            return 4;
        }
        if (a1 == Double.TYPE || a1 == Double.class) {
            return 8;
        }
        if (Structure.class.isAssignableFrom(a1)) {
            if (Structure.ByValue.class.isAssignableFrom(a1)) {
                return Structure.size(a1);
            }
            return Native.POINTER_SIZE;
        }
        else {
            if (Pointer.class.isAssignableFrom(a1) || (Platform.HAS_BUFFERS && Buffers.isBuffer(a1)) || Callback.class.isAssignableFrom(a1) || String.class == a1 || WString.class == a1) {
                return Native.POINTER_SIZE;
            }
            throw new IllegalArgumentException("Native size for type \"" + a1.getName() + "\" is unknown");
        }
    }
    
    public static boolean isSupportedNativeType(final Class<?> v1) {
        if (Structure.class.isAssignableFrom(v1)) {
            return true;
        }
        try {
            return getNativeSize(v1) != 0;
        }
        catch (IllegalArgumentException a1) {
            return false;
        }
    }
    
    public static void setCallbackExceptionHandler(final Callback.UncaughtExceptionHandler a1) {
        Native.callbackExceptionHandler = ((a1 == null) ? Native.DEFAULT_HANDLER : a1);
    }
    
    public static Callback.UncaughtExceptionHandler getCallbackExceptionHandler() {
        return Native.callbackExceptionHandler;
    }
    
    public static void register(final String a1) {
        register(findDirectMappedClass(getCallingClass()), a1);
    }
    
    public static void register(final NativeLibrary a1) {
        register(findDirectMappedClass(getCallingClass()), a1);
    }
    
    static Class<?> findDirectMappedClass(final Class<?> v-2) {
        final Method[] declaredMethods;
        final Method[] array = declaredMethods = v-2.getDeclaredMethods();
        for (final Method a1 : declaredMethods) {
            if ((a1.getModifiers() & 0x100) != 0x0) {
                return v-2;
            }
        }
        final int v0 = v-2.getName().lastIndexOf("$");
        if (v0 != -1) {
            final String v2 = v-2.getName().substring(0, v0);
            try {
                return findDirectMappedClass(Class.forName(v2, true, v-2.getClassLoader()));
            }
            catch (ClassNotFoundException ex) {}
        }
        throw new IllegalArgumentException("Can't determine class with native methods from the current context (" + v-2 + ")");
    }
    
    static Class<?> getCallingClass() {
        final Class<?>[] v1 = new SecurityManager() {
            Native$6() {
                super();
            }
            
            public Class<?>[] getClassContext() {
                return (Class<?>[])super.getClassContext();
            }
        }.getClassContext();
        if (v1 == null) {
            throw new IllegalStateException("The SecurityManager implementation on this platform is broken; you must explicitly provide the class to register");
        }
        if (v1.length < 4) {
            throw new IllegalStateException("This method must be called from the static initializer of a class");
        }
        return v1[3];
    }
    
    public static void setCallbackThreadInitializer(final Callback a1, final CallbackThreadInitializer a2) {
        CallbackReference.setCallbackThreadInitializer(a1, a2);
    }
    
    private static void unregisterAll() {
        synchronized (Native.registeredClasses) {
            for (final Map.Entry<Class<?>, long[]> v1 : Native.registeredClasses.entrySet()) {
                unregister(v1.getKey(), v1.getValue());
            }
            Native.registeredClasses.clear();
        }
    }
    
    public static void unregister() {
        unregister(findDirectMappedClass(getCallingClass()));
    }
    
    public static void unregister(final Class<?> v1) {
        synchronized (Native.registeredClasses) {
            final long[] a1 = Native.registeredClasses.get(v1);
            if (a1 != null) {
                unregister(v1, a1);
                Native.registeredClasses.remove(v1);
                Native.registeredLibraries.remove(v1);
            }
        }
    }
    
    public static boolean registered(final Class<?> a1) {
        synchronized (Native.registeredClasses) {
            return Native.registeredClasses.containsKey(a1);
        }
    }
    
    private static native void unregister(final Class<?> p0, final long[] p1);
    
    static String getSignature(final Class<?> a1) {
        if (a1.isArray()) {
            return "[" + getSignature(a1.getComponentType());
        }
        if (a1.isPrimitive()) {
            if (a1 == Void.TYPE) {
                return "V";
            }
            if (a1 == Boolean.TYPE) {
                return "Z";
            }
            if (a1 == Byte.TYPE) {
                return "B";
            }
            if (a1 == Short.TYPE) {
                return "S";
            }
            if (a1 == Character.TYPE) {
                return "C";
            }
            if (a1 == Integer.TYPE) {
                return "I";
            }
            if (a1 == Long.TYPE) {
                return "J";
            }
            if (a1 == Float.TYPE) {
                return "F";
            }
            if (a1 == Double.TYPE) {
                return "D";
            }
        }
        return "L" + replace(".", "/", a1.getName()) + ";";
    }
    
    static String replace(final String a2, final String a3, String v1) {
        final StringBuilder v2 = new StringBuilder();
        while (true) {
            final int a4 = v1.indexOf(a2);
            if (a4 == -1) {
                break;
            }
            v2.append(v1.substring(0, a4));
            v2.append(a3);
            v1 = v1.substring(a4 + a2.length());
        }
        v2.append(v1);
        return v2.toString();
    }
    
    private static int getConversion(Class<?> v-1, final TypeMapper v0) {
        if (v-1 == Boolean.class) {
            v-1 = Boolean.TYPE;
        }
        else if (v-1 == Byte.class) {
            v-1 = Byte.TYPE;
        }
        else if (v-1 == Short.class) {
            v-1 = Short.TYPE;
        }
        else if (v-1 == Character.class) {
            v-1 = Character.TYPE;
        }
        else if (v-1 == Integer.class) {
            v-1 = Integer.TYPE;
        }
        else if (v-1 == Long.class) {
            v-1 = Long.TYPE;
        }
        else if (v-1 == Float.class) {
            v-1 = Float.TYPE;
        }
        else if (v-1 == Double.class) {
            v-1 = Double.TYPE;
        }
        else if (v-1 == Void.class) {
            v-1 = Void.TYPE;
        }
        if (v0 != null) {
            final FromNativeConverter v = v0.getFromNativeConverter(v-1);
            final ToNativeConverter v2 = v0.getToNativeConverter(v-1);
            if (v != null) {
                final Class<?> a1 = v.nativeType();
                if (a1 == String.class) {
                    return 24;
                }
                if (a1 == WString.class) {
                    return 25;
                }
                return 23;
            }
            else if (v2 != null) {
                final Class<?> a2 = v2.nativeType();
                if (a2 == String.class) {
                    return 24;
                }
                if (a2 == WString.class) {
                    return 25;
                }
                return 23;
            }
        }
        if (Pointer.class.isAssignableFrom(v-1)) {
            return 1;
        }
        if (String.class == v-1) {
            return 2;
        }
        if (WString.class.isAssignableFrom(v-1)) {
            return 20;
        }
        if (Platform.HAS_BUFFERS && Buffers.isBuffer(v-1)) {
            return 5;
        }
        if (Structure.class.isAssignableFrom(v-1)) {
            if (Structure.ByValue.class.isAssignableFrom(v-1)) {
                return 4;
            }
            return 3;
        }
        else {
            if (v-1.isArray()) {
                switch (v-1.getName().charAt(1)) {
                    case 'Z': {
                        return 13;
                    }
                    case 'B': {
                        return 6;
                    }
                    case 'S': {
                        return 7;
                    }
                    case 'C': {
                        return 8;
                    }
                    case 'I': {
                        return 9;
                    }
                    case 'J': {
                        return 10;
                    }
                    case 'F': {
                        return 11;
                    }
                    case 'D': {
                        return 12;
                    }
                }
            }
            if (v-1.isPrimitive()) {
                return (v-1 == Boolean.TYPE) ? 14 : 0;
            }
            if (Callback.class.isAssignableFrom(v-1)) {
                return 15;
            }
            if (IntegerType.class.isAssignableFrom(v-1)) {
                return 21;
            }
            if (PointerType.class.isAssignableFrom(v-1)) {
                return 22;
            }
            if (!NativeMapped.class.isAssignableFrom(v-1)) {
                return -1;
            }
            final Class<?> v3 = NativeMappedConverter.getInstance(v-1).nativeType();
            if (v3 == String.class) {
                return 18;
            }
            if (v3 == WString.class) {
                return 19;
            }
            return 17;
        }
    }
    
    public static void register(final Class<?> a1, final String a2) {
        final NativeLibrary v1 = NativeLibrary.getInstance(a2, Collections.singletonMap("classloader", a1.getClassLoader()));
        register(a1, v1);
    }
    
    public static void register(final Class<?> v-12, final NativeLibrary v-11) {
        final Method[] declaredMethods = v-12.getDeclaredMethods();
        final List<Method> list = new ArrayList<Method>();
        Map<String, ?> v19 = v-11.getOptions();
        final TypeMapper typeMapper = (TypeMapper)v19.get("type-mapper");
        v19 = cacheOptions(v-12, v19, null);
        for (final Method a1 : declaredMethods) {
            if ((a1.getModifiers() & 0x100) != 0x0) {
                list.add(a1);
            }
        }
        final long[] array2 = new long[list.size()];
        for (int j = 0; j < array2.length; ++j) {
            final Method v20 = list.get(j);
            String s = "(";
            final Class<?> returnType = v20.getReturnType();
            final Class<?>[] v3 = v20.getParameterTypes();
            final long[] v4 = new long[v3.length];
            final long[] v5 = new long[v3.length];
            final int[] v6 = new int[v3.length];
            final ToNativeConverter[] v7 = new ToNativeConverter[v3.length];
            FromNativeConverter v8 = null;
            final int v9 = getConversion(returnType, typeMapper);
            boolean v10 = false;
            long v11 = 0L;
            long n = 0L;
            switch (v9) {
                case -1: {
                    throw new IllegalArgumentException(returnType + " is not a supported return type (in method " + v20.getName() + " in " + v-12 + ")");
                }
                case 23:
                case 24:
                case 25: {
                    v8 = typeMapper.getFromNativeConverter(returnType);
                    v11 = Structure.FFIType.get(returnType.isPrimitive() ? returnType : Pointer.class).peer;
                    final long a2 = Structure.FFIType.get(v8.nativeType()).peer;
                    break;
                }
                case 17:
                case 18:
                case 19:
                case 21:
                case 22: {
                    v11 = Structure.FFIType.get(Pointer.class).peer;
                    n = Structure.FFIType.get(NativeMappedConverter.getInstance(returnType).nativeType()).peer;
                    break;
                }
                case 3: {
                    n = (v11 = Structure.FFIType.get(Pointer.class).peer);
                    break;
                }
                case 4: {
                    v11 = Structure.FFIType.get(Pointer.class).peer;
                    n = Structure.FFIType.get(returnType).peer;
                    break;
                }
                default: {
                    n = (v11 = Structure.FFIType.get(returnType).peer);
                    break;
                }
            }
            for (int v12 = 0; v12 < v3.length; ++v12) {
                Class<?> v13 = v3[v12];
                s += getSignature(v13);
                final int v14 = getConversion(v13, typeMapper);
                if ((v6[v12] = v14) == -1) {
                    throw new IllegalArgumentException(v13 + " is not a supported argument type (in method " + v20.getName() + " in " + v-12 + ")");
                }
                if (v14 == 17 || v14 == 18 || v14 == 19 || v14 == 21) {
                    v13 = NativeMappedConverter.getInstance(v13).nativeType();
                }
                else if (v14 == 23 || v14 == 24 || v14 == 25) {
                    v7[v12] = typeMapper.getToNativeConverter(v13);
                }
                switch (v14) {
                    case 4:
                    case 17:
                    case 18:
                    case 19:
                    case 21:
                    case 22: {
                        v4[v12] = Structure.FFIType.get(v13).peer;
                        v5[v12] = Structure.FFIType.get(Pointer.class).peer;
                        break;
                    }
                    case 23:
                    case 24:
                    case 25: {
                        v5[v12] = Structure.FFIType.get(v13.isPrimitive() ? v13 : Pointer.class).peer;
                        v4[v12] = Structure.FFIType.get(v7[v12].nativeType()).peer;
                        break;
                    }
                    case 0: {
                        v5[v12] = (v4[v12] = Structure.FFIType.get(v13).peer);
                        break;
                    }
                    default: {
                        v5[v12] = (v4[v12] = Structure.FFIType.get(Pointer.class).peer);
                        break;
                    }
                }
            }
            s += ")";
            s += getSignature(returnType);
            final Class<?>[] v15 = v20.getExceptionTypes();
            for (int v16 = 0; v16 < v15.length; ++v16) {
                if (LastErrorException.class.isAssignableFrom(v15[v16])) {
                    v10 = true;
                    break;
                }
            }
            final Function v17 = v-11.getFunction(v20.getName(), v20);
            try {
                array2[j] = registerMethod(v-12, v20.getName(), s, v6, v5, v4, v9, v11, n, v20, v17.peer, v17.getCallingConvention(), v10, v7, v8, v17.encoding);
            }
            catch (NoSuchMethodError v18) {
                throw new UnsatisfiedLinkError("No method " + v20.getName() + " with signature " + s + " in " + v-12);
            }
        }
        synchronized (Native.registeredClasses) {
            Native.registeredClasses.put(v-12, array2);
            Native.registeredLibraries.put(v-12, v-11);
        }
    }
    
    private static Map<String, Object> cacheOptions(final Class<?> a3, final Map<String, ?> v1, final Object v2) {
        final Map<String, Object> v3 = new HashMap<String, Object>(v1);
        v3.put("enclosing-library", a3);
        synchronized (Native.libraries) {
            Native.typeOptions.put(a3, v3);
            if (v2 != null) {
                Native.libraries.put(a3, new WeakReference<Object>(v2));
            }
            if (!a3.isInterface() && Library.class.isAssignableFrom(a3)) {
                final Class<?>[] interfaces;
                final Class<?>[] a4 = interfaces = a3.getInterfaces();
                for (final Class<?> a5 : interfaces) {
                    if (Library.class.isAssignableFrom(a5)) {
                        cacheOptions(a5, v3, v2);
                        break;
                    }
                }
            }
        }
        return v3;
    }
    
    private static native long registerMethod(final Class<?> p0, final String p1, final String p2, final int[] p3, final long[] p4, final long[] p5, final int p6, final long p7, final long p8, final Method p9, final long p10, final int p11, final boolean p12, final ToNativeConverter[] p13, final FromNativeConverter p14, final String p15);
    
    private static NativeMapped fromNative(final Class<?> a1, final Object a2) {
        return (NativeMapped)NativeMappedConverter.getInstance(a1).fromNative(a2, new FromNativeContext(a1));
    }
    
    private static NativeMapped fromNative(final Method a1, final Object a2) {
        final Class<?> v1 = a1.getReturnType();
        return (NativeMapped)NativeMappedConverter.getInstance(v1).fromNative(a2, new MethodResultContext(v1, null, null, a1));
    }
    
    private static Class<?> nativeType(final Class<?> a1) {
        return NativeMappedConverter.getInstance(a1).nativeType();
    }
    
    private static Object toNative(final ToNativeConverter a1, final Object a2) {
        return a1.toNative(a2, new ToNativeContext());
    }
    
    private static Object fromNative(final FromNativeConverter a1, final Object a2, final Method a3) {
        return a1.fromNative(a2, new MethodResultContext(a3.getReturnType(), null, null, a3));
    }
    
    public static native long ffi_prep_cif(final int p0, final int p1, final long p2, final long p3);
    
    public static native void ffi_call(final long p0, final long p1, final long p2, final long p3);
    
    public static native long ffi_prep_closure(final long p0, final ffi_callback p1);
    
    public static native void ffi_free_closure(final long p0);
    
    static native int initialize_ffi_type(final long p0);
    
    public static void main(final String[] a1) {
        final String v1 = "Java Native Access (JNA)";
        final String v2 = "4.4.0";
        final String v3 = "4.4.0 (package information missing)";
        final Package v4 = Native.class.getPackage();
        String v5 = (v4 != null) ? v4.getSpecificationTitle() : "Java Native Access (JNA)";
        if (v5 == null) {
            v5 = "Java Native Access (JNA)";
        }
        String v6 = (v4 != null) ? v4.getSpecificationVersion() : "4.4.0";
        if (v6 == null) {
            v6 = "4.4.0";
        }
        v5 = v5 + " API Version " + v6;
        System.out.println(v5);
        v6 = ((v4 != null) ? v4.getImplementationVersion() : "4.4.0 (package information missing)");
        if (v6 == null) {
            v6 = "4.4.0 (package information missing)";
        }
        System.out.println("Version: " + v6);
        System.out.println(" Native: " + getNativeVersion() + " (" + getAPIChecksum() + ")");
        System.out.println(" Prefix: " + Platform.RESOURCE_PREFIX);
    }
    
    static synchronized native void freeNativeCallback(final long p0);
    
    static synchronized native long createNativeCallback(final Callback p0, final Method p1, final Class<?>[] p2, final Class<?> p3, final int p4, final int p5, final String p6);
    
    static native int invokeInt(final Function p0, final long p1, final int p2, final Object[] p3);
    
    static native long invokeLong(final Function p0, final long p1, final int p2, final Object[] p3);
    
    static native void invokeVoid(final Function p0, final long p1, final int p2, final Object[] p3);
    
    static native float invokeFloat(final Function p0, final long p1, final int p2, final Object[] p3);
    
    static native double invokeDouble(final Function p0, final long p1, final int p2, final Object[] p3);
    
    static native long invokePointer(final Function p0, final long p1, final int p2, final Object[] p3);
    
    private static native void invokeStructure(final Function p0, final long p1, final int p2, final Object[] p3, final long p4, final long p5);
    
    static Structure invokeStructure(final Function a1, final long a2, final int a3, final Object[] a4, final Structure a5) {
        invokeStructure(a1, a2, a3, a4, a5.getPointer().peer, a5.getTypeInfo().peer);
        return a5;
    }
    
    static native Object invokeObject(final Function p0, final long p1, final int p2, final Object[] p3);
    
    static long open(final String a1) {
        return open(a1, -1);
    }
    
    static native long open(final String p0, final int p1);
    
    static native void close(final long p0);
    
    static native long findSymbol(final long p0, final String p1);
    
    static native long indexOf(final Pointer p0, final long p1, final long p2, final byte p3);
    
    static native void read(final Pointer p0, final long p1, final long p2, final byte[] p3, final int p4, final int p5);
    
    static native void read(final Pointer p0, final long p1, final long p2, final short[] p3, final int p4, final int p5);
    
    static native void read(final Pointer p0, final long p1, final long p2, final char[] p3, final int p4, final int p5);
    
    static native void read(final Pointer p0, final long p1, final long p2, final int[] p3, final int p4, final int p5);
    
    static native void read(final Pointer p0, final long p1, final long p2, final long[] p3, final int p4, final int p5);
    
    static native void read(final Pointer p0, final long p1, final long p2, final float[] p3, final int p4, final int p5);
    
    static native void read(final Pointer p0, final long p1, final long p2, final double[] p3, final int p4, final int p5);
    
    static native void write(final Pointer p0, final long p1, final long p2, final byte[] p3, final int p4, final int p5);
    
    static native void write(final Pointer p0, final long p1, final long p2, final short[] p3, final int p4, final int p5);
    
    static native void write(final Pointer p0, final long p1, final long p2, final char[] p3, final int p4, final int p5);
    
    static native void write(final Pointer p0, final long p1, final long p2, final int[] p3, final int p4, final int p5);
    
    static native void write(final Pointer p0, final long p1, final long p2, final long[] p3, final int p4, final int p5);
    
    static native void write(final Pointer p0, final long p1, final long p2, final float[] p3, final int p4, final int p5);
    
    static native void write(final Pointer p0, final long p1, final long p2, final double[] p3, final int p4, final int p5);
    
    static native byte getByte(final Pointer p0, final long p1, final long p2);
    
    static native char getChar(final Pointer p0, final long p1, final long p2);
    
    static native short getShort(final Pointer p0, final long p1, final long p2);
    
    static native int getInt(final Pointer p0, final long p1, final long p2);
    
    static native long getLong(final Pointer p0, final long p1, final long p2);
    
    static native float getFloat(final Pointer p0, final long p1, final long p2);
    
    static native double getDouble(final Pointer p0, final long p1, final long p2);
    
    static Pointer getPointer(final long a1) {
        final long v1 = _getPointer(a1);
        return (v1 == 0L) ? null : new Pointer(v1);
    }
    
    private static native long _getPointer(final long p0);
    
    static native String getWideString(final Pointer p0, final long p1, final long p2);
    
    static String getString(final Pointer a1, final long a2) {
        return getString(a1, a2, getDefaultStringEncoding());
    }
    
    static String getString(final Pointer a1, final long a2, final String a3) {
        final byte[] v1 = getStringBytes(a1, a1.peer, a2);
        if (a3 != null) {
            try {
                return new String(v1, a3);
            }
            catch (UnsupportedEncodingException ex) {}
        }
        return new String(v1);
    }
    
    static native byte[] getStringBytes(final Pointer p0, final long p1, final long p2);
    
    static native void setMemory(final Pointer p0, final long p1, final long p2, final long p3, final byte p4);
    
    static native void setByte(final Pointer p0, final long p1, final long p2, final byte p3);
    
    static native void setShort(final Pointer p0, final long p1, final long p2, final short p3);
    
    static native void setChar(final Pointer p0, final long p1, final long p2, final char p3);
    
    static native void setInt(final Pointer p0, final long p1, final long p2, final int p3);
    
    static native void setLong(final Pointer p0, final long p1, final long p2, final long p3);
    
    static native void setFloat(final Pointer p0, final long p1, final long p2, final float p3);
    
    static native void setDouble(final Pointer p0, final long p1, final long p2, final double p3);
    
    static native void setPointer(final Pointer p0, final long p1, final long p2, final long p3);
    
    static native void setWideString(final Pointer p0, final long p1, final long p2, final String p3);
    
    static native ByteBuffer getDirectByteBuffer(final Pointer p0, final long p1, final long p2, final long p3);
    
    public static native long malloc(final long p0);
    
    public static native void free(final long p0);
    
    @Deprecated
    public static native ByteBuffer getDirectByteBuffer(final long p0, final long p1);
    
    public static void detach(final boolean v-1) {
        final Thread v0 = Thread.currentThread();
        if (v-1) {
            Native.nativeThreads.remove(v0);
            final Pointer a1 = Native.nativeThreadTerminationFlag.get();
            setDetachState(true, 0L);
        }
        else if (!Native.nativeThreads.containsKey(v0)) {
            final Pointer v2 = Native.nativeThreadTerminationFlag.get();
            Native.nativeThreads.put(v0, v2);
            setDetachState(false, v2.peer);
        }
    }
    
    static Pointer getTerminationFlag(final Thread a1) {
        return Native.nativeThreads.get(a1);
    }
    
    private static native void setDetachState(final boolean p0, final long p1);
    
    static /* bridge */ void access$000() {
        dispose();
    }
    
    static {
        DEFAULT_ENCODING = Charset.defaultCharset().name();
        Native.DEBUG_LOAD = Boolean.getBoolean("jna.debug_load");
        Native.DEBUG_JNA_LOAD = Boolean.getBoolean("jna.debug_load.jna");
        Native.jnidispatchPath = null;
        typeOptions = new WeakHashMap<Class<?>, Map<String, Object>>();
        libraries = new WeakHashMap<Class<?>, Reference<?>>();
        DEFAULT_HANDLER = new Callback.UncaughtExceptionHandler() {
            Native$1() {
                super();
            }
            
            @Override
            public void uncaughtException(final Callback a1, final Throwable a2) {
                System.err.println("JNA: Callback " + a1 + " threw the following exception:");
                a2.printStackTrace();
            }
        };
        Native.callbackExceptionHandler = Native.DEFAULT_HANDLER;
        loadNativeDispatchLibrary();
        if (!isCompatibleVersion("5.1.0", getNativeVersion())) {
            final String v1 = System.getProperty("line.separator");
            throw new Error(v1 + v1 + "There is an incompatible JNA native library installed on this system" + v1 + "Expected: " + "5.1.0" + v1 + "Found:    " + getNativeVersion() + v1 + ((Native.jnidispatchPath != null) ? ("(at " + Native.jnidispatchPath + ")") : System.getProperty("java.library.path")) + "." + v1 + "To resolve this issue you may do one of the following:" + v1 + " - remove or uninstall the offending library" + v1 + " - set the system property jna.nosys=true" + v1 + " - set jna.boot.library.path to include the path to the version of the " + v1 + "   jnidispatch library included with the JNA jar file you are using" + v1);
        }
        POINTER_SIZE = sizeof(0);
        LONG_SIZE = sizeof(1);
        WCHAR_SIZE = sizeof(2);
        SIZE_T_SIZE = sizeof(3);
        BOOL_SIZE = sizeof(4);
        initIDs();
        if (Boolean.getBoolean("jna.protected")) {
            setProtected(true);
        }
        MAX_ALIGNMENT = ((Platform.isSPARC() || Platform.isWindows() || (Platform.isLinux() && (Platform.isARM() || Platform.isPPC())) || Platform.isAIX() || Platform.isAndroid()) ? 8 : Native.LONG_SIZE);
        MAX_PADDING = ((Platform.isMac() && Platform.isPPC()) ? 8 : Native.MAX_ALIGNMENT);
        System.setProperty("jna.loaded", "true");
        finalizer = new Object() {
            Native$2() {
                super();
            }
            
            @Override
            protected void finalize() {
                dispose();
            }
        };
        Native.registeredClasses = new WeakHashMap<Class<?>, long[]>();
        Native.registeredLibraries = new WeakHashMap<Class<?>, NativeLibrary>();
        nativeThreadTerminationFlag = new ThreadLocal<Memory>() {
            Native$7() {
                super();
            }
            
            @Override
            protected Memory initialValue() {
                final Memory v1 = new Memory(4L);
                v1.clear();
                return v1;
            }
            
            @Override
            protected /* bridge */ Object initialValue() {
                return this.initialValue();
            }
        };
        nativeThreads = Collections.synchronizedMap(new WeakHashMap<Thread, Pointer>());
    }
    
    private static class Buffers
    {
        private Buffers() {
            super();
        }
        
        static boolean isBuffer(final Class<?> a1) {
            return Buffer.class.isAssignableFrom(a1);
        }
    }
    
    private static class AWT
    {
        private AWT() {
            super();
        }
        
        static long getWindowID(final Window a1) throws HeadlessException {
            return getComponentID(a1);
        }
        
        static long getComponentID(final Object a1) throws HeadlessException {
            if (GraphicsEnvironment.isHeadless()) {
                throw new HeadlessException("No native windows when headless");
            }
            final Component v1 = (Component)a1;
            if (v1.isLightweight()) {
                throw new IllegalArgumentException("Component must be heavyweight");
            }
            if (!v1.isDisplayable()) {
                throw new IllegalStateException("Component must be displayable");
            }
            if (Platform.isX11() && System.getProperty("java.version").startsWith("1.4") && !v1.isVisible()) {
                throw new IllegalStateException("Component must be visible");
            }
            return Native.getWindowHandle0(v1);
        }
    }
    
    public interface ffi_callback
    {
        void invoke(final long p0, final long p1, final long p2);
    }
}
