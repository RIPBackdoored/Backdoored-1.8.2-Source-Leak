package javassist.bytecode;

import java.io.*;
import javassist.*;
import java.util.*;

public class ClassFilePrinter
{
    public ClassFilePrinter() {
        super();
    }
    
    public static void print(final ClassFile a1) {
        print(a1, new PrintWriter(System.out, true));
    }
    
    public static void print(final ClassFile v-7, final PrintWriter v-6) {
        final int modifier = AccessFlag.toModifier(v-7.getAccessFlags() & 0xFFFFFFDF);
        v-6.println("major: " + v-7.major + ", minor: " + v-7.minor + " modifiers: " + Integer.toHexString(v-7.getAccessFlags()));
        v-6.println(Modifier.toString(modifier) + " class " + v-7.getName() + " extends " + v-7.getSuperclass());
        final String[] interfaces = v-7.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            v-6.print("    implements ");
            v-6.print(interfaces[0]);
            for (int a1 = 1; a1 < interfaces.length; ++a1) {
                v-6.print(", " + interfaces[a1]);
            }
            v-6.println();
        }
        v-6.println();
        List list = v-7.getFields();
        for (int n = list.size(), i = 0; i < n; ++i) {
            final FieldInfo a2 = list.get(i);
            final int v1 = a2.getAccessFlags();
            v-6.println(Modifier.toString(AccessFlag.toModifier(v1)) + " " + a2.getName() + "\t" + a2.getDescriptor());
            printAttributes(a2.getAttributes(), v-6, 'f');
        }
        v-6.println();
        list = v-7.getMethods();
        for (int n = list.size(), i = 0; i < n; ++i) {
            final MethodInfo v2 = list.get(i);
            final int v1 = v2.getAccessFlags();
            v-6.println(Modifier.toString(AccessFlag.toModifier(v1)) + " " + v2.getName() + "\t" + v2.getDescriptor());
            printAttributes(v2.getAttributes(), v-6, 'm');
            v-6.println();
        }
        v-6.println();
        printAttributes(v-7.getAttributes(), v-6, 'c');
    }
    
    static void printAttributes(final List v-7, final PrintWriter v-6, final char v-5) {
        if (v-7 == null) {
            return;
        }
        for (int size = v-7.size(), i = 0; i < size; ++i) {
            final AttributeInfo attributeInfo = v-7.get(i);
            if (attributeInfo instanceof CodeAttribute) {
                final CodeAttribute a1 = (CodeAttribute)attributeInfo;
                v-6.println("attribute: " + attributeInfo.getName() + ": " + attributeInfo.getClass().getName());
                v-6.println("max stack " + a1.getMaxStack() + ", max locals " + a1.getMaxLocals() + ", " + a1.getExceptionTable().size() + " catch blocks");
                v-6.println("<code attribute begin>");
                printAttributes(a1.getAttributes(), v-6, v-5);
                v-6.println("<code attribute end>");
            }
            else if (attributeInfo instanceof AnnotationsAttribute) {
                v-6.println("annnotation: " + attributeInfo.toString());
            }
            else if (attributeInfo instanceof ParameterAnnotationsAttribute) {
                v-6.println("parameter annnotations: " + attributeInfo.toString());
            }
            else if (attributeInfo instanceof StackMapTable) {
                v-6.println("<stack map table begin>");
                StackMapTable.Printer.print((StackMapTable)attributeInfo, v-6);
                v-6.println("<stack map table end>");
            }
            else if (attributeInfo instanceof StackMap) {
                v-6.println("<stack map begin>");
                ((StackMap)attributeInfo).print(v-6);
                v-6.println("<stack map end>");
            }
            else if (attributeInfo instanceof SignatureAttribute) {
                final SignatureAttribute signatureAttribute = (SignatureAttribute)attributeInfo;
                final String v0 = signatureAttribute.getSignature();
                v-6.println("signature: " + v0);
                try {
                    String v2 = null;
                    if (v-5 == 'c') {
                        final String a2 = SignatureAttribute.toClassSignature(v0).toString();
                    }
                    else if (v-5 == 'm') {
                        final String a3 = SignatureAttribute.toMethodSignature(v0).toString();
                    }
                    else {
                        v2 = SignatureAttribute.toFieldSignature(v0).toString();
                    }
                    v-6.println("           " + v2);
                }
                catch (BadBytecode v3) {
                    v-6.println("           syntax error");
                }
            }
            else {
                v-6.println("attribute: " + attributeInfo.getName() + " (" + attributeInfo.get().length + " byte): " + attributeInfo.getClass().getName());
            }
        }
    }
}
