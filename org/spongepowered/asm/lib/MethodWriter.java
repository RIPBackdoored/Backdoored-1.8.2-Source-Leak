package org.spongepowered.asm.lib;

class MethodWriter extends MethodVisitor
{
    static final int ACC_CONSTRUCTOR = 524288;
    static final int SAME_FRAME = 0;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
    static final int RESERVED = 128;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
    static final int CHOP_FRAME = 248;
    static final int SAME_FRAME_EXTENDED = 251;
    static final int APPEND_FRAME = 252;
    static final int FULL_FRAME = 255;
    static final int FRAMES = 0;
    static final int INSERTED_FRAMES = 1;
    static final int MAXS = 2;
    static final int NOTHING = 3;
    final ClassWriter cw;
    private int access;
    private final int name;
    private final int desc;
    private final String descriptor;
    String signature;
    int classReaderOffset;
    int classReaderLength;
    int exceptionCount;
    int[] exceptions;
    private ByteVector annd;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private AnnotationWriter[] panns;
    private AnnotationWriter[] ipanns;
    private int synthetics;
    private Attribute attrs;
    private ByteVector code;
    private int maxStack;
    private int maxLocals;
    private int currentLocals;
    private int frameCount;
    private ByteVector stackMap;
    private int previousFrameOffset;
    private int[] previousFrame;
    private int[] frame;
    private int handlerCount;
    private Handler firstHandler;
    private Handler lastHandler;
    private int methodParametersCount;
    private ByteVector methodParameters;
    private int localVarCount;
    private ByteVector localVar;
    private int localVarTypeCount;
    private ByteVector localVarType;
    private int lineNumberCount;
    private ByteVector lineNumber;
    private int lastCodeOffset;
    private AnnotationWriter ctanns;
    private AnnotationWriter ictanns;
    private Attribute cattrs;
    private int subroutines;
    private final int compute;
    private Label labels;
    private Label previousBlock;
    private Label currentBlock;
    private int stackSize;
    private int maxStackSize;
    
    MethodWriter(final ClassWriter a4, final int a5, final String a6, final String a7, final String v1, final String[] v2, final int v3) {
        super(327680);
        this.code = new ByteVector();
        if (a4.firstMethod == null) {
            a4.firstMethod = this;
        }
        else {
            a4.lastMethod.mv = this;
        }
        a4.lastMethod = this;
        this.cw = a4;
        this.access = a5;
        if ("<init>".equals(a6)) {
            this.access |= 0x80000;
        }
        this.name = a4.newUTF8(a6);
        this.desc = a4.newUTF8(a7);
        this.descriptor = a7;
        this.signature = v1;
        if (v2 != null && v2.length > 0) {
            this.exceptionCount = v2.length;
            this.exceptions = new int[this.exceptionCount];
            for (int a8 = 0; a8 < this.exceptionCount; ++a8) {
                this.exceptions[a8] = a4.newClass(v2[a8]);
            }
        }
        if ((this.compute = v3) != 3) {
            int a9 = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
            if ((a5 & 0x8) != 0x0) {
                --a9;
            }
            this.maxLocals = a9;
            this.currentLocals = a9;
            this.labels = new Label();
            final Label labels = this.labels;
            labels.status |= 0x8;
            this.visitLabel(this.labels);
        }
    }
    
