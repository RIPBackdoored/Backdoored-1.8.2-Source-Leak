package javassist.bytecode;

import java.util.*;

public class CodeIterator implements Opcode
{
    protected CodeAttribute codeAttr;
    protected byte[] bytecode;
    protected int endPos;
    protected int currentPos;
    protected int mark;
    private static final int[] opcodeLength;
    
    protected CodeIterator(final CodeAttribute a1) {
        super();
        this.codeAttr = a1;
        this.bytecode = a1.getCode();
        this.begin();
    }
    
    public void begin() {
        final int n = 0;
        this.mark = n;
        this.currentPos = n;
        this.endPos = this.getCodeLength();
    }
    
    public void move(final int a1) {
        this.currentPos = a1;
    }
    
    public void setMark(final int a1) {
        this.mark = a1;
    }
    
    public int getMark() {
        return this.mark;
    }
    
    public CodeAttribute get() {
        return this.codeAttr;
    }
    
    public int getCodeLength() {
        return this.bytecode.length;
    }
    
    public int byteAt(final int a1) {
        return this.bytecode[a1] & 0xFF;
    }
    
    public int signedByteAt(final int a1) {
        return this.bytecode[a1];
    }
    
    public void writeByte(final int a1, final int a2) {
        this.bytecode[a2] = (byte)a1;
    }
    
    public int u16bitAt(final int a1) {
        return ByteArray.readU16bit(this.bytecode, a1);
    }
    
    public int s16bitAt(final int a1) {
        return ByteArray.readS16bit(this.bytecode, a1);
    }
    
    public void write16bit(final int a1, final int a2) {
        ByteArray.write16bit(a1, this.bytecode, a2);
    }
    
    public int s32bitAt(final int a1) {
        return ByteArray.read32bit(this.bytecode, a1);
    }
    
    public void write32bit(final int a1, final int a2) {
        ByteArray.write32bit(a1, this.bytecode, a2);
    }
    
    public void write(final byte[] v1, int v2) {
        for (int v3 = v1.length, a1 = 0; a1 < v3; ++a1) {
            this.bytecode[v2++] = v1[a1];
        }
    }
    
    public boolean hasNext() {
        return this.currentPos < this.endPos;
    }
    
    public int next() throws BadBytecode {
        final int v1 = this.currentPos;
        this.currentPos = nextOpcode(this.bytecode, v1);
        return v1;
    }
    
    public int lookAhead() {
        return this.currentPos;
    }
    
