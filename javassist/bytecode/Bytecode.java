package javassist.bytecode;

import javassist.*;

public class Bytecode extends ByteVector implements Cloneable, Opcode
{
    public static final CtClass THIS;
    ConstPool constPool;
    int maxStack;
    int maxLocals;
    ExceptionTable tryblocks;
    private int stackDepth;
    
    public Bytecode(final ConstPool a1, final int a2, final int a3) {
        super();
        this.constPool = a1;
        this.maxStack = a2;
        this.maxLocals = a3;
        this.tryblocks = new ExceptionTable(a1);
        this.stackDepth = 0;
    }
    
    public Bytecode(final ConstPool a1) {
        this(a1, 0, 0);
    }
    
    @Override
    public Object clone() {
        try {
            final Bytecode v1 = (Bytecode)super.clone();
            v1.tryblocks = (ExceptionTable)this.tryblocks.clone();
            return v1;
        }
        catch (CloneNotSupportedException v2) {
            throw new RuntimeException(v2);
        }
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
    }
    
    public ExceptionTable getExceptionTable() {
        return this.tryblocks;
    }
    
    public CodeAttribute toCodeAttribute() {
        return new CodeAttribute(this.constPool, this.maxStack, this.maxLocals, this.get(), this.tryblocks);
    }
    
    public int length() {
        return this.getSize();
    }
    
    public byte[] get() {
        return this.copy();
    }
    
    public int getMaxStack() {
        return this.maxStack;
    }
    
    public void setMaxStack(final int a1) {
        this.maxStack = a1;
    }
    
    public int getMaxLocals() {
        return this.maxLocals;
    }
    
    public void setMaxLocals(final int a1) {
        this.maxLocals = a1;
    }
    
    public void setMaxLocals(final boolean v-3, final CtClass[] v-2, int v-1) {
        if (!v-3) {
            ++v-1;
        }
        if (v-2 != null) {
            final CtClass a3 = CtClass.doubleType;
            final CtClass v1 = CtClass.longType;
            for (final CtClass a5 : v-2) {
                if (a5 == a3 || a5 == v1) {
                    v-1 += 2;
                }
                else {
                    ++v-1;
                }
            }
        }
        this.maxLocals = v-1;
    }
    
    public void incMaxLocals(final int a1) {
        this.maxLocals += a1;
    }
    
    public void addExceptionHandler(final int a1, final int a2, final int a3, final CtClass a4) {
        this.addExceptionHandler(a1, a2, a3, this.constPool.addClassInfo(a4));
    }
    
    public void addExceptionHandler(final int a1, final int a2, final int a3, final String a4) {
        this.addExceptionHandler(a1, a2, a3, this.constPool.addClassInfo(a4));
    }
    
    public void addExceptionHandler(final int a1, final int a2, final int a3, final int a4) {
        this.tryblocks.add(a1, a2, a3, a4);
    }
    
    public int currentPc() {
        return this.getSize();
    }
    
    @Override
    public int read(final int a1) {
        return super.read(a1);
    }
    
    public int read16bit(final int a1) {
        final int v1 = this.read(a1);
        final int v2 = this.read(a1 + 1);
        return (v1 << 8) + (v2 & 0xFF);
    }
    
    public int read32bit(final int a1) {
        final int v1 = this.read16bit(a1);
        final int v2 = this.read16bit(a1 + 2);
        return (v1 << 16) + (v2 & 0xFFFF);
    }
    
    @Override
    public void write(final int a1, final int a2) {
        super.write(a1, a2);
    }
    
    public void write16bit(final int a1, final int a2) {
        this.write(a1, a2 >> 8);
        this.write(a1 + 1, a2);
    }
    
    public void write32bit(final int a1, final int a2) {
        this.write16bit(a1, a2 >> 16);
        this.write16bit(a1 + 2, a2);
    }
    
    @Override
    public void add(final int a1) {
        super.add(a1);
    }
    
    public void add32bit(final int a1) {
        this.add(a1 >> 24, a1 >> 16, a1 >> 8, a1);
    }
    
    @Override
    public void addGap(final int a1) {
        super.addGap(a1);
    }
    
    public void addOpcode(final int a1) {
        this.add(a1);
        this.growStack(Bytecode.STACK_GROW[a1]);
    }
    