    @Override
    public void visitParameter(final String a1, final int a2) {
        if (this.methodParameters == null) {
            this.methodParameters = new ByteVector();
        }
        ++this.methodParametersCount;
        this.methodParameters.putShort((a1 == null) ? 0 : this.cw.newUTF8(a1)).putShort(a2);
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        this.annd = new ByteVector();
        return new AnnotationWriter(this.cw, false, this.annd, null, 0);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final ByteVector v1 = new ByteVector();
        v1.putShort(this.cw.newUTF8(a1)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this.cw, true, v1, v1, 2);
        if (a2) {
            v2.next = this.anns;
            this.anns = v2;
        }
        else {
            v2.next = this.ianns;
            this.ianns = v2;
        }
        return v2;
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final ByteVector v1 = new ByteVector();
        AnnotationWriter.putTarget(a1, a2, v1);
        v1.putShort(this.cw.newUTF8(a3)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this.cw, true, v1, v1, v1.length - 2);
        if (a4) {
            v2.next = this.tanns;
            this.tanns = v2;
        }
        else {
            v2.next = this.itanns;
            this.itanns = v2;
        }
        return v2;
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        final ByteVector v1 = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(a2)) {
            this.synthetics = Math.max(this.synthetics, a1 + 1);
            return new AnnotationWriter(this.cw, false, v1, null, 0);
        }
        v1.putShort(this.cw.newUTF8(a2)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this.cw, true, v1, v1, 2);
        if (a3) {
            if (this.panns == null) {
                this.panns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            v2.next = this.panns[a1];
            this.panns[a1] = v2;
        }
        else {
            if (this.ipanns == null) {
                this.ipanns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            v2.next = this.ipanns[a1];
            this.ipanns[a1] = v2;
        }
        return v2;
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        if (a1.isCodeAttribute()) {
            a1.next = this.cattrs;
            this.cattrs = a1;
        }
        else {
            a1.next = this.attrs;
            this.attrs = a1;
        }
    }
    
    @Override
    public void visitCode() {
    }
    
    @Override
    public void visitFrame(final int v-5, final int v-4, final Object[] v-3, final int v-2, final Object[] v-1) {
        if (this.compute == 0) {
            return;
        }
        if (this.compute == 1) {
            if (this.currentBlock.frame == null) {
                this.currentBlock.frame = new CurrentFrame();
                this.currentBlock.frame.owner = this.currentBlock;
                this.currentBlock.frame.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), v-4);
                this.visitImplicitFirstFrame();
            }
            else {
                if (v-5 == -1) {
                    this.currentBlock.frame.set(this.cw, v-4, v-3, v-2, v-1);
                }
                this.visitFrame(this.currentBlock.frame);
            }
        }
        else if (v-5 == -1) {
            if (this.previousFrame == null) {
                this.visitImplicitFirstFrame();
            }
            this.currentLocals = v-4;
            int a3 = this.startFrame(this.code.length, v-4, v-2);
            for (int a4 = 0; a4 < v-4; ++a4) {
                if (v-3[a4] instanceof String) {
                    this.frame[a3++] = (0x1700000 | this.cw.addType((String)v-3[a4]));
                }
                else if (v-3[a4] instanceof Integer) {
                    this.frame[a3++] = (int)v-3[a4];
                }
                else {
                    this.frame[a3++] = (0x1800000 | this.cw.addUninitializedType("", ((Label)v-3[a4]).position));
                }
            }
            for (int a5 = 0; a5 < v-2; ++a5) {
                if (v-1[a5] instanceof String) {
                    this.frame[a3++] = (0x1700000 | this.cw.addType((String)v-1[a5]));
                }
                else if (v-1[a5] instanceof Integer) {
                    this.frame[a3++] = (int)v-1[a5];
                }
                else {
                    this.frame[a3++] = (0x1800000 | this.cw.addUninitializedType("", ((Label)v-1[a5]).position));
                }
            }
            this.endFrame();
        }
        else {
            int v0 = 0;
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
                final int a6 = this.code.length;
            }
            else {
                v0 = this.code.length - this.previousFrameOffset - 1;
                if (v0 < 0) {
                    if (v-5 == 3) {
                        return;
                    }
                    throw new IllegalStateException();
                }
            }
            switch (v-5) {
                case 0: {
                    this.currentLocals = v-4;
                    this.stackMap.putByte(255).putShort(v0).putShort(v-4);
                    for (int a7 = 0; a7 < v-4; ++a7) {
                        this.writeFrameType(v-3[a7]);
                    }
                    this.stackMap.putShort(v-2);
                    for (int v2 = 0; v2 < v-2; ++v2) {
                        this.writeFrameType(v-1[v2]);
                    }
                    break;
                }
                case 1: {
                    this.currentLocals += v-4;
                    this.stackMap.putByte(251 + v-4).putShort(v0);
                    for (int v2 = 0; v2 < v-4; ++v2) {
                        this.writeFrameType(v-3[v2]);
                    }
                    break;
                }
                case 2: {
                    this.currentLocals -= v-4;
                    this.stackMap.putByte(251 - v-4).putShort(v0);
                    break;
                }
                case 3: {
                    if (v0 < 64) {
                        this.stackMap.putByte(v0);
                        break;
                    }
                    this.stackMap.putByte(251).putShort(v0);
                    break;
                }
                case 4: {
                    if (v0 < 64) {
                        this.stackMap.putByte(64 + v0);
                    }
                    else {
                        this.stackMap.putByte(247).putShort(v0);
                    }
                    this.writeFrameType(v-1[0]);
                    break;
                }
            }
            this.previousFrameOffset = this.code.length;
            ++this.frameCount;
        }
        this.maxStack = Math.max(this.maxStack, v-2);
        this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
    }
    
    @Override
    public void visitInsn(final int v2) {
        this.lastCodeOffset = this.code.length;
        this.code.putByte(v2);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(v2, 0, null, null);
            }
            else {
                final int a1 = this.stackSize + Frame.SIZE[v2];
                if (a1 > this.maxStackSize) {
                    this.maxStackSize = a1;
                }
                this.stackSize = a1;
            }
            if ((v2 >= 172 && v2 <= 177) || v2 == 191) {
                this.noSuccessor();
            }
        }
    }
    
    @Override
    public void visitIntInsn(final int v1, final int v2) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(v1, v2, null, null);
            }
            else if (v1 != 188) {
                final int a1 = this.stackSize + 1;
                if (a1 > this.maxStackSize) {
                    this.maxStackSize = a1;
                }
                this.stackSize = a1;
            }
        }
        if (v1 == 17) {
            this.code.put12(v1, v2);
        }
        else {
            this.code.put11(v1, v2);
        }
    }
    
    @Override
    public void visitVarInsn(final int v-1, final int v0) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(v-1, v0, null, null);
            }
            else if (v-1 == 169) {
                final Label currentBlock = this.currentBlock;
                currentBlock.status |= 0x100;
                this.currentBlock.inputStackTop = this.stackSize;
                this.noSuccessor();
            }
            else {
                final int a1 = this.stackSize + Frame.SIZE[v-1];
                if (a1 > this.maxStackSize) {
                    this.maxStackSize = a1;
                }
                this.stackSize = a1;
            }
        }
        if (this.compute != 3) {
            int v = 0;
            if (v-1 == 22 || v-1 == 24 || v-1 == 55 || v-1 == 57) {
                final int a2 = v0 + 2;
            }
            else {
                v = v0 + 1;
            }
            if (v > this.maxLocals) {
                this.maxLocals = v;
            }
        }
        if (v0 < 4 && v-1 != 169) {
            int v;
            if (v-1 < 54) {
                v = 26 + (v-1 - 21 << 2) + v0;
            }
            else {
                v = 59 + (v-1 - 54 << 2) + v0;
            }
            this.code.putByte(v);
        }
        else if (v0 >= 256) {
            this.code.putByte(196).put12(v-1, v0);
        }
        else {
            this.code.put11(v-1, v0);
        }
        if (v-1 >= 54 && this.compute == 0 && this.handlerCount > 0) {
            this.visitLabel(new Label());
        }
    }
    
    @Override
    public void visitTypeInsn(final int v1, final String v2) {
        this.lastCodeOffset = this.code.length;
        final Item v3 = this.cw.newClassItem(v2);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(v1, this.code.length, this.cw, v3);
            }
            else if (v1 == 187) {
                final int a1 = this.stackSize + 1;
                if (a1 > this.maxStackSize) {
                    this.maxStackSize = a1;
                }
                this.stackSize = a1;
            }
        }
        this.code.put12(v1, v3.index);
    }
    
    @Override
    public void visitFieldInsn(final int v-4, final String v-3, final String v-2, final String v-1) {
        this.lastCodeOffset = this.code.length;
        final Item v0 = this.cw.newFieldItem(v-3, v-2, v-1);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(v-4, 0, this.cw, v0);
            }
            else {
                final char v2 = v-1.charAt(0);
                final int a4;
                switch (v-4) {
                    case 178: {
                        final int a1 = this.stackSize + ((v2 == 'D' || v2 == 'J') ? 2 : 1);
                        break;
                    }
                    case 179: {
                        final int a2 = this.stackSize + ((v2 == 'D' || v2 == 'J') ? -2 : -1);
                        break;
                    }
                    case 180: {
                        final int a3 = this.stackSize + ((v2 == 'D' || v2 == 'J') ? 1 : 0);
                        break;
                    }
                    default: {
                        a4 = this.stackSize + ((v2 == 'D' || v2 == 'J') ? -3 : -2);
                        break;
                    }
                }
                if (a4 > this.maxStackSize) {
                    this.maxStackSize = a4;
                }
                this.stackSize = a4;
            }
        }
        this.code.put12(v-4, v0.index);
    }
    
    @Override
    public void visitMethodInsn(final int a4, final String a5, final String v1, final String v2, final boolean v3) {
        this.lastCodeOffset = this.code.length;
        final Item v4 = this.cw.newMethodItem(a5, v1, v2, v3);
        int v5 = v4.intVal;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(a4, 0, this.cw, v4);
            }
            else {
                if (v5 == 0) {
                    v5 = Type.getArgumentsAndReturnSizes(v2);
                    v4.intVal = v5;
                }
                int a7 = 0;
                if (a4 == 184) {
                    final int a6 = this.stackSize - (v5 >> 2) + (v5 & 0x3) + 1;
                }
                else {
                    a7 = this.stackSize - (v5 >> 2) + (v5 & 0x3);
                }
                if (a7 > this.maxStackSize) {
                    this.maxStackSize = a7;
                }
                this.stackSize = a7;
            }
        }
        if (a4 == 185) {
            if (v5 == 0) {
                v5 = Type.getArgumentsAndReturnSizes(v2);
                v4.intVal = v5;
            }
            this.code.put12(185, v4.index).put11(v5 >> 2, 0);
        }
        else {
            this.code.put12(a4, v4.index);
        }
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String a3, final String a4, final Handle v1, final Object... v2) {
        this.lastCodeOffset = this.code.length;
        final Item v3 = this.cw.newInvokeDynamicItem(a3, a4, v1, v2);
        int v4 = v3.intVal;
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(186, 0, this.cw, v3);
            }
            else {
                if (v4 == 0) {
                    v4 = Type.getArgumentsAndReturnSizes(a4);
                    v3.intVal = v4;
                }
                final int a5 = this.stackSize - (v4 >> 2) + (v4 & 0x3) + 1;
                if (a5 > this.maxStackSize) {
                    this.maxStackSize = a5;
                }
                this.stackSize = a5;
            }
        }
        this.code.put12(186, v3.index);
        this.code.putShort(0);
    }
    
    @Override
    public void visitJumpInsn(int a1, final Label a2) {
        final boolean v1 = a1 >= 200;
        a1 = (v1 ? (a1 - 33) : a1);
        this.lastCodeOffset = this.code.length;
        Label v2 = null;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(a1, 0, null, null);
                final Label first = a2.getFirst();
                first.status |= 0x10;
                this.addSuccessor(0, a2);
                if (a1 != 167) {
                    v2 = new Label();
                }
            }
            else if (this.compute == 1) {
                this.currentBlock.frame.execute(a1, 0, null, null);
            }
            else if (a1 == 168) {
                if ((a2.status & 0x200) == 0x0) {
                    a2.status |= 0x200;
                    ++this.subroutines;
                }
                final Label currentBlock = this.currentBlock;
                currentBlock.status |= 0x80;
                this.addSuccessor(this.stackSize + 1, a2);
                v2 = new Label();
            }
            else {
                this.addSuccessor(this.stackSize += Frame.SIZE[a1], a2);
            }
        }
        if ((a2.status & 0x2) != 0x0 && a2.position - this.code.length < -32768) {
            if (a1 == 167) {
                this.code.putByte(200);
            }
            else if (a1 == 168) {
                this.code.putByte(201);
            }
            else {
                if (v2 != null) {
                    final Label label = v2;
                    label.status |= 0x10;
                }
                this.code.putByte((a1 <= 166) ? ((a1 + 1 ^ 0x1) - 1) : (a1 ^ 0x1));
                this.code.putShort(8);
                this.code.putByte(200);
            }
            a2.put(this, this.code, this.code.length - 1, true);
        }
        else if (v1) {
            this.code.putByte(a1 + 33);
            a2.put(this, this.code, this.code.length - 1, true);
        }
        else {
            this.code.putByte(a1);
            a2.put(this, this.code, this.code.length - 1, false);
        }
        if (this.currentBlock != null) {
            if (v2 != null) {
                this.visitLabel(v2);
            }
            if (a1 == 167) {
                this.noSuccessor();
            }
        }
    }
    
    @Override
    public void visitLabel(final Label a1) {
        final ClassWriter cw = this.cw;
        cw.hasAsmInsns |= a1.resolve(this, this.code.length, this.code.data);
        if ((a1.status & 0x1) != 0x0) {
            return;
        }
        if (this.compute == 0) {
            if (this.currentBlock != null) {
                if (a1.position == this.currentBlock.position) {
                    final Label currentBlock = this.currentBlock;
                    currentBlock.status |= (a1.status & 0x10);
                    a1.frame = this.currentBlock.frame;
                    return;
                }
                this.addSuccessor(0, a1);
            }
            this.currentBlock = a1;
            if (a1.frame == null) {
                a1.frame = new Frame();
                a1.frame.owner = a1;
            }
            if (this.previousBlock != null) {
                if (a1.position == this.previousBlock.position) {
                    final Label previousBlock = this.previousBlock;
                    previousBlock.status |= (a1.status & 0x10);
                    a1.frame = this.previousBlock.frame;
                    this.currentBlock = this.previousBlock;
                    return;
                }
                this.previousBlock.successor = a1;
            }
            this.previousBlock = a1;
        }
        else if (this.compute == 1) {
            if (this.currentBlock == null) {
                this.currentBlock = a1;
            }
            else {
                this.currentBlock.frame.owner = a1;
            }
        }
        else if (this.compute == 2) {
            if (this.currentBlock != null) {
                this.currentBlock.outputStackMax = this.maxStackSize;
                this.addSuccessor(this.stackSize, a1);
            }
            this.currentBlock = a1;
            this.stackSize = 0;
            this.maxStackSize = 0;
            if (this.previousBlock != null) {
                this.previousBlock.successor = a1;
            }
            this.previousBlock = a1;
        }
    }
    
    @Override
    public void visitLdcInsn(final Object v-1) {
        this.lastCodeOffset = this.code.length;
        final Item v0 = this.cw.newConstItem(v-1);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(18, 0, this.cw, v0);
            }
            else {
                int v2 = 0;
                if (v0.type == 5 || v0.type == 6) {
                    final int a1 = this.stackSize + 2;
                }
                else {
                    v2 = this.stackSize + 1;
                }
                if (v2 > this.maxStackSize) {
                    this.maxStackSize = v2;
                }
                this.stackSize = v2;
            }
        }
        int v2 = v0.index;
        if (v0.type == 5 || v0.type == 6) {
            this.code.put12(20, v2);
        }
        else if (v2 >= 256) {
            this.code.put12(19, v2);
        }
        else {
            this.code.put11(18, v2);
        }
    }
    
    @Override
    public void visitIincInsn(final int v1, final int v2) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null && (this.compute == 0 || this.compute == 1)) {
            this.currentBlock.frame.execute(132, v1, null, null);
        }
        if (this.compute != 3) {
            final int a1 = v1 + 1;
            if (a1 > this.maxLocals) {
                this.maxLocals = a1;
            }
        }
        if (v1 > 255 || v2 > 127 || v2 < -128) {
            this.code.putByte(196).put12(132, v1).putShort(v2);
        }
        else {
            this.code.putByte(132).put11(v1, v2);
        }
    }
    
    @Override
    public void visitTableSwitchInsn(final int a3, final int a4, final Label v1, final Label... v2) {
        this.lastCodeOffset = this.code.length;
        final int v3 = this.code.length;
        this.code.putByte(170);
        this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
        v1.put(this, this.code, v3, true);
        this.code.putInt(a3).putInt(a4);
        for (int a5 = 0; a5 < v2.length; ++a5) {
            v2[a5].put(this, this.code, v3, true);
        }
        this.visitSwitchInsn(v1, v2);
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label a3, final int[] v1, final Label[] v2) {
        this.lastCodeOffset = this.code.length;
        final int v3 = this.code.length;
        this.code.putByte(171);
        this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
        a3.put(this, this.code, v3, true);
        this.code.putInt(v2.length);
        for (int a4 = 0; a4 < v2.length; ++a4) {
            this.code.putInt(v1[a4]);
            v2[a4].put(this, this.code, v3, true);
        }
        this.visitSwitchInsn(a3, v2);
    }
    
    private void visitSwitchInsn(final Label v2, final Label[] v3) {
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(171, 0, null, null);
                this.addSuccessor(0, v2);
                final Label first = v2.getFirst();
                first.status |= 0x10;
                for (int a1 = 0; a1 < v3.length; ++a1) {
                    this.addSuccessor(0, v3[a1]);
                    final Label first2 = v3[a1].getFirst();
                    first2.status |= 0x10;
                }
            }
            else {
                this.addSuccessor(--this.stackSize, v2);
                for (int a2 = 0; a2 < v3.length; ++a2) {
                    this.addSuccessor(this.stackSize, v3[a2]);
                }
            }
            this.noSuccessor();
        }
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String a1, final int a2) {
        this.lastCodeOffset = this.code.length;
        final Item v1 = this.cw.newClassItem(a1);
        if (this.currentBlock != null) {
            if (this.compute == 0 || this.compute == 1) {
                this.currentBlock.frame.execute(197, a2, this.cw, v1);
            }
            else {
                this.stackSize += 1 - a2;
            }
        }
        this.code.put12(197, v1.index).putByte(a2);
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(int a1, final TypePath a2, final String a3, final boolean a4) {
        final ByteVector v1 = new ByteVector();
        a1 = ((a1 & 0xFF0000FF) | this.lastCodeOffset << 8);
        AnnotationWriter.putTarget(a1, a2, v1);
        v1.putShort(this.cw.newUTF8(a3)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this.cw, true, v1, v1, v1.length - 2);
        if (a4) {
            v2.next = this.ctanns;
            this.ctanns = v2;
        }
        else {
            v2.next = this.ictanns;
            this.ictanns = v2;
        }
        return v2;
    }
    
    @Override
    public void visitTryCatchBlock(final Label a1, final Label a2, final Label a3, final String a4) {
        ++this.handlerCount;
        final Handler v1 = new Handler();
        v1.start = a1;
        v1.end = a2;
        v1.handler = a3;
        v1.desc = a4;
        v1.type = ((a4 != null) ? this.cw.newClass(a4) : 0);
        if (this.lastHandler == null) {
            this.firstHandler = v1;
        }
        else {
            this.lastHandler.next = v1;
        }
        this.lastHandler = v1;
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final ByteVector v1 = new ByteVector();
        AnnotationWriter.putTarget(a1, a2, v1);
        v1.putShort(this.cw.newUTF8(a3)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this.cw, true, v1, v1, v1.length - 2);
        if (a4) {
            v2.next = this.ctanns;
            this.ctanns = v2;
        }
        else {
            v2.next = this.ictanns;
            this.ictanns = v2;
        }
        return v2;
    }
    
    @Override
    public void visitLocalVariable(final String a4, final String a5, final String a6, final Label v1, final Label v2, final int v3) {
        if (a6 != null) {
            if (this.localVarType == null) {
                this.localVarType = new ByteVector();
            }
            ++this.localVarTypeCount;
            this.localVarType.putShort(v1.position).putShort(v2.position - v1.position).putShort(this.cw.newUTF8(a4)).putShort(this.cw.newUTF8(a6)).putShort(v3);
        }
        if (this.localVar == null) {
            this.localVar = new ByteVector();
        }
        ++this.localVarCount;
        this.localVar.putShort(v1.position).putShort(v2.position - v1.position).putShort(this.cw.newUTF8(a4)).putShort(this.cw.newUTF8(a5)).putShort(v3);
        if (this.compute != 3) {
            final char a7 = a5.charAt(0);
            final int a8 = v3 + ((a7 == 'J' || a7 == 'D') ? 2 : 1);
            if (a8 > this.maxLocals) {
                this.maxLocals = a8;
            }
        }
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int a4, final TypePath a5, final Label[] a6, final Label[] a7, final int[] v1, final String v2, final boolean v3) {
        final ByteVector v4 = new ByteVector();
        v4.putByte(a4 >>> 24).putShort(a6.length);
        for (int a8 = 0; a8 < a6.length; ++a8) {
            v4.putShort(a6[a8].position).putShort(a7[a8].position - a6[a8].position).putShort(v1[a8]);
        }
        if (a5 == null) {
            v4.putByte(0);
        }
        else {
            final int a9 = a5.b[a5.offset] * 2 + 1;
            v4.putByteArray(a5.b, a5.offset, a9);
        }
        v4.putShort(this.cw.newUTF8(v2)).putShort(0);
        final AnnotationWriter v5 = new AnnotationWriter(this.cw, true, v4, v4, v4.length - 2);
        if (v3) {
            v5.next = this.ctanns;
            this.ctanns = v5;
        }
        else {
            v5.next = this.ictanns;
            this.ictanns = v5;
        }
        return v5;
    }
    
    @Override
    public void visitLineNumber(final int a1, final Label a2) {
        if (this.lineNumber == null) {
            this.lineNumber = new ByteVector();
        }
        ++this.lineNumberCount;
        this.lineNumber.putShort(a2.position);
        this.lineNumber.putShort(a1);
    }
    
    @Override
    public void visitMaxs(final int v-3, final int v-2) {
        if (this.compute == 0) {
            for (Handler handler = this.firstHandler; handler != null; handler = handler.next) {
                Label a2 = handler.start.getFirst();
                final Label v1 = handler.handler.getFirst();
                final Label v2 = handler.end.getFirst();
                final String v3 = (handler.desc == null) ? "java/lang/Throwable" : handler.desc;
                final int v4 = 0x1700000 | this.cw.addType(v3);
                final Label label = v1;
                label.status |= 0x10;
                while (a2 != v2) {
                    final Edge a3 = new Edge();
                    a3.info = v4;
                    a3.successor = v1;
                    a3.next = a2.successors;
                    a2.successors = a3;
                    a2 = a2.successor;
                }
            }
            Frame v5 = this.labels.frame;
            v5.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), this.maxLocals);
            this.visitFrame(v5);
            int v6 = 0;
            Label v2 = this.labels;
            while (v2 != null) {
                final Label v7 = v2;
                v2 = v2.next;
                v7.next = null;
                v5 = v7.frame;
                if ((v7.status & 0x10) != 0x0) {
                    final Label label2 = v7;
                    label2.status |= 0x20;
                }
                final Label label3 = v7;
                label3.status |= 0x40;
                final int v4 = v5.inputStack.length + v7.outputStackMax;
                if (v4 > v6) {
                    v6 = v4;
                }
                for (Edge v8 = v7.successors; v8 != null; v8 = v8.next) {
                    final Label v9 = v8.successor.getFirst();
                    final boolean v10 = v5.merge(this.cw, v9.frame, v8.info);
                    if (v10 && v9.next == null) {
                        v9.next = v2;
                        v2 = v9;
                    }
                }
            }
            for (Label v7 = this.labels; v7 != null; v7 = v7.successor) {
                v5 = v7.frame;
                if ((v7.status & 0x20) != 0x0) {
                    this.visitFrame(v5);
                }
                if ((v7.status & 0x40) == 0x0) {
                    final Label v11 = v7.successor;
                    final int v12 = v7.position;
                    final int v13 = ((v11 == null) ? this.code.length : v11.position) - 1;
                    if (v13 >= v12) {
                        v6 = Math.max(v6, 1);
                        for (int v14 = v12; v14 < v13; ++v14) {
                            this.code.data[v14] = 0;
                        }
                        this.code.data[v13] = -65;
                        int v14 = this.startFrame(v12, 0, 1);
                        this.frame[v14] = (0x1700000 | this.cw.addType("java/lang/Throwable"));
                        this.endFrame();
                        this.firstHandler = Handler.remove(this.firstHandler, v7, v11);
                    }
                }
            }
            Handler handler = this.firstHandler;
            this.handlerCount = 0;
            while (handler != null) {
                ++this.handlerCount;
                handler = handler.next;
            }
            this.maxStack = v6;
        }
        else if (this.compute == 2) {
            for (Handler handler = this.firstHandler; handler != null; handler = handler.next) {
                Label v15 = handler.start;
                final Label v1 = handler.handler;
                for (Label v2 = handler.end; v15 != v2; v15 = v15.successor) {
                    final Edge v16 = new Edge();
                    v16.info = 0;
                    v16.successor = v1;
                    if ((v15.status & 0x80) == 0x0) {
                        v16.next = v15.successors;
                        v15.successors = v16;
                    }
                    else {
                        v16.next = v15.successors.next.next;
                        v15.successors.next.next = v16;
                    }
                }
            }
            if (this.subroutines > 0) {
                int v17 = 0;
                this.labels.visitSubroutine(null, 1L, this.subroutines);
                for (Label v1 = this.labels; v1 != null; v1 = v1.successor) {
                    if ((v1.status & 0x80) != 0x0) {
                        final Label v2 = v1.successors.next.successor;
                        if ((v2.status & 0x400) == 0x0) {
                            ++v17;
                            v2.visitSubroutine(null, v17 / 32L << 32 | 1L << v17 % 32, this.subroutines);
                        }
                    }
                }
                for (Label v1 = this.labels; v1 != null; v1 = v1.successor) {
                    if ((v1.status & 0x80) != 0x0) {
                        for (Label v2 = this.labels; v2 != null; v2 = v2.successor) {
                            final Label label4 = v2;
                            label4.status &= 0xFFFFF7FF;
                        }
                        final Label v7 = v1.successors.next.successor;
                        v7.visitSubroutine(v1, 0L, this.subroutines);
                    }
                }
            }
            int v17 = 0;
            Label v1 = this.labels;
            while (v1 != null) {
                Label v2 = v1;
                v1 = v1.next;
                final int v18 = v2.inputStackTop;
                final int v4 = v18 + v2.outputStackMax;
                if (v4 > v17) {
                    v17 = v4;
                }
                Edge v8 = v2.successors;
                if ((v2.status & 0x80) != 0x0) {
                    v8 = v8.next;
                }
                while (v8 != null) {
                    v2 = v8.successor;
                    if ((v2.status & 0x8) == 0x0) {
                        v2.inputStackTop = ((v8.info == 0) ? 1 : (v18 + v8.info));
                        final Label label5 = v2;
                        label5.status |= 0x8;
                        v2.next = v1;
                        v1 = v2;
                    }
                    v8 = v8.next;
                }
            }
            this.maxStack = Math.max(v-3, v17);
        }
        else {
            this.maxStack = v-3;
            this.maxLocals = v-2;
        }
    }
    
    @Override
    public void visitEnd() {
    }
    
    private void addSuccessor(final int a1, final Label a2) {
        final Edge v1 = new Edge();
        v1.info = a1;
        v1.successor = a2;
        v1.next = this.currentBlock.successors;
        this.currentBlock.successors = v1;
    }
    
    private void noSuccessor() {
        if (this.compute == 0) {
            final Label v1 = new Label();
            v1.frame = new Frame();
            (v1.frame.owner = v1).resolve(this, this.code.length, this.code.data);
            this.previousBlock.successor = v1;
            this.previousBlock = v1;
        }
        else {
            this.currentBlock.outputStackMax = this.maxStackSize;
        }
        if (this.compute != 1) {
            this.currentBlock = null;
        }
    }
    
    private void visitFrame(final Frame v-6) {
        int n = 0;
        int i = 0;
        int a2 = 0;
        final int[] inputLocals = v-6.inputLocals;
        final int[] inputStack = v-6.inputStack;
        for (int v0 = 0; v0 < inputLocals.length; ++v0) {
            final int a1 = inputLocals[v0];
            if (a1 == 16777216) {
                ++n;
            }
            else {
                i += n + 1;
                n = 0;
            }
            if (a1 == 16777220 || a1 == 16777219) {
                ++v0;
            }
        }
        for (int v0 = 0; v0 < inputStack.length; ++v0) {
            final int v2 = inputStack[v0];
            ++a2;
            if (v2 == 16777220 || v2 == 16777219) {
                ++v0;
            }
        }
        int v3 = this.startFrame(v-6.owner.position, i, a2);
        int v0 = 0;
        while (i > 0) {
            final int v2 = inputLocals[v0];
            this.frame[v3++] = v2;
            if (v2 == 16777220 || v2 == 16777219) {
                ++v0;
            }
            ++v0;
            --i;
        }
        for (v0 = 0; v0 < inputStack.length; ++v0) {
            final int v2 = inputStack[v0];
            this.frame[v3++] = v2;
            if (v2 == 16777220 || v2 == 16777219) {
                ++v0;
            }
        }
        this.endFrame();
    }
    
    private void visitImplicitFirstFrame() {
        int startFrame = this.startFrame(0, this.descriptor.length() + 1, 0);
        if ((this.access & 0x8) == 0x0) {
            if ((this.access & 0x80000) == 0x0) {
                this.frame[startFrame++] = (0x1700000 | this.cw.addType(this.cw.thisName));
            }
            else {
                this.frame[startFrame++] = 6;
            }
        }
        int v0 = 1;
        while (true) {
            final int v2 = v0;
            switch (this.descriptor.charAt(v0++)) {
                case 'B':
                case 'C':
                case 'I':
                case 'S':
                case 'Z': {
                    this.frame[startFrame++] = 1;
                    continue;
                }
                case 'F': {
                    this.frame[startFrame++] = 2;
                    continue;
                }
                case 'J': {
                    this.frame[startFrame++] = 4;
                    continue;
                }
                case 'D': {
                    this.frame[startFrame++] = 3;
                    continue;
                }
                case '[': {
                    while (this.descriptor.charAt(v0) == '[') {
                        ++v0;
                    }
                    if (this.descriptor.charAt(v0) == 'L') {
                        ++v0;
                        while (this.descriptor.charAt(v0) != ';') {
                            ++v0;
                        }
                    }
                    this.frame[startFrame++] = (0x1700000 | this.cw.addType(this.descriptor.substring(v2, ++v0)));
                    continue;
                }
                case 'L': {
                    while (this.descriptor.charAt(v0) != ';') {
                        ++v0;
                    }
                    this.frame[startFrame++] = (0x1700000 | this.cw.addType(this.descriptor.substring(v2 + 1, v0++)));
                    continue;
                }
                default: {
                    this.frame[1] = startFrame - 3;
                    this.endFrame();
                }
            }
        }
    }
    
    private int startFrame(final int a1, final int a2, final int a3) {
        final int v1 = 3 + a2 + a3;
        if (this.frame == null || this.frame.length < v1) {
            this.frame = new int[v1];
        }
        this.frame[0] = a1;
        this.frame[1] = a2;
        this.frame[2] = a3;
        return 3;
    }
    
    private void endFrame() {
        if (this.previousFrame != null) {
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
            }
            this.writeFrame();
            ++this.frameCount;
        }
        this.previousFrame = this.frame;
        this.frame = null;
    }
    
    private void writeFrame() {
        final int n = this.frame[1];
        final int n2 = this.frame[2];
        if ((this.cw.version & 0xFFFF) < 50) {
            this.stackMap.putShort(this.frame[0]).putShort(n);
            this.writeFrameTypes(3, 3 + n);
            this.stackMap.putShort(n2);
            this.writeFrameTypes(3 + n, 3 + n + n2);
            return;
        }
        int n3 = this.previousFrame[1];
        int n4 = 255;
        int v0 = 0;
        int v2;
        if (this.frameCount == 0) {
            v2 = this.frame[0];
        }
        else {
            v2 = this.frame[0] - this.previousFrame[0] - 1;
        }
        if (n2 == 0) {
            v0 = n - n3;
            switch (v0) {
                case -3:
                case -2:
                case -1: {
                    n4 = 248;
                    n3 = n;
                    break;
                }
                case 0: {
                    n4 = ((v2 < 64) ? 0 : 251);
                    break;
                }
                case 1:
                case 2:
                case 3: {
                    n4 = 252;
                    break;
                }
            }
        }
        else if (n == n3 && n2 == 1) {
            n4 = ((v2 < 63) ? 64 : 247);
        }
        if (n4 != 255) {
            int v3 = 3;
            for (int v4 = 0; v4 < n3; ++v4) {
                if (this.frame[v3] != this.previousFrame[v3]) {
                    n4 = 255;
                    break;
                }
                ++v3;
            }
        }
        switch (n4) {
            case 0: {
                this.stackMap.putByte(v2);
                break;
            }
            case 64: {
                this.stackMap.putByte(64 + v2);
                this.writeFrameTypes(3 + n, 4 + n);
                break;
            }
            case 247: {
                this.stackMap.putByte(247).putShort(v2);
                this.writeFrameTypes(3 + n, 4 + n);
                break;
            }
            case 251: {
                this.stackMap.putByte(251).putShort(v2);
                break;
            }
            case 248: {
                this.stackMap.putByte(251 + v0).putShort(v2);
                break;
            }
            case 252: {
                this.stackMap.putByte(251 + v0).putShort(v2);
                this.writeFrameTypes(3 + n3, 3 + n);
                break;
            }
            default: {
                this.stackMap.putByte(255).putShort(v2).putShort(n);
                this.writeFrameTypes(3, 3 + n);
                this.stackMap.putShort(n2);
                this.writeFrameTypes(3 + n, 3 + n + n2);
                break;
            }
        }
    }
    
    private void writeFrameTypes(final int v-2, final int v-1) {
        for (int v0 = v-2; v0 < v-1; ++v0) {
            final int v2 = this.frame[v0];
            int v3 = v2 & 0xF0000000;
            if (v3 == 0) {
                final int a1 = v2 & 0xFFFFF;
                switch (v2 & 0xFF00000) {
                    case 24117248: {
                        this.stackMap.putByte(7).putShort(this.cw.newClass(this.cw.typeTable[a1].strVal1));
                        break;
                    }
                    case 25165824: {
                        this.stackMap.putByte(8).putShort(this.cw.typeTable[a1].intVal);
                        break;
                    }
                    default: {
                        this.stackMap.putByte(a1);
                        break;
                    }
                }
            }
            else {
                final StringBuilder a2 = new StringBuilder();
                v3 >>= 28;
                while (v3-- > 0) {
                    a2.append('[');
                }
                if ((v2 & 0xFF00000) == 0x1700000) {
                    a2.append('L');
                    a2.append(this.cw.typeTable[v2 & 0xFFFFF].strVal1);
                    a2.append(';');
                }
                else {
                    switch (v2 & 0xF) {
                        case 1: {
                            a2.append('I');
                            break;
                        }
                        case 2: {
                            a2.append('F');
                            break;
                        }
                        case 3: {
                            a2.append('D');
                            break;
                        }
                        case 9: {
                            a2.append('Z');
                            break;
                        }
                        case 10: {
                            a2.append('B');
                            break;
                        }
                        case 11: {
                            a2.append('C');
                            break;
                        }
                        case 12: {
                            a2.append('S');
                            break;
                        }
                        default: {
                            a2.append('J');
                            break;
                        }
                    }
                }
                this.stackMap.putByte(7).putShort(this.cw.newClass(a2.toString()));
            }
        }
    }
    
    private void writeFrameType(final Object a1) {
        if (a1 instanceof String) {
            this.stackMap.putByte(7).putShort(this.cw.newClass((String)a1));
        }
        else if (a1 instanceof Integer) {
            this.stackMap.putByte((int)a1);
        }
        else {
            this.stackMap.putByte(8).putShort(((Label)a1).position);
        }
    }
    
    final int getSize() {
        if (this.classReaderOffset != 0) {
            return 6 + this.classReaderLength;
        }
        int v0 = 8;
        if (this.code.length > 0) {
            if (this.code.length > 65535) {
                throw new RuntimeException("Method code too large!");
            }
            this.cw.newUTF8("Code");
            v0 += 18 + this.code.length + 8 * this.handlerCount;
            if (this.localVar != null) {
                this.cw.newUTF8("LocalVariableTable");
                v0 += 8 + this.localVar.length;
            }
            if (this.localVarType != null) {
                this.cw.newUTF8("LocalVariableTypeTable");
                v0 += 8 + this.localVarType.length;
            }
            if (this.lineNumber != null) {
                this.cw.newUTF8("LineNumberTable");
                v0 += 8 + this.lineNumber.length;
            }
            if (this.stackMap != null) {
                final boolean v2 = (this.cw.version & 0xFFFF) >= 50;
                this.cw.newUTF8(v2 ? "StackMapTable" : "StackMap");
                v0 += 8 + this.stackMap.length;
            }
            if (this.ctanns != null) {
                this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
                v0 += 8 + this.ctanns.getSize();
            }
            if (this.ictanns != null) {
                this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
                v0 += 8 + this.ictanns.getSize();
            }
            if (this.cattrs != null) {
                v0 += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
        }
        if (this.exceptionCount > 0) {
            this.cw.newUTF8("Exceptions");
            v0 += 8 + 2 * this.exceptionCount;
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            this.cw.newUTF8("Synthetic");
            v0 += 6;
        }
        if ((this.access & 0x20000) != 0x0) {
            this.cw.newUTF8("Deprecated");
            v0 += 6;
        }
        if (this.signature != null) {
            this.cw.newUTF8("Signature");
            this.cw.newUTF8(this.signature);
            v0 += 8;
        }
        if (this.methodParameters != null) {
            this.cw.newUTF8("MethodParameters");
            v0 += 7 + this.methodParameters.length;
        }
        if (this.annd != null) {
            this.cw.newUTF8("AnnotationDefault");
            v0 += 6 + this.annd.length;
        }
        if (this.anns != null) {
            this.cw.newUTF8("RuntimeVisibleAnnotations");
            v0 += 8 + this.anns.getSize();
        }
        if (this.ianns != null) {
            this.cw.newUTF8("RuntimeInvisibleAnnotations");
            v0 += 8 + this.ianns.getSize();
        }
        if (this.tanns != null) {
            this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
            v0 += 8 + this.tanns.getSize();
        }
        if (this.itanns != null) {
            this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
            v0 += 8 + this.itanns.getSize();
        }
        if (this.panns != null) {
            this.cw.newUTF8("RuntimeVisibleParameterAnnotations");
            v0 += 7 + 2 * (this.panns.length - this.synthetics);
            for (int v3 = this.panns.length - 1; v3 >= this.synthetics; --v3) {
                v0 += ((this.panns[v3] == null) ? 0 : this.panns[v3].getSize());
            }
        }
        if (this.ipanns != null) {
            this.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
            v0 += 7 + 2 * (this.ipanns.length - this.synthetics);
            for (int v3 = this.ipanns.length - 1; v3 >= this.synthetics; --v3) {
                v0 += ((this.ipanns[v3] == null) ? 0 : this.ipanns[v3].getSize());
            }
        }
        if (this.attrs != null) {
            v0 += this.attrs.getSize(this.cw, null, 0, -1, -1);
        }
        return v0;
    }
    
    final void put(final ByteVector v-4) {
        final int n = 64;
        final int n2 = 0xE0000 | (this.access & 0x40000) / 64;
        v-4.putShort(this.access & ~n2).putShort(this.name).putShort(this.desc);
        if (this.classReaderOffset != 0) {
            v-4.putByteArray(this.cw.cr.b, this.classReaderOffset, this.classReaderLength);
            return;
        }
        int n3 = 0;
        if (this.code.length > 0) {
            ++n3;
        }
        if (this.exceptionCount > 0) {
            ++n3;
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            ++n3;
        }
        if ((this.access & 0x20000) != 0x0) {
            ++n3;
        }
        if (this.signature != null) {
            ++n3;
        }
        if (this.methodParameters != null) {
            ++n3;
        }
        if (this.annd != null) {
            ++n3;
        }
        if (this.anns != null) {
            ++n3;
        }
        if (this.ianns != null) {
            ++n3;
        }
        if (this.tanns != null) {
            ++n3;
        }
        if (this.itanns != null) {
            ++n3;
        }
        if (this.panns != null) {
            ++n3;
        }
        if (this.ipanns != null) {
            ++n3;
        }
        if (this.attrs != null) {
            n3 += this.attrs.getCount();
        }
        v-4.putShort(n3);
        if (this.code.length > 0) {
            int v0 = 12 + this.code.length + 8 * this.handlerCount;
            if (this.localVar != null) {
                v0 += 8 + this.localVar.length;
            }
            if (this.localVarType != null) {
                v0 += 8 + this.localVarType.length;
            }
            if (this.lineNumber != null) {
                v0 += 8 + this.lineNumber.length;
            }
            if (this.stackMap != null) {
                v0 += 8 + this.stackMap.length;
            }
            if (this.ctanns != null) {
                v0 += 8 + this.ctanns.getSize();
            }
            if (this.ictanns != null) {
                v0 += 8 + this.ictanns.getSize();
            }
            if (this.cattrs != null) {
                v0 += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
            v-4.putShort(this.cw.newUTF8("Code")).putInt(v0);
            v-4.putShort(this.maxStack).putShort(this.maxLocals);
            v-4.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
            v-4.putShort(this.handlerCount);
            if (this.handlerCount > 0) {
                for (Handler a1 = this.firstHandler; a1 != null; a1 = a1.next) {
                    v-4.putShort(a1.start.position).putShort(a1.end.position).putShort(a1.handler.position).putShort(a1.type);
                }
            }
            n3 = 0;
            if (this.localVar != null) {
                ++n3;
            }
            if (this.localVarType != null) {
                ++n3;
            }
            if (this.lineNumber != null) {
                ++n3;
            }
            if (this.stackMap != null) {
                ++n3;
            }
            if (this.ctanns != null) {
                ++n3;
            }
            if (this.ictanns != null) {
                ++n3;
            }
            if (this.cattrs != null) {
                n3 += this.cattrs.getCount();
            }
            v-4.putShort(n3);
            if (this.localVar != null) {
                v-4.putShort(this.cw.newUTF8("LocalVariableTable"));
                v-4.putInt(this.localVar.length + 2).putShort(this.localVarCount);
                v-4.putByteArray(this.localVar.data, 0, this.localVar.length);
            }
            if (this.localVarType != null) {
                v-4.putShort(this.cw.newUTF8("LocalVariableTypeTable"));
                v-4.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
                v-4.putByteArray(this.localVarType.data, 0, this.localVarType.length);
            }
            if (this.lineNumber != null) {
                v-4.putShort(this.cw.newUTF8("LineNumberTable"));
                v-4.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
                v-4.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
            }
            if (this.stackMap != null) {
                final boolean v2 = (this.cw.version & 0xFFFF) >= 50;
                v-4.putShort(this.cw.newUTF8(v2 ? "StackMapTable" : "StackMap"));
                v-4.putInt(this.stackMap.length + 2).putShort(this.frameCount);
                v-4.putByteArray(this.stackMap.data, 0, this.stackMap.length);
            }
            if (this.ctanns != null) {
                v-4.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
                this.ctanns.put(v-4);
            }
            if (this.ictanns != null) {
                v-4.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
                this.ictanns.put(v-4);
            }
            if (this.cattrs != null) {
                this.cattrs.put(this.cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, v-4);
            }
        }
        if (this.exceptionCount > 0) {
            v-4.putShort(this.cw.newUTF8("Exceptions")).putInt(2 * this.exceptionCount + 2);
            v-4.putShort(this.exceptionCount);
            for (int v0 = 0; v0 < this.exceptionCount; ++v0) {
                v-4.putShort(this.exceptions[v0]);
            }
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            v-4.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.access & 0x20000) != 0x0) {
            v-4.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
        }
        if (this.signature != null) {
            v-4.putShort(this.cw.newUTF8("Signature")).putInt(2).putShort(this.cw.newUTF8(this.signature));
        }
        if (this.methodParameters != null) {
            v-4.putShort(this.cw.newUTF8("MethodParameters"));
            v-4.putInt(this.methodParameters.length + 1).putByte(this.methodParametersCount);
            v-4.putByteArray(this.methodParameters.data, 0, this.methodParameters.length);
        }
        if (this.annd != null) {
            v-4.putShort(this.cw.newUTF8("AnnotationDefault"));
            v-4.putInt(this.annd.length);
            v-4.putByteArray(this.annd.data, 0, this.annd.length);
        }
        if (this.anns != null) {
            v-4.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(v-4);
        }
        if (this.ianns != null) {
            v-4.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(v-4);
        }
        if (this.tanns != null) {
            v-4.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(v-4);
        }
        if (this.itanns != null) {
            v-4.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(v-4);
        }
        if (this.panns != null) {
            v-4.putShort(this.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
            AnnotationWriter.put(this.panns, this.synthetics, v-4);
        }
        if (this.ipanns != null) {
            v-4.putShort(this.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
            AnnotationWriter.put(this.ipanns, this.synthetics, v-4);
        }
        if (this.attrs != null) {
            this.attrs.put(this.cw, null, 0, -1, -1, v-4);
        }
    }
}