    public int skipConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(-1);
    }
    
    public int skipSuperConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(0);
    }
    
    public int skipThisConstructor() throws BadBytecode {
        return this.skipSuperConstructor0(1);
    }
    
    private int skipSuperConstructor0(final int v-5) throws BadBytecode {
        this.begin();
        final ConstPool constPool = this.codeAttr.getConstPool();
        final String declaringClass = this.codeAttr.getDeclaringClass();
        int n = 0;
        while (this.hasNext()) {
            final int next = this.next();
            final int v0 = this.byteAt(next);
            if (v0 == 187) {
                ++n;
            }
            else {
                if (v0 != 183) {
                    continue;
                }
                final int v2 = ByteArray.readU16bit(this.bytecode, next + 1);
                if (!constPool.getMethodrefName(v2).equals("<init>") || --n >= 0) {
                    continue;
                }
                if (v-5 < 0) {
                    return next;
                }
                final String a1 = constPool.getMethodrefClassName(v2);
                if (a1.equals(declaringClass) == v-5 > 0) {
                    return next;
                }
                break;
            }
        }
        this.begin();
        return -1;
    }
    
    public int insert(final byte[] a1) throws BadBytecode {
        return this.insert0(this.currentPos, a1, false);
    }
    
    public void insert(final int a1, final byte[] a2) throws BadBytecode {
        this.insert0(a1, a2, false);
    }
    
    public int insertAt(final int a1, final byte[] a2) throws BadBytecode {
        return this.insert0(a1, a2, false);
    }
    
    public int insertEx(final byte[] a1) throws BadBytecode {
        return this.insert0(this.currentPos, a1, true);
    }
    
    public void insertEx(final int a1, final byte[] a2) throws BadBytecode {
        this.insert0(a1, a2, true);
    }
    
    public int insertExAt(final int a1, final byte[] a2) throws BadBytecode {
        return this.insert0(a1, a2, true);
    }
    
    private int insert0(int a3, final byte[] v1, final boolean v2) throws BadBytecode {
        final int v3 = v1.length;
        if (v3 <= 0) {
            return a3;
        }
        int v4;
        a3 = (v4 = this.insertGapAt(a3, v3, v2).position);
        for (int a4 = 0; a4 < v3; ++a4) {
            this.bytecode[v4++] = v1[a4];
        }
        return a3;
    }
    
    public int insertGap(final int a1) throws BadBytecode {
        return this.insertGapAt(this.currentPos, a1, false).position;
    }
    
    public int insertGap(final int a1, final int a2) throws BadBytecode {
        return this.insertGapAt(a1, a2, false).length;
    }
    
    public int insertExGap(final int a1) throws BadBytecode {
        return this.insertGapAt(this.currentPos, a1, true).position;
    }
    
    public int insertExGap(final int a1, final int a2) throws BadBytecode {
        return this.insertGapAt(a1, a2, true).length;
    }
    
    public Gap insertGapAt(int v2, final int v3, final boolean v4) throws BadBytecode {
        final Gap v5 = new Gap();
        if (v3 <= 0) {
            v5.position = v2;
            v5.length = 0;
            return v5;
        }
        byte[] v6 = null;
        int v7 = 0;
        if (this.bytecode.length + v3 > 32767) {
            final byte[] a1 = this.insertGapCore0w(this.bytecode, v2, v3, v4, this.get().getExceptionTable(), this.codeAttr, v5);
            v2 = v5.position;
            final int a2 = v3;
        }
        else {
            final int a3 = this.currentPos;
            v6 = insertGapCore0(this.bytecode, v2, v3, v4, this.get().getExceptionTable(), this.codeAttr);
            v7 = v6.length - this.bytecode.length;
            v5.position = v2;
            v5.length = v7;
            if (a3 >= v2) {
                this.currentPos = a3 + v7;
            }
            if (this.mark > v2 || (this.mark == v2 && v4)) {
                this.mark += v7;
            }
        }
        this.codeAttr.setCode(v6);
        this.bytecode = v6;
        this.endPos = this.getCodeLength();
        this.updateCursors(v2, v7);
        return v5;
    }
    
    protected void updateCursors(final int a1, final int a2) {
    }
    
    public void insert(final ExceptionTable a1, final int a2) {
        this.codeAttr.getExceptionTable().add(0, a1, a2);
    }
    
    public int append(final byte[] v2) {
        final int v3 = this.getCodeLength();
        final int v4 = v2.length;
        if (v4 <= 0) {
            return v3;
        }
        this.appendGap(v4);
        final byte[] v5 = this.bytecode;
        for (int a1 = 0; a1 < v4; ++a1) {
            v5[a1 + v3] = v2[a1];
        }
        return v3;
    }
    
    public void appendGap(final int a1) {
        final byte[] v1 = this.bytecode;
        final int v2 = v1.length;
        final byte[] v3 = new byte[v2 + a1];
        for (int v4 = 0; v4 < v2; ++v4) {
            v3[v4] = v1[v4];
        }
        for (int v4 = v2; v4 < v2 + a1; ++v4) {
            v3[v4] = 0;
        }
        this.codeAttr.setCode(v3);
        this.bytecode = v3;
        this.endPos = this.getCodeLength();
    }
    
    public void append(final ExceptionTable a1, final int a2) {
        final ExceptionTable v1 = this.codeAttr.getExceptionTable();
        v1.add(v1.size(), a1, a2);
    }
    
    static int nextOpcode(final byte[] v-4, final int v-3) throws BadBytecode {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iload_1         /* v-3 */
        //     2: baload         
        //     3: sipush          255
        //     6: iand           
        //     7: istore_2        /* a1 */
        //     8: goto            22
        //    11: astore_3        /* a2 */
        //    12: new             Ljavassist/bytecode/BadBytecode;
        //    15: dup            
        //    16: ldc             "invalid opcode address"
        //    18: invokespecial   javassist/bytecode/BadBytecode.<init>:(Ljava/lang/String;)V
        //    21: athrow         
        //    22: getstatic       javassist/bytecode/CodeIterator.opcodeLength:[I
        //    25: iload_2         /* v-2 */
        //    26: iaload         
        //    27: istore_3        /* v-1 */
        //    28: iload_3         /* v-1 */
        //    29: ifle            36
        //    32: iload_1         /* v-3 */
        //    33: iload_3         /* v-1 */
        //    34: iadd           
        //    35: ireturn        
        //    36: iload_2         /* v-2 */
        //    37: sipush          196
        //    40: if_icmpne       62
        //    43: aload_0         /* v-4 */
        //    44: iload_1         /* v-3 */
        //    45: iconst_1       
        //    46: iadd           
        //    47: baload         
        //    48: bipush          -124
        //    50: if_icmpne       58
        //    53: iload_1         /* v-3 */
        //    54: bipush          6
        //    56: iadd           
        //    57: ireturn        
        //    58: iload_1         /* v-3 */
        //    59: iconst_4       
        //    60: iadd           
        //    61: ireturn        
        //    62: iload_1         /* v-3 */
        //    63: bipush          -4
        //    65: iand           
        //    66: bipush          8
        //    68: iadd           
        //    69: istore          v0
        //    71: iload_2         /* v-2 */
        //    72: sipush          171
        //    75: if_icmpne       97
        //    78: aload_0         /* v-4 */
        //    79: iload           v0
        //    81: invokestatic    javassist/bytecode/ByteArray.read32bit:([BI)I
        //    84: istore          v1
        //    86: iload           v0
        //    88: iload           v1
        //    90: bipush          8
        //    92: imul           
        //    93: iadd           
        //    94: iconst_4       
        //    95: iadd           
        //    96: ireturn        
        //    97: iload_2         /* v-2 */
        //    98: sipush          170
        //   101: if_icmpne       138
        //   104: aload_0         /* v-4 */
        //   105: iload           v0
        //   107: invokestatic    javassist/bytecode/ByteArray.read32bit:([BI)I
        //   110: istore          v1
        //   112: aload_0         /* v-4 */
        //   113: iload           v0
        //   115: iconst_4       
        //   116: iadd           
        //   117: invokestatic    javassist/bytecode/ByteArray.read32bit:([BI)I
        //   120: istore          v2
        //   122: iload           v0
        //   124: iload           v2
        //   126: iload           v1
        //   128: isub           
        //   129: iconst_1       
        //   130: iadd           
        //   131: iconst_4       
        //   132: imul           
        //   133: iadd           
        //   134: bipush          8
        //   136: iadd           
        //   137: ireturn        
        //   138: goto            142
        //   141: astore_3       
        //   142: new             Ljavassist/bytecode/BadBytecode;
        //   145: dup            
        //   146: iload_2         /* v-2 */
        //   147: invokespecial   javassist/bytecode/BadBytecode.<init>:(I)V
        //   150: athrow         
        //    Exceptions:
        //  throws javassist.bytecode.BadBytecode
        //    StackMapTable: 00 09 4B 07 00 FB FC 00 0A 01 FC 00 0D 01 15 03 FC 00 22 01 F9 00 28 42 07 00 FB 00
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                 
        //  -----  -----  -----  -----  -------------------------------------
        //  0      8      11     22     Ljava/lang/IndexOutOfBoundsException;
        //  22     35     141    142    Ljava/lang/IndexOutOfBoundsException;
        //  36     57     141    142    Ljava/lang/IndexOutOfBoundsException;
        //  58     61     141    142    Ljava/lang/IndexOutOfBoundsException;
        //  62     96     141    142    Ljava/lang/IndexOutOfBoundsException;
        //  97     137    141    142    Ljava/lang/IndexOutOfBoundsException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static byte[] insertGapCore0(final byte[] a3, final int a4, final int a5, final boolean a6, final ExceptionTable v1, final CodeAttribute v2) throws BadBytecode {
        if (a5 <= 0) {
            return a3;
        }
        try {
            return insertGapCore1(a3, a4, a5, a6, v1, v2);
        }
        catch (AlignmentException a7) {
            try {
                return insertGapCore1(a3, a4, a5 + 3 & 0xFFFFFFFC, a6, v1, v2);
            }
            catch (AlignmentException a8) {
                throw new RuntimeException("fatal error?");
            }
        }
    }
    
    private static byte[] insertGapCore1(final byte[] a1, final int a2, final int a3, final boolean a4, final ExceptionTable a5, final CodeAttribute a6) throws BadBytecode, AlignmentException {
        final int v1 = a1.length;
        final byte[] v2 = new byte[v1 + a3];
        insertGap2(a1, a2, a3, v1, v2, a4);
        a5.shiftPc(a2, a3, a4);
        final LineNumberAttribute v3 = (LineNumberAttribute)a6.getAttribute("LineNumberTable");
        if (v3 != null) {
            v3.shiftPc(a2, a3, a4);
        }
        final LocalVariableAttribute v4 = (LocalVariableAttribute)a6.getAttribute("LocalVariableTable");
        if (v4 != null) {
            v4.shiftPc(a2, a3, a4);
        }
        final LocalVariableAttribute v5 = (LocalVariableAttribute)a6.getAttribute("LocalVariableTypeTable");
        if (v5 != null) {
            v5.shiftPc(a2, a3, a4);
        }
        final StackMapTable v6 = (StackMapTable)a6.getAttribute("StackMapTable");
        if (v6 != null) {
            v6.shiftPc(a2, a3, a4);
        }
        final StackMap v7 = (StackMap)a6.getAttribute("StackMap");
        if (v7 != null) {
            v7.shiftPc(a2, a3, a4);
        }
        return v2;
    }
    
    private static void insertGap2(final byte[] v-11, final int v-10, final int v-9, final int v-8, final byte[] v-7, final boolean v-6) throws BadBytecode, AlignmentException {
        int i = 0;
        int j = 0;
        while (i < v-8) {
            if (i == v-10) {
                for (int a1 = j + v-9; j < a1; v-7[j++] = 0) {}
            }
            final int nextOpcode = nextOpcode(v-11, i);
            final int n = v-11[i] & 0xFF;
            if ((153 <= n && n <= 168) || n == 198 || n == 199) {
                int a2 = v-11[i + 1] << 8 | (v-11[i + 2] & 0xFF);
                a2 = newOffset(i, a2, v-10, v-9, v-6);
                v-7[j] = v-11[i];
                ByteArray.write16bit(a2, v-7, j + 1);
                j += 3;
            }
            else if (n == 200 || n == 201) {
                int a3 = ByteArray.read32bit(v-11, i + 1);
                a3 = newOffset(i, a3, v-10, v-9, v-6);
                v-7[j++] = v-11[i];
                ByteArray.write32bit(a3, v-7, j);
                j += 4;
            }
            else if (n == 170) {
                if (i != j && (v-9 & 0x3) != 0x0) {
                    throw new AlignmentException();
                }
                int a4 = (i & 0xFFFFFFFC) + 4;
                j = copyGapBytes(v-7, j, v-11, i, a4);
                final int a5 = newOffset(i, ByteArray.read32bit(v-11, a4), v-10, v-9, v-6);
                ByteArray.write32bit(a5, v-7, j);
                final int v1 = ByteArray.read32bit(v-11, a4 + 4);
                ByteArray.write32bit(v1, v-7, j + 4);
                final int v2 = ByteArray.read32bit(v-11, a4 + 8);
                ByteArray.write32bit(v2, v-7, j + 8);
                j += 12;
                int v3;
                for (v3 = a4 + 12, a4 = v3 + (v2 - v1 + 1) * 4; v3 < a4; v3 += 4) {
                    final int a6 = newOffset(i, ByteArray.read32bit(v-11, v3), v-10, v-9, v-6);
                    ByteArray.write32bit(a6, v-7, j);
                    j += 4;
                }
            }
            else if (n == 171) {
                if (i != j && (v-9 & 0x3) != 0x0) {
                    throw new AlignmentException();
                }
                int n2 = (i & 0xFFFFFFFC) + 4;
                j = copyGapBytes(v-7, j, v-11, i, n2);
                final int v4 = newOffset(i, ByteArray.read32bit(v-11, n2), v-10, v-9, v-6);
                ByteArray.write32bit(v4, v-7, j);
                final int v1 = ByteArray.read32bit(v-11, n2 + 4);
                ByteArray.write32bit(v1, v-7, j + 4);
                j += 8;
                int v2;
                for (v2 = n2 + 8, n2 = v2 + v1 * 8; v2 < n2; v2 += 8) {
                    ByteArray.copy32bit(v-11, v2, v-7, j);
                    final int v3 = newOffset(i, ByteArray.read32bit(v-11, v2 + 4), v-10, v-9, v-6);
                    ByteArray.write32bit(v3, v-7, j + 4);
                    j += 8;
                }
            }
            else {
                while (i < nextOpcode) {
                    v-7[j++] = v-11[i++];
                }
            }
            i = nextOpcode;
        }
    }
    
    private static int copyGapBytes(final byte[] a1, int a2, final byte[] a3, int a4, final int a5) {
        switch (a5 - a4) {
            case 4: {
                a1[a2++] = a3[a4++];
            }
            case 3: {
                a1[a2++] = a3[a4++];
            }
            case 2: {
                a1[a2++] = a3[a4++];
            }
            case 1: {
                a1[a2++] = a3[a4++];
                break;
            }
        }
        return a2;
    }
    
    private static int newOffset(final int a1, int a2, final int a3, final int a4, final boolean a5) {
        final int v1 = a1 + a2;
        if (a1 < a3) {
            if (a3 < v1 || (a5 && a3 == v1)) {
                a2 += a4;
            }
        }
        else if (a1 == a3) {
            if (v1 < a3) {
                a2 -= a4;
            }
        }
        else if (v1 < a3 || (!a5 && a3 == v1)) {
            a2 -= a4;
        }
        return a2;
    }
    
    static byte[] changeLdcToLdcW(final byte[] a1, final ExceptionTable a2, final CodeAttribute a3, CodeAttribute.LdcEntry a4) throws BadBytecode {
        final Pointers v1 = new Pointers(0, 0, 0, a2, a3);
        final ArrayList v2 = makeJumpList(a1, a1.length, v1);
        while (a4 != null) {
            addLdcW(a4, v2);
            a4 = a4.next;
        }
        final byte[] v3 = insertGap2w(a1, 0, 0, false, v2, v1);
        return v3;
    }
    
    private static void addLdcW(final CodeAttribute.LdcEntry a2, final ArrayList v1) {
        final int v2 = a2.where;
        final LdcW v3 = new LdcW(v2, a2.index);
        for (int v4 = v1.size(), a3 = 0; a3 < v4; ++a3) {
            if (v2 < v1.get(a3).orgPos) {
                v1.add(a3, v3);
                return;
            }
        }
        v1.add(v3);
    }
    
    private byte[] insertGapCore0w(final byte[] a1, final int a2, final int a3, final boolean a4, final ExceptionTable a5, final CodeAttribute a6, final Gap a7) throws BadBytecode {
        if (a3 <= 0) {
            return a1;
        }
        final Pointers v1 = new Pointers(this.currentPos, this.mark, a2, a5, a6);
        final ArrayList v2 = makeJumpList(a1, a1.length, v1);
        final byte[] v3 = insertGap2w(a1, a2, a3, a4, v2, v1);
        this.currentPos = v1.cursor;
        this.mark = v1.mark;
        int v4 = v1.mark0;
        if (v4 == this.currentPos && !a4) {
            this.currentPos += a3;
        }
        if (a4) {
            v4 -= a3;
        }
        a7.position = v4;
        a7.length = a3;
        return v3;
    }
    
    private static byte[] insertGap2w(final byte[] v-11, final int v-10, final int v-9, final boolean v-8, final ArrayList v-7, final Pointers v-6) throws BadBytecode {
        final int size = v-7.size();
        if (v-9 > 0) {
            v-6.shiftPc(v-10, v-9, v-8);
            for (int a1 = 0; a1 < size; ++a1) {
                v-7.get(a1).shift(v-10, v-9, v-8);
            }
        }
        boolean b = true;
        while (true) {
            if (b) {
                b = false;
                for (int a2 = 0; a2 < size; ++a2) {
                    final Branch a3 = v-7.get(a2);
                    if (a3.expanded()) {
                        b = true;
                        final int a4 = a3.pos;
                        final int a5 = a3.deltaSize();
                        v-6.shiftPc(a4, a5, false);
                        for (int a6 = 0; a6 < size; ++a6) {
                            v-7.get(a6).shift(a4, a5, false);
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < size; ++i) {
                    final Branch branch = v-7.get(i);
                    final int gapChanged = branch.gapChanged();
                    if (gapChanged > 0) {
                        b = true;
                        final int v0 = branch.pos;
                        v-6.shiftPc(v0, gapChanged, false);
                        for (int v2 = 0; v2 < size; ++v2) {
                            v-7.get(v2).shift(v0, gapChanged, false);
                        }
                    }
                }
                if (!b) {
                    break;
                }
                continue;
            }
        }
        return makeExapndedCode(v-11, v-7, v-10, v-9);
    }
    
    private static ArrayList makeJumpList(final byte[] v-6, final int v-5, final Pointers v-4) throws BadBytecode {
        final ArrayList list = new ArrayList();
        int nextOpcode;
        for (int i = 0; i < v-5; i = nextOpcode) {
            nextOpcode = nextOpcode(v-6, i);
            final int v0 = v-6[i] & 0xFF;
            if ((153 <= v0 && v0 <= 168) || v0 == 198 || v0 == 199) {
                final int a2 = v-6[i + 1] << 8 | (v-6[i + 2] & 0xFF);
                Branch a4 = null;
                if (v0 == 167 || v0 == 168) {
                    final Branch a3 = new Jump16(i, a2);
                }
                else {
                    a4 = new If16(i, a2);
                }
                list.add(a4);
            }
            else if (v0 == 200 || v0 == 201) {
                final int v2 = ByteArray.read32bit(v-6, i + 1);
                list.add(new Jump32(i, v2));
            }
            else if (v0 == 170) {
                final int v2 = (i & 0xFFFFFFFC) + 4;
                final int v3 = ByteArray.read32bit(v-6, v2);
                final int v4 = ByteArray.read32bit(v-6, v2 + 4);
                final int v5 = ByteArray.read32bit(v-6, v2 + 8);
                int v6 = v2 + 12;
                final int v7 = v5 - v4 + 1;
                final int[] v8 = new int[v7];
                for (int v9 = 0; v9 < v7; ++v9) {
                    v8[v9] = ByteArray.read32bit(v-6, v6);
                    v6 += 4;
                }
                list.add(new Table(i, v3, v4, v5, v8, v-4));
            }
            else if (v0 == 171) {
                final int v2 = (i & 0xFFFFFFFC) + 4;
                final int v3 = ByteArray.read32bit(v-6, v2);
                final int v4 = ByteArray.read32bit(v-6, v2 + 4);
                int v5 = v2 + 8;
                final int[] v10 = new int[v4];
                final int[] v11 = new int[v4];
                for (int v12 = 0; v12 < v4; ++v12) {
                    v10[v12] = ByteArray.read32bit(v-6, v5);
                    v11[v12] = ByteArray.read32bit(v-6, v5 + 4);
                    v5 += 8;
                }
                list.add(new Lookup(i, v3, v10, v11, v-4));
            }
        }
        return list;
    }
    
    private static byte[] makeExapndedCode(final byte[] v-12, final ArrayList v-11, final int v-10, final int v-9) throws BadBytecode {
        final int size = v-11.size();
        int n = v-12.length + v-9;
        for (int a2 = 0; a2 < size; ++a2) {
            final Branch a3 = v-11.get(a2);
            n += a3.deltaSize();
        }
        final byte[] array = new byte[n];
        int i = 0;
        int j = 0;
        int n2 = 0;
        final int length = v-12.length;
        Branch branch = null;
        int v0 = 0;
        if (0 < size) {
            final Branch a4 = v-11.get(0);
            final int a5 = a4.orgPos;
        }
        else {
            branch = null;
            v0 = length;
        }
        while (i < length) {
            if (i == v-10) {
                for (int v2 = j + v-9; j < v2; array[j++] = 0) {}
            }
            if (i != v0) {
                array[j++] = v-12[i++];
            }
            else {
                final int v2 = branch.write(i, v-12, j, array);
                i += v2;
                j += v2 + branch.deltaSize();
                if (++n2 < size) {
                    branch = v-11.get(n2);
                    v0 = branch.orgPos;
                }
                else {
                    branch = null;
                    v0 = length;
                }
            }
        }
        return array;
    }
    
    static {
        opcodeLength = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 3, 3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 0, 0, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 5, 5, 3, 2, 3, 1, 1, 3, 3, 1, 1, 0, 4, 3, 3, 5, 5 };
    }
    
    public static class Gap
    {
        public int position;
        public int length;
        
        public Gap() {
            super();
        }
    }
    
    static class AlignmentException extends Exception
    {
        AlignmentException() {
            super();
        }
    }
    
    static class Pointers
    {
        int cursor;
        int mark0;
        int mark;
        ExceptionTable etable;
        LineNumberAttribute line;
        LocalVariableAttribute vars;
        LocalVariableAttribute types;
        StackMapTable stack;
        StackMap stack2;
        
        Pointers(final int a1, final int a2, final int a3, final ExceptionTable a4, final CodeAttribute a5) {
            super();
            this.cursor = a1;
            this.mark = a2;
            this.mark0 = a3;
            this.etable = a4;
            this.line = (LineNumberAttribute)a5.getAttribute("LineNumberTable");
            this.vars = (LocalVariableAttribute)a5.getAttribute("LocalVariableTable");
            this.types = (LocalVariableAttribute)a5.getAttribute("LocalVariableTypeTable");
            this.stack = (StackMapTable)a5.getAttribute("StackMapTable");
            this.stack2 = (StackMap)a5.getAttribute("StackMap");
        }
        
        void shiftPc(final int a1, final int a2, final boolean a3) throws BadBytecode {
            if (a1 < this.cursor || (a1 == this.cursor && a3)) {
                this.cursor += a2;
            }
            if (a1 < this.mark || (a1 == this.mark && a3)) {
                this.mark += a2;
            }
            if (a1 < this.mark0 || (a1 == this.mark0 && a3)) {
                this.mark0 += a2;
            }
            this.etable.shiftPc(a1, a2, a3);
            if (this.line != null) {
                this.line.shiftPc(a1, a2, a3);
            }
            if (this.vars != null) {
                this.vars.shiftPc(a1, a2, a3);
            }
            if (this.types != null) {
                this.types.shiftPc(a1, a2, a3);
            }
            if (this.stack != null) {
                this.stack.shiftPc(a1, a2, a3);
            }
            if (this.stack2 != null) {
                this.stack2.shiftPc(a1, a2, a3);
            }
        }
        
        void shiftForSwitch(final int a1, final int a2) throws BadBytecode {
            if (this.stack != null) {
                this.stack.shiftForSwitch(a1, a2);
            }
            if (this.stack2 != null) {
                this.stack2.shiftForSwitch(a1, a2);
            }
        }
    }
    
    abstract static class Branch
    {
        int pos;
        int orgPos;
        
        Branch(final int a1) {
            super();
            this.orgPos = a1;
            this.pos = a1;
        }
        
        void shift(final int a1, final int a2, final boolean a3) {
            if (a1 < this.pos || (a1 == this.pos && a3)) {
                this.pos += a2;
            }
        }
        
        static int shiftOffset(final int a1, int a2, final int a3, final int a4, final boolean a5) {
            final int v1 = a1 + a2;
            if (a1 < a3) {
                if (a3 < v1 || (a5 && a3 == v1)) {
                    a2 += a4;
                }
            }
            else if (a1 == a3) {
                if (v1 < a3 && a5) {
                    a2 -= a4;
                }
                else if (a3 < v1 && !a5) {
                    a2 += a4;
                }
            }
            else if (v1 < a3 || (!a5 && a3 == v1)) {
                a2 -= a4;
            }
            return a2;
        }
        
        boolean expanded() {
            return false;
        }
        
        int gapChanged() {
            return 0;
        }
        
        int deltaSize() {
            return 0;
        }
        
        abstract int write(final int p0, final byte[] p1, final int p2, final byte[] p3) throws BadBytecode;
    }
    
    static class LdcW extends Branch
    {
        int index;
        boolean state;
        
        LdcW(final int a1, final int a2) {
            super(a1);
            this.index = a2;
            this.state = true;
        }
        
        @Override
        boolean expanded() {
            if (this.state) {
                this.state = false;
                return true;
            }
            return false;
        }
        
        @Override
        int deltaSize() {
            return 1;
        }
        
        @Override
        int write(final int a1, final byte[] a2, final int a3, final byte[] a4) {
            a4[a3] = 19;
            ByteArray.write16bit(this.index, a4, a3 + 1);
            return 2;
        }
    }
    
    abstract static class Branch16 extends Branch
    {
        int offset;
        int state;
        static final int BIT16 = 0;
        static final int EXPAND = 1;
        static final int BIT32 = 2;
        
        Branch16(final int a1, final int a2) {
            super(a1);
            this.offset = a2;
            this.state = 0;
        }
        
        @Override
        void shift(final int a1, final int a2, final boolean a3) {
            this.offset = Branch.shiftOffset(this.pos, this.offset, a1, a2, a3);
            super.shift(a1, a2, a3);
            if (this.state == 0 && (this.offset < -32768 || 32767 < this.offset)) {
                this.state = 1;
            }
        }
        
        @Override
        boolean expanded() {
            if (this.state == 1) {
                this.state = 2;
                return true;
            }
            return false;
        }
        
        @Override
        abstract int deltaSize();
        
        abstract void write32(final int p0, final byte[] p1, final int p2, final byte[] p3);
        
        @Override
        int write(final int a1, final byte[] a2, final int a3, final byte[] a4) {
            if (this.state == 2) {
                this.write32(a1, a2, a3, a4);
            }
            else {
                a4[a3] = a2[a1];
                ByteArray.write16bit(this.offset, a4, a3 + 1);
            }
            return 3;
        }
    }
    
    static class Jump16 extends Branch16
    {
        Jump16(final int a1, final int a2) {
            super(a1, a2);
        }
        
        @Override
        int deltaSize() {
            return (this.state == 2) ? 2 : 0;
        }
        
        @Override
        void write32(final int a1, final byte[] a2, final int a3, final byte[] a4) {
            a4[a3] = (byte)(((a2[a1] & 0xFF) == 0xA7) ? 200 : 201);
            ByteArray.write32bit(this.offset, a4, a3 + 1);
        }
    }
    
    static class If16 extends Branch16
    {
        If16(final int a1, final int a2) {
            super(a1, a2);
        }
        
        @Override
        int deltaSize() {
            return (this.state == 2) ? 5 : 0;
        }
        
        @Override
        void write32(final int a1, final byte[] a2, final int a3, final byte[] a4) {
            a4[a3] = (byte)this.opcode(a2[a1] & 0xFF);
            a4[a3 + 1] = 0;
            a4[a3 + 2] = 8;
            a4[a3 + 3] = -56;
            ByteArray.write32bit(this.offset - 3, a4, a3 + 4);
        }
        
        int opcode(final int a1) {
            if (a1 == 198) {
                return 199;
            }
            if (a1 == 199) {
                return 198;
            }
            if ((a1 - 153 & 0x1) == 0x0) {
                return a1 + 1;
            }
            return a1 - 1;
        }
    }
    
    static class Jump32 extends Branch
    {
        int offset;
        
        Jump32(final int a1, final int a2) {
            super(a1);
            this.offset = a2;
        }
        
        @Override
        void shift(final int a1, final int a2, final boolean a3) {
            this.offset = Branch.shiftOffset(this.pos, this.offset, a1, a2, a3);
            super.shift(a1, a2, a3);
        }
        
        @Override
        int write(final int a1, final byte[] a2, final int a3, final byte[] a4) {
            a4[a3] = a2[a1];
            ByteArray.write32bit(this.offset, a4, a3 + 1);
            return 5;
        }
    }
    
    abstract static class Switcher extends Branch
    {
        int gap;
        int defaultByte;
        int[] offsets;
        Pointers pointers;
        
        Switcher(final int a1, final int a2, final int[] a3, final Pointers a4) {
            super(a1);
            this.gap = 3 - (a1 & 0x3);
            this.defaultByte = a2;
            this.offsets = a3;
            this.pointers = a4;
        }
        
        @Override
        void shift(final int a3, final int v1, final boolean v2) {
            final int v3 = this.pos;
            this.defaultByte = Branch.shiftOffset(v3, this.defaultByte, a3, v1, v2);
            for (int v4 = this.offsets.length, a4 = 0; a4 < v4; ++a4) {
                this.offsets[a4] = Branch.shiftOffset(v3, this.offsets[a4], a3, v1, v2);
            }
            super.shift(a3, v1, v2);
        }
        
        @Override
        int gapChanged() {
            final int v0 = 3 - (this.pos & 0x3);
            if (v0 > this.gap) {
                final int v2 = v0 - this.gap;
                this.gap = v0;
                return v2;
            }
            return 0;
        }
        
        @Override
        int deltaSize() {
            return this.gap - (3 - (this.orgPos & 0x3));
        }
        
        @Override
        int write(final int a1, final byte[] a2, int a3, final byte[] a4) throws BadBytecode {
            int v1 = 3 - (this.pos & 0x3);
            int v2 = this.gap - v1;
            final int v3 = 5 + (3 - (this.orgPos & 0x3)) + this.tableSize();
            if (v2 > 0) {
                this.adjustOffsets(v3, v2);
            }
            a4[a3++] = a2[a1];
            while (v1-- > 0) {
                a4[a3++] = 0;
            }
            ByteArray.write32bit(this.defaultByte, a4, a3);
            final int v4 = this.write2(a3 + 4, a4);
            a3 += v4 + 4;
            while (v2-- > 0) {
                a4[a3++] = 0;
            }
            return 5 + (3 - (this.orgPos & 0x3)) + v4;
        }
        
        abstract int write2(final int p0, final byte[] p1);
        
        abstract int tableSize();
        
        void adjustOffsets(final int v1, final int v2) throws BadBytecode {
            this.pointers.shiftForSwitch(this.pos + v1, v2);
            if (this.defaultByte == v1) {
                this.defaultByte -= v2;
            }
            for (int a1 = 0; a1 < this.offsets.length; ++a1) {
                if (this.offsets[a1] == v1) {
                    final int[] offsets = this.offsets;
                    final int n = a1;
                    offsets[n] -= v2;
                }
            }
        }
    }
    
    static class Table extends Switcher
    {
        int low;
        int high;
        
        Table(final int a1, final int a2, final int a3, final int a4, final int[] a5, final Pointers a6) {
            super(a1, a2, a5, a6);
            this.low = a3;
            this.high = a4;
        }
        
        @Override
        int write2(int v1, final byte[] v2) {
            ByteArray.write32bit(this.low, v2, v1);
            ByteArray.write32bit(this.high, v2, v1 + 4);
            final int v3 = this.offsets.length;
            v1 += 8;
            for (int a1 = 0; a1 < v3; ++a1) {
                ByteArray.write32bit(this.offsets[a1], v2, v1);
                v1 += 4;
            }
            return 8 + 4 * v3;
        }
        
        @Override
        int tableSize() {
            return 8 + 4 * this.offsets.length;
        }
    }
    
    static class Lookup extends Switcher
    {
        int[] matches;
        
        Lookup(final int a1, final int a2, final int[] a3, final int[] a4, final Pointers a5) {
            super(a1, a2, a4, a5);
            this.matches = a3;
        }
        
        @Override
        int write2(int v1, final byte[] v2) {
            final int v3 = this.matches.length;
            ByteArray.write32bit(v3, v2, v1);
            v1 += 4;
            for (int a1 = 0; a1 < v3; ++a1) {
                ByteArray.write32bit(this.matches[a1], v2, v1);
                ByteArray.write32bit(this.offsets[a1], v2, v1 + 4);
                v1 += 8;
            }
            return 4 + 8 * v3;
        }
        
        @Override
        int tableSize() {
            return 4 + 8 * this.matches.length;
        }
    }
}
