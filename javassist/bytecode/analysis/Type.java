package javassist.bytecode.analysis;

import javassist.*;
import java.util.*;

public class Type
{
    private final CtClass clazz;
    private final boolean special;
    private static final Map prims;
    public static final Type DOUBLE;
    public static final Type BOOLEAN;
    public static final Type LONG;
    public static final Type CHAR;
    public static final Type BYTE;
    public static final Type SHORT;
    public static final Type INTEGER;
    public static final Type FLOAT;
    public static final Type VOID;
    public static final Type UNINIT;
    public static final Type RETURN_ADDRESS;
    public static final Type TOP;
    public static final Type BOGUS;
    public static final Type OBJECT;
    public static final Type SERIALIZABLE;
    public static final Type CLONEABLE;
    public static final Type THROWABLE;
    
    public static Type get(final CtClass a1) {
        final Type v1 = Type.prims.get(a1);
        return (v1 != null) ? v1 : new Type(a1);
    }
    
    private static Type lookupType(final String v1) {
        try {
            return new Type(ClassPool.getDefault().get(v1));
        }
        catch (NotFoundException a1) {
            throw new RuntimeException(a1);
        }
    }
    
    Type(final CtClass a1) {
        this(a1, false);
    }
    
    private Type(final CtClass a1, final boolean a2) {
        super();
        this.clazz = a1;
        this.special = a2;
    }
    
    boolean popChanged() {
        return false;
    }
    
    public int getSize() {
        return (this.clazz == CtClass.doubleType || this.clazz == CtClass.longType || this == Type.TOP) ? 2 : 1;
    }
    
    public CtClass getCtClass() {
        return this.clazz;
    }
    
    public boolean isReference() {
        return !this.special && (this.clazz == null || !this.clazz.isPrimitive());
    }
    
    public boolean isSpecial() {
        return this.special;
    }
    
    public boolean isArray() {
        return this.clazz != null && this.clazz.isArray();
    }
    
    public int getDimensions() {
        if (!this.isArray()) {
            return 0;
        }
        String v1;
        int v2;
        int v3;
        for (v1 = this.clazz.getName(), v2 = v1.length() - 1, v3 = 0; v1.charAt(v2) == ']'; v2 -= 2, ++v3) {}
        return v3;
    }
    
    public Type getComponent() {
        if (this.clazz == null || !this.clazz.isArray()) {
            return null;
        }
        CtClass v1;
        try {
            v1 = this.clazz.getComponentType();
        }
        catch (NotFoundException v2) {
            throw new RuntimeException(v2);
        }
        final Type v3 = Type.prims.get(v1);
        return (v3 != null) ? v3 : new Type(v1);
    }
    
    public boolean isAssignableFrom(final Type v2) {
        if (this == v2) {
            return true;
        }
        if ((v2 == Type.UNINIT && this.isReference()) || (this == Type.UNINIT && v2.isReference())) {
            return true;
        }
        if (v2 instanceof MultiType) {
            return ((MultiType)v2).isAssignableTo(this);
        }
        if (v2 instanceof MultiArrayType) {
            return ((MultiArrayType)v2).isAssignableTo(this);
        }
        if (this.clazz == null || this.clazz.isPrimitive()) {
            return false;
        }
        try {
            return v2.clazz.subtypeOf(this.clazz);
        }
        catch (Exception a1) {
            throw new RuntimeException(a1);
        }
    }
    
    public Type merge(final Type v2) {
        if (v2 == this) {
            return this;
        }
        if (v2 == null) {
            return this;
        }
        if (v2 == Type.UNINIT) {
            return this;
        }
        if (this == Type.UNINIT) {
            return v2;
        }
        if (!v2.isReference() || !this.isReference()) {
            return Type.BOGUS;
        }
        if (v2 instanceof MultiType) {
            return v2.merge(this);
        }
        if (v2.isArray() && this.isArray()) {
            return this.mergeArray(v2);
        }
        try {
            return this.mergeClasses(v2);
        }
        catch (NotFoundException a1) {
            throw new RuntimeException(a1);
        }
    }
    
    Type getRootComponent(Type a1) {
        while (a1.isArray()) {
            a1 = a1.getComponent();
        }
        return a1;
    }
    
