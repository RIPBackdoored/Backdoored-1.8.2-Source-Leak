package javassist.bytecode.stackmap;

import javassist.*;
import java.util.*;
import javassist.bytecode.*;

public class MapMaker extends Tracer
{
    public static StackMapTable make(final ClassPool v-4, final MethodInfo v-3) throws BadBytecode {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   javassist/bytecode/MethodInfo.getCodeAttribute:()Ljavassist/bytecode/CodeAttribute;
        //     4: astore_2        /* v-2 */
        //     5: aload_2         /* v-2 */
        //     6: ifnonnull       11
        //     9: aconst_null    
        //    10: areturn        
        //    11: aload_1         /* v-3 */
        //    12: aload_2         /* v-2 */
        //    13: iconst_1       
        //    14: invokestatic    javassist/bytecode/stackmap/TypedBlock.makeBlocks:(Ljavassist/bytecode/MethodInfo;Ljavassist/bytecode/CodeAttribute;Z)[Ljavassist/bytecode/stackmap/TypedBlock;
        //    17: astore_3        /* a1 */
        //    18: goto            25
        //    21: astore          a2
        //    23: aconst_null    
        //    24: areturn        
        //    25: aload_3         /* v-1 */
        //    26: ifnonnull       31
        //    29: aconst_null    
        //    30: areturn        
        //    31: new             Ljavassist/bytecode/stackmap/MapMaker;
        //    34: dup            
        //    35: aload_0         /* v-4 */
        //    36: aload_1         /* v-3 */
        //    37: aload_2         /* v-2 */
        //    38: invokespecial   javassist/bytecode/stackmap/MapMaker.<init>:(Ljavassist/ClassPool;Ljavassist/bytecode/MethodInfo;Ljavassist/bytecode/CodeAttribute;)V
        //    41: astore          v0
        //    43: aload           v0
        //    45: aload_3         /* v-1 */
        //    46: aload_2         /* v-2 */
        //    47: invokevirtual   javassist/bytecode/CodeAttribute.getCode:()[B
        //    50: invokevirtual   javassist/bytecode/stackmap/MapMaker.make:([Ljavassist/bytecode/stackmap/TypedBlock;[B)V
        //    53: goto            69
        //    56: astore          v1
        //    58: new             Ljavassist/bytecode/BadBytecode;
        //    61: dup            
        //    62: aload_1         /* v-3 */
        //    63: aload           v1
        //    65: invokespecial   javassist/bytecode/BadBytecode.<init>:(Ljavassist/bytecode/MethodInfo;Ljava/lang/Throwable;)V
        //    68: athrow         
        //    69: aload           v0
        //    71: aload_3         /* v-1 */
        //    72: invokevirtual   javassist/bytecode/stackmap/MapMaker.toStackMap:([Ljavassist/bytecode/stackmap/TypedBlock;)Ljavassist/bytecode/StackMapTable;
        //    75: areturn        
        //    Exceptions:
        //  throws javassist.bytecode.BadBytecode
        //    StackMapTable: 00 06 FC 00 0B 07 00 2D 49 07 00 07 FC 00 03 07 00 35 05 FF 00 18 00 05 07 00 42 07 00 27 07 00 2D 07 00 35 07 00 02 00 01 07 00 25 0C
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                                
        //  -----  -----  -----  -----  ----------------------------------------------------
        //  11     18     21     25     Ljavassist/bytecode/stackmap/BasicBlock$JsrBytecode;
        //  43     53     56     69     Ljavassist/bytecode/BadBytecode;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static StackMap make2(final ClassPool v-4, final MethodInfo v-3) throws BadBytecode {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   javassist/bytecode/MethodInfo.getCodeAttribute:()Ljavassist/bytecode/CodeAttribute;
        //     4: astore_2        /* v-2 */
        //     5: aload_2         /* v-2 */
        //     6: ifnonnull       11
        //     9: aconst_null    
        //    10: areturn        
        //    11: aload_1         /* v-3 */
        //    12: aload_2         /* v-2 */
        //    13: iconst_1       
        //    14: invokestatic    javassist/bytecode/stackmap/TypedBlock.makeBlocks:(Ljavassist/bytecode/MethodInfo;Ljavassist/bytecode/CodeAttribute;Z)[Ljavassist/bytecode/stackmap/TypedBlock;
        //    17: astore_3        /* a1 */
        //    18: goto            25
        //    21: astore          a2
        //    23: aconst_null    
        //    24: areturn        
        //    25: aload_3         /* v-1 */
        //    26: ifnonnull       31
        //    29: aconst_null    
        //    30: areturn        
        //    31: new             Ljavassist/bytecode/stackmap/MapMaker;
        //    34: dup            
        //    35: aload_0         /* v-4 */
        //    36: aload_1         /* v-3 */
        //    37: aload_2         /* v-2 */
        //    38: invokespecial   javassist/bytecode/stackmap/MapMaker.<init>:(Ljavassist/ClassPool;Ljavassist/bytecode/MethodInfo;Ljavassist/bytecode/CodeAttribute;)V
        //    41: astore          v0
        //    43: aload           v0
        //    45: aload_3         /* v-1 */
        //    46: aload_2         /* v-2 */
        //    47: invokevirtual   javassist/bytecode/CodeAttribute.getCode:()[B
        //    50: invokevirtual   javassist/bytecode/stackmap/MapMaker.make:([Ljavassist/bytecode/stackmap/TypedBlock;[B)V
        //    53: goto            69
        //    56: astore          v1
        //    58: new             Ljavassist/bytecode/BadBytecode;
        //    61: dup            
        //    62: aload_1         /* v-3 */
        //    63: aload           v1
        //    65: invokespecial   javassist/bytecode/BadBytecode.<init>:(Ljavassist/bytecode/MethodInfo;Ljava/lang/Throwable;)V
        //    68: athrow         
        //    69: aload           v0
        //    71: aload_1         /* v-3 */
        //    72: invokevirtual   javassist/bytecode/MethodInfo.getConstPool:()Ljavassist/bytecode/ConstPool;
        //    75: aload_3         /* v-1 */
        //    76: invokevirtual   javassist/bytecode/stackmap/MapMaker.toStackMap2:(Ljavassist/bytecode/ConstPool;[Ljavassist/bytecode/stackmap/TypedBlock;)Ljavassist/bytecode/StackMap;
        //    79: areturn        
        //    Exceptions:
        //  throws javassist.bytecode.BadBytecode
        //    StackMapTable: 00 06 FC 00 0B 07 00 2D 49 07 00 07 FC 00 03 07 00 35 05 FF 00 18 00 05 07 00 42 07 00 27 07 00 2D 07 00 35 07 00 02 00 01 07 00 25 0C
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                                
        //  -----  -----  -----  -----  ----------------------------------------------------
        //  11     18     21     25     Ljavassist/bytecode/stackmap/BasicBlock$JsrBytecode;
        //  43     53     56     69     Ljavassist/bytecode/BadBytecode;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public MapMaker(final ClassPool a1, final MethodInfo a2, final CodeAttribute a3) {
        super(a1, a2.getConstPool(), a3.getMaxStack(), a3.getMaxLocals(), TypedBlock.getRetType(a2.getDescriptor()));
    }
    