    public void growStack(final int a1) {
        this.setStackDepth(this.stackDepth + a1);
    }
    
    public int getStackDepth() {
        return this.stackDepth;
    }
    
    public void setStackDepth(final int a1) {
        this.stackDepth = a1;
        if (this.stackDepth > this.maxStack) {
            this.maxStack = this.stackDepth;
        }
    }
    
    public void addIndex(final int a1) {
        this.add(a1 >> 8, a1);
    }
    
    public void addAload(final int a1) {
        if (a1 < 4) {
            this.addOpcode(42 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(25);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(25);
            this.addIndex(a1);
        }
    }
    
    public void addAstore(final int a1) {
        if (a1 < 4) {
            this.addOpcode(75 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(58);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(58);
            this.addIndex(a1);
        }
    }
    
    public void addIconst(final int a1) {
        if (a1 < 6 && -2 < a1) {
            this.addOpcode(3 + a1);
        }
        else if (a1 <= 127 && -128 <= a1) {
            this.addOpcode(16);
            this.add(a1);
        }
        else if (a1 <= 32767 && -32768 <= a1) {
            this.addOpcode(17);
            this.add(a1 >> 8);
            this.add(a1);
        }
        else {
            this.addLdc(this.constPool.addIntegerInfo(a1));
        }
    }
    
    public void addConstZero(final CtClass a1) {
        if (a1.isPrimitive()) {
            if (a1 == CtClass.longType) {
                this.addOpcode(9);
            }
            else if (a1 == CtClass.floatType) {
                this.addOpcode(11);
            }
            else if (a1 == CtClass.doubleType) {
                this.addOpcode(14);
            }
            else {
                if (a1 == CtClass.voidType) {
                    throw new RuntimeException("void type?");
                }
                this.addOpcode(3);
            }
        }
        else {
            this.addOpcode(1);
        }
    }
    
    public void addIload(final int a1) {
        if (a1 < 4) {
            this.addOpcode(26 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(21);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(21);
            this.addIndex(a1);
        }
    }
    
    public void addIstore(final int a1) {
        if (a1 < 4) {
            this.addOpcode(59 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(54);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(54);
            this.addIndex(a1);
        }
    }
    
    public void addLconst(final long a1) {
        if (a1 == 0L || a1 == 1L) {
            this.addOpcode(9 + (int)a1);
        }
        else {
            this.addLdc2w(a1);
        }
    }
    
    public void addLload(final int a1) {
        if (a1 < 4) {
            this.addOpcode(30 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(22);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(22);
            this.addIndex(a1);
        }
    }
    
    public void addLstore(final int a1) {
        if (a1 < 4) {
            this.addOpcode(63 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(55);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(55);
            this.addIndex(a1);
        }
    }
    
    public void addDconst(final double a1) {
        if (a1 == 0.0 || a1 == 1.0) {
            this.addOpcode(14 + (int)a1);
        }
        else {
            this.addLdc2w(a1);
        }
    }
    
    public void addDload(final int a1) {
        if (a1 < 4) {
            this.addOpcode(38 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(24);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(24);
            this.addIndex(a1);
        }
    }
    
    public void addDstore(final int a1) {
        if (a1 < 4) {
            this.addOpcode(71 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(57);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(57);
            this.addIndex(a1);
        }
    }
    
    public void addFconst(final float a1) {
        if (a1 == 0.0f || a1 == 1.0f || a1 == 2.0f) {
            this.addOpcode(11 + (int)a1);
        }
        else {
            this.addLdc(this.constPool.addFloatInfo(a1));
        }
    }
    
    public void addFload(final int a1) {
        if (a1 < 4) {
            this.addOpcode(34 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(23);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(23);
            this.addIndex(a1);
        }
    }
    
    public void addFstore(final int a1) {
        if (a1 < 4) {
            this.addOpcode(67 + a1);
        }
        else if (a1 < 256) {
            this.addOpcode(56);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(56);
            this.addIndex(a1);
        }
    }
    
    public int addLoad(final int a1, final CtClass a2) {
        if (a2.isPrimitive()) {
            if (a2 == CtClass.booleanType || a2 == CtClass.charType || a2 == CtClass.byteType || a2 == CtClass.shortType || a2 == CtClass.intType) {
                this.addIload(a1);
            }
            else {
                if (a2 == CtClass.longType) {
                    this.addLload(a1);
                    return 2;
                }
                if (a2 == CtClass.floatType) {
                    this.addFload(a1);
                }
                else {
                    if (a2 == CtClass.doubleType) {
                        this.addDload(a1);
                        return 2;
                    }
                    throw new RuntimeException("void type?");
                }
            }
        }
        else {
            this.addAload(a1);
        }
        return 1;
    }
    
    public int addStore(final int a1, final CtClass a2) {
        if (a2.isPrimitive()) {
            if (a2 == CtClass.booleanType || a2 == CtClass.charType || a2 == CtClass.byteType || a2 == CtClass.shortType || a2 == CtClass.intType) {
                this.addIstore(a1);
            }
            else {
                if (a2 == CtClass.longType) {
                    this.addLstore(a1);
                    return 2;
                }
                if (a2 == CtClass.floatType) {
                    this.addFstore(a1);
                }
                else {
                    if (a2 == CtClass.doubleType) {
                        this.addDstore(a1);
                        return 2;
                    }
                    throw new RuntimeException("void type?");
                }
            }
        }
        else {
            this.addAstore(a1);
        }
        return 1;
    }
    
    public int addLoadParameters(final CtClass[] v2, final int v3) {
        int v4 = 0;
        if (v2 != null) {
            for (int a2 = v2.length, a3 = 0; a3 < a2; ++a3) {
                v4 += this.addLoad(v4 + v3, v2[a3]);
            }
        }
        return v4;
    }
    
    public void addCheckcast(final CtClass a1) {
        this.addOpcode(192);
        this.addIndex(this.constPool.addClassInfo(a1));
    }
    
    public void addCheckcast(final String a1) {
        this.addOpcode(192);
        this.addIndex(this.constPool.addClassInfo(a1));
    }
    
    public void addInstanceof(final String a1) {
        this.addOpcode(193);
        this.addIndex(this.constPool.addClassInfo(a1));
    }
    
    public void addGetfield(final CtClass a1, final String a2, final String a3) {
        this.add(180);
        final int v1 = this.constPool.addClassInfo(a1);
        this.addIndex(this.constPool.addFieldrefInfo(v1, a2, a3));
        this.growStack(Descriptor.dataSize(a3) - 1);
    }
    
    public void addGetfield(final String a1, final String a2, final String a3) {
        this.add(180);
        final int v1 = this.constPool.addClassInfo(a1);
        this.addIndex(this.constPool.addFieldrefInfo(v1, a2, a3));
        this.growStack(Descriptor.dataSize(a3) - 1);
    }
    
    public void addGetstatic(final CtClass a1, final String a2, final String a3) {
        this.add(178);
        final int v1 = this.constPool.addClassInfo(a1);
        this.addIndex(this.constPool.addFieldrefInfo(v1, a2, a3));
        this.growStack(Descriptor.dataSize(a3));
    }
    
    public void addGetstatic(final String a1, final String a2, final String a3) {
        this.add(178);
        final int v1 = this.constPool.addClassInfo(a1);
        this.addIndex(this.constPool.addFieldrefInfo(v1, a2, a3));
        this.growStack(Descriptor.dataSize(a3));
    }
    
    public void addInvokespecial(final CtClass a1, final String a2, final CtClass a3, final CtClass[] a4) {
        final String v1 = Descriptor.ofMethod(a3, a4);
        this.addInvokespecial(a1, a2, v1);
    }
    
    public void addInvokespecial(final CtClass a1, final String a2, final String a3) {
        final boolean v1 = a1 != null && a1.isInterface();
        this.addInvokespecial(v1, this.constPool.addClassInfo(a1), a2, a3);
    }
    
    public void addInvokespecial(final String a1, final String a2, final String a3) {
        this.addInvokespecial(false, this.constPool.addClassInfo(a1), a2, a3);
    }
    
    public void addInvokespecial(final int a1, final String a2, final String a3) {
        this.addInvokespecial(false, a1, a2, a3);
    }
    
    public void addInvokespecial(final boolean a3, final int a4, final String v1, final String v2) {
        int v3 = 0;
        if (a3) {
            final int a5 = this.constPool.addInterfaceMethodrefInfo(a4, v1, v2);
        }
        else {
            v3 = this.constPool.addMethodrefInfo(a4, v1, v2);
        }
        this.addInvokespecial(v3, v2);
    }
    
    public void addInvokespecial(final int a1, final String a2) {
        this.add(183);
        this.addIndex(a1);
        this.growStack(Descriptor.dataSize(a2) - 1);
    }
    
    public void addInvokestatic(final CtClass a1, final String a2, final CtClass a3, final CtClass[] a4) {
        final String v1 = Descriptor.ofMethod(a3, a4);
        this.addInvokestatic(a1, a2, v1);
    }
    
    public void addInvokestatic(final CtClass a3, final String v1, final String v2) {
        boolean v3 = false;
        if (a3 == Bytecode.THIS) {
            final boolean a4 = false;
        }
        else {
            v3 = a3.isInterface();
        }
        this.addInvokestatic(this.constPool.addClassInfo(a3), v1, v2, v3);
    }
    
    public void addInvokestatic(final String a1, final String a2, final String a3) {
        this.addInvokestatic(this.constPool.addClassInfo(a1), a2, a3);
    }
    
    public void addInvokestatic(final int a1, final String a2, final String a3) {
        this.addInvokestatic(a1, a2, a3, false);
    }
    
    private void addInvokestatic(final int a3, final String a4, final String v1, final boolean v2) {
        this.add(184);
        int v3 = 0;
        if (v2) {
            final int a5 = this.constPool.addInterfaceMethodrefInfo(a3, a4, v1);
        }
        else {
            v3 = this.constPool.addMethodrefInfo(a3, a4, v1);
        }
        this.addIndex(v3);
        this.growStack(Descriptor.dataSize(v1));
    }
    
    public void addInvokevirtual(final CtClass a1, final String a2, final CtClass a3, final CtClass[] a4) {
        final String v1 = Descriptor.ofMethod(a3, a4);
        this.addInvokevirtual(a1, a2, v1);
    }
    
    public void addInvokevirtual(final CtClass a1, final String a2, final String a3) {
        this.addInvokevirtual(this.constPool.addClassInfo(a1), a2, a3);
    }
    
    public void addInvokevirtual(final String a1, final String a2, final String a3) {
        this.addInvokevirtual(this.constPool.addClassInfo(a1), a2, a3);
    }
    
    public void addInvokevirtual(final int a1, final String a2, final String a3) {
        this.add(182);
        this.addIndex(this.constPool.addMethodrefInfo(a1, a2, a3));
        this.growStack(Descriptor.dataSize(a3) - 1);
    }
    
    public void addInvokeinterface(final CtClass a1, final String a2, final CtClass a3, final CtClass[] a4, final int a5) {
        final String v1 = Descriptor.ofMethod(a3, a4);
        this.addInvokeinterface(a1, a2, v1, a5);
    }
    
    public void addInvokeinterface(final CtClass a1, final String a2, final String a3, final int a4) {
        this.addInvokeinterface(this.constPool.addClassInfo(a1), a2, a3, a4);
    }
    
    public void addInvokeinterface(final String a1, final String a2, final String a3, final int a4) {
        this.addInvokeinterface(this.constPool.addClassInfo(a1), a2, a3, a4);
    }
    
    public void addInvokeinterface(final int a1, final String a2, final String a3, final int a4) {
        this.add(185);
        this.addIndex(this.constPool.addInterfaceMethodrefInfo(a1, a2, a3));
        this.add(a4);
        this.add(0);
        this.growStack(Descriptor.dataSize(a3) - 1);
    }
    
    public void addInvokedynamic(final int a1, final String a2, final String a3) {
        final int v1 = this.constPool.addNameAndTypeInfo(a2, a3);
        final int v2 = this.constPool.addInvokeDynamicInfo(a1, v1);
        this.add(186);
        this.addIndex(v2);
        this.add(0, 0);
        this.growStack(Descriptor.dataSize(a3));
    }
    
    public void addLdc(final String a1) {
        this.addLdc(this.constPool.addStringInfo(a1));
    }
    
    public void addLdc(final int a1) {
        if (a1 > 255) {
            this.addOpcode(19);
            this.addIndex(a1);
        }
        else {
            this.addOpcode(18);
            this.add(a1);
        }
    }
    
    public void addLdc2w(final long a1) {
        this.addOpcode(20);
        this.addIndex(this.constPool.addLongInfo(a1));
    }
    
    public void addLdc2w(final double a1) {
        this.addOpcode(20);
        this.addIndex(this.constPool.addDoubleInfo(a1));
    }
    
    public void addNew(final CtClass a1) {
        this.addOpcode(187);
        this.addIndex(this.constPool.addClassInfo(a1));
    }
    
    public void addNew(final String a1) {
        this.addOpcode(187);
        this.addIndex(this.constPool.addClassInfo(a1));
    }
    
    public void addAnewarray(final String a1) {
        this.addOpcode(189);
        this.addIndex(this.constPool.addClassInfo(a1));
    }
    
    public void addAnewarray(final CtClass a1, final int a2) {
        this.addIconst(a2);
        this.addOpcode(189);
        this.addIndex(this.constPool.addClassInfo(a1));
    }
    
    public void addNewarray(final int a1, final int a2) {
        this.addIconst(a2);
        this.addOpcode(188);
        this.add(a1);
    }
    
    public int addMultiNewarray(final CtClass v1, final int[] v2) {
        final int v3 = v2.length;
        for (int a1 = 0; a1 < v3; ++a1) {
            this.addIconst(v2[a1]);
        }
        this.growStack(v3);
        return this.addMultiNewarray(v1, v3);
    }
    
    public int addMultiNewarray(final CtClass a1, final int a2) {
        this.add(197);
        this.addIndex(this.constPool.addClassInfo(a1));
        this.add(a2);
        this.growStack(1 - a2);
        return a2;
    }
    
    public int addMultiNewarray(final String a1, final int a2) {
        this.add(197);
        this.addIndex(this.constPool.addClassInfo(a1));
        this.add(a2);
        this.growStack(1 - a2);
        return a2;
    }
    
    public void addPutfield(final CtClass a1, final String a2, final String a3) {
        this.addPutfield0(a1, null, a2, a3);
    }
    
    public void addPutfield(final String a1, final String a2, final String a3) {
        this.addPutfield0(null, a1, a2, a3);
    }
    
    private void addPutfield0(final CtClass a1, final String a2, final String a3, final String a4) {
        this.add(181);
        final int v1 = (a2 == null) ? this.constPool.addClassInfo(a1) : this.constPool.addClassInfo(a2);
        this.addIndex(this.constPool.addFieldrefInfo(v1, a3, a4));
        this.growStack(-1 - Descriptor.dataSize(a4));
    }
    
    public void addPutstatic(final CtClass a1, final String a2, final String a3) {
        this.addPutstatic0(a1, null, a2, a3);
    }
    
    public void addPutstatic(final String a1, final String a2, final String a3) {
        this.addPutstatic0(null, a1, a2, a3);
    }
    
    private void addPutstatic0(final CtClass a1, final String a2, final String a3, final String a4) {
        this.add(179);
        final int v1 = (a2 == null) ? this.constPool.addClassInfo(a1) : this.constPool.addClassInfo(a2);
        this.addIndex(this.constPool.addFieldrefInfo(v1, a3, a4));
        this.growStack(-Descriptor.dataSize(a4));
    }
    
    public void addReturn(final CtClass v2) {
        if (v2 == null) {
            this.addOpcode(177);
        }
        else if (v2.isPrimitive()) {
            final CtPrimitiveType a1 = (CtPrimitiveType)v2;
            this.addOpcode(a1.getReturnOp());
        }
        else {
            this.addOpcode(176);
        }
    }
    
    public void addRet(final int a1) {
        if (a1 < 256) {
            this.addOpcode(169);
            this.add(a1);
        }
        else {
            this.addOpcode(196);
            this.addOpcode(169);
            this.addIndex(a1);
        }
    }
    
    public void addPrintln(final String a1) {
        this.addGetstatic("java.lang.System", "err", "Ljava/io/PrintStream;");
        this.addLdc(a1);
        this.addInvokevirtual("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
    }
    
    @Override
    public /* bridge */ void add(final int a1, final int a2, final int a3, final int a4) {
        super.add(a1, a2, a3, a4);
    }
    
    @Override
    public /* bridge */ void add(final int a1, final int a2) {
        super.add(a1, a2);
    }
    
    static {
        THIS = ConstPool.THIS;
    }
}
