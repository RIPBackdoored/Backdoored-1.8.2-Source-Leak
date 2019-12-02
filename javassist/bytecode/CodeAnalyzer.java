package javassist.bytecode;

class CodeAnalyzer implements Opcode
{
    private ConstPool constPool;
    private CodeAttribute codeAttr;
    
    public CodeAnalyzer(final CodeAttribute a1) {
        super();
        this.codeAttr = a1;
        this.constPool = a1.getConstPool();
    }
    
    public int computeMaxStack() throws BadBytecode {
        final CodeIterator iterator = this.codeAttr.iterator();
        final int codeLength = iterator.getCodeLength();
        final int[] array = new int[codeLength];
        this.constPool = this.codeAttr.getConstPool();
        this.initStack(array, this.codeAttr);
        boolean v0;
        do {
            v0 = false;
            for (int v2 = 0; v2 < codeLength; ++v2) {
                if (array[v2] < 0) {
                    v0 = true;
                    this.visitBytecode(iterator, array, v2);
                }
            }
        } while (v0);
        int v2 = 1;
        for (int v3 = 0; v3 < codeLength; ++v3) {
            if (array[v3] > v2) {
                v2 = array[v3];
            }
        }
        return v2 - 1;
    }
    
    private void initStack(final int[] v2, final CodeAttribute v3) {
        v2[0] = -1;
        final ExceptionTable v4 = v3.getExceptionTable();
        if (v4 != null) {
            for (int a2 = v4.size(), a3 = 0; a3 < a2; ++a3) {
                v2[v4.handlerPc(a3)] = -2;
            }
        }
    }
    
    private void visitBytecode(final CodeIterator a3, final int[] v1, int v2) throws BadBytecode {
        final int v3 = v1.length;
        a3.move(v2);
        int v4 = -v1[v2];
        final int[] v5 = { -1 };
        while (a3.hasNext()) {
            v2 = a3.next();
            v1[v2] = v4;
            final int a4 = a3.byteAt(v2);
            v4 = this.visitInst(a4, a3, v2, v4);
            if (v4 < 1) {
                throw new BadBytecode("stack underflow at " + v2);
            }
            if (this.processBranch(a4, a3, v2, v3, v1, v4, v5)) {
                break;
            }
            if (isEnd(a4)) {
                break;
            }
            if (a4 != 168 && a4 != 201) {
                continue;
            }
            --v4;
        }
    }
    