    protected MapMaker(final MapMaker a1) {
        super(a1);
    }
    
    void make(final TypedBlock[] v1, final byte[] v2) throws BadBytecode {
        this.make(v2, v1[0]);
        this.findDeadCatchers(v2, v1);
        try {
            this.fixTypes(v2, v1);
        }
        catch (NotFoundException a1) {
            throw new BadBytecode("failed to resolve types", a1);
        }
    }
    
    private void make(final byte[] v-3, final TypedBlock v-2) throws BadBytecode {
        copyTypeData(v-2.stackTop, v-2.stackTypes, this.stackTypes);
        this.stackTop = v-2.stackTop;
        copyTypeData(v-2.localsTypes.length, v-2.localsTypes, this.localsTypes);
        this.traceException(v-3, v-2.toCatch);
        int i = v-2.position;
        final int v0 = i + v-2.length;
        while (i < v0) {
            i += this.doOpcode(i, v-3);
            this.traceException(v-3, v-2.toCatch);
        }
        if (v-2.exit != null) {
            for (int v2 = 0; v2 < v-2.exit.length; ++v2) {
                final TypedBlock a2 = (TypedBlock)v-2.exit[v2];
                if (a2.alreadySet()) {
                    this.mergeMap(a2, true);
                }
                else {
                    this.recordStackMap(a2);
                    final MapMaker a3 = new MapMaker(this);
                    a3.make(v-3, a2);
                }
            }
        }
    }
    
