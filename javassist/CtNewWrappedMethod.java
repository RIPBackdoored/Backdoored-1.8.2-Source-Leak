package javassist;

import java.util.*;
import javassist.bytecode.*;
import javassist.compiler.*;

class CtNewWrappedMethod
{
    private static final String addedWrappedMethod = "_added_m$";
    
    CtNewWrappedMethod() {
        super();
    }
    
    public static CtMethod wrapped(final CtClass a2, final String a3, final CtClass[] a4, final CtClass[] a5, final CtMethod a6, final CtMethod.ConstParameter a7, final CtClass v1) throws CannotCompileException {
        final CtMethod v2 = new CtMethod(a2, a3, a4, v1);
        v2.setModifiers(a6.getModifiers());
        try {
            v2.setExceptionTypes(a5);
        }
        catch (NotFoundException a8) {
            throw new CannotCompileException(a8);
        }
        final Bytecode v3 = makeBody(v1, v1.getClassFile2(), a6, a4, a2, a7);
        final MethodInfo v4 = v2.getMethodInfo2();
        v4.setCodeAttribute(v3.toCodeAttribute());
        return v2;
    }
    
    static Bytecode makeBody(final CtClass a1, final ClassFile a2, final CtMethod a3, final CtClass[] a4, final CtClass a5, final CtMethod.ConstParameter a6) throws CannotCompileException {
        final boolean v1 = Modifier.isStatic(a3.getModifiers());
        final Bytecode v2 = new Bytecode(a2.getConstPool(), 0, 0);
        final int v3 = makeBody0(a1, a2, a3, v1, a4, a5, a6, v2);
        v2.setMaxStack(v3);
        v2.setMaxLocals(v1, a4, 0);
        return v2;
    }
    
    protected static int makeBody0(final CtClass a5, final ClassFile a6, final CtMethod a7, final boolean a8, final CtClass[] v1, final CtClass v2, final CtMethod.ConstParameter v3, final Bytecode v4) throws CannotCompileException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: instanceof      Ljavassist/CtClassType;
        //     4: ifne            37
        //     7: new             Ljavassist/CannotCompileException;
        //    10: dup            
        //    11: new             Ljava/lang/StringBuilder;
        //    14: dup            
        //    15: invokespecial   java/lang/StringBuilder.<init>:()V
        //    18: ldc             "bad declaring class"
        //    20: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    23: aload_0         /* a5 */
        //    24: invokevirtual   javassist/CtClass.getName:()Ljava/lang/String;
        //    27: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    30: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    33: invokespecial   javassist/CannotCompileException.<init>:(Ljava/lang/String;)V
        //    36: athrow         
        //    37: iload_3         /* a8 */
        //    38: ifne            47
        //    41: aload           v4
        //    43: iconst_0       
        //    44: invokevirtual   javassist/bytecode/Bytecode.addAload:(I)V
        //    47: aload           v4
        //    49: aload           v1
        //    51: iload_3         /* a8 */
        //    52: ifeq            59
        //    55: iconst_0       
        //    56: goto            60
        //    59: iconst_1       
        //    60: invokestatic    javassist/CtNewWrappedMethod.compileParameterList:(Ljavassist/bytecode/Bytecode;[Ljavassist/CtClass;I)I
        //    63: istore          v5
        //    65: aload           v3
        //    67: ifnonnull       81
        //    70: iconst_0       
        //    71: istore          a1
        //    73: invokestatic    javassist/CtMethod$ConstParameter.defaultDescriptor:()Ljava/lang/String;
        //    76: astore          a2
        //    78: goto            97
        //    81: aload           v3
        //    83: aload           v4
        //    85: invokevirtual   javassist/CtMethod$ConstParameter.compile:(Ljavassist/bytecode/Bytecode;)I
        //    88: istore          v6
        //    90: aload           v3
        //    92: invokevirtual   javassist/CtMethod$ConstParameter.descriptor:()Ljava/lang/String;
        //    95: astore          v7
        //    97: aload_2         /* a7 */
        //    98: aload           v7
        //   100: invokestatic    javassist/CtNewWrappedMethod.checkSignature:(Ljavassist/CtMethod;Ljava/lang/String;)V
        //   103: aload_0         /* a5 */
        //   104: checkcast       Ljavassist/CtClassType;
        //   107: aload_1         /* a6 */
        //   108: aload_2         /* a7 */
        //   109: invokestatic    javassist/CtNewWrappedMethod.addBodyMethod:(Ljavassist/CtClassType;Ljavassist/bytecode/ClassFile;Ljavassist/CtMethod;)Ljava/lang/String;
        //   112: astore          a3
        //   114: goto            129
        //   117: astore          a4
        //   119: new             Ljavassist/CannotCompileException;
        //   122: dup            
        //   123: aload           a4
        //   125: invokespecial   javassist/CannotCompileException.<init>:(Ljava/lang/Throwable;)V
        //   128: athrow         
        //   129: iload_3         /* a8 */
        //   130: ifeq            148
        //   133: aload           v4
        //   135: getstatic       javassist/bytecode/Bytecode.THIS:Ljavassist/CtClass;
        //   138: aload           v8
        //   140: aload           v7
        //   142: invokevirtual   javassist/bytecode/Bytecode.addInvokestatic:(Ljavassist/CtClass;Ljava/lang/String;Ljava/lang/String;)V
        //   145: goto            160
        //   148: aload           v4
        //   150: getstatic       javassist/bytecode/Bytecode.THIS:Ljavassist/CtClass;
        //   153: aload           v8
        //   155: aload           v7
        //   157: invokevirtual   javassist/bytecode/Bytecode.addInvokespecial:(Ljavassist/CtClass;Ljava/lang/String;Ljava/lang/String;)V
        //   160: aload           v4
        //   162: aload           v2
        //   164: invokestatic    javassist/CtNewWrappedMethod.compileReturn:(Ljavassist/bytecode/Bytecode;Ljavassist/CtClass;)V
        //   167: iload           v5
        //   169: iload           v6
        //   171: iconst_2       
        //   172: iadd           
        //   173: if_icmpge       182
        //   176: iload           v6
        //   178: iconst_2       
        //   179: iadd           
        //   180: istore          v5
        //   182: iload           v5
        //   184: ireturn        
        //    Exceptions:
        //  throws javassist.CannotCompileException
        //    StackMapTable: 00 0B 25 09 FF 00 0B 00 08 07 00 30 07 00 68 07 00 09 01 07 00 34 07 00 30 07 00 07 07 00 45 00 02 07 00 45 07 00 34 FF 00 00 00 08 07 00 30 07 00 68 07 00 09 01 07 00 34 07 00 30 07 00 07 07 00 45 00 03 07 00 45 07 00 34 01 FC 00 14 01 FD 00 0F 01 07 00 32 53 07 00 7F FC 00 0B 07 00 32 12 0B 15
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  103    114    117    129    Ljavassist/bytecode/BadBytecode;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static void checkSignature(final CtMethod a1, final String a2) throws CannotCompileException {
        if (!a2.equals(a1.getMethodInfo2().getDescriptor())) {
            throw new CannotCompileException("wrapped method with a bad signature: " + a1.getDeclaringClass().getName() + '.' + a1.getName());
        }
    }
    