    private boolean processBranch(final int v-11, final CodeIterator v-10, final int v-9, final int v-8, final int[] v-7, final int v-6, final int[] v-5) throws BadBytecode {
        if ((153 <= v-11 && v-11 <= 166) || v-11 == 198 || v-11 == 199) {
            final int a1 = v-9 + v-10.s16bitAt(v-9 + 1);
            this.checkTarget(v-9, a1, v-8, v-7, v-6);
        }
        else {
            switch (v-11) {
                case 167: {
                    final int a2 = v-9 + v-10.s16bitAt(v-9 + 1);
                    this.checkTarget(v-9, a2, v-8, v-7, v-6);
                    return true;
                }
                case 200: {
                    final int a3 = v-9 + v-10.s32bitAt(v-9 + 1);
                    this.checkTarget(v-9, a3, v-8, v-7, v-6);
                    return true;
                }
                case 168:
                case 201: {
                    int a5 = 0;
                    if (v-11 == 168) {
                        final int a4 = v-9 + v-10.s16bitAt(v-9 + 1);
                    }
                    else {
                        a5 = v-9 + v-10.s32bitAt(v-9 + 1);
                    }
                    this.checkTarget(v-9, a5, v-8, v-7, v-6);
                    if (v-5[0] < 0) {
                        v-5[0] = v-6;
                        return false;
                    }
                    if (v-6 == v-5[0]) {
                        return false;
                    }
                    throw new BadBytecode("sorry, cannot compute this data flow due to JSR: " + v-6 + "," + v-5[0]);
                }
                case 169: {
                    if (v-5[0] < 0) {
                        v-5[0] = v-6 + 1;
                        return false;
                    }
                    if (v-6 + 1 == v-5[0]) {
                        return true;
                    }
                    throw new BadBytecode("sorry, cannot compute this data flow due to RET: " + v-6 + "," + v-5[0]);
                }
                case 170:
                case 171: {
                    int a8 = (v-9 & 0xFFFFFFFC) + 4;
                    int a9 = v-9 + v-10.s32bitAt(a8);
                    this.checkTarget(v-9, a9, v-8, v-7, v-6);
                    if (v-11 == 171) {
                        final int a6 = v-10.s32bitAt(a8 + 4);
                        a8 += 12;
                        for (int a7 = 0; a7 < a6; ++a7) {
                            a9 = v-9 + v-10.s32bitAt(a8);
                            this.checkTarget(v-9, a9, v-8, v-7, v-6);
                            a8 += 8;
                        }
                    }
                    else {
                        final int s32bit = v-10.s32bitAt(a8 + 4);
                        final int s32bit2 = v-10.s32bitAt(a8 + 8);
                        final int v0 = s32bit2 - s32bit + 1;
                        a8 += 12;
                        for (int v2 = 0; v2 < v0; ++v2) {
                            a9 = v-9 + v-10.s32bitAt(a8);
                            this.checkTarget(v-9, a9, v-8, v-7, v-6);
                            a8 += 4;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    private void checkTarget(final int a1, final int a2, final int a3, final int[] a4, final int a5) throws BadBytecode {
        if (a2 < 0 || a3 <= a2) {
            throw new BadBytecode("bad branch offset at " + a1);
        }
        final int v1 = a4[a2];
        if (v1 == 0) {
            a4[a2] = -a5;
        }
        else if (v1 != a5 && v1 != -a5) {
            throw new BadBytecode("verification error (" + a5 + "," + v1 + ") at " + a1);
        }
    }
    
    private static boolean isEnd(final int a1) {
        return (172 <= a1 && a1 <= 177) || a1 == 191;
    }
    
    private int visitInst(int v2, final CodeIterator v3, final int v4, int v5) throws BadBytecode {
        switch (v2) {
            case 180: {
                v5 += this.getFieldSize(v3, v4) - 1;
                return v5;
            }
            case 181: {
                v5 -= this.getFieldSize(v3, v4) + 1;
                return v5;
            }
            case 178: {
                v5 += this.getFieldSize(v3, v4);
                return v5;
            }
            case 179: {
                v5 -= this.getFieldSize(v3, v4);
                return v5;
            }
            case 182:
            case 183: {
                final String a1 = this.constPool.getMethodrefType(v3.u16bitAt(v4 + 1));
                v5 += Descriptor.dataSize(a1) - 1;
                return v5;
            }
            case 184: {
                final String a2 = this.constPool.getMethodrefType(v3.u16bitAt(v4 + 1));
                v5 += Descriptor.dataSize(a2);
                return v5;
            }
            case 185: {
                final String a3 = this.constPool.getInterfaceMethodrefType(v3.u16bitAt(v4 + 1));
                v5 += Descriptor.dataSize(a3) - 1;
                return v5;
            }
            case 186: {
                final String a4 = this.constPool.getInvokeDynamicType(v3.u16bitAt(v4 + 1));
                v5 += Descriptor.dataSize(a4);
                return v5;
            }
            case 191: {
                v5 = 1;
                return v5;
            }
            case 197: {
                v5 += 1 - v3.byteAt(v4 + 3);
                return v5;
            }
            case 196: {
                v2 = v3.byteAt(v4 + 1);
                break;
            }
        }
        v5 += CodeAnalyzer.STACK_GROW[v2];
        return v5;
    }
    
    private int getFieldSize(final CodeIterator a1, final int a2) {
        final String v1 = this.constPool.getFieldrefType(a1.u16bitAt(a2 + 1));
        return Descriptor.dataSize(v1);
    }
}