    private void traceException(final byte[] v2, BasicBlock.Catch v3) throws BadBytecode {
        while (v3 != null) {
            final TypedBlock a2 = (TypedBlock)v3.body;
            if (a2.alreadySet()) {
                this.mergeMap(a2, false);
                if (a2.stackTop < 1) {
                    throw new BadBytecode("bad catch clause: " + v3.typeIndex);
                }
                a2.stackTypes[0] = this.merge(this.toExceptionType(v3.typeIndex), a2.stackTypes[0]);
            }
            else {
                this.recordStackMap(a2, v3.typeIndex);
                final MapMaker a3 = new MapMaker(this);
                a3.make(v2, a2);
            }
            v3 = v3.next;
        }
    }
    
    private void mergeMap(final TypedBlock v2, final boolean v3) throws BadBytecode {
        for (int v4 = this.localsTypes.length, a1 = 0; a1 < v4; ++a1) {
            v2.localsTypes[a1] = this.merge(validateTypeData(this.localsTypes, v4, a1), v2.localsTypes[a1]);
        }
        if (v3) {
            for (int v4 = this.stackTop, a2 = 0; a2 < v4; ++a2) {
                v2.stackTypes[a2] = this.merge(this.stackTypes[a2], v2.stackTypes[a2]);
            }
        }
    }
    
    private TypeData merge(final TypeData a1, final TypeData a2) throws BadBytecode {
        if (a1 == a2) {
            return a2;
        }
        if (a2 instanceof TypeData.ClassName || a2 instanceof TypeData.BasicType) {
            return a2;
        }
        if (a2 instanceof TypeData.AbsTypeVar) {
            ((TypeData.AbsTypeVar)a2).merge(a1);
            return a2;
        }
        throw new RuntimeException("fatal: this should never happen");
    }
    
    private void recordStackMap(final TypedBlock a1) throws BadBytecode {
        final TypeData[] v1 = TypeData.make(this.stackTypes.length);
        final int v2 = this.stackTop;
        recordTypeData(v2, this.stackTypes, v1);
        this.recordStackMap0(a1, v2, v1);
    }
    
    private void recordStackMap(final TypedBlock a1, final int a2) throws BadBytecode {
        final TypeData[] v1 = TypeData.make(this.stackTypes.length);
        v1[0] = this.toExceptionType(a2).join();
        this.recordStackMap0(a1, 1, v1);
    }
    
    private TypeData.ClassName toExceptionType(final int v2) {
        String v3 = null;
        if (v2 == 0) {
            final String a1 = "java.lang.Throwable";
        }
        else {
            v3 = this.cpool.getClassInfo(v2);
        }
        return new TypeData.ClassName(v3);
    }
    
    private void recordStackMap0(final TypedBlock a1, final int a2, final TypeData[] a3) throws BadBytecode {
        final int v1 = this.localsTypes.length;
        final TypeData[] v2 = TypeData.make(v1);
        final int v3 = recordTypeData(v1, this.localsTypes, v2);
        a1.setStackMap(a2, a3, v3, v2);
    }
    
    protected static int recordTypeData(final int a3, final TypeData[] v1, final TypeData[] v2) {
        int v3 = -1;
        for (int a4 = 0; a4 < a3; ++a4) {
            final TypeData a5 = validateTypeData(v1, a3, a4);
            v2[a4] = a5.join();
            if (a5 != MapMaker.TOP) {
                v3 = a4 + 1;
            }
        }
        return v3 + 1;
    }
    
    protected static void copyTypeData(final int a2, final TypeData[] a3, final TypeData[] v1) {
        for (int a4 = 0; a4 < a2; ++a4) {
            v1[a4] = a3[a4];
        }
    }
    
    private static TypeData validateTypeData(final TypeData[] a1, final int a2, final int a3) {
        final TypeData v1 = a1[a3];
        if (v1.is2WordType() && a3 + 1 < a2 && a1[a3 + 1] != MapMaker.TOP) {
            return MapMaker.TOP;
        }
        return v1;
    }
    
