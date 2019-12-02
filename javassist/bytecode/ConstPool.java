package javassist.bytecode;

import javassist.*;
import java.util.*;
import java.io.*;

public final class ConstPool
{
    LongVector items;
    int numOfItems;
    int thisClassInfo;
    HashMap itemsCache;
    public static final int CONST_Class = 7;
    public static final int CONST_Fieldref = 9;
    public static final int CONST_Methodref = 10;
    public static final int CONST_InterfaceMethodref = 11;
    public static final int CONST_String = 8;
    public static final int CONST_Integer = 3;
    public static final int CONST_Float = 4;
    public static final int CONST_Long = 5;
    public static final int CONST_Double = 6;
    public static final int CONST_NameAndType = 12;
    public static final int CONST_Utf8 = 1;
    public static final int CONST_MethodHandle = 15;
    public static final int CONST_MethodType = 16;
    public static final int CONST_InvokeDynamic = 18;
    public static final CtClass THIS;
    public static final int REF_getField = 1;
    public static final int REF_getStatic = 2;
    public static final int REF_putField = 3;
    public static final int REF_putStatic = 4;
    public static final int REF_invokeVirtual = 5;
    public static final int REF_invokeStatic = 6;
    public static final int REF_invokeSpecial = 7;
    public static final int REF_newInvokeSpecial = 8;
    public static final int REF_invokeInterface = 9;
    
    public ConstPool(final String a1) {
        super();
        this.items = new LongVector();
        this.itemsCache = null;
        this.numOfItems = 0;
        this.addItem0(null);
        this.thisClassInfo = this.addClassInfo(a1);
    }
    
    public ConstPool(final DataInputStream a1) throws IOException {
        super();
        this.itemsCache = null;
        this.thisClassInfo = 0;
        this.read(a1);
    }
    
    void prune() {
        this.itemsCache = null;
    }
    
    public int getSize() {
        return this.numOfItems;
    }
    
    public String getClassName() {
        return this.getClassInfo(this.thisClassInfo);
    }
    
    public int getThisClassInfo() {
        return this.thisClassInfo;
    }
    
    void setThisClassInfo(final int a1) {
        this.thisClassInfo = a1;
    }
    
    ConstInfo getItem(final int a1) {
        return this.items.elementAt(a1);
    }
    
    public int getTag(final int a1) {
        return this.getItem(a1).getTag();
    }
    
    public String getClassInfo(final int a1) {
        final ClassInfo v1 = (ClassInfo)this.getItem(a1);
        if (v1 == null) {
            return null;
        }
        return Descriptor.toJavaName(this.getUtf8Info(v1.name));
    }
    
    public String getClassInfoByDescriptor(final int v2) {
        final ClassInfo v3 = (ClassInfo)this.getItem(v2);
        if (v3 == null) {
            return null;
        }
        final String a1 = this.getUtf8Info(v3.name);
        if (a1.charAt(0) == '[') {
            return a1;
        }
        return Descriptor.of(a1);
    }
    
    public int getNameAndTypeName(final int a1) {
        final NameAndTypeInfo v1 = (NameAndTypeInfo)this.getItem(a1);
        return v1.memberName;
    }
    
    public int getNameAndTypeDescriptor(final int a1) {
        final NameAndTypeInfo v1 = (NameAndTypeInfo)this.getItem(a1);
        return v1.typeDescriptor;
    }
    
