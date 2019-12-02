package javassist.bytecode.analysis;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public class Analyzer implements Opcode
{
    private final SubroutineScanner scanner;
    private CtClass clazz;
    private ExceptionInfo[] exceptions;
    private Frame[] frames;
    private Subroutine[] subroutines;
    
    public Analyzer() {
        super();
        this.scanner = new SubroutineScanner();
    }
    
    public Frame[] analyze(final CtClass a1, final MethodInfo a2) throws BadBytecode {
        this.clazz = a1;
        final CodeAttribute v1 = a2.getCodeAttribute();
        if (v1 == null) {
            return null;
        }
        final int v2 = v1.getMaxLocals();
        final int v3 = v1.getMaxStack();
        final int v4 = v1.getCodeLength();
        final CodeIterator v5 = v1.iterator();
        final IntQueue v6 = new IntQueue();
        this.exceptions = this.buildExceptionInfo(a2);
        this.subroutines = this.scanner.scan(a2);
        final Executor v7 = new Executor(a1.getClassPool(), a2.getConstPool());
        (this.frames = new Frame[v4])[v5.lookAhead()] = this.firstFrame(a2, v2, v3);
        v6.add(v5.next());
        while (!v6.isEmpty()) {
            this.analyzeNextEntry(a2, v5, v6, v7);
        }
        return this.frames;
    }
    
    public Frame[] analyze(final CtMethod a1) throws BadBytecode {
        return this.analyze(a1.getDeclaringClass(), a1.getMethodInfo2());
    }
    
    private void analyzeNextEntry(final MethodInfo a4, final CodeIterator v1, final IntQueue v2, final Executor v3) throws BadBytecode {
        final int v4 = v2.take();
        v1.move(v4);
        v1.next();
        final Frame v5 = this.frames[v4].copy();
        final Subroutine v6 = this.subroutines[v4];
        try {
            v3.execute(a4, v4, v1, v5, v6);
        }
        catch (RuntimeException a5) {
            throw new BadBytecode(a5.getMessage() + "[pos = " + v4 + "]", a5);
        }
        final int v7 = v1.byteAt(v4);
        if (v7 == 170) {
            this.mergeTableSwitch(v2, v4, v1, v5);
        }
        else if (v7 == 171) {
            this.mergeLookupSwitch(v2, v4, v1, v5);
        }
        else if (v7 == 169) {
            this.mergeRet(v2, v1, v4, v5, v6);
        }
        else if (Util.isJumpInstruction(v7)) {
            final int a6 = Util.getJumpTarget(v4, v1);
            if (Util.isJsr(v7)) {
                this.mergeJsr(v2, this.frames[v4], this.subroutines[a6], v4, this.lookAhead(v1, v4));
            }
            else if (!Util.isGoto(v7)) {
                this.merge(v2, v5, this.lookAhead(v1, v4));
            }
            this.merge(v2, v5, a6);
        }
        else if (v7 != 191 && !Util.isReturn(v7)) {
            this.merge(v2, v5, this.lookAhead(v1, v4));
        }
        this.mergeExceptionHandlers(v2, a4, v4, v5);
    }
    
    private ExceptionInfo[] buildExceptionInfo(final MethodInfo v-7) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   javassist/bytecode/MethodInfo.getConstPool:()Ljavassist/bytecode/ConstPool;
        //     4: astore_2        /* v-6 */
        //     5: aload_0         /* v-8 */
        //     6: getfield        javassist/bytecode/analysis/Analyzer.clazz:Ljavassist/CtClass;
        //     9: invokevirtual   javassist/CtClass.getClassPool:()Ljavassist/ClassPool;
        //    12: astore_3        /* v-5 */
        //    13: aload_1         /* v-7 */
        //    14: invokevirtual   javassist/bytecode/MethodInfo.getCodeAttribute:()Ljavassist/bytecode/CodeAttribute;
        //    17: invokevirtual   javassist/bytecode/CodeAttribute.getExceptionTable:()Ljavassist/bytecode/ExceptionTable;
        //    20: astore          v-4
        //    22: aload           v-4
        //    24: invokevirtual   javassist/bytecode/ExceptionTable.size:()I
        //    27: anewarray       Ljavassist/bytecode/analysis/Analyzer$ExceptionInfo;
        //    30: astore          v-3
        //    32: iconst_0       
        //    33: istore          v-2
        //    35: iload           v-2
        //    37: aload           v-4
        //    39: invokevirtual   javassist/bytecode/ExceptionTable.size:()I
        //    42: if_icmpge       140
        //    45: aload           v-4
        //    47: iload           v-2
        //    49: invokevirtual   javassist/bytecode/ExceptionTable.catchType:(I)I
        //    52: istore          v-1
        //    54: iload           v-1
        //    56: ifne            65
        //    59: getstatic       javassist/bytecode/analysis/Type.THROWABLE:Ljavassist/bytecode/analysis/Type;
        //    62: goto            78
        //    65: aload_3         /* v-5 */
        //    66: aload_2         /* v-6 */
        //    67: iload           v-1
        //    69: invokevirtual   javassist/bytecode/ConstPool.getClassInfo:(I)Ljava/lang/String;
        //    72: invokevirtual   javassist/ClassPool.get:(Ljava/lang/String;)Ljavassist/CtClass;
        //    75: invokestatic    javassist/bytecode/analysis/Type.get:(Ljavassist/CtClass;)Ljavassist/bytecode/analysis/Type;
        //    78: astore          a1
        //    80: goto            98
        //    83: astore          v1
        //    85: new             Ljava/lang/IllegalStateException;
        //    88: dup            
        //    89: aload           v1
        //    91: invokevirtual   javassist/NotFoundException.getMessage:()Ljava/lang/String;
        //    94: invokespecial   java/lang/IllegalStateException.<init>:(Ljava/lang/String;)V
        //    97: athrow         
        //    98: aload           v-3
        //   100: iload           v-2
        //   102: new             Ljavassist/bytecode/analysis/Analyzer$ExceptionInfo;
        //   105: dup            
        //   106: aload           v-4
        //   108: iload           v-2
        //   110: invokevirtual   javassist/bytecode/ExceptionTable.startPc:(I)I
        //   113: aload           v-4
        //   115: iload           v-2
        //   117: invokevirtual   javassist/bytecode/ExceptionTable.endPc:(I)I
        //   120: aload           v-4
        //   122: iload           v-2
        //   124: invokevirtual   javassist/bytecode/ExceptionTable.handlerPc:(I)I
        //   127: aload           v0
        //   129: aconst_null    
        //   130: invokespecial   javassist/bytecode/analysis/Analyzer$ExceptionInfo.<init>:(IIILjavassist/bytecode/analysis/Type;Ljavassist/bytecode/analysis/Analyzer$1;)V
        //   133: aastore        
        //   134: iinc            v-2, 1
        //   137: goto            35
        //   140: aload           v-3
        //   142: areturn        
        //    StackMapTable: 00 06 FF 00 23 00 07 07 00 02 07 00 29 07 01 01 07 01 03 07 00 FC 07 01 04 01 00 00 FC 00 1D 01 4C 07 01 09 44 07 00 F6 FC 00 0E 07 01 09 F8 00 29
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                         
        //  -----  -----  -----  -----  -----------------------------
        //  54     80     83     98     Ljavassist/NotFoundException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private Frame firstFrame(final MethodInfo v-5, final int v-4, final int v-3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore          v-2
        //     3: new             Ljavassist/bytecode/analysis/Frame;
        //     6: dup            
        //     7: iload_2         /* v-4 */
        //     8: iload_3         /* v-3 */
        //     9: invokespecial   javassist/bytecode/analysis/Frame.<init>:(II)V
        //    12: astore          v-1
        //    14: aload_1         /* v-5 */
        //    15: invokevirtual   javassist/bytecode/MethodInfo.getAccessFlags:()I
        //    18: bipush          8
        //    20: iand           
        //    21: ifne            41
        //    24: aload           v-1
        //    26: iload           v-2
        //    28: iinc            v-2, 1
        //    31: aload_0         /* v-6 */
        //    32: getfield        javassist/bytecode/analysis/Analyzer.clazz:Ljavassist/CtClass;
        //    35: invokestatic    javassist/bytecode/analysis/Type.get:(Ljavassist/CtClass;)Ljavassist/bytecode/analysis/Type;
        //    38: invokevirtual   javassist/bytecode/analysis/Frame.setLocal:(ILjavassist/bytecode/analysis/Type;)V
        //    41: aload_1         /* v-5 */
        //    42: invokevirtual   javassist/bytecode/MethodInfo.getDescriptor:()Ljava/lang/String;
        //    45: aload_0         /* v-6 */
        //    46: getfield        javassist/bytecode/analysis/Analyzer.clazz:Ljavassist/CtClass;
        //    49: invokevirtual   javassist/CtClass.getClassPool:()Ljavassist/ClassPool;
        //    52: invokestatic    javassist/bytecode/Descriptor.getParameterTypes:(Ljava/lang/String;Ljavassist/ClassPool;)[Ljavassist/CtClass;
        //    55: astore          a1
        //    57: goto            72
        //    60: astore          a2
        //    62: new             Ljava/lang/RuntimeException;
        //    65: dup            
        //    66: aload           a2
        //    68: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/Throwable;)V
        //    71: athrow         
        //    72: iconst_0       
        //    73: istore          v1
        //    75: iload           v1
        //    77: aload           v0
        //    79: arraylength    
        //    80: if_icmpge       137
        //    83: aload_0         /* v-6 */
        //    84: aload           v0
        //    86: iload           v1
        //    88: aaload         
        //    89: invokestatic    javassist/bytecode/analysis/Type.get:(Ljavassist/CtClass;)Ljavassist/bytecode/analysis/Type;
        //    92: invokespecial   javassist/bytecode/analysis/Analyzer.zeroExtend:(Ljavassist/bytecode/analysis/Type;)Ljavassist/bytecode/analysis/Type;
        //    95: astore          a3
        //    97: aload           v-1
        //    99: iload           v-2
        //   101: iinc            v-2, 1
        //   104: aload           a3
        //   106: invokevirtual   javassist/bytecode/analysis/Frame.setLocal:(ILjavassist/bytecode/analysis/Type;)V
        //   109: aload           a3
        //   111: invokevirtual   javassist/bytecode/analysis/Type.getSize:()I
        //   114: iconst_2       
        //   115: if_icmpne       131
        //   118: aload           v-1
        //   120: iload           v-2
        //   122: iinc            v-2, 1
        //   125: getstatic       javassist/bytecode/analysis/Type.TOP:Ljavassist/bytecode/analysis/Type;
        //   128: invokevirtual   javassist/bytecode/analysis/Frame.setLocal:(ILjavassist/bytecode/analysis/Type;)V
        //   131: iinc            v1, 1
        //   134: goto            75
        //   137: aload           v-1
        //   139: areturn        
        //    StackMapTable: 00 06 FD 00 29 01 07 00 5D 52 07 00 F6 FC 00 0B 07 01 4F FC 00 02 01 37 FA 00 05
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                         
        //  -----  -----  -----  -----  -----------------------------
        //  41     57     60     72     Ljavassist/NotFoundException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private int getNext(final CodeIterator a1, final int a2, final int a3) throws BadBytecode {
        a1.move(a2);
        a1.next();
        final int v1 = a1.lookAhead();
        a1.move(a3);
        a1.next();
        return v1;
    }
    
    private int lookAhead(final CodeIterator a1, final int a2) throws BadBytecode {
        if (!a1.hasNext()) {
            throw new BadBytecode("Execution falls off end! [pos = " + a2 + "]");
        }
        return a1.lookAhead();
    }
    
    private void merge(final IntQueue a3, final Frame v1, final int v2) {
        final Frame v3 = this.frames[v2];
        boolean v4 = false;
        if (v3 == null) {
            this.frames[v2] = v1.copy();
            final boolean a4 = true;
        }
        else {
            v4 = v3.merge(v1);
        }
        if (v4) {
            a3.add(v2);
        }
    }
    
    private void mergeExceptionHandlers(final IntQueue v1, final MethodInfo v2, final int v3, final Frame v4) {
        for (int a3 = 0; a3 < this.exceptions.length; ++a3) {
            final ExceptionInfo a4 = this.exceptions[a3];
            if (v3 >= a4.start && v3 < a4.end) {
                final Frame a5 = v4.copy();
                a5.clearStack();
                a5.push(a4.type);
                this.merge(v1, a5, a4.handler);
            }
        }
    }
    
    private void mergeJsr(final IntQueue a5, final Frame v1, final Subroutine v2, final int v3, final int v4) throws BadBytecode {
        if (v2 == null) {
            throw new BadBytecode("No subroutine at jsr target! [pos = " + v3 + "]");
        }
        Frame v5 = this.frames[v4];
        boolean v6 = false;
        if (v5 == null) {
            final Frame[] frames = this.frames;
            final Frame copy = v1.copy();
            frames[v4] = copy;
            v5 = copy;
            v6 = true;
        }
        else {
            for (int a6 = 0; a6 < v1.localsLength(); ++a6) {
                if (!v2.isAccessed(a6)) {
                    final Type a7 = v5.getLocal(a6);
                    Type a8 = v1.getLocal(a6);
                    if (a7 == null) {
                        v5.setLocal(a6, a8);
                        v6 = true;
                    }
                    else {
                        a8 = a7.merge(a8);
                        v5.setLocal(a6, a8);
                        if (!a8.equals(a7) || a8.popChanged()) {
                            v6 = true;
                        }
                    }
                }
            }
        }
        if (!v5.isJsrMerged()) {
            v5.setJsrMerged(true);
            v6 = true;
        }
        if (v6 && v5.isRetMerged()) {
            a5.add(v4);
        }
    }
    
    private void mergeLookupSwitch(final IntQueue a3, final int a4, final CodeIterator v1, final Frame v2) throws BadBytecode {
        int v3 = (a4 & 0xFFFFFFFC) + 4;
        this.merge(a3, v2, a4 + v1.s32bitAt(v3));
        v3 += 4;
        final int v4 = v1.s32bitAt(v3);
        final int n = v4 * 8;
        for (v3 += 4, final int v5 = n + v3, v3 += 4; v3 < v5; v3 += 8) {
            final int a5 = v1.s32bitAt(v3) + a4;
            this.merge(a3, v2, a5);
        }
    }
    
    private void mergeRet(final IntQueue v-6, final CodeIterator v-5, final int v-4, final Frame v-3, final Subroutine v-2) throws BadBytecode {
        if (v-2 == null) {
            throw new BadBytecode("Ret on no subroutine! [pos = " + v-4 + "]");
        }
        for (final int a5 : v-2.callers()) {
            final int v1 = this.getNext(v-5, a5, v-4);
            boolean v2 = false;
            Frame v3 = this.frames[v1];
            if (v3 == null) {
                final Frame[] frames = this.frames;
                final int n = v1;
                final Frame copyStack = v-3.copyStack();
                frames[n] = copyStack;
                v3 = copyStack;
                v2 = true;
            }
            else {
                v2 = v3.mergeStack(v-3);
            }
            for (final int a7 : v-2.accessed()) {
                final Type a8 = v3.getLocal(a7);
                final Type a9 = v-3.getLocal(a7);
                if (a8 != a9) {
                    v3.setLocal(a7, a9);
                    v2 = true;
                }
            }
            if (!v3.isRetMerged()) {
                v3.setRetMerged(true);
                v2 = true;
            }
            if (v2 && v3.isJsrMerged()) {
                v-6.add(v1);
            }
        }
    }
    
    private void mergeTableSwitch(final IntQueue a3, final int a4, final CodeIterator v1, final Frame v2) throws BadBytecode {
        int v3 = (a4 & 0xFFFFFFFC) + 4;
        this.merge(a3, v2, a4 + v1.s32bitAt(v3));
        v3 += 4;
        final int v4 = v1.s32bitAt(v3);
        v3 += 4;
        final int v5 = v1.s32bitAt(v3);
        final int n = (v5 - v4 + 1) * 4;
        v3 += 4;
        for (int v6 = n + v3; v3 < v6; v3 += 4) {
            final int a5 = v1.s32bitAt(v3) + a4;
            this.merge(a3, v2, a5);
        }
    }
    
    private Type zeroExtend(final Type a1) {
        if (a1 == Type.SHORT || a1 == Type.BYTE || a1 == Type.CHAR || a1 == Type.BOOLEAN) {
            return Type.INTEGER;
        }
        return a1;
    }
    
    private static class ExceptionInfo
    {
        private int end;
        private int handler;
        private int start;
        private Type type;
        
        private ExceptionInfo(final int a1, final int a2, final int a3, final Type a4) {
            super();
            this.start = a1;
            this.end = a2;
            this.handler = a3;
            this.type = a4;
        }
        
        ExceptionInfo(final int a1, final int a2, final int a3, final Type a4, final Analyzer$1 a5) {
            this(a1, a2, a3, a4);
        }
        
        static /* synthetic */ int access$100(final ExceptionInfo a1) {
            return a1.start;
        }
        
        static /* synthetic */ int access$200(final ExceptionInfo a1) {
            return a1.end;
        }
        
        static /* synthetic */ Type access$300(final ExceptionInfo a1) {
            return a1.type;
        }
        
        static /* synthetic */ int access$400(final ExceptionInfo a1) {
            return a1.handler;
        }
    }
}