    private void findDeadCatchers(final byte[] v-3, final TypedBlock[] v-2) throws BadBytecode {
        for (final TypedBlock v2 : v-2) {
            if (!v2.alreadySet()) {
                this.fixDeadcode(v-3, v2);
                final BasicBlock.Catch a2 = v2.toCatch;
                if (a2 != null) {
                    final TypedBlock a3 = (TypedBlock)a2.body;
                    if (!a3.alreadySet()) {
                        this.recordStackMap(a3, a2.typeIndex);
                        this.fixDeadcode(v-3, a3);
                        a3.incoming = 1;
                    }
                }
            }
        }
    }
    
    private void fixDeadcode(final byte[] v1, final TypedBlock v2) throws BadBytecode {
        final int v3 = v2.position;
        final int v4 = v2.length - 3;
        if (v4 < 0) {
            if (v4 == -1) {
                v1[v3] = 0;
            }
            v1[v3 + v2.length - 1] = -65;
            v2.incoming = 1;
            this.recordStackMap(v2, 0);
            return;
        }
        v2.incoming = 0;
        for (int a1 = 0; a1 < v4; ++a1) {
            v1[v3 + a1] = 0;
        }
        v1[v3 + v4] = -89;
        ByteArray.write16bit(-v4, v1, v3 + v4 + 1);
    }
    
    private void fixTypes(final byte[] v-6, final TypedBlock[] v-5) throws NotFoundException, BadBytecode {
        final ArrayList list = new ArrayList();
        final int length = v-5.length;
        int n = 0;
        for (final TypedBlock v0 : v-5) {
            if (v0.alreadySet()) {
                for (int v2 = v0.localsTypes.length, a1 = 0; a1 < v2; ++a1) {
                    n = v0.localsTypes[a1].dfs(list, n, this.classPool);
                }
                for (int v2 = v0.stackTop, a2 = 0; a2 < v2; ++a2) {
                    n = v0.stackTypes[a2].dfs(list, n, this.classPool);
                }
            }
        }
    }
    
    public StackMapTable toStackMap(final TypedBlock[] v-5) {
        final StackMapTable.Writer v3 = new StackMapTable.Writer(32);
        final int length = v-5.length;
        TypedBlock v4 = v-5[0];
        int length2 = v4.length;
        if (v4.incoming > 0) {
            v3.sameFrame(0);
            --length2;
        }
        for (int v0 = 1; v0 < length; ++v0) {
            final TypedBlock v2 = v-5[v0];
            if (this.isTarget(v2, v-5[v0 - 1])) {
                v2.resetNumLocals();
                final int a1 = stackMapDiff(v4.numLocals, v4.localsTypes, v2.numLocals, v2.localsTypes);
                this.toStackMapBody(v3, v2, a1, length2, v4);
                length2 = v2.length - 1;
                v4 = v2;
            }
            else if (v2.incoming == 0) {
                v3.sameFrame(length2);
                length2 = v2.length - 1;
                v4 = v2;
            }
            else {
                length2 += v2.length;
            }
        }
        return v3.toStackMapTable(this.cpool);
    }
    
    private boolean isTarget(final TypedBlock a1, final TypedBlock a2) {
        final int v1 = a1.incoming;
        return v1 > 1 || (v1 >= 1 && a2.stop);
    }
    
    private void toStackMapBody(final StackMapTable.Writer v1, final TypedBlock v2, final int v3, final int v4, final TypedBlock v5) {
        final int v6 = v2.stackTop;
        if (v6 == 0) {
            if (v3 == 0) {
                v1.sameFrame(v4);
                return;
            }
            if (0 > v3 && v3 >= -3) {
                v1.chopFrame(v4, -v3);
                return;
            }
            if (0 < v3 && v3 <= 3) {
                final int[] a1 = new int[v3];
                final int[] a2 = this.fillStackMap(v2.numLocals - v5.numLocals, v5.numLocals, a1, v2.localsTypes);
                v1.appendFrame(v4, a2, a1);
                return;
            }
        }
        else {
            if (v6 == 1 && v3 == 0) {
                final TypeData a3 = v2.stackTypes[0];
                v1.sameLocals(v4, a3.getTypeTag(), a3.getTypeData(this.cpool));
                return;
            }
            if (v6 == 2 && v3 == 0) {
                final TypeData a4 = v2.stackTypes[0];
                if (a4.is2WordType()) {
                    v1.sameLocals(v4, a4.getTypeTag(), a4.getTypeData(this.cpool));
                    return;
                }
            }
        }
        final int[] v7 = new int[v6];
        final int[] v8 = this.fillStackMap(v6, 0, v7, v2.stackTypes);
        final int[] v9 = new int[v2.numLocals];
        final int[] v10 = this.fillStackMap(v2.numLocals, 0, v9, v2.localsTypes);
        v1.fullFrame(v4, v10, v9, v8, v7);
    }
    
