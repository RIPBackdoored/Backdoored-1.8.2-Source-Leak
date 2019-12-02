package com.sun.jna;

import java.nio.*;
import java.lang.reflect.*;
import java.util.*;

public abstract class Structure
{
    public static final int ALIGN_DEFAULT = 0;
    public static final int ALIGN_NONE = 1;
    public static final int ALIGN_GNUC = 2;
    public static final int ALIGN_MSVC = 3;
    protected static final int CALCULATE_SIZE = -1;
    static final Map<Class<?>, LayoutInfo> layoutInfo;
    static final Map<Class<?>, List<String>> fieldOrder;
    private Pointer memory;
    private int size;
    private int alignType;
    private String encoding;
    private int actualAlignType;
    private int structAlignment;
    private Map<String, StructField> structFields;
    private final Map<String, Object> nativeStrings;
    private TypeMapper typeMapper;
    private long typeInfo;
    private boolean autoRead;
    private boolean autoWrite;
    private Structure[] array;
    private boolean readCalled;
    private static final ThreadLocal<Map<Pointer, Structure>> reads;
    private static final ThreadLocal<Set<Structure>> busy;
    private static final Pointer PLACEHOLDER_MEMORY;
    
    protected Structure() {
        this(0);
    }
    
    protected Structure(final TypeMapper a1) {
        this(null, 0, a1);
    }
    
    protected Structure(final int a1) {
        this(null, a1);
    }
    
    protected Structure(final int a1, final TypeMapper a2) {
        this(null, a1, a2);
    }
    
    protected Structure(final Pointer a1) {
        this(a1, 0);
    }
    
    protected Structure(final Pointer a1, final int a2) {
        this(a1, a2, null);
    }
    
    protected Structure(final Pointer a1, final int a2, final TypeMapper a3) {
        super();
        this.size = -1;
        this.nativeStrings = new HashMap<String, Object>();
        this.autoRead = true;
        this.autoWrite = true;
        this.setAlignType(a2);
        this.setStringEncoding(Native.getStringEncoding(this.getClass()));
        this.initializeTypeMapper(a3);
        this.validateFields();
        if (a1 != null) {
            this.useMemory(a1, 0, true);
        }
        else {
            this.allocateMemory(-1);
        }
        this.initializeFields();
    }
    
    Map<String, StructField> fields() {
        return this.structFields;
    }
    
    TypeMapper getTypeMapper() {
        return this.typeMapper;
    }
    
    private void initializeTypeMapper(TypeMapper a1) {
        if (a1 == null) {
            a1 = Native.getTypeMapper(this.getClass());
        }
        this.typeMapper = a1;
        this.layoutChanged();
    }
    
    private void layoutChanged() {
        if (this.size != -1) {
            this.size = -1;
            if (this.memory instanceof AutoAllocated) {
                this.memory = null;
            }
            this.ensureAllocated();
        }
    }
    
    protected void setStringEncoding(final String a1) {
        this.encoding = a1;
    }
    
    protected String getStringEncoding() {
        return this.encoding;
    }
    
    protected void setAlignType(int a1) {
        this.alignType = a1;
        if (a1 == 0) {
            a1 = Native.getStructureAlignment(this.getClass());
            if (a1 == 0) {
                if (Platform.isWindows()) {
                    a1 = 3;
                }
                else {
                    a1 = 2;
                }
            }
        }
        this.actualAlignType = a1;
        this.layoutChanged();
    }
    
    protected Memory autoAllocate(final int a1) {
        return new AutoAllocated(a1);
    }
    
    protected void useMemory(final Pointer a1) {
        this.useMemory(a1, 0);
    }
    
    protected void useMemory(final Pointer a1, final int a2) {
        this.useMemory(a1, a2, false);
    }
    
    void useMemory(final Pointer v1, final int v2, final boolean v3) {
        try {
            this.nativeStrings.clear();
            if (this instanceof ByValue && !v3) {
                final byte[] a1 = new byte[this.size()];
                v1.read(0L, a1, 0, a1.length);
                this.memory.write(0L, a1, 0, a1.length);
            }
            else {
                this.memory = v1.share(v2);
                if (this.size == -1) {
                    this.size = this.calculateSize(false);
                }
                if (this.size != -1) {
                    this.memory = v1.share(v2, this.size);
                }
            }
            this.array = null;
            this.readCalled = false;
        }
        catch (IndexOutOfBoundsException a2) {
            throw new IllegalArgumentException("Structure exceeds provided memory bounds", a2);
        }
    }
    
    protected void ensureAllocated() {
        this.ensureAllocated(false);
    }
    
    private void ensureAllocated(final boolean v2) {
        if (this.memory == null) {
            this.allocateMemory(v2);
        }
        else if (this.size == -1) {
            this.size = this.calculateSize(true, v2);
            if (!(this.memory instanceof AutoAllocated)) {
                try {
                    this.memory = this.memory.share(0L, this.size);
                }
                catch (IndexOutOfBoundsException a1) {
                    throw new IllegalArgumentException("Structure exceeds provided memory bounds", a1);
                }
            }
        }
    }
    
    protected void allocateMemory() {
        this.allocateMemory(false);
    }
    
    private void allocateMemory(final boolean a1) {
        this.allocateMemory(this.calculateSize(true, a1));
    }
    
    protected void allocateMemory(int a1) {
        if (a1 == -1) {
            a1 = this.calculateSize(false);
        }
        else if (a1 <= 0) {
            throw new IllegalArgumentException("Structure size must be greater than zero: " + a1);
        }
        if (a1 != -1) {
            if (this.memory == null || this.memory instanceof AutoAllocated) {
                this.memory = this.autoAllocate(a1);
            }
            this.size = a1;
        }
    }
    
    public int size() {
        this.ensureAllocated();
        return this.size;
    }
    
    public void clear() {
        this.ensureAllocated();
        this.memory.clear(this.size());
    }
    
    public Pointer getPointer() {
        this.ensureAllocated();
        return this.memory;
    }
    
    static Set<Structure> busy() {
        return Structure.busy.get();
    }
    
    static Map<Pointer, Structure> reading() {
        return Structure.reads.get();
    }
    
    void conditionalAutoRead() {
        if (!this.readCalled) {
            this.autoRead();
        }
    }
    
    public void read() {
        if (this.memory == Structure.PLACEHOLDER_MEMORY) {
            return;
        }
        this.readCalled = true;
        this.ensureAllocated();
        if (busy().contains(this)) {
            return;
        }
        busy().add(this);
        if (this instanceof ByReference) {
            reading().put(this.getPointer(), this);
        }
        try {
            for (final StructField v1 : this.fields().values()) {
                this.readField(v1);
            }
        }
        finally {
            busy().remove(this);
            if (reading().get(this.getPointer()) == this) {
                reading().remove(this.getPointer());
            }
        }
    }
    
    protected int fieldOffset(final String a1) {
        this.ensureAllocated();
        final StructField v1 = this.fields().get(a1);
        if (v1 == null) {
            throw new IllegalArgumentException("No such field: " + a1);
        }
        return v1.offset;
    }
    
    public Object readField(final String a1) {
        this.ensureAllocated();
        final StructField v1 = this.fields().get(a1);
        if (v1 == null) {
            throw new IllegalArgumentException("No such field: " + a1);
        }
        return this.readField(v1);
    }
    
