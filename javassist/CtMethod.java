package javassist;

import javassist.bytecode.*;

public final class CtMethod extends CtBehavior
{
    protected String cachedStringRep;
    
    CtMethod(final MethodInfo a1, final CtClass a2) {
        super(a2, a1);
        this.cachedStringRep = null;
    }
    
    public CtMethod(final CtClass a1, final String a2, final CtClass[] a3, final CtClass a4) {
        this(null, a4);
        final ConstPool v1 = a4.getClassFile2().getConstPool();
        final String v2 = Descriptor.ofMethod(a1, a3);
        this.methodInfo = new MethodInfo(v1, a2, v2);
        this.setModifiers(1025);
    }
    
    public CtMethod(final CtMethod a1, final CtClass a2, final ClassMap a3) throws CannotCompileException {
        this(null, a2);
        this.copy(a1, false, a3);
    }
    
    public static CtMethod make(final String a1, final CtClass a2) throws CannotCompileException {
        return CtNewMethod.make(a1, a2);
    }
    
    public static CtMethod make(final MethodInfo a1, final CtClass a2) throws CannotCompileException {
        if (a2.getClassFile2().getConstPool() != a1.getConstPool()) {
            throw new CannotCompileException("bad declaring class");
        }
        return new CtMethod(a1, a2);
    }
    
    @Override
    public int hashCode() {
        return this.getStringRep().hashCode();
    }
    
    @Override
    void nameReplaced() {
        this.cachedStringRep = null;
    }
    
    final String getStringRep() {
        if (this.cachedStringRep == null) {
            this.cachedStringRep = this.methodInfo.getName() + Descriptor.getParamDescriptor(this.methodInfo.getDescriptor());
        }
        return this.cachedStringRep;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 != null && a1 instanceof CtMethod && ((CtMethod)a1).getStringRep().equals(this.getStringRep());
    }
    
    @Override
    public String getLongName() {
        return this.getDeclaringClass().getName() + "." + this.getName() + Descriptor.toString(this.getSignature());
    }
    
    @Override
    public String getName() {
        return this.methodInfo.getName();
    }
    
    public void setName(final String a1) {
        this.declaringClass.checkModify();
        this.methodInfo.setName(a1);
    }
    
    public CtClass getReturnType() throws NotFoundException {
        return this.getReturnType0();
    }
    
    @Override
    public boolean isEmpty() {
        final CodeAttribute v1 = this.getMethodInfo2().getCodeAttribute();
        if (v1 == null) {
            return (this.getModifiers() & 0x400) != 0x0;
        }
        final CodeIterator v2 = v1.iterator();
        try {
            return v2.hasNext() && v2.byteAt(v2.next()) == 177 && !v2.hasNext();
        }
        catch (BadBytecode badBytecode) {
            return false;
        }
    }
    
    public void setBody(final CtMethod a1, final ClassMap a2) throws CannotCompileException {
        CtBehavior.setBody0(a1.declaringClass, a1.methodInfo, this.declaringClass, this.methodInfo, a2);
    }
    