    private Type createArray(final Type v2, final int v3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Ljavassist/bytecode/analysis/MultiType;
        //     4: ifeq            20
        //     7: new             Ljavassist/bytecode/analysis/MultiArrayType;
        //    10: dup            
        //    11: aload_1         /* v2 */
        //    12: checkcast       Ljavassist/bytecode/analysis/MultiType;
        //    15: iload_2         /* v3 */
        //    16: invokespecial   javassist/bytecode/analysis/MultiArrayType.<init>:(Ljavassist/bytecode/analysis/MultiType;I)V
        //    19: areturn        
        //    20: aload_0         /* v1 */
        //    21: aload_1         /* v2 */
        //    22: getfield        javassist/bytecode/analysis/Type.clazz:Ljavassist/CtClass;
        //    25: invokevirtual   javassist/CtClass.getName:()Ljava/lang/String;
        //    28: iload_2         /* v3 */
        //    29: invokevirtual   javassist/bytecode/analysis/Type.arrayName:(Ljava/lang/String;I)Ljava/lang/String;
        //    32: astore_3        /* v4 */
        //    33: aload_0         /* v1 */
        //    34: aload_1         /* v2 */
        //    35: invokespecial   javassist/bytecode/analysis/Type.getClassPool:(Ljavassist/bytecode/analysis/Type;)Ljavassist/ClassPool;
        //    38: aload_3         /* v4 */
        //    39: invokevirtual   javassist/ClassPool.get:(Ljava/lang/String;)Ljavassist/CtClass;
        //    42: invokestatic    javassist/bytecode/analysis/Type.get:(Ljavassist/CtClass;)Ljavassist/bytecode/analysis/Type;
        //    45: astore          a1
        //    47: goto            62
        //    50: astore          a2
        //    52: new             Ljava/lang/RuntimeException;
        //    55: dup            
        //    56: aload           a2
        //    58: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/Throwable;)V
        //    61: athrow         
        //    62: aload           v5
        //    64: areturn        
        //    StackMapTable: 00 03 14 FF 00 1D 00 04 07 00 02 07 00 02 01 07 00 6C 00 01 07 00 30 FC 00 0B 07 00 02
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                         
        //  -----  -----  -----  -----  -----------------------------
        //  33     47     50     62     Ljavassist/NotFoundException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    String arrayName(String a1, final int a2) {
        int v1 = a1.length();
        final int v2 = v1 + a2 * 2;
        final char[] v3 = new char[v2];
        a1.getChars(0, v1, v3, 0);
        while (v1 < v2) {
            v3[v1++] = '[';
            v3[v1++] = ']';
        }
        a1 = new String(v3);
        return a1;
    }
    
    private ClassPool getClassPool(final Type a1) {
        final ClassPool v1 = a1.clazz.getClassPool();
        return (v1 != null) ? v1 : ClassPool.getDefault();
    }
    
    private Type mergeArray(final Type v-4) {
        final Type rootComponent = this.getRootComponent(v-4);
        final Type rootComponent2 = this.getRootComponent(this);
        final int dimensions = v-4.getDimensions();
        final int v0 = this.getDimensions();
        if (dimensions == v0) {
            final Type a1 = rootComponent2.merge(rootComponent);
            if (a1 == Type.BOGUS) {
                return Type.OBJECT;
            }
            return this.createArray(a1, v0);
        }
        else {
            Type v2;
            int v3;
            if (dimensions < v0) {
                v2 = rootComponent;
                v3 = dimensions;
            }
            else {
                v2 = rootComponent2;
                v3 = v0;
            }
            if (eq(Type.CLONEABLE.clazz, v2.clazz) || eq(Type.SERIALIZABLE.clazz, v2.clazz)) {
                return this.createArray(v2, v3);
            }
            return this.createArray(Type.OBJECT, v3);
        }
    }
    
    private static CtClass findCommonSuperClass(final CtClass v1, final CtClass v2) throws NotFoundException {
        CtClass v3 = v1;
        CtClass v5;
        CtClass v4 = v5 = v2;
        CtClass v6 = v3;
        while (!eq(v3, v4) || v3.getSuperclass() == null) {
            final CtClass a1 = v3.getSuperclass();
            final CtClass a2 = v4.getSuperclass();
            if (a2 == null) {
                v4 = v5;
            }
            else {
                if (a1 != null) {
                    v3 = a1;
                    v4 = a2;
                    continue;
                }
                v3 = v6;
                v6 = v5;
                v5 = v3;
                v3 = v4;
                v4 = v5;
            }
            while (true) {
                v3 = v3.getSuperclass();
                if (v3 == null) {
                    break;
                }
                v6 = v6.getSuperclass();
            }
            for (v3 = v6; !eq(v3, v4); v3 = v3.getSuperclass(), v4 = v4.getSuperclass()) {}
            return v3;
        }
        return v3;
    }
    