    Object getFieldValue(final Field v2) {
        try {
            return v2.get(this);
        }
        catch (Exception a1) {
            throw new Error("Exception reading field '" + v2.getName() + "' in " + this.getClass(), a1);
        }
    }
    
    void setFieldValue(final Field a1, final Object a2) {
        this.setFieldValue(a1, a2, false);
    }
    
    private void setFieldValue(final Field v1, final Object v2, final boolean v3) {
        try {
            v1.set(this, v2);
        }
        catch (IllegalAccessException a2) {
            final int a1 = v1.getModifiers();
            if (!Modifier.isFinal(a1)) {
                throw new Error("Unexpectedly unable to write to field '" + v1.getName() + "' within " + this.getClass(), a2);
            }
            if (v3) {
                throw new UnsupportedOperationException("This VM does not support Structures with final fields (field '" + v1.getName() + "' within " + this.getClass() + ")", a2);
            }
            throw new UnsupportedOperationException("Attempt to write to read-only field '" + v1.getName() + "' within " + this.getClass(), a2);
        }
    }
    
    static Structure updateStructureByReference(final Class<?> a2, Structure a3, final Pointer v1) {
        if (v1 == null) {
            a3 = null;
        }
        else if (a3 == null || !v1.equals(a3.getPointer())) {
            final Structure a4 = reading().get(v1);
            if (a4 != null && a2.equals(a4.getClass())) {
                a3 = a4;
                a3.autoRead();
            }
            else {
                a3 = newInstance(a2, v1);
                a3.conditionalAutoRead();
            }
        }
        else {
            a3.autoRead();
        }
        return a3;
    }
    
    protected Object readField(final StructField v-4) {
        final int offset = v-4.offset;
        Class<?> v-5 = v-4.type;
        final FromNativeConverter readConverter = v-4.readConverter;
        if (readConverter != null) {
            v-5 = readConverter.nativeType();
        }
        final Object v0 = (Structure.class.isAssignableFrom(v-5) || Callback.class.isAssignableFrom(v-5) || (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(v-5)) || Pointer.class.isAssignableFrom(v-5) || NativeMapped.class.isAssignableFrom(v-5) || v-5.isArray()) ? this.getFieldValue(v-4.field) : null;
        Object v2;
        if (v-5 == String.class) {
            final Pointer a1 = this.memory.getPointer(offset);
            v2 = ((a1 == null) ? null : a1.getString(0L, this.encoding));
        }
        else {
            v2 = this.memory.getValue(offset, v-5, v0);
        }
        if (readConverter != null) {
            v2 = readConverter.fromNative(v2, v-4.context);
            if (v0 != null && v0.equals(v2)) {
                v2 = v0;
            }
        }
        if (v-5.equals(String.class) || v-5.equals(WString.class)) {
            this.nativeStrings.put(v-4.name + ".ptr", this.memory.getPointer(offset));
            this.nativeStrings.put(v-4.name + ".val", v2);
        }
        this.setFieldValue(v-4.field, v2, true);
        return v2;
    }
    
    public void write() {
        if (this.memory == Structure.PLACEHOLDER_MEMORY) {
            return;
        }
        this.ensureAllocated();
        if (this instanceof ByValue) {
            this.getTypeInfo();
        }
        if (busy().contains(this)) {
            return;
        }
        busy().add(this);
        try {
            for (final StructField v1 : this.fields().values()) {
                if (!v1.isVolatile) {
                    this.writeField(v1);
                }
            }
        }
        finally {
            busy().remove(this);
        }
    }
    
    public void writeField(final String a1) {
        this.ensureAllocated();
        final StructField v1 = this.fields().get(a1);
        if (v1 == null) {
            throw new IllegalArgumentException("No such field: " + a1);
        }
        this.writeField(v1);
    }
    
    public void writeField(final String a1, final Object a2) {
        this.ensureAllocated();
        final StructField v1 = this.fields().get(a1);
        if (v1 == null) {
            throw new IllegalArgumentException("No such field: " + a1);
        }
        this.setFieldValue(v1.field, a2);
        this.writeField(v1);
    }
    
    protected void writeField(final StructField v-4) {
        if (v-4.isReadOnly) {
            return;
        }
        final int offset = v-4.offset;
        Object v-5 = this.getFieldValue(v-4.field);
        Class<?> v-6 = v-4.type;
        final ToNativeConverter v0 = v-4.writeConverter;
        if (v0 != null) {
            v-5 = v0.toNative(v-5, new StructureWriteContext(this, v-4.field));
            v-6 = v0.nativeType();
        }
        if (String.class == v-6 || WString.class == v-6) {
            final boolean v2 = v-6 == WString.class;
            if (v-5 != null) {
                if (this.nativeStrings.containsKey(v-4.name + ".ptr") && v-5.equals(this.nativeStrings.get(v-4.name + ".val"))) {
                    return;
                }
                final NativeString a1 = v2 ? new NativeString(v-5.toString(), true) : new NativeString(v-5.toString(), this.encoding);
                this.nativeStrings.put(v-4.name, a1);
                v-5 = a1.getPointer();
            }
            else {
                this.nativeStrings.remove(v-4.name);
            }
            this.nativeStrings.remove(v-4.name + ".ptr");
            this.nativeStrings.remove(v-4.name + ".val");
        }
        try {
            this.memory.setValue(offset, v-5, v-6);
        }
        catch (IllegalArgumentException v4) {
            final String v3 = "Structure field \"" + v-4.name + "\" was declared as " + v-4.type + ((v-4.type == v-6) ? "" : (" (native type " + v-6 + ")")) + ", which is not supported within a Structure";
            throw new IllegalArgumentException(v3, v4);
        }
    }
    
    protected abstract List<String> getFieldOrder();
    
    @Deprecated
    protected final void setFieldOrder(final String[] a1) {
        throw new Error("This method is obsolete, use getFieldOrder() instead");
    }
    
    protected void sortFields(final List<Field> v-2, final List<String> v-1) {
        for (int v0 = 0; v0 < v-1.size(); ++v0) {
            final String v2 = v-1.get(v0);
            for (int a2 = 0; a2 < v-2.size(); ++a2) {
                final Field a3 = v-2.get(a2);
                if (v2.equals(a3.getName())) {
                    Collections.swap(v-2, v0, a2);
                    break;
                }
            }
        }
    }
    
    protected List<Field> getFieldList() {
        final List<Field> list = new ArrayList<Field>();
        for (Class<?> clazz = this.getClass(); !clazz.equals(Structure.class); clazz = clazz.getSuperclass()) {
            final List<Field> list2 = new ArrayList<Field>();
            final Field[] declaredFields = clazz.getDeclaredFields();
            for (int v0 = 0; v0 < declaredFields.length; ++v0) {
                final int v2 = declaredFields[v0].getModifiers();
                if (!Modifier.isStatic(v2)) {
                    if (Modifier.isPublic(v2)) {
                        list2.add(declaredFields[v0]);
                    }
                }
            }
            list.addAll(0, list2);
        }
        return list;
    }
    
    private List<String> fieldOrder() {
        final Class<?> class1 = this.getClass();
        synchronized (Structure.fieldOrder) {
            List<String> v1 = Structure.fieldOrder.get(class1);
            if (v1 == null) {
                v1 = this.getFieldOrder();
                Structure.fieldOrder.put(class1, v1);
            }
            return v1;
        }
    }
    
    public static List<String> createFieldsOrder(final List<String> a1, final String... a2) {
        return createFieldsOrder(a1, Arrays.asList(a2));
    }
    