    public void setWrappedBody(final CtMethod v-4, final ConstParameter v-3) throws CannotCompileException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        javassist/CtMethod.declaringClass:Ljavassist/CtClass;
        //     4: invokevirtual   javassist/CtClass.checkModify:()V
        //     7: aload_0         /* v-5 */
        //     8: invokevirtual   javassist/CtMethod.getDeclaringClass:()Ljavassist/CtClass;
        //    11: astore_3        /* v-2 */
        //    12: aload_0         /* v-5 */
        //    13: invokevirtual   javassist/CtMethod.getParameterTypes:()[Ljavassist/CtClass;
        //    16: astore          a1
        //    18: aload_0         /* v-5 */
        //    19: invokevirtual   javassist/CtMethod.getReturnType:()Ljavassist/CtClass;
        //    22: astore          a2
        //    24: goto            39
        //    27: astore          v1
        //    29: new             Ljavassist/CannotCompileException;
        //    32: dup            
        //    33: aload           v1
        //    35: invokespecial   javassist/CannotCompileException.<init>:(Ljavassist/NotFoundException;)V
        //    38: athrow         
        //    39: aload_3         /* v-2 */
        //    40: aload_3         /* v-2 */
        //    41: invokevirtual   javassist/CtClass.getClassFile2:()Ljavassist/bytecode/ClassFile;
        //    44: aload_1         /* v-4 */
        //    45: aload           v-1
        //    47: aload           v0
        //    49: aload_2         /* v-3 */
        //    50: invokestatic    javassist/CtNewWrappedMethod.makeBody:(Ljavassist/CtClass;Ljavassist/bytecode/ClassFile;Ljavassist/CtMethod;[Ljavassist/CtClass;Ljavassist/CtClass;Ljavassist/CtMethod$ConstParameter;)Ljavassist/bytecode/Bytecode;
        //    53: astore          v1
        //    55: aload           v1
        //    57: invokevirtual   javassist/bytecode/Bytecode.toCodeAttribute:()Ljavassist/bytecode/CodeAttribute;
        //    60: astore          v2
        //    62: aload_0         /* v-5 */
        //    63: getfield        javassist/CtMethod.methodInfo:Ljavassist/bytecode/MethodInfo;
        //    66: aload           v2
        //    68: invokevirtual   javassist/bytecode/MethodInfo.setCodeAttribute:(Ljavassist/bytecode/CodeAttribute;)V
        //    71: aload_0         /* v-5 */
        //    72: getfield        javassist/CtMethod.methodInfo:Ljavassist/bytecode/MethodInfo;
        //    75: aload_0         /* v-5 */
        //    76: getfield        javassist/CtMethod.methodInfo:Ljavassist/bytecode/MethodInfo;
        //    79: invokevirtual   javassist/bytecode/MethodInfo.getAccessFlags:()I
        //    82: sipush          -1025
        //    85: iand           
        //    86: invokevirtual   javassist/bytecode/MethodInfo.setAccessFlags:(I)V
        //    89: return         
        //    Exceptions:
        //  throws javassist.CannotCompileException
        //    StackMapTable: 00 02 FF 00 1B 00 04 07 00 02 07 00 02 07 00 10 07 00 25 00 01 07 00 9C FD 00 0B 07 00 D4 07 00 25
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                         
        //  -----  -----  -----  -----  -----------------------------
        //  12     24     27     39     Ljavassist/NotFoundException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static class ConstParameter
    {
        public static ConstParameter integer(final int a1) {
            return new IntConstParameter(a1);
        }
        
        public static ConstParameter integer(final long a1) {
            return new LongConstParameter(a1);
        }
        
        public static ConstParameter string(final String a1) {
            return new StringConstParameter(a1);
        }
        
        ConstParameter() {
            super();
        }
        
        int compile(final Bytecode a1) throws CannotCompileException {
            return 0;
        }
        
        String descriptor() {
            return defaultDescriptor();
        }
        
        static String defaultDescriptor() {
            return "([Ljava/lang/Object;)Ljava/lang/Object;";
        }
        
        String constDescriptor() {
            return defaultConstDescriptor();
        }
        
        static String defaultConstDescriptor() {
            return "([Ljava/lang/Object;)V";
        }
    }
    
    static class IntConstParameter extends ConstParameter
    {
        int param;
        
        IntConstParameter(final int a1) {
            super();
            this.param = a1;
        }
        
        @Override
        int compile(final Bytecode a1) throws CannotCompileException {
            a1.addIconst(this.param);
            return 1;
        }
        
        @Override
        String descriptor() {
            return "([Ljava/lang/Object;I)Ljava/lang/Object;";
        }
        
        @Override
        String constDescriptor() {
            return "([Ljava/lang/Object;I)V";
        }
    }
    
    static class LongConstParameter extends ConstParameter
    {
        long param;
        
        LongConstParameter(final long a1) {
            super();
            this.param = a1;
        }
        
        @Override
        int compile(final Bytecode a1) throws CannotCompileException {
            a1.addLconst(this.param);
            return 2;
        }
        
        @Override
        String descriptor() {
            return "([Ljava/lang/Object;J)Ljava/lang/Object;";
        }
        
        @Override
        String constDescriptor() {
            return "([Ljava/lang/Object;J)V";
        }
    }
    
    static class StringConstParameter extends ConstParameter
    {
        String param;
        
        StringConstParameter(final String a1) {
            super();
            this.param = a1;
        }
        
        @Override
        int compile(final Bytecode a1) throws CannotCompileException {
            a1.addLdc(this.param);
            return 1;
        }
        
        @Override
        String descriptor() {
            return "([Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;";
        }
        
        @Override
        String constDescriptor() {
            return "([Ljava/lang/Object;Ljava/lang/String;)V";
        }
    }
}