    private Type mergeClasses(final Type v2) throws NotFoundException {
        final CtClass v3 = findCommonSuperClass(this.clazz, v2.clazz);
        if (v3.getSuperclass() == null) {
            final Map a1 = this.findCommonInterfaces(v2);
            if (a1.size() == 1) {
                return new Type(a1.values().iterator().next());
            }
            if (a1.size() > 1) {
                return new MultiType(a1);
            }
            return new Type(v3);
        }
        else {
            final Map v4 = this.findExclusiveDeclaredInterfaces(v2, v3);
            if (v4.size() > 0) {
                return new MultiType(v4, new Type(v3));
            }
            return new Type(v3);
        }
    }
    
    private Map findCommonInterfaces(final Type a1) {
        final Map v1 = this.getAllInterfaces(a1.clazz, null);
        final Map v2 = this.getAllInterfaces(this.clazz, null);
        return this.findCommonInterfaces(v1, v2);
    }
    
    private Map findExclusiveDeclaredInterfaces(final Type v1, final CtClass v2) {
        final Map v3 = this.getDeclaredInterfaces(v1.clazz, null);
        final Map v4 = this.getDeclaredInterfaces(this.clazz, null);
        final Map v5 = this.getAllInterfaces(v2, null);
        for (final Object a1 : v5.keySet()) {
            v3.remove(a1);
            v4.remove(a1);
        }
        return this.findCommonInterfaces(v3, v4);
    }
    
    Map findCommonInterfaces(final Map v-4, final Map v-3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     6: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    11: astore_3        /* v-2 */
        //    12: aload_3         /* v-2 */
        //    13: invokeinterface java/util/Iterator.hasNext:()Z
        //    18: ifeq            45
        //    21: aload_1         /* v-4 */
        //    22: aload_3         /* v-2 */
        //    23: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    28: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //    33: ifne            12
        //    36: aload_3         /* v-2 */
        //    37: invokeinterface java/util/Iterator.remove:()V
        //    42: goto            12
        //    45: new             Ljava/util/ArrayList;
        //    48: dup            
        //    49: aload_2         /* v-3 */
        //    50: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //    55: invokespecial   java/util/ArrayList.<init>:(Ljava/util/Collection;)V
        //    58: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //    61: astore_3        /* v-2 */
        //    62: aload_3         /* v-2 */
        //    63: invokeinterface java/util/Iterator.hasNext:()Z
        //    68: ifeq            139
        //    71: aload_3         /* v-2 */
        //    72: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    77: checkcast       Ljavassist/CtClass;
        //    80: astore          v-1
        //    82: aload           v-1
        //    84: invokevirtual   javassist/CtClass.getInterfaces:()[Ljavassist/CtClass;
        //    87: astore          a1
        //    89: goto            104
        //    92: astore          a2
        //    94: new             Ljava/lang/RuntimeException;
        //    97: dup            
        //    98: aload           a2
        //   100: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/Throwable;)V
        //   103: athrow         
        //   104: iconst_0       
        //   105: istore          v1
        //   107: iload           v1
        //   109: aload           v0
        //   111: arraylength    
        //   112: if_icmpge       136
        //   115: aload_2         /* v-3 */
        //   116: aload           v0
        //   118: iload           v1
        //   120: aaload         
        //   121: invokevirtual   javassist/CtClass.getName:()Ljava/lang/String;
        //   124: invokeinterface java/util/Map.remove:(Ljava/lang/Object;)Ljava/lang/Object;
        //   129: pop            
        //   130: iinc            v1, 1
        //   133: goto            107
        //   136: goto            62
        //   139: aload_2         /* v-3 */
        //   140: areturn        
        //    StackMapTable: 00 08 FC 00 0C 07 00 ED 20 10 FF 00 1D 00 05 07 00 02 07 00 23 07 00 23 07 00 ED 07 00 52 00 01 07 00 30 FC 00 0B 07 01 26 FC 00 02 01 F8 00 1C 02
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                         
        //  -----  -----  -----  -----  -----------------------------
        //  82     89     92     104    Ljavassist/NotFoundException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    Map getAllInterfaces(CtClass v-1, Map v0) {
        if (v0 == null) {
            v0 = new HashMap<String, CtClass>();
        }
        if (v-1.isInterface()) {
            v0.put(v-1.getName(), v-1);
        }
        do {
            try {
                final CtClass[] v = v-1.getInterfaces();
                for (int a2 = 0; a2 < v.length; ++a2) {
                    final CtClass a3 = v[a2];
                    v0.put(a3.getName(), a3);
                    this.getAllInterfaces(a3, v0);
                }
                v-1 = v-1.getSuperclass();
            }
            catch (NotFoundException v2) {
                throw new RuntimeException(v2);
            }
        } while (v-1 != null);
        return v0;
    }
    