    private static String addBodyMethod(final CtClassType v-7, final ClassFile v-6, final CtMethod v-5) throws BadBytecode, CannotCompileException {
        final Hashtable hiddenMethods = v-7.getHiddenMethods();
        String string = hiddenMethods.get(v-5);
        if (string == null) {
            do {
                string = "_added_m$" + v-7.getUniqueNumber();
            } while (v-6.getMethod(string) != null);
            final ClassMap a1 = new ClassMap();
            a1.put(v-5.getDeclaringClass().getName(), v-7.getName());
            final MethodInfo a2 = new MethodInfo(v-6.getConstPool(), string, v-5.getMethodInfo2(), a1);
            final int a3 = a2.getAccessFlags();
            a2.setAccessFlags(AccessFlag.setPrivate(a3));
            a2.addAttribute(new SyntheticAttribute(v-6.getConstPool()));
            v-6.addMethod(a2);
            hiddenMethods.put(v-5, string);
            final CtMember.Cache v1 = v-7.hasMemberCache();
            if (v1 != null) {
                v1.addMethod(new CtMethod(a2, v-7));
            }
        }
        return string;
    }
    
    static int compileParameterList(final Bytecode a1, final CtClass[] a2, final int a3) {
        return JvstCodeGen.compileParameterList(a1, a2, a3);
    }
    
    private static void compileReturn(final Bytecode v1, final CtClass v2) {
        if (v2.isPrimitive()) {
            final CtPrimitiveType a2 = (CtPrimitiveType)v2;
            if (a2 != CtClass.voidType) {
                final String a3 = a2.getWrapperName();
                v1.addCheckcast(a3);
                v1.addInvokevirtual(a3, a2.getGetMethodName(), a2.getGetMethodDescriptor());
            }
            v1.addOpcode(a2.getReturnOp());
        }
        else {
            v1.addCheckcast(v2);
            v1.addOpcode(176);
        }
    }
}
