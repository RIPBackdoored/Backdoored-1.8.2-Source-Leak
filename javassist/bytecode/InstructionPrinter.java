package javassist.bytecode;

import java.io.*;
import javassist.*;

public class InstructionPrinter implements Opcode
{
    private static final String[] opcodes;
    private final PrintStream stream;
    
    public InstructionPrinter(final PrintStream a1) {
        super();
        this.stream = a1;
    }
    
    public static void print(final CtMethod a1, final PrintStream a2) {
        new InstructionPrinter(a2).print(a1);
    }
    
    public void print(final CtMethod v-5) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   javassist/CtMethod.getMethodInfo2:()Ljavassist/bytecode/MethodInfo;
        //     4: astore_2        /* v-4 */
        //     5: aload_2         /* v-4 */
        //     6: invokevirtual   javassist/bytecode/MethodInfo.getConstPool:()Ljavassist/bytecode/ConstPool;
        //     9: astore_3        /* v-3 */
        //    10: aload_2         /* v-4 */
        //    11: invokevirtual   javassist/bytecode/MethodInfo.getCodeAttribute:()Ljavassist/bytecode/CodeAttribute;
        //    14: astore          v-2
        //    16: aload           v-2
        //    18: ifnonnull       22
        //    21: return         
        //    22: aload           v-2
        //    24: invokevirtual   javassist/bytecode/CodeAttribute.iterator:()Ljavassist/bytecode/CodeIterator;
        //    27: astore          v-1
        //    29: aload           v-1
        //    31: invokevirtual   javassist/bytecode/CodeIterator.hasNext:()Z
        //    34: ifeq            100
        //    37: aload           v-1
        //    39: invokevirtual   javassist/bytecode/CodeIterator.next:()I
        //    42: istore          a1
        //    44: goto            59
        //    47: astore          v1
        //    49: new             Ljava/lang/RuntimeException;
        //    52: dup            
        //    53: aload           v1
        //    55: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/Throwable;)V
        //    58: athrow         
        //    59: aload_0         /* v-6 */
        //    60: getfield        javassist/bytecode/InstructionPrinter.stream:Ljava/io/PrintStream;
        //    63: new             Ljava/lang/StringBuilder;
        //    66: dup            
        //    67: invokespecial   java/lang/StringBuilder.<init>:()V
        //    70: iload           v0
        //    72: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //    75: ldc             ": "
        //    77: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    80: aload           v-1
        //    82: iload           v0
        //    84: aload_3         /* v-3 */
        //    85: invokestatic    javassist/bytecode/InstructionPrinter.instructionString:(Ljavassist/bytecode/CodeIterator;ILjavassist/bytecode/ConstPool;)Ljava/lang/String;
        //    88: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    91: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    94: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //    97: goto            29
        //   100: return         
        //    StackMapTable: 00 05 FE 00 16 07 00 28 07 00 32 07 00 34 FC 00 06 07 00 3A 51 07 00 20 FC 00 0B 01 FA 00 28
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  37     44     47     59     Ljavassist/bytecode/BadBytecode;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static String instructionString(final CodeIterator a1, final int a2, final ConstPool a3) {
        final int v1 = a1.byteAt(a2);
        if (v1 > InstructionPrinter.opcodes.length || v1 < 0) {
            throw new IllegalArgumentException("Invalid opcode, opcode: " + v1 + " pos: " + a2);
        }
        final String v2 = InstructionPrinter.opcodes[v1];
        switch (v1) {
            case 16: {
                return v2 + " " + a1.byteAt(a2 + 1);
            }
            case 17: {
                return v2 + " " + a1.s16bitAt(a2 + 1);
            }
            case 18: {
                return v2 + " " + ldc(a3, a1.byteAt(a2 + 1));
            }
            case 19:
            case 20: {
                return v2 + " " + ldc(a3, a1.u16bitAt(a2 + 1));
            }
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58: {
                return v2 + " " + a1.byteAt(a2 + 1);
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 198:
            case 199: {
                return v2 + " " + (a1.s16bitAt(a2 + 1) + a2);
            }
            case 132: {
                return v2 + " " + a1.byteAt(a2 + 1) + ", " + a1.signedByteAt(a2 + 2);
            }
            case 167:
            case 168: {
                return v2 + " " + (a1.s16bitAt(a2 + 1) + a2);
            }
            case 169: {
                return v2 + " " + a1.byteAt(a2 + 1);
            }
            case 170: {
                return tableSwitch(a1, a2);
            }
            case 171: {
                return lookupSwitch(a1, a2);
            }
            case 178:
            case 179:
            case 180:
            case 181: {
                return v2 + " " + fieldInfo(a3, a1.u16bitAt(a2 + 1));
            }
            case 182:
            case 183:
            case 184: {
                return v2 + " " + methodInfo(a3, a1.u16bitAt(a2 + 1));
            }
            case 185: {
                return v2 + " " + interfaceMethodInfo(a3, a1.u16bitAt(a2 + 1));
            }
            case 186: {
                return v2 + " " + a1.u16bitAt(a2 + 1);
            }
            case 187: {
                return v2 + " " + classInfo(a3, a1.u16bitAt(a2 + 1));
            }
            case 188: {
                return v2 + " " + arrayInfo(a1.byteAt(a2 + 1));
            }
            case 189:
            case 192: {
                return v2 + " " + classInfo(a3, a1.u16bitAt(a2 + 1));
            }
            case 196: {
                return wide(a1, a2);
            }
            case 197: {
                return v2 + " " + classInfo(a3, a1.u16bitAt(a2 + 1));
            }
            case 200:
            case 201: {
                return v2 + " " + (a1.s32bitAt(a2 + 1) + a2);
            }
            default: {
                return v2;
            }
        }
    }
    
    private static String wide(final CodeIterator a1, final int a2) {
        final int v1 = a1.byteAt(a2 + 1);
        final int v2 = a1.u16bitAt(a2 + 2);
        switch (v1) {
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 132:
            case 169: {
                return InstructionPrinter.opcodes[v1] + " " + v2;
            }
            default: {
                throw new RuntimeException("Invalid WIDE operand");
            }
        }
    }
    
    private static String arrayInfo(final int a1) {
        switch (a1) {
            case 4: {
                return "boolean";
            }
            case 5: {
                return "char";
            }
            case 8: {
                return "byte";
            }
            case 9: {
                return "short";
            }
            case 10: {
                return "int";
            }
            case 11: {
                return "long";
            }
            case 6: {
                return "float";
            }
            case 7: {
                return "double";
            }
            default: {
                throw new RuntimeException("Invalid array type");
            }
        }
    }
    
    private static String classInfo(final ConstPool a1, final int a2) {
        return "#" + a2 + " = Class " + a1.getClassInfo(a2);
    }
    
    private static String interfaceMethodInfo(final ConstPool a1, final int a2) {
        return "#" + a2 + " = Method " + a1.getInterfaceMethodrefClassName(a2) + "." + a1.getInterfaceMethodrefName(a2) + "(" + a1.getInterfaceMethodrefType(a2) + ")";
    }
    
    private static String methodInfo(final ConstPool a1, final int a2) {
        return "#" + a2 + " = Method " + a1.getMethodrefClassName(a2) + "." + a1.getMethodrefName(a2) + "(" + a1.getMethodrefType(a2) + ")";
    }
    
    private static String fieldInfo(final ConstPool a1, final int a2) {
        return "#" + a2 + " = Field " + a1.getFieldrefClassName(a2) + "." + a1.getFieldrefName(a2) + "(" + a1.getFieldrefType(a2) + ")";
    }
    
    private static String lookupSwitch(final CodeIterator v1, final int v2) {
        final StringBuffer v3 = new StringBuffer("lookupswitch {\n");
        int v4 = (v2 & 0xFFFFFFFC) + 4;
        v3.append("\t\tdefault: ").append(v2 + v1.s32bitAt(v4)).append("\n");
        v4 += 4;
        final int v5 = v1.s32bitAt(v4);
        final int n = v5 * 8;
        v4 += 4;
        for (int v6 = n + v4; v4 < v6; v4 += 8) {
            final int a1 = v1.s32bitAt(v4);
            final int a2 = v1.s32bitAt(v4 + 4) + v2;
            v3.append("\t\t").append(a1).append(": ").append(a2).append("\n");
        }
        v3.setCharAt(v3.length() - 1, '}');
        return v3.toString();
    }
    
    private static String tableSwitch(final CodeIterator v1, final int v2) {
        final StringBuffer v3 = new StringBuffer("tableswitch {\n");
        int v4 = (v2 & 0xFFFFFFFC) + 4;
        v3.append("\t\tdefault: ").append(v2 + v1.s32bitAt(v4)).append("\n");
        v4 += 4;
        final int v5 = v1.s32bitAt(v4);
        v4 += 4;
        final int v6 = v1.s32bitAt(v4);
        final int n = (v6 - v5 + 1) * 4;
        v4 += 4;
        for (int v7 = n + v4, a2 = v5; v4 < v7; v4 += 4, ++a2) {
            final int a3 = v1.s32bitAt(v4) + v2;
            v3.append("\t\t").append(a2).append(": ").append(a3).append("\n");
        }
        v3.setCharAt(v3.length() - 1, '}');
        return v3.toString();
    }
    
    private static String ldc(final ConstPool a1, final int a2) {
        final int v1 = a1.getTag(a2);
        switch (v1) {
            case 8: {
                return "#" + a2 + " = \"" + a1.getStringInfo(a2) + "\"";
            }
            case 3: {
                return "#" + a2 + " = int " + a1.getIntegerInfo(a2);
            }
            case 4: {
                return "#" + a2 + " = float " + a1.getFloatInfo(a2);
            }
            case 5: {
                return "#" + a2 + " = long " + a1.getLongInfo(a2);
            }
            case 6: {
                return "#" + a2 + " = int " + a1.getDoubleInfo(a2);
            }
            case 7: {
                return classInfo(a1, a2);
            }
            default: {
                throw new RuntimeException("bad LDC: " + v1);
            }
        }
    }
    
    static {
        opcodes = Mnemonic.OPCODE;
    }
}
