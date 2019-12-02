package javassist.bytecode;

import javassist.*;

static class PrettyPrinter
{
    PrettyPrinter() {
        super();
    }
    
    static String toString(final String v1) {
        final StringBuffer v2 = new StringBuffer();
        if (v1.charAt(0) == '(') {
            int a1 = 1;
            v2.append('(');
            while (v1.charAt(a1) != ')') {
                if (a1 > 1) {
                    v2.append(',');
                }
                a1 = readType(v2, a1, v1);
            }
            v2.append(')');
        }
        else {
            readType(v2, 0, v1);
        }
        return v2.toString();
    }
    
    static int readType(final StringBuffer a2, int a3, final String v1) {
        char v2 = v1.charAt(a3);
        int v3 = 0;
        while (v2 == '[') {
            ++v3;
            v2 = v1.charAt(++a3);
        }
        if (v2 == 'L') {
            while (true) {
                v2 = v1.charAt(++a3);
                if (v2 == ';') {
                    break;
                }
                if (v2 == '/') {
                    v2 = '.';
                }
                a2.append(v2);
            }
        }
        else {
            final CtClass a4 = Descriptor.toPrimitiveClass(v2);
            a2.append(a4.getName());
        }
        while (v3-- > 0) {
            a2.append("[]");
        }
        return a3 + 1;
    }
}
