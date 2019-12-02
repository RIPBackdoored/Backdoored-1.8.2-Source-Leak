package org.spongepowered.asm.lib.signature;

public class SignatureReader
{
    private final String signature;
    
    public SignatureReader(final String a1) {
        super();
        this.signature = a1;
    }
    
    public void accept(final SignatureVisitor v-2) {
        final String signature = this.signature;
        final int v0 = signature.length();
        int v2;
        if (signature.charAt(0) == '<') {
            v2 = 2;
            char v3;
            do {
                final int a1 = signature.indexOf(58, v2);
                v-2.visitFormalTypeParameter(signature.substring(v2 - 1, a1));
                v2 = a1 + 1;
                v3 = signature.charAt(v2);
                if (v3 == 'L' || v3 == '[' || v3 == 'T') {
                    v2 = parseType(signature, v2, v-2.visitClassBound());
                }
                while ((v3 = signature.charAt(v2++)) == ':') {
                    v2 = parseType(signature, v2, v-2.visitInterfaceBound());
                }
            } while (v3 != '>');
        }
        else {
            v2 = 0;
        }
        if (signature.charAt(v2) == '(') {
            ++v2;
            while (signature.charAt(v2) != ')') {
                v2 = parseType(signature, v2, v-2.visitParameterType());
            }
            for (v2 = parseType(signature, v2 + 1, v-2.visitReturnType()); v2 < v0; v2 = parseType(signature, v2 + 1, v-2.visitExceptionType())) {}
        }
        else {
            for (v2 = parseType(signature, v2, v-2.visitSuperclass()); v2 < v0; v2 = parseType(signature, v2, v-2.visitInterface())) {}
        }
    }
    
    public void acceptType(final SignatureVisitor a1) {
        parseType(this.signature, 0, a1);
    }
    
    private static int parseType(final String v1, int v2, final SignatureVisitor v3) {
        char v4;
        switch (v4 = v1.charAt(v2++)) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'V':
            case 'Z': {
                v3.visitBaseType(v4);
                return v2;
            }
            case '[': {
                return parseType(v1, v2, v3.visitArrayType());
            }
            case 'T': {
                final int a1 = v1.indexOf(59, v2);
                v3.visitTypeVariable(v1.substring(v2, a1));
                return a1 + 1;
            }
            default: {
                int v5 = v2;
                boolean v6 = false;
                boolean v7 = false;
            Block_3:
                while (true) {
                    switch (v4 = v1.charAt(v2++)) {
                        case '.':
                        case ';': {
                            if (!v6) {
                                final String a2 = v1.substring(v5, v2 - 1);
                                if (v7) {
                                    v3.visitInnerClassType(a2);
                                }
                                else {
                                    v3.visitClassType(a2);
                                }
                            }
                            if (v4 == ';') {
                                break Block_3;
                            }
                            v5 = v2;
                            v6 = false;
                            v7 = true;
                            continue;
                        }
                        case '<': {
                            final String a3 = v1.substring(v5, v2 - 1);
                            if (v7) {
                                v3.visitInnerClassType(a3);
                            }
                            else {
                                v3.visitClassType(a3);
                            }
                            v6 = true;
                        Label_0368:
                            while (true) {
                                switch (v4 = v1.charAt(v2)) {
                                    case '>': {
                                        break Label_0368;
                                    }
                                    case '*': {
                                        ++v2;
                                        v3.visitTypeArgument();
                                        continue;
                                    }
                                    case '+':
                                    case '-': {
                                        v2 = parseType(v1, v2 + 1, v3.visitTypeArgument(v4));
                                        continue;
                                    }
                                    default: {
                                        v2 = parseType(v1, v2, v3.visitTypeArgument('='));
                                        continue;
                                    }
                                }
                            }
                            continue;
                        }
                    }
                }
                v3.visitEnd();
                return v2;
            }
        }
    }
}