    public static List<String> createFieldsOrder(final List<String> a1, final List<String> a2) {
        final List<String> v1 = new ArrayList<String>(a1.size() + a2.size());
        v1.addAll(a1);
        v1.addAll(a2);
        return Collections.unmodifiableList((List<? extends String>)v1);
    }
    
    public static List<String> createFieldsOrder(final String a1) {
        return Collections.unmodifiableList((List<? extends String>)Collections.singletonList((T)a1));
    }
    
    public static List<String> createFieldsOrder(final String... a1) {
        return Collections.unmodifiableList((List<? extends String>)Arrays.asList((T[])a1));
    }
    
    private static <T extends java.lang.Object> List<T> sort(final Collection<? extends T> a1) {
        final List<T> v1 = new ArrayList<T>(a1);
        Collections.sort(v1);
        return v1;
    }
    
    protected List<Field> getFields(final boolean v2) {
        final List<Field> v3 = this.getFieldList();
        final Set<String> v4 = new HashSet<String>();
        for (final Field a1 : v3) {
            v4.add(a1.getName());
        }
        final List<String> v5 = this.fieldOrder();
        if (v5.size() != v3.size() && v3.size() > 1) {
            if (v2) {
                throw new Error("Structure.getFieldOrder() on " + this.getClass() + " does not provide enough names [" + v5.size() + "] (" + sort((Collection<?>)v5) + ") to match declared fields [" + v3.size() + "] (" + sort((Collection<?>)v4) + ")");
            }
            return null;
        }
        else {
            final Set<String> v6 = new HashSet<String>(v5);
            if (!v6.equals(v4)) {
                throw new Error("Structure.getFieldOrder() on " + this.getClass() + " returns names (" + sort((Collection<?>)v5) + ") which do not match declared field names (" + sort((Collection<?>)v4) + ")");
            }
            this.sortFields(v3, v5);
            return v3;
        }
    }
    
    protected int calculateSize(final boolean a1) {
        return this.calculateSize(a1, false);
    }
    
    static int size(final Class<?> a1) {
        return size(a1, null);
    }
    
