package javassist.bytecode;

public class AccessFlag
{
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int PROTECTED = 4;
    public static final int STATIC = 8;
    public static final int FINAL = 16;
    public static final int SYNCHRONIZED = 32;
    public static final int VOLATILE = 64;
    public static final int BRIDGE = 64;
    public static final int TRANSIENT = 128;
    public static final int VARARGS = 128;
    public static final int NATIVE = 256;
    public static final int INTERFACE = 512;
    public static final int ABSTRACT = 1024;
    public static final int STRICT = 2048;
    public static final int SYNTHETIC = 4096;
    public static final int ANNOTATION = 8192;
    public static final int ENUM = 16384;
    public static final int MANDATED = 32768;
    public static final int SUPER = 32;
    
    public AccessFlag() {
        super();
    }
    
    public static int setPublic(final int a1) {
        return (a1 & 0xFFFFFFF9) | 0x1;
    }
    
    public static int setProtected(final int a1) {
        return (a1 & 0xFFFFFFFC) | 0x4;
    }
    
    public static int setPrivate(final int a1) {
        return (a1 & 0xFFFFFFFA) | 0x2;
    }
    
    public static int setPackage(final int a1) {
        return a1 & 0xFFFFFFF8;
    }
    
    public static boolean isPublic(final int a1) {
        return (a1 & 0x1) != 0x0;
    }
    
    public static boolean isProtected(final int a1) {
        return (a1 & 0x4) != 0x0;
    }
    
    public static boolean isPrivate(final int a1) {
        return (a1 & 0x2) != 0x0;
    }
    
    public static boolean isPackage(final int a1) {
        return (a1 & 0x7) == 0x0;
    }
    
    public static int clear(final int a1, final int a2) {
        return a1 & ~a2;
    }
    
    public static int of(final int a1) {
        return a1;
    }
    
    public static int toModifier(final int a1) {
        return a1;
    }
}