    public int getMemberClass(final int a1) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a1);
        return v1.classIndex;
    }
    
    public int getMemberNameAndType(final int a1) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a1);
        return v1.nameAndTypeIndex;
    }
    
    public int getFieldrefClass(final int a1) {
        final FieldrefInfo v1 = (FieldrefInfo)this.getItem(a1);
        return v1.classIndex;
    }
    
    public String getFieldrefClassName(final int a1) {
        final FieldrefInfo v1 = (FieldrefInfo)this.getItem(a1);
        if (v1 == null) {
            return null;
        }
        return this.getClassInfo(v1.classIndex);
    }
    
    public int getFieldrefNameAndType(final int a1) {
        final FieldrefInfo v1 = (FieldrefInfo)this.getItem(a1);
        return v1.nameAndTypeIndex;
    }
    
    public String getFieldrefName(final int v2) {
        final FieldrefInfo v3 = (FieldrefInfo)this.getItem(v2);
        if (v3 == null) {
            return null;
        }
        final NameAndTypeInfo a1 = (NameAndTypeInfo)this.getItem(v3.nameAndTypeIndex);
        if (a1 == null) {
            return null;
        }
        return this.getUtf8Info(a1.memberName);
    }
    
    public String getFieldrefType(final int v2) {
        final FieldrefInfo v3 = (FieldrefInfo)this.getItem(v2);
        if (v3 == null) {
            return null;
        }
        final NameAndTypeInfo a1 = (NameAndTypeInfo)this.getItem(v3.nameAndTypeIndex);
        if (a1 == null) {
            return null;
        }
        return this.getUtf8Info(a1.typeDescriptor);
    }
    
    public int getMethodrefClass(final int a1) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a1);
        return v1.classIndex;
    }
    
    public String getMethodrefClassName(final int a1) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a1);
        if (v1 == null) {
            return null;
        }
        return this.getClassInfo(v1.classIndex);
    }
    
    public int getMethodrefNameAndType(final int a1) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a1);
        return v1.nameAndTypeIndex;
    }
    
    public String getMethodrefName(final int v2) {
        final MemberrefInfo v3 = (MemberrefInfo)this.getItem(v2);
        if (v3 == null) {
            return null;
        }
        final NameAndTypeInfo a1 = (NameAndTypeInfo)this.getItem(v3.nameAndTypeIndex);
        if (a1 == null) {
            return null;
        }
        return this.getUtf8Info(a1.memberName);
    }
    
    public String getMethodrefType(final int v2) {
        final MemberrefInfo v3 = (MemberrefInfo)this.getItem(v2);
        if (v3 == null) {
            return null;
        }
        final NameAndTypeInfo a1 = (NameAndTypeInfo)this.getItem(v3.nameAndTypeIndex);
        if (a1 == null) {
            return null;
        }
        return this.getUtf8Info(a1.typeDescriptor);
    }
    
    public int getInterfaceMethodrefClass(final int a1) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a1);
        return v1.classIndex;
    }
    
    public String getInterfaceMethodrefClassName(final int a1) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a1);
        return this.getClassInfo(v1.classIndex);
    }
    
    public int getInterfaceMethodrefNameAndType(final int a1) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a1);
        return v1.nameAndTypeIndex;
    }
    
    public String getInterfaceMethodrefName(final int v2) {
        final MemberrefInfo v3 = (MemberrefInfo)this.getItem(v2);
        if (v3 == null) {
            return null;
        }
        final NameAndTypeInfo a1 = (NameAndTypeInfo)this.getItem(v3.nameAndTypeIndex);
        if (a1 == null) {
            return null;
        }
        return this.getUtf8Info(a1.memberName);
    }
    
    public String getInterfaceMethodrefType(final int v2) {
        final MemberrefInfo v3 = (MemberrefInfo)this.getItem(v2);
        if (v3 == null) {
            return null;
        }
        final NameAndTypeInfo a1 = (NameAndTypeInfo)this.getItem(v3.nameAndTypeIndex);
        if (a1 == null) {
            return null;
        }
        return this.getUtf8Info(a1.typeDescriptor);
    }
    
    public Object getLdcValue(final int a1) {
        final ConstInfo v1 = this.getItem(a1);
        Object v2 = null;
        if (v1 instanceof StringInfo) {
            v2 = this.getStringInfo(a1);
        }
        else if (v1 instanceof FloatInfo) {
            v2 = new Float(this.getFloatInfo(a1));
        }
        else if (v1 instanceof IntegerInfo) {
            v2 = new Integer(this.getIntegerInfo(a1));
        }
        else if (v1 instanceof LongInfo) {
            v2 = new Long(this.getLongInfo(a1));
        }
        else if (v1 instanceof DoubleInfo) {
            v2 = new Double(this.getDoubleInfo(a1));
        }
        else {
            v2 = null;
        }
        return v2;
    }
    
    public int getIntegerInfo(final int a1) {
        final IntegerInfo v1 = (IntegerInfo)this.getItem(a1);
        return v1.value;
    }
    
    public float getFloatInfo(final int a1) {
        final FloatInfo v1 = (FloatInfo)this.getItem(a1);
        return v1.value;
    }
    
    public long getLongInfo(final int a1) {
        final LongInfo v1 = (LongInfo)this.getItem(a1);
        return v1.value;
    }
    
    public double getDoubleInfo(final int a1) {
        final DoubleInfo v1 = (DoubleInfo)this.getItem(a1);
        return v1.value;
    }
    
    public String getStringInfo(final int a1) {
        final StringInfo v1 = (StringInfo)this.getItem(a1);
        return this.getUtf8Info(v1.string);
    }
    
    public String getUtf8Info(final int a1) {
        final Utf8Info v1 = (Utf8Info)this.getItem(a1);
        return v1.string;
    }
    
    public int getMethodHandleKind(final int a1) {
        final MethodHandleInfo v1 = (MethodHandleInfo)this.getItem(a1);
        return v1.refKind;
    }
    
    public int getMethodHandleIndex(final int a1) {
        final MethodHandleInfo v1 = (MethodHandleInfo)this.getItem(a1);
        return v1.refIndex;
    }
    
    public int getMethodTypeInfo(final int a1) {
        final MethodTypeInfo v1 = (MethodTypeInfo)this.getItem(a1);
        return v1.descriptor;
    }
    
    public int getInvokeDynamicBootstrap(final int a1) {
        final InvokeDynamicInfo v1 = (InvokeDynamicInfo)this.getItem(a1);
        return v1.bootstrap;
    }
    
    public int getInvokeDynamicNameAndType(final int a1) {
        final InvokeDynamicInfo v1 = (InvokeDynamicInfo)this.getItem(a1);
        return v1.nameAndType;
    }
    
    public String getInvokeDynamicType(final int v2) {
        final InvokeDynamicInfo v3 = (InvokeDynamicInfo)this.getItem(v2);
        if (v3 == null) {
            return null;
        }
        final NameAndTypeInfo a1 = (NameAndTypeInfo)this.getItem(v3.nameAndType);
        if (a1 == null) {
            return null;
        }
        return this.getUtf8Info(a1.typeDescriptor);
    }
    
    public int isConstructor(final String a1, final int a2) {
        return this.isMember(a1, "<init>", a2);
    }
    
    public int isMember(final String a3, final String v1, final int v2) {
        final MemberrefInfo v3 = (MemberrefInfo)this.getItem(v2);
        if (this.getClassInfo(v3.classIndex).equals(a3)) {
            final NameAndTypeInfo a4 = (NameAndTypeInfo)this.getItem(v3.nameAndTypeIndex);
            if (this.getUtf8Info(a4.memberName).equals(v1)) {
                return a4.typeDescriptor;
            }
        }
        return 0;
    }
    
    public String eqMember(final String a1, final String a2, final int a3) {
        final MemberrefInfo v1 = (MemberrefInfo)this.getItem(a3);
        final NameAndTypeInfo v2 = (NameAndTypeInfo)this.getItem(v1.nameAndTypeIndex);
        if (this.getUtf8Info(v2.memberName).equals(a1) && this.getUtf8Info(v2.typeDescriptor).equals(a2)) {
            return this.getClassInfo(v1.classIndex);
        }
        return null;
    }
    
    private int addItem0(final ConstInfo a1) {
        this.items.addElement(a1);
        return this.numOfItems++;
    }
    
    private int addItem(final ConstInfo a1) {
        if (this.itemsCache == null) {
            this.itemsCache = makeItemsCache(this.items);
        }
        final ConstInfo v1 = this.itemsCache.get(a1);
        if (v1 != null) {
            return v1.index;
        }
        this.items.addElement(a1);
        this.itemsCache.put(a1, a1);
        return this.numOfItems++;
    }
    
    public int copy(final int a1, final ConstPool a2, final Map a3) {
        if (a1 == 0) {
            return 0;
        }
        final ConstInfo v1 = this.getItem(a1);
        return v1.copy(this, a2, a3);
    }
    
    int addConstInfoPadding() {
        return this.addItem0(new ConstInfoPadding(this.numOfItems));
    }
    
    public int addClassInfo(final CtClass a1) {
        if (a1 == ConstPool.THIS) {
            return this.thisClassInfo;
        }
        if (!a1.isArray()) {
            return this.addClassInfo(a1.getName());
        }
        return this.addClassInfo(Descriptor.toJvmName(a1));
    }
    
    public int addClassInfo(final String a1) {
        final int v1 = this.addUtf8Info(Descriptor.toJvmName(a1));
        return this.addItem(new ClassInfo(v1, this.numOfItems));
    }
    
    public int addNameAndTypeInfo(final String a1, final String a2) {
        return this.addNameAndTypeInfo(this.addUtf8Info(a1), this.addUtf8Info(a2));
    }
    
    public int addNameAndTypeInfo(final int a1, final int a2) {
        return this.addItem(new NameAndTypeInfo(a1, a2, this.numOfItems));
    }
    
    public int addFieldrefInfo(final int a1, final String a2, final String a3) {
        final int v1 = this.addNameAndTypeInfo(a2, a3);
        return this.addFieldrefInfo(a1, v1);
    }
    
    public int addFieldrefInfo(final int a1, final int a2) {
        return this.addItem(new FieldrefInfo(a1, a2, this.numOfItems));
    }
    
    public int addMethodrefInfo(final int a1, final String a2, final String a3) {
        final int v1 = this.addNameAndTypeInfo(a2, a3);
        return this.addMethodrefInfo(a1, v1);
    }
    
    public int addMethodrefInfo(final int a1, final int a2) {
        return this.addItem(new MethodrefInfo(a1, a2, this.numOfItems));
    }
    
    public int addInterfaceMethodrefInfo(final int a1, final String a2, final String a3) {
        final int v1 = this.addNameAndTypeInfo(a2, a3);
        return this.addInterfaceMethodrefInfo(a1, v1);
    }
    
    public int addInterfaceMethodrefInfo(final int a1, final int a2) {
        return this.addItem(new InterfaceMethodrefInfo(a1, a2, this.numOfItems));
    }
    
    public int addStringInfo(final String a1) {
        final int v1 = this.addUtf8Info(a1);
        return this.addItem(new StringInfo(v1, this.numOfItems));
    }
    
    public int addIntegerInfo(final int a1) {
        return this.addItem(new IntegerInfo(a1, this.numOfItems));
    }
    
    public int addFloatInfo(final float a1) {
        return this.addItem(new FloatInfo(a1, this.numOfItems));
    }
    
    public int addLongInfo(final long a1) {
        final int v1 = this.addItem(new LongInfo(a1, this.numOfItems));
        if (v1 == this.numOfItems - 1) {
            this.addConstInfoPadding();
        }
        return v1;
    }
    
    public int addDoubleInfo(final double a1) {
        final int v1 = this.addItem(new DoubleInfo(a1, this.numOfItems));
        if (v1 == this.numOfItems - 1) {
            this.addConstInfoPadding();
        }
        return v1;
    }
    
    public int addUtf8Info(final String a1) {
        return this.addItem(new Utf8Info(a1, this.numOfItems));
    }
    
    public int addMethodHandleInfo(final int a1, final int a2) {
        return this.addItem(new MethodHandleInfo(a1, a2, this.numOfItems));
    }
    
    public int addMethodTypeInfo(final int a1) {
        return this.addItem(new MethodTypeInfo(a1, this.numOfItems));
    }
    
    public int addInvokeDynamicInfo(final int a1, final int a2) {
        return this.addItem(new InvokeDynamicInfo(a1, a2, this.numOfItems));
    }
    
    public Set getClassNames() {
        final HashSet set = new HashSet();
        final LongVector items = this.items;
        for (int numOfItems = this.numOfItems, v0 = 1; v0 < numOfItems; ++v0) {
            final String v2 = items.elementAt(v0).getClassName(this);
            if (v2 != null) {
                set.add(v2);
            }
        }
        return set;
    }
    
    public void renameClass(final String v2, final String v3) {
        final LongVector v4 = this.items;
        for (int v5 = this.numOfItems, a2 = 1; a2 < v5; ++a2) {
            final ConstInfo a3 = v4.elementAt(a2);
            a3.renameClass(this, v2, v3, this.itemsCache);
        }
    }
    
    public void renameClass(final Map v-2) {
        final LongVector items = this.items;
        for (int v0 = this.numOfItems, v2 = 1; v2 < v0; ++v2) {
            final ConstInfo a1 = items.elementAt(v2);
            a1.renameClass(this, v-2, this.itemsCache);
        }
    }
    
    private void read(final DataInputStream v2) throws IOException {
        int v3 = v2.readUnsignedShort();
        this.items = new LongVector(v3);
        this.numOfItems = 0;
        this.addItem0(null);
        while (--v3 > 0) {
            final int a1 = this.readOne(v2);
            if (a1 == 5 || a1 == 6) {
                this.addConstInfoPadding();
                --v3;
            }
        }
    }
    
    private static HashMap makeItemsCache(final LongVector v1) {
        final HashMap v2 = new HashMap();
        int v3 = 1;
        while (true) {
            final ConstInfo a1 = v1.elementAt(v3++);
            if (a1 == null) {
                break;
            }
            v2.put(a1, a1);
        }
        return v2;
    }
    
    private int readOne(final DataInputStream v0) throws IOException {
        final int v = v0.readUnsignedByte();
        ConstInfo v2 = null;
        switch (v) {
            case 1: {
                final ConstInfo a1 = new Utf8Info(v0, this.numOfItems);
                break;
            }
            case 3: {
                v2 = new IntegerInfo(v0, this.numOfItems);
                break;
            }
            case 4: {
                v2 = new FloatInfo(v0, this.numOfItems);
                break;
            }
            case 5: {
                v2 = new LongInfo(v0, this.numOfItems);
                break;
            }
            case 6: {
                v2 = new DoubleInfo(v0, this.numOfItems);
                break;
            }
            case 7: {
                v2 = new ClassInfo(v0, this.numOfItems);
                break;
            }
            case 8: {
                v2 = new StringInfo(v0, this.numOfItems);
                break;
            }
            case 9: {
                v2 = new FieldrefInfo(v0, this.numOfItems);
                break;
            }
            case 10: {
                v2 = new MethodrefInfo(v0, this.numOfItems);
                break;
            }
            case 11: {
                v2 = new InterfaceMethodrefInfo(v0, this.numOfItems);
                break;
            }
            case 12: {
                v2 = new NameAndTypeInfo(v0, this.numOfItems);
                break;
            }
            case 15: {
                v2 = new MethodHandleInfo(v0, this.numOfItems);
                break;
            }
            case 16: {
                v2 = new MethodTypeInfo(v0, this.numOfItems);
                break;
            }
            case 18: {
                v2 = new InvokeDynamicInfo(v0, this.numOfItems);
                break;
            }
            default: {
                throw new IOException("invalid constant type: " + v + " at " + this.numOfItems);
            }
        }
        this.addItem0(v2);
        return v;
    }
    
    public void write(final DataOutputStream v2) throws IOException {
        v2.writeShort(this.numOfItems);
        final LongVector v3 = this.items;
        for (int v4 = this.numOfItems, a1 = 1; a1 < v4; ++a1) {
            v3.elementAt(a1).write(v2);
        }
    }
    
    public void print() {
        this.print(new PrintWriter(System.out, true));
    }
    
    public void print(final PrintWriter v2) {
        for (int v3 = this.numOfItems, a1 = 1; a1 < v3; ++a1) {
            v2.print(a1);
            v2.print(" ");
            this.items.elementAt(a1).print(v2);
        }
    }
    
    static {
        THIS = null;
    }
}