    Map getDeclaredInterfaces(final CtClass v-3, final Map v-2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: new             Ljava/util/HashMap;
        //     7: dup            
        //     8: invokespecial   java/util/HashMap.<init>:()V
        //    11: astore_2        /* v-2 */
        //    12: aload_1         /* v-3 */
        //    13: invokevirtual   javassist/CtClass.isInterface:()Z
        //    16: ifeq            31
        //    19: aload_2         /* v-2 */
        //    20: aload_1         /* v-3 */
        //    21: invokevirtual   javassist/CtClass.getName:()Ljava/lang/String;
        //    24: aload_1         /* v-3 */
        //    25: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    30: pop            
        //    31: aload_1         /* v-3 */
        //    32: invokevirtual   javassist/CtClass.getInterfaces:()[Ljavassist/CtClass;
        //    35: astore_3        /* a1 */
        //    36: goto            51
        //    39: astore          a2
        //    41: new             Ljava/lang/RuntimeException;
        //    44: dup            
        //    45: aload           a2
        //    47: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/Throwable;)V
        //    50: athrow         
        //    51: iconst_0       
        //    52: istore          v0
        //    54: iload           v0
        //    56: aload_3         /* v-1 */
        //    57: arraylength    
        //    58: if_icmpge       95
        //    61: aload_3         /* v-1 */
        //    62: iload           v0
        //    64: aaload         
        //    65: astore          v1
        //    67: aload_2         /* v-2 */
        //    68: aload           v1
        //    70: invokevirtual   javassist/CtClass.getName:()Ljava/lang/String;
        //    73: aload           v1
        //    75: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    80: pop            
        //    81: aload_0         /* v-4 */
        //    82: aload           v1
        //    84: aload_2         /* v-2 */
        //    85: invokevirtual   javassist/bytecode/analysis/Type.getDeclaredInterfaces:(Ljavassist/CtClass;Ljava/util/Map;)Ljava/util/Map;
        //    88: pop            
        //    89: iinc            v0, 1
        //    92: goto            54
        //    95: aload_2         /* v-2 */
        //    96: areturn        
        //    StackMapTable: 00 06 0C 12 47 07 00 30 FC 00 0B 07 01 26 FC 00 02 01 FA 00 28
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                         
        //  -----  -----  -----  -----  -----------------------------
        //  31     36     39     51     Ljavassist/NotFoundException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof Type && a1.getClass() == this.getClass() && eq(this.clazz, ((Type)a1).clazz);
    }
    
    static boolean eq(final CtClass a1, final CtClass a2) {
        return a1 == a2 || (a1 != null && a2 != null && a1.getName().equals(a2.getName()));
    }
    
    @Override
    public String toString() {
        if (this == Type.BOGUS) {
            return "BOGUS";
        }
        if (this == Type.UNINIT) {
            return "UNINIT";
        }
        if (this == Type.RETURN_ADDRESS) {
            return "RETURN ADDRESS";
        }
        if (this == Type.TOP) {
            return "TOP";
        }
        return (this.clazz == null) ? "null" : this.clazz.getName();
    }
    
    static {
        prims = new IdentityHashMap();
        DOUBLE = new Type(CtClass.doubleType);
        BOOLEAN = new Type(CtClass.booleanType);
        LONG = new Type(CtClass.longType);
        CHAR = new Type(CtClass.charType);
        BYTE = new Type(CtClass.byteType);
        SHORT = new Type(CtClass.shortType);
        INTEGER = new Type(CtClass.intType);
        FLOAT = new Type(CtClass.floatType);
        VOID = new Type(CtClass.voidType);
        UNINIT = new Type(null);
        RETURN_ADDRESS = new Type(null, true);
        TOP = new Type(null, true);
        BOGUS = new Type(null, true);
        OBJECT = lookupType("java.lang.Object");
        SERIALIZABLE = lookupType("java.io.Serializable");
        CLONEABLE = lookupType("java.lang.Cloneable");
        THROWABLE = lookupType("java.lang.Throwable");
        Type.prims.put(CtClass.doubleType, Type.DOUBLE);
        Type.prims.put(CtClass.longType, Type.LONG);
        Type.prims.put(CtClass.charType, Type.CHAR);
        Type.prims.put(CtClass.shortType, Type.SHORT);
        Type.prims.put(CtClass.intType, Type.INTEGER);
        Type.prims.put(CtClass.floatType, Type.FLOAT);
        Type.prims.put(CtClass.byteType, Type.BYTE);
        Type.prims.put(CtClass.booleanType, Type.BOOLEAN);
        Type.prims.put(CtClass.voidType, Type.VOID);
    }
}