    private int[] fillStackMap(final int a4, final int v1, final int[] v2, final TypeData[] v3) {
        final int v4 = diffSize(v3, v1, v1 + a4);
        final ConstPool v5 = this.cpool;
        final int[] v6 = new int[v4];
        int v7 = 0;
        for (int a5 = 0; a5 < a4; ++a5) {
            final TypeData a6 = v3[v1 + a5];
            v6[v7] = a6.getTypeTag();
            v2[v7] = a6.getTypeData(v5);
            if (a6.is2WordType()) {
                ++a5;
            }
            ++v7;
        }
        return v6;
    }
    
    private static int stackMapDiff(final int a2, final TypeData[] a3, final int a4, final TypeData[] v1) {
        final int v2 = a4 - a2;
        int v3 = 0;
        if (v2 > 0) {
            final int a5 = a2;
        }
        else {
            v3 = a4;
        }
        if (!stackMapEq(a3, v1, v3)) {
            return -100;
        }
        if (v2 > 0) {
            return diffSize(v1, v3, a4);
        }
        return -diffSize(a3, v3, a2);
    }
    
    private static boolean stackMapEq(final TypeData[] a2, final TypeData[] a3, final int v1) {
        for (int a4 = 0; a4 < v1; ++a4) {
            if (!a2[a4].eq(a3[a4])) {
                return false;
            }
        }
        return true;
    }
    
    private static int diffSize(final TypeData[] a2, int a3, final int v1) {
        int v2 = 0;
        while (a3 < v1) {
            final TypeData a4 = a2[a3++];
            ++v2;
            if (a4.is2WordType()) {
                ++a3;
            }
        }
        return v2;
    }
    
    public StackMap toStackMap2(final ConstPool v-6, final TypedBlock[] v-5) {
        final StackMap.Writer a4 = new StackMap.Writer();
        final int length = v-5.length;
        final boolean[] array = new boolean[length];
        TypedBlock typedBlock = v-5[0];
        array[0] = (typedBlock.incoming > 0);
        int v0 = array[0] ? 1 : 0;
        for (int a2 = 1; a2 < length; ++a2) {
            final TypedBlock a3 = v-5[a2];
            final boolean[] array2 = array;
            final int n = a2;
            final boolean target = this.isTarget(a3, v-5[a2 - 1]);
            array2[n] = target;
            if (target) {
                a3.resetNumLocals();
                typedBlock = a3;
                ++v0;
            }
        }
        if (v0 == 0) {
            return null;
        }
        a4.write16bit(v0);
        for (int v2 = 0; v2 < length; ++v2) {
            if (array[v2]) {
                this.writeStackFrame(a4, v-6, v-5[v2].position, v-5[v2]);
            }
        }
        return a4.toStackMap(v-6);
    }
    
    private void writeStackFrame(final StackMap.Writer a1, final ConstPool a2, final int a3, final TypedBlock a4) {
        a1.write16bit(a3);
        this.writeVerifyTypeInfo(a1, a2, a4.localsTypes, a4.numLocals);
        this.writeVerifyTypeInfo(a1, a2, a4.stackTypes, a4.stackTop);
    }
    
    private void writeVerifyTypeInfo(final StackMap.Writer v2, final ConstPool v3, final TypeData[] v4, final int v5) {
        int v6 = 0;
        for (int a2 = 0; a2 < v5; ++a2) {
            final TypeData a3 = v4[a2];
            if (a3 != null && a3.is2WordType()) {
                ++v6;
                ++a2;
            }
        }
        v2.write16bit(v5 - v6);
        for (int a4 = 0; a4 < v5; ++a4) {
            final TypeData a5 = v4[a4];
            v2.writeVerifyTypeInfo(a5.getTypeTag(), a5.getTypeData(v3));
            if (a5.is2WordType()) {
                ++a4;
            }
        }
    }
}