    static int size(final Class<?> a2, final Structure v1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: astore_3       
        //     5: monitorenter   
        //     6: getstatic       com/sun/jna/Structure.layoutInfo:Ljava/util/Map;
        //     9: aload_0         /* a2 */
        //    10: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    15: checkcast       Lcom/sun/jna/Structure$LayoutInfo;
        //    18: astore_2        /* a1 */
        //    19: aload_3        
        //    20: monitorexit    
        //    21: goto            31
        //    24: astore          4
        //    26: aload_3        
        //    27: monitorexit    
        //    28: aload           4
        //    30: athrow         
        //    31: aload_2         /* v2 */
        //    32: ifnull          49
        //    35: aload_2         /* v2 */
        //    36: invokestatic    com/sun/jna/Structure$LayoutInfo.access$000:(Lcom/sun/jna/Structure$LayoutInfo;)Z
        //    39: ifne            49
        //    42: aload_2         /* v2 */
        //    43: invokestatic    com/sun/jna/Structure$LayoutInfo.access$100:(Lcom/sun/jna/Structure$LayoutInfo;)I
        //    46: goto            50
        //    49: iconst_m1      
        //    50: istore_3        /* v3 */
        //    51: iload_3         /* v3 */
        //    52: iconst_m1      
        //    53: if_icmpne       73
        //    56: aload_1         /* v1 */
        //    57: ifnonnull       68
        //    60: aload_0         /* a2 */
        //    61: getstatic       com/sun/jna/Structure.PLACEHOLDER_MEMORY:Lcom/sun/jna/Pointer;
        //    64: invokestatic    com/sun/jna/Structure.newInstance:(Ljava/lang/Class;Lcom/sun/jna/Pointer;)Lcom/sun/jna/Structure;
        //    67: astore_1        /* v1 */
        //    68: aload_1         /* v1 */
        //    69: invokevirtual   com/sun/jna/Structure.size:()I
        //    72: istore_3        /* v3 */
        //    73: iload_3         /* v3 */
        //    74: ireturn        
        //    Signature:
        //  (Ljava/lang/Class<*>;Lcom/sun/jna/Structure;)I
        //    StackMapTable: 00 06 FF 00 18 00 04 07 01 AF 07 00 02 00 07 00 04 00 01 07 01 4F FF 00 06 00 03 07 01 AF 07 00 02 07 00 10 00 00 11 40 01 FC 00 11 01 04
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  ----
        //  6      21     24     31     Any
        //  24     28     24     31     Any
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    int calculateSize(final boolean v1, final boolean v2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: aload_0         /* a2 */
        //     3: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //     6: astore          v4
        //     8: getstatic       com/sun/jna/Structure.layoutInfo:Ljava/util/Map;
        //    11: dup            
        //    12: astore          6
        //    14: monitorenter   
        //    15: getstatic       com/sun/jna/Structure.layoutInfo:Ljava/util/Map;
        //    18: aload           v4
        //    20: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    25: checkcast       Lcom/sun/jna/Structure$LayoutInfo;
        //    28: astore          a1
        //    30: aload           6
        //    32: monitorexit    
        //    33: goto            44
        //    36: astore          7
        //    38: aload           6
        //    40: monitorexit    
        //    41: aload           7
        //    43: athrow         
        //    44: aload           v5
        //    46: ifnull          73
        //    49: aload_0         /* a2 */
        //    50: getfield        com/sun/jna/Structure.alignType:I
        //    53: aload           v5
        //    55: invokestatic    com/sun/jna/Structure$LayoutInfo.access$200:(Lcom/sun/jna/Structure$LayoutInfo;)I
        //    58: if_icmpne       73
        //    61: aload_0         /* a2 */
        //    62: getfield        com/sun/jna/Structure.typeMapper:Lcom/sun/jna/TypeMapper;
        //    65: aload           v5
        //    67: invokestatic    com/sun/jna/Structure$LayoutInfo.access$300:(Lcom/sun/jna/Structure$LayoutInfo;)Lcom/sun/jna/TypeMapper;
        //    70: if_acmpeq       81
        //    73: aload_0         /* a2 */
        //    74: iload_1         /* v1 */
        //    75: iload_2         /* v2 */
        //    76: invokespecial   com/sun/jna/Structure.deriveLayout:(ZZ)Lcom/sun/jna/Structure$LayoutInfo;
        //    79: astore          v5
        //    81: aload           v5
        //    83: ifnull          179
        //    86: aload_0         /* a2 */
        //    87: aload           v5
        //    89: invokestatic    com/sun/jna/Structure$LayoutInfo.access$400:(Lcom/sun/jna/Structure$LayoutInfo;)I
        //    92: putfield        com/sun/jna/Structure.structAlignment:I
        //    95: aload_0         /* a2 */
        //    96: aload           v5
        //    98: invokestatic    com/sun/jna/Structure$LayoutInfo.access$500:(Lcom/sun/jna/Structure$LayoutInfo;)Ljava/util/Map;
        //   101: putfield        com/sun/jna/Structure.structFields:Ljava/util/Map;
        //   104: aload           v5
        //   106: invokestatic    com/sun/jna/Structure$LayoutInfo.access$000:(Lcom/sun/jna/Structure$LayoutInfo;)Z
        //   109: ifne            173
        //   112: getstatic       com/sun/jna/Structure.layoutInfo:Ljava/util/Map;
        //   115: dup            
        //   116: astore          6
        //   118: monitorenter   
        //   119: getstatic       com/sun/jna/Structure.layoutInfo:Ljava/util/Map;
        //   122: aload           v4
        //   124: invokeinterface java/util/Map.containsKey:(Ljava/lang/Object;)Z
        //   129: ifeq            146
        //   132: aload_0         /* a2 */
        //   133: getfield        com/sun/jna/Structure.alignType:I
        //   136: ifne            146
        //   139: aload_0         /* a2 */
        //   140: getfield        com/sun/jna/Structure.typeMapper:Lcom/sun/jna/TypeMapper;
        //   143: ifnull          159
        //   146: getstatic       com/sun/jna/Structure.layoutInfo:Ljava/util/Map;
        //   149: aload           v4
        //   151: aload           v5
        //   153: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   158: pop            
        //   159: aload           6
        //   161: monitorexit    
        //   162: goto            173
        //   165: astore          8
        //   167: aload           6
        //   169: monitorexit    
        //   170: aload           8
        //   172: athrow         
        //   173: aload           v5
        //   175: invokestatic    com/sun/jna/Structure$LayoutInfo.access$100:(Lcom/sun/jna/Structure$LayoutInfo;)I
        //   178: istore_3        /* v3 */
        //   179: iload_3         /* v3 */
        //   180: ireturn        
        //    StackMapTable: 00 09 FF 00 24 00 07 07 00 02 01 01 01 07 01 AF 00 07 00 04 00 01 07 01 4F FF 00 07 00 06 07 00 02 01 01 01 07 01 AF 07 00 10 00 00 1C 07 FC 00 40 07 00 04 0C 45 07 01 4F FA 00 07 05
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type
        //  -----  -----  -----  -----  ----
        //  15     33     36     44     Any
        //  36     41     36     44     Any
        //  119    162    165    173    Any
        //  165    170    165    173    Any
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void validateField(final String v-1, final Class<?> v0) {
        if (this.typeMapper != null) {
            final ToNativeConverter a1 = this.typeMapper.getToNativeConverter(v0);
            if (a1 != null) {
                this.validateField(v-1, a1.nativeType());
                return;
            }
        }
        if (v0.isArray()) {
            this.validateField(v-1, v0.getComponentType());
        }
        else {
            try {
                this.getNativeSize(v0);
            }
            catch (IllegalArgumentException v) {
                final String a2 = "Invalid Structure field in " + this.getClass() + ", field name '" + v-1 + "' (" + v0 + "): " + v.getMessage();
                throw new IllegalArgumentException(a2, v);
            }
        }
    }
    
    private void validateFields() {
        final List<Field> fieldList = this.getFieldList();
        for (final Field v1 : fieldList) {
            this.validateField(v1.getName(), v1.getType());
        }
    }
    
    private LayoutInfo deriveLayout(final boolean v-15, final boolean v-14) {
        int max = 0;
        final List<Field> fields = this.getFields(v-15);
        if (fields == null) {
            return null;
        }
        final LayoutInfo layoutInfo = new LayoutInfo();
        layoutInfo.alignType = this.alignType;
        layoutInfo.typeMapper = this.typeMapper;
        boolean v4 = true;
        for (final Field a3 : fields) {
            final int modifiers = a3.getModifiers();
            final Class<?> type = a3.getType();
            if (type.isArray()) {
                layoutInfo.variable = true;
            }
            final StructField a4 = new StructField();
            a4.isVolatile = Modifier.isVolatile(modifiers);
            a4.isReadOnly = Modifier.isFinal(modifiers);
            if (a4.isReadOnly) {
                if (!Platform.RO_FIELDS) {
                    throw new IllegalArgumentException("This VM does not support read-only fields (field '" + a3.getName() + "' within " + this.getClass() + ")");
                }
                a3.setAccessible(true);
            }
            a4.field = a3;
            a4.name = a3.getName();
            a4.type = type;
            if (Callback.class.isAssignableFrom(type) && !type.isInterface()) {
                throw new IllegalArgumentException("Structure Callback field '" + a3.getName() + "' must be an interface");
            }
            if (type.isArray() && Structure.class.equals(type.getComponentType())) {
                final String a1 = "Nested Structure arrays must use a derived Structure type so that the size of the elements can be determined";
                throw new IllegalArgumentException(a1);
            }
            int nativeAlignment = 1;
            if (Modifier.isPublic(a3.getModifiers())) {
                Object o = this.getFieldValue(a4.field);
                if (o == null && type.isArray()) {
                    if (v-15) {
                        throw new IllegalStateException("Array fields must be initialized");
                    }
                    return null;
                }
                else {
                    Class<?> nativeType = type;
                    if (NativeMapped.class.isAssignableFrom(type)) {
                        final NativeMappedConverter a2 = NativeMappedConverter.getInstance(type);
                        nativeType = a2.nativeType();
                        a4.writeConverter = a2;
                        a4.readConverter = a2;
                        a4.context = new StructureReadContext(this, a3);
                    }
                    else if (this.typeMapper != null) {
                        final ToNativeConverter toNativeConverter = this.typeMapper.getToNativeConverter(type);
                        final FromNativeConverter v0 = this.typeMapper.getFromNativeConverter(type);
                        if (toNativeConverter != null && v0 != null) {
                            o = toNativeConverter.toNative(o, new StructureWriteContext(this, a4.field));
                            nativeType = ((o != null) ? o.getClass() : Pointer.class);
                            a4.writeConverter = toNativeConverter;
                            a4.readConverter = v0;
                            a4.context = new StructureReadContext(this, a3);
                        }
                        else if (toNativeConverter != null || v0 != null) {
                            final String v2 = "Structures require bidirectional type conversion for " + type;
                            throw new IllegalArgumentException(v2);
                        }
                    }
                    if (o == null) {
                        o = this.initializeField(a4.field, type);
                    }
                    try {
                        a4.size = this.getNativeSize(nativeType, o);
                        nativeAlignment = this.getNativeAlignment(nativeType, o, v4);
                    }
                    catch (IllegalArgumentException ex) {
                        if (!v-15 && this.typeMapper == null) {
                            return null;
                        }
                        final String v3 = "Invalid Structure field in " + this.getClass() + ", field name '" + a4.name + "' (" + a4.type + "): " + ex.getMessage();
                        throw new IllegalArgumentException(v3, ex);
                    }
                    if (nativeAlignment == 0) {
                        throw new Error("Field alignment is zero for field '" + a4.name + "' within " + this.getClass());
                    }
                    layoutInfo.alignment = Math.max(layoutInfo.alignment, nativeAlignment);
                    if (max % nativeAlignment != 0) {
                        max += nativeAlignment - max % nativeAlignment;
                    }
                    if (this instanceof Union) {
                        a4.offset = 0;
                        max = Math.max(max, a4.size);
                    }
                    else {
                        a4.offset = max;
                        max += a4.size;
                    }
                    layoutInfo.fields.put(a4.name, a4);
                    if (layoutInfo.typeInfoField == null || layoutInfo.typeInfoField.size < a4.size || (layoutInfo.typeInfoField.size == a4.size && Structure.class.isAssignableFrom(a4.type))) {
                        layoutInfo.typeInfoField = a4;
                    }
                }
            }
            v4 = false;
        }
        if (max > 0) {
            final int addPadding = this.addPadding(max, layoutInfo.alignment);
            if (this instanceof ByValue && !v-14) {
                this.getTypeInfo();
            }
            layoutInfo.size = addPadding;
            return layoutInfo;
        }
        throw new IllegalArgumentException("Structure " + this.getClass() + " has unknown or zero size (ensure all fields are public)");
    }
    
    private void initializeFields() {
        final List<Field> fieldList = this.getFieldList();
        for (final Field v0 : fieldList) {
            try {
                final Object v2 = v0.get(this);
                if (v2 != null) {
                    continue;
                }
                this.initializeField(v0, v0.getType());
            }
            catch (Exception v3) {
                throw new Error("Exception reading field '" + v0.getName() + "' in " + this.getClass(), v3);
            }
        }
    }
    
    private Object initializeField(final Field v-2, final Class<?> v-1) {
        Object v0 = null;
        if (Structure.class.isAssignableFrom(v-1) && !ByReference.class.isAssignableFrom(v-1)) {
            try {
                v0 = newInstance(v-1, Structure.PLACEHOLDER_MEMORY);
                this.setFieldValue(v-2, v0);
                return v0;
            }
            catch (IllegalArgumentException a2) {
                final String a1 = "Can't determine size of nested structure";
                throw new IllegalArgumentException(a1, a2);
            }
        }
        if (NativeMapped.class.isAssignableFrom(v-1)) {
            final NativeMappedConverter v2 = NativeMappedConverter.getInstance(v-1);
            v0 = v2.defaultValue();
            this.setFieldValue(v-2, v0);
        }
        return v0;
    }
    
    private int addPadding(final int a1) {
        return this.addPadding(a1, this.structAlignment);
    }
    
    private int addPadding(int a1, final int a2) {
        if (this.actualAlignType != 1 && a1 % a2 != 0) {
            a1 += a2 - a1 % a2;
        }
        return a1;
    }
    
    protected int getStructAlignment() {
        if (this.size == -1) {
            this.calculateSize(true);
        }
        return this.structAlignment;
    }
    
    protected int getNativeAlignment(Class<?> a3, Object v1, final boolean v2) {
        int v3 = 1;
        if (NativeMapped.class.isAssignableFrom(a3)) {
            final NativeMappedConverter a4 = NativeMappedConverter.getInstance(a3);
            a3 = a4.nativeType();
            v1 = a4.toNative(v1, new ToNativeContext());
        }
        final int v4 = Native.getNativeSize(a3, v1);
        if (a3.isPrimitive() || Long.class == a3 || Integer.class == a3 || Short.class == a3 || Character.class == a3 || Byte.class == a3 || Boolean.class == a3 || Float.class == a3 || Double.class == a3) {
            v3 = v4;
        }
        else if ((Pointer.class.isAssignableFrom(a3) && !Function.class.isAssignableFrom(a3)) || (Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(a3)) || Callback.class.isAssignableFrom(a3) || WString.class == a3 || String.class == a3) {
            v3 = Pointer.SIZE;
        }
        else if (Structure.class.isAssignableFrom(a3)) {
            if (ByReference.class.isAssignableFrom(a3)) {
                v3 = Pointer.SIZE;
            }
            else {
                if (v1 == null) {
                    v1 = newInstance(a3, Structure.PLACEHOLDER_MEMORY);
                }
                v3 = ((Structure)v1).getStructAlignment();
            }
        }
        else {
            if (!a3.isArray()) {
                throw new IllegalArgumentException("Type " + a3 + " has unknown native alignment");
            }
            v3 = this.getNativeAlignment(a3.getComponentType(), null, v2);
        }
        if (this.actualAlignType == 1) {
            v3 = 1;
        }
        else if (this.actualAlignType == 3) {
            v3 = Math.min(8, v3);
        }
        else if (this.actualAlignType == 2) {
            if (!v2 || !Platform.isMac() || !Platform.isPPC()) {
                v3 = Math.min(Native.MAX_ALIGNMENT, v3);
            }
            if (!v2 && Platform.isAIX() && (a3 == Double.TYPE || a3 == Double.class)) {
                v3 = 4;
            }
        }
        return v3;
    }
    
    @Override
    public String toString() {
        return this.toString(Boolean.getBoolean("jna.dump_memory"));
    }
    
    public String toString(final boolean a1) {
        return this.toString(0, true, a1);
    }
    
    private String format(final Class<?> a1) {
        final String v1 = a1.getName();
        final int v2 = v1.lastIndexOf(".");
        return v1.substring(v2 + 1);
    }
    
    private String toString(final int v-9, final boolean v-8, final boolean v-7) {
        this.ensureAllocated();
        final String property = System.getProperty("line.separator");
        String s = this.format(this.getClass()) + "(" + this.getPointer() + ")";
        if (!(this.getPointer() instanceof Memory)) {
            s = s + " (" + this.size() + " bytes)";
        }
        String string = "";
        for (int a1 = 0; a1 < v-9; ++a1) {
            string += "  ";
        }
        String s2 = property;
        if (!v-8) {
            s2 = "...}";
        }
        else {
            final Iterator<StructField> iterator = this.fields().values().iterator();
            while (iterator.hasNext()) {
                final StructField a2 = iterator.next();
                Object a3 = this.getFieldValue(a2.field);
                String v1 = this.format(a2.type);
                String v2 = "";
                s2 += string;
                if (a2.type.isArray() && a3 != null) {
                    v1 = this.format(a2.type.getComponentType());
                    v2 = "[" + Array.getLength(a3) + "]";
                }
                s2 = s2 + "  " + v1 + " " + a2.name + v2 + "@" + Integer.toHexString(a2.offset);
                if (a3 instanceof Structure) {
                    a3 = ((Structure)a3).toString(v-9 + 1, !(a3 instanceof ByReference), v-7);
                }
                s2 += "=";
                if (a3 instanceof Long) {
                    s2 += Long.toHexString((long)a3);
                }
                else if (a3 instanceof Integer) {
                    s2 += Integer.toHexString((int)a3);
                }
                else if (a3 instanceof Short) {
                    s2 += Integer.toHexString((short)a3);
                }
                else if (a3 instanceof Byte) {
                    s2 += Integer.toHexString((byte)a3);
                }
                else {
                    s2 += String.valueOf(a3).trim();
                }
                s2 += property;
                if (!iterator.hasNext()) {
                    s2 = s2 + string + "}";
                }
            }
        }
        if (v-9 == 0 && v-7) {
            final int n = 4;
            s2 = s2 + property + "memory dump" + property;
            final byte[] byteArray = this.getPointer().getByteArray(0L, this.size());
            for (int v3 = 0; v3 < byteArray.length; ++v3) {
                if (v3 % 4 == 0) {
                    s2 += "[";
                }
                if (byteArray[v3] >= 0 && byteArray[v3] < 16) {
                    s2 += "0";
                }
                s2 += Integer.toHexString(byteArray[v3] & 0xFF);
                if (v3 % 4 == 3 && v3 < byteArray.length - 1) {
                    s2 = s2 + "]" + property;
                }
            }
            s2 += "]";
        }
        return s + " {" + s2;
    }
    
    public Structure[] toArray(final Structure[] v-1) {
        this.ensureAllocated();
        if (this.memory instanceof AutoAllocated) {
            final Memory a1 = (Memory)this.memory;
            final int v1 = v-1.length * this.size();
            if (a1.size() < v1) {
                this.useMemory(this.autoAllocate(v1));
            }
        }
        v-1[0] = this;
        final int v2 = this.size();
        for (int v1 = 1; v1 < v-1.length; ++v1) {
            (v-1[v1] = newInstance(this.getClass(), this.memory.share(v1 * v2, v2))).conditionalAutoRead();
        }
        if (!(this instanceof ByValue)) {
            this.array = v-1;
        }
        return v-1;
    }
    
    public Structure[] toArray(final int a1) {
        return this.toArray((Structure[])Array.newInstance(this.getClass(), a1));
    }
    
    private Class<?> baseClass() {
        if ((this instanceof ByReference || this instanceof ByValue) && Structure.class.isAssignableFrom(this.getClass().getSuperclass())) {
            return this.getClass().getSuperclass();
        }
        return this.getClass();
    }
    
    public boolean dataEquals(final Structure a1) {
        return this.dataEquals(a1, false);
    }
    
    public boolean dataEquals(final Structure v1, final boolean v2) {
        if (v2) {
            v1.getPointer().clear(v1.size());
            v1.write();
            this.getPointer().clear(this.size());
            this.write();
        }
        final byte[] v3 = v1.getPointer().getByteArray(0L, v1.size());
        final byte[] v4 = this.getPointer().getByteArray(0L, this.size());
        if (v3.length == v4.length) {
            for (int a1 = 0; a1 < v3.length; ++a1) {
                if (v3[a1] != v4[a1]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof Structure && a1.getClass() == this.getClass() && ((Structure)a1).getPointer().equals(this.getPointer());
    }
    
    @Override
    public int hashCode() {
        final Pointer v1 = this.getPointer();
        if (v1 != null) {
            return this.getPointer().hashCode();
        }
        return this.getClass().hashCode();
    }
    
    protected void cacheTypeInfo(final Pointer a1) {
        this.typeInfo = a1.peer;
    }
    
    Pointer getFieldTypeInfo(final StructField v2) {
        Class<?> v3 = v2.type;
        Object v4 = this.getFieldValue(v2.field);
        if (this.typeMapper != null) {
            final ToNativeConverter a1 = this.typeMapper.getToNativeConverter(v3);
            if (a1 != null) {
                v3 = a1.nativeType();
                v4 = a1.toNative(v4, new ToNativeContext());
            }
        }
        return get(v4, v3);
    }
    
    Pointer getTypeInfo() {
        final Pointer v1 = getTypeInfo(this);
        this.cacheTypeInfo(v1);
        return v1;
    }
    
    public void setAutoSynch(final boolean a1) {
        this.setAutoRead(a1);
        this.setAutoWrite(a1);
    }
    
    public void setAutoRead(final boolean a1) {
        this.autoRead = a1;
    }
    
    public boolean getAutoRead() {
        return this.autoRead;
    }
    
    public void setAutoWrite(final boolean a1) {
        this.autoWrite = a1;
    }
    
    public boolean getAutoWrite() {
        return this.autoWrite;
    }
    
    static Pointer getTypeInfo(final Object a1) {
        return FFIType.get(a1);
    }
    
    private static Structure newInstance(final Class<?> v1, final long v2) {
        try {
            final Structure a1 = newInstance(v1, (v2 == 0L) ? Structure.PLACEHOLDER_MEMORY : new Pointer(v2));
            if (v2 != 0L) {
                a1.conditionalAutoRead();
            }
            return a1;
        }
        catch (Throwable a2) {
            System.err.println("JNA: Error creating structure: " + a2);
            return null;
        }
    }
    
    public static Structure newInstance(final Class<?> v-1, final Pointer v0) throws IllegalArgumentException {
        try {
            final Constructor<?> a1 = v-1.getConstructor(Pointer.class);
            return (Structure)a1.newInstance(v0);
        }
        catch (NoSuchMethodException ex) {}
        catch (SecurityException ex2) {}
        catch (InstantiationException v) {
            final String a2 = "Can't instantiate " + v-1;
            throw new IllegalArgumentException(a2, v);
        }
        catch (IllegalAccessException v3) {
            final String v2 = "Instantiation of " + v-1 + " (Pointer) not allowed, is it public?";
            throw new IllegalArgumentException(v2, v3);
        }
        catch (InvocationTargetException v4) {
            final String v2 = "Exception thrown while instantiating an instance of " + v-1;
            v4.printStackTrace();
            throw new IllegalArgumentException(v2, v4);
        }
        final Structure v5 = newInstance(v-1);
        if (v0 != Structure.PLACEHOLDER_MEMORY) {
            v5.useMemory(v0);
        }
        return v5;
    }
    
    public static Structure newInstance(final Class<?> v-1) throws IllegalArgumentException {
        try {
            final Structure a1 = (Structure)v-1.newInstance();
            if (a1 instanceof ByValue) {
                a1.allocateMemory();
            }
            return a1;
        }
        catch (InstantiationException v2) {
            final String v1 = "Can't instantiate " + v-1;
            throw new IllegalArgumentException(v1, v2);
        }
        catch (IllegalAccessException v3) {
            final String v1 = "Instantiation of " + v-1 + " not allowed, is it public?";
            throw new IllegalArgumentException(v1, v3);
        }
    }
    
    StructField typeInfoField() {
        final LayoutInfo v1;
        synchronized (Structure.layoutInfo) {
            v1 = Structure.layoutInfo.get(this.getClass());
        }
        if (v1 != null) {
            return v1.typeInfoField;
        }
        return null;
    }
    
    private static void structureArrayCheck(final Structure[] v-2) {
        if (ByReference[].class.isAssignableFrom(v-2.getClass())) {
            return;
        }
        final Pointer pointer = v-2[0].getPointer();
        final int v0 = v-2[0].size();
        for (int v2 = 1; v2 < v-2.length; ++v2) {
            if (v-2[v2].getPointer().peer != pointer.peer + v0 * v2) {
                final String a1 = "Structure array elements must use contiguous memory (bad backing address at Structure array index " + v2 + ")";
                throw new IllegalArgumentException(a1);
            }
        }
    }
    
    public static void autoRead(final Structure[] v1) {
        structureArrayCheck(v1);
        if (v1[0].array == v1) {
            v1[0].autoRead();
        }
        else {
            for (int a1 = 0; a1 < v1.length; ++a1) {
                if (v1[a1] != null) {
                    v1[a1].autoRead();
                }
            }
        }
    }
    
    public void autoRead() {
        if (this.getAutoRead()) {
            this.read();
            if (this.array != null) {
                for (int v1 = 1; v1 < this.array.length; ++v1) {
                    this.array[v1].autoRead();
                }
            }
        }
    }
    
    public static void autoWrite(final Structure[] v1) {
        structureArrayCheck(v1);
        if (v1[0].array == v1) {
            v1[0].autoWrite();
        }
        else {
            for (int a1 = 0; a1 < v1.length; ++a1) {
                if (v1[a1] != null) {
                    v1[a1].autoWrite();
                }
            }
        }
    }
    
    public void autoWrite() {
        if (this.getAutoWrite()) {
            this.write();
            if (this.array != null) {
                for (int v1 = 1; v1 < this.array.length; ++v1) {
                    this.array[v1].autoWrite();
                }
            }
        }
    }
    
    protected int getNativeSize(final Class<?> a1) {
        return this.getNativeSize(a1, null);
    }
    
    protected int getNativeSize(final Class<?> a1, final Object a2) {
        return Native.getNativeSize(a1, a2);
    }
    
    static void validate(final Class<?> a1) {
        newInstance(a1, Structure.PLACEHOLDER_MEMORY);
    }
    
    static /* bridge */ void access$1900(final Structure a1, final boolean a2) {
        a1.ensureAllocated(a2);
    }
    
    static /* synthetic */ Pointer access$2000() {
        return Structure.PLACEHOLDER_MEMORY;
    }
    
    static {
        layoutInfo = new WeakHashMap<Class<?>, LayoutInfo>();
        fieldOrder = new WeakHashMap<Class<?>, List<String>>();
        reads = new ThreadLocal<Map<Pointer, Structure>>() {
            Structure$1() {
                super();
            }
            
            @Override
            protected synchronized Map<Pointer, Structure> initialValue() {
                return new HashMap<Pointer, Structure>();
            }
            
            @Override
            protected /* bridge */ Object initialValue() {
                return this.initialValue();
            }
        };
        busy = new ThreadLocal<Set<Structure>>() {
            Structure$2() {
                super();
            }
            
            @Override
            protected synchronized Set<Structure> initialValue() {
                return new StructureSet();
            }
            
            @Override
            protected /* bridge */ Object initialValue() {
                return this.initialValue();
            }
        };
        PLACEHOLDER_MEMORY = new Pointer(0L) {
            Structure$3(final long a1) {
                super(a1);
            }
            
            @Override
            public Pointer share(final long a1, final long a2) {
                return this;
            }
        };
    }
    
    static class StructureSet extends AbstractCollection<Structure> implements Set<Structure>
    {
        Structure[] elements;
        private int count;
        
        StructureSet() {
            super();
        }
        
        private void ensureCapacity(final int v2) {
            if (this.elements == null) {
                this.elements = new Structure[v2 * 3 / 2];
            }
            else if (this.elements.length < v2) {
                final Structure[] a1 = new Structure[v2 * 3 / 2];
                System.arraycopy(this.elements, 0, a1, 0, this.elements.length);
                this.elements = a1;
            }
        }
        
        public Structure[] getElements() {
            return this.elements;
        }
        
        @Override
        public int size() {
            return this.count;
        }
        
        @Override
        public boolean contains(final Object a1) {
            return this.indexOf((Structure)a1) != -1;
        }
        
        @Override
        public boolean add(final Structure a1) {
            if (!this.contains(a1)) {
                this.ensureCapacity(this.count + 1);
                this.elements[this.count++] = a1;
            }
            return true;
        }
        
        private int indexOf(final Structure v0) {
            for (int v = 0; v < this.count; ++v) {
                final Structure a1 = this.elements[v];
                if (v0 == a1 || (v0.getClass() == a1.getClass() && v0.size() == a1.size() && v0.getPointer().equals(a1.getPointer()))) {
                    return v;
                }
            }
            return -1;
        }
        
        @Override
        public boolean remove(final Object a1) {
            final int v1 = this.indexOf((Structure)a1);
            if (v1 != -1) {
                if (--this.count >= 0) {
                    this.elements[v1] = this.elements[this.count];
                    this.elements[this.count] = null;
                }
                return true;
            }
            return false;
        }
        
        @Override
        public Iterator<Structure> iterator() {
            final Structure[] v1 = new Structure[this.count];
            if (this.count > 0) {
                System.arraycopy(this.elements, 0, v1, 0, this.count);
            }
            return Arrays.asList(v1).iterator();
        }
        
        @Override
        public /* bridge */ boolean add(final Object o) {
            return this.add((Structure)o);
        }
    }
    
    private static class LayoutInfo
    {
        private int size;
        private int alignment;
        private final Map<String, StructField> fields;
        private int alignType;
        private TypeMapper typeMapper;
        private boolean variable;
        private StructField typeInfoField;
        
        private LayoutInfo() {
            super();
            this.size = -1;
            this.alignment = 1;
            this.fields = Collections.synchronizedMap(new LinkedHashMap<String, StructField>());
            this.alignType = 0;
        }
        
        static /* synthetic */ boolean access$000(final LayoutInfo a1) {
            return a1.variable;
        }
        
        static /* synthetic */ int access$100(final LayoutInfo a1) {
            return a1.size;
        }
        
        static /* synthetic */ int access$200(final LayoutInfo a1) {
            return a1.alignType;
        }
        
        static /* synthetic */ TypeMapper access$300(final LayoutInfo a1) {
            return a1.typeMapper;
        }
        
        static /* synthetic */ int access$400(final LayoutInfo a1) {
            return a1.alignment;
        }
        
        static /* synthetic */ Map access$500(final LayoutInfo a1) {
            return a1.fields;
        }
        
        LayoutInfo(final Structure$1 a1) {
            this();
        }
        
        static /* synthetic */ int access$202(final LayoutInfo a1, final int a2) {
            return a1.alignType = a2;
        }
        
        static /* synthetic */ TypeMapper access$302(final LayoutInfo a1, final TypeMapper a2) {
            return a1.typeMapper = a2;
        }
        
        static /* synthetic */ boolean access$002(final LayoutInfo a1, final boolean a2) {
            return a1.variable = a2;
        }
        
        static /* synthetic */ int access$402(final LayoutInfo a1, final int a2) {
            return a1.alignment = a2;
        }
        
        static /* synthetic */ StructField access$700(final LayoutInfo a1) {
            return a1.typeInfoField;
        }
        
        static /* synthetic */ StructField access$702(final LayoutInfo a1, final StructField a2) {
            return a1.typeInfoField = a2;
        }
        
        static /* synthetic */ int access$102(final LayoutInfo a1, final int a2) {
            return a1.size = a2;
        }
    }
    
    protected static class StructField
    {
        public String name;
        public Class<?> type;
        public Field field;
        public int size;
        public int offset;
        public boolean isVolatile;
        public boolean isReadOnly;
        public FromNativeConverter readConverter;
        public ToNativeConverter writeConverter;
        public FromNativeContext context;
        
        protected StructField() {
            super();
            this.size = -1;
            this.offset = -1;
        }
        
        @Override
        public String toString() {
            return this.name + "@" + this.offset + "[" + this.size + "] (" + this.type + ")";
        }
    }
    
    static class FFIType extends Structure
    {
        private static final Map<Object, Object> typeInfoMap;
        private static final int FFI_TYPE_STRUCT = 13;
        public size_t size;
        public short alignment;
        public short type;
        public Pointer elements;
        
        private FFIType(final Structure v0) {
            super();
            this.type = 13;
            v0.ensureAllocated(true);
            Pointer[] v;
            if (v0 instanceof Union) {
                final StructField a1 = ((Union)v0).typeInfoField();
                v = new Pointer[] { get(v0.getFieldValue(a1.field), a1.type), null };
            }
            else {
                v = new Pointer[v0.fields().size() + 1];
                int v2 = 0;
                for (final StructField v3 : v0.fields().values()) {
                    v[v2++] = v0.getFieldTypeInfo(v3);
                }
            }
            this.init(v);
        }
        
        private FFIType(final Object v1, final Class<?> v2) {
            super();
            this.type = 13;
            final int v3 = Array.getLength(v1);
            final Pointer[] v4 = new Pointer[v3 + 1];
            final Pointer v5 = get(null, v2.getComponentType());
            for (int a1 = 0; a1 < v3; ++a1) {
                v4[a1] = v5;
            }
            this.init(v4);
        }
        
        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("size", "alignment", "type", "elements");
        }
        
        private void init(final Pointer[] a1) {
            (this.elements = new Memory(Pointer.SIZE * a1.length)).write(0L, a1, 0, a1.length);
            this.write();
        }
        
        static Pointer get(final Object a1) {
            if (a1 == null) {
                return FFITypes.ffi_type_pointer;
            }
            if (a1 instanceof Class) {
                return get(null, (Class<?>)a1);
            }
            return get(a1, a1.getClass());
        }
        
        private static Pointer get(Object v-4, Class<?> v-3) {
            final TypeMapper typeMapper = Native.getTypeMapper(v-3);
            if (typeMapper != null) {
                final ToNativeConverter a1 = typeMapper.getToNativeConverter(v-3);
                if (a1 != null) {
                    v-3 = a1.nativeType();
                }
            }
            synchronized (FFIType.typeInfoMap) {
                final Object v0 = FFIType.typeInfoMap.get(v-3);
                if (v0 instanceof Pointer) {
                    return (Pointer)v0;
                }
                if (v0 instanceof FFIType) {
                    return ((FFIType)v0).getPointer();
                }
                if ((Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(v-3)) || Callback.class.isAssignableFrom(v-3)) {
                    FFIType.typeInfoMap.put(v-3, FFITypes.ffi_type_pointer);
                    return FFITypes.ffi_type_pointer;
                }
                if (Structure.class.isAssignableFrom(v-3)) {
                    if (v-4 == null) {
                        v-4 = Structure.newInstance(v-3, Structure.PLACEHOLDER_MEMORY);
                    }
                    if (ByReference.class.isAssignableFrom(v-3)) {
                        FFIType.typeInfoMap.put(v-3, FFITypes.ffi_type_pointer);
                        return FFITypes.ffi_type_pointer;
                    }
                    final FFIType a2 = new FFIType((Structure)v-4);
                    FFIType.typeInfoMap.put(v-3, a2);
                    return a2.getPointer();
                }
                else {
                    if (NativeMapped.class.isAssignableFrom(v-3)) {
                        final NativeMappedConverter v2 = NativeMappedConverter.getInstance(v-3);
                        return get(v2.toNative(v-4, new ToNativeContext()), v2.nativeType());
                    }
                    if (v-3.isArray()) {
                        final FFIType v3 = new FFIType(v-4, v-3);
                        FFIType.typeInfoMap.put(v-4, v3);
                        return v3.getPointer();
                    }
                    throw new IllegalArgumentException("Unsupported type " + v-3);
                }
            }
        }
        
        static /* bridge */ Pointer access$800(final Object a1, final Class a2) {
            return get(a1, a2);
        }
        
        static {
            typeInfoMap = new WeakHashMap<Object, Object>();
            if (Native.POINTER_SIZE == 0) {
                throw new Error("Native library not initialized");
            }
            if (FFITypes.ffi_type_void == null) {
                throw new Error("FFI types not initialized");
            }
            FFIType.typeInfoMap.put(Void.TYPE, FFITypes.ffi_type_void);
            FFIType.typeInfoMap.put(Void.class, FFITypes.ffi_type_void);
            FFIType.typeInfoMap.put(Float.TYPE, FFITypes.ffi_type_float);
            FFIType.typeInfoMap.put(Float.class, FFITypes.ffi_type_float);
            FFIType.typeInfoMap.put(Double.TYPE, FFITypes.ffi_type_double);
            FFIType.typeInfoMap.put(Double.class, FFITypes.ffi_type_double);
            FFIType.typeInfoMap.put(Long.TYPE, FFITypes.ffi_type_sint64);
            FFIType.typeInfoMap.put(Long.class, FFITypes.ffi_type_sint64);
            FFIType.typeInfoMap.put(Integer.TYPE, FFITypes.ffi_type_sint32);
            FFIType.typeInfoMap.put(Integer.class, FFITypes.ffi_type_sint32);
            FFIType.typeInfoMap.put(Short.TYPE, FFITypes.ffi_type_sint16);
            FFIType.typeInfoMap.put(Short.class, FFITypes.ffi_type_sint16);
            final Pointer v1 = (Native.WCHAR_SIZE == 2) ? FFITypes.ffi_type_uint16 : FFITypes.ffi_type_uint32;
            FFIType.typeInfoMap.put(Character.TYPE, v1);
            FFIType.typeInfoMap.put(Character.class, v1);
            FFIType.typeInfoMap.put(Byte.TYPE, FFITypes.ffi_type_sint8);
            FFIType.typeInfoMap.put(Byte.class, FFITypes.ffi_type_sint8);
            FFIType.typeInfoMap.put(Pointer.class, FFITypes.ffi_type_pointer);
            FFIType.typeInfoMap.put(String.class, FFITypes.ffi_type_pointer);
            FFIType.typeInfoMap.put(WString.class, FFITypes.ffi_type_pointer);
            FFIType.typeInfoMap.put(Boolean.TYPE, FFITypes.ffi_type_uint32);
            FFIType.typeInfoMap.put(Boolean.class, FFITypes.ffi_type_uint32);
        }
        
        public static class size_t extends IntegerType
        {
            private static final long serialVersionUID = 1L;
            
            public size_t() {
                this(0L);
            }
            
            public size_t(final long a1) {
                super(Native.SIZE_T_SIZE, a1);
            }
        }
        
        private static class FFITypes
        {
            private static Pointer ffi_type_void;
            private static Pointer ffi_type_float;
            private static Pointer ffi_type_double;
            private static Pointer ffi_type_longdouble;
            private static Pointer ffi_type_uint8;
            private static Pointer ffi_type_sint8;
            private static Pointer ffi_type_uint16;
            private static Pointer ffi_type_sint16;
            private static Pointer ffi_type_uint32;
            private static Pointer ffi_type_sint32;
            private static Pointer ffi_type_uint64;
            private static Pointer ffi_type_sint64;
            private static Pointer ffi_type_pointer;
            
            private FFITypes() {
                super();
            }
            
            static /* synthetic */ Pointer access$900() {
                return FFITypes.ffi_type_void;
            }
            
            static /* synthetic */ Pointer access$1000() {
                return FFITypes.ffi_type_float;
            }
            
            static /* synthetic */ Pointer access$1100() {
                return FFITypes.ffi_type_double;
            }
            
            static /* synthetic */ Pointer access$1200() {
                return FFITypes.ffi_type_sint64;
            }
            
            static /* synthetic */ Pointer access$1300() {
                return FFITypes.ffi_type_sint32;
            }
            
            static /* synthetic */ Pointer access$1400() {
                return FFITypes.ffi_type_sint16;
            }
            
            static /* synthetic */ Pointer access$1500() {
                return FFITypes.ffi_type_uint16;
            }
            
            static /* synthetic */ Pointer access$1600() {
                return FFITypes.ffi_type_uint32;
            }
            
            static /* synthetic */ Pointer access$1700() {
                return FFITypes.ffi_type_sint8;
            }
            
            static /* synthetic */ Pointer access$1800() {
                return FFITypes.ffi_type_pointer;
            }
        }
    }
    
    private static class AutoAllocated extends Memory
    {
        public AutoAllocated(final int a1) {
            super(a1);
            super.clear();
        }
        
        @Override
        public String toString() {
            return "auto-" + super.toString();
        }
    }
    
    public interface ByReference
    {
    }
    
    public interface ByValue
    {
    }
}
