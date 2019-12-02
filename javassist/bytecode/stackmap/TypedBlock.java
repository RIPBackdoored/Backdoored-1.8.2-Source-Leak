package javassist.bytecode.stackmap;

import javassist.bytecode.*;

public class TypedBlock extends BasicBlock
{
    public int stackTop;
    public int numLocals;
    public TypeData[] localsTypes;
    public TypeData[] stackTypes;
    
    public static TypedBlock[] makeBlocks(final MethodInfo a1, final CodeAttribute a2, final boolean a3) throws BadBytecode {
        final TypedBlock[] v1 = (TypedBlock[])new Maker().make(a1);
        if (a3 && v1.length < 2 && (v1.length == 0 || v1[0].incoming == 0)) {
            return null;
        }
        final ConstPool v2 = a1.getConstPool();
        final boolean v3 = (a1.getAccessFlags() & 0x8) != 0x0;
        v1[0].initFirstBlock(a2.getMaxStack(), a2.getMaxLocals(), v2.getClassName(), a1.getDescriptor(), v3, a1.isConstructor());
        return v1;
    }
    
    protected TypedBlock(final int a1) {
        super(a1);
        this.localsTypes = null;
    }
    
    @Override
    protected void toString2(final StringBuffer a1) {
        super.toString2(a1);
        a1.append(",\n stack={");
        this.printTypes(a1, this.stackTop, this.stackTypes);
        a1.append("}, locals={");
        this.printTypes(a1, this.numLocals, this.localsTypes);
        a1.append('}');
    }
    
    private void printTypes(final StringBuffer v1, final int v2, final TypeData[] v3) {
        if (v3 == null) {
            return;
        }
        for (int a2 = 0; a2 < v2; ++a2) {
            if (a2 > 0) {
                v1.append(", ");
            }
            final TypeData a3 = v3[a2];
            v1.append((a3 == null) ? "<>" : a3.toString());
        }
    }
    
    public boolean alreadySet() {
        return this.localsTypes != null;
    }
    
    public void setStackMap(final int a1, final TypeData[] a2, final int a3, final TypeData[] a4) throws BadBytecode {
        this.stackTop = a1;
        this.stackTypes = a2;
        this.numLocals = a3;
        this.localsTypes = a4;
    }
    
    public void resetNumLocals() {
        if (this.localsTypes != null) {
            int v1;
            for (v1 = this.localsTypes.length; v1 > 0 && this.localsTypes[v1 - 1].isBasicType() == TypeTag.TOP && (v1 <= 1 || !this.localsTypes[v1 - 2].is2WordType()); --v1) {}
            this.numLocals = v1;
        }
    }
    
    void initFirstBlock(final int a3, final int a4, final String a5, final String a6, final boolean v1, final boolean v2) throws BadBytecode {
        if (a6.charAt(0) != '(') {
            throw new BadBytecode("no method descriptor: " + a6);
        }
        this.stackTop = 0;
        this.stackTypes = TypeData.make(a3);
        final TypeData[] v3 = TypeData.make(a4);
        if (v2) {
            v3[0] = new TypeData.UninitThis(a5);
        }
        else if (!v1) {
            v3[0] = new TypeData.ClassName(a5);
        }
        int v4 = v1 ? -1 : 0;
        int v5 = 1;
        try {
            while ((v5 = descToTag(a6, v5, ++v4, v3)) > 0) {
                if (v3[v4].is2WordType()) {
                    v3[++v4] = TypeTag.TOP;
                }
            }
        }
        catch (StringIndexOutOfBoundsException a7) {
            throw new BadBytecode("bad method descriptor: " + a6);
        }
        this.numLocals = v4;
        this.localsTypes = v3;
    }
    
    private static int descToTag(final String a3, int a4, final int v1, final TypeData[] v2) throws BadBytecode {
        final int v3 = a4;
        int v4 = 0;
        char v5 = a3.charAt(a4);
        if (v5 == ')') {
            return 0;
        }
        while (v5 == '[') {
            ++v4;
            v5 = a3.charAt(++a4);
        }
        if (v5 == 'L') {
            int a5 = a3.indexOf(59, ++a4);
            if (v4 > 0) {
                v2[v1] = new TypeData.ClassName(a3.substring(v3, ++a5));
            }
            else {
                v2[v1] = new TypeData.ClassName(a3.substring(v3 + 1, ++a5 - 1).replace('/', '.'));
            }
            return a5;
        }
        if (v4 > 0) {
            v2[v1] = new TypeData.ClassName(a3.substring(v3, ++a4));
            return a4;
        }
        final TypeData a6 = toPrimitiveTag(v5);
        if (a6 == null) {
            throw new BadBytecode("bad method descriptor: " + a3);
        }
        v2[v1] = a6;
        return a4 + 1;
    }
    
    private static TypeData toPrimitiveTag(final char a1) {
        switch (a1) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z': {
                return TypeTag.INTEGER;
            }
            case 'J': {
                return TypeTag.LONG;
            }
            case 'F': {
                return TypeTag.FLOAT;
            }
            case 'D': {
                return TypeTag.DOUBLE;
            }
            default: {
                return null;
            }
        }
    }
    
    public static String getRetType(final String a1) {
        final int v1 = a1.indexOf(41);
        if (v1 < 0) {
            return "java.lang.Object";
        }
        final char v2 = a1.charAt(v1 + 1);
        if (v2 == '[') {
            return a1.substring(v1 + 1);
        }
        if (v2 == 'L') {
            return a1.substring(v1 + 2, a1.length() - 1).replace('/', '.');
        }
        return "java.lang.Object";
    }
    
    public static class Maker extends BasicBlock.Maker
    {
        public Maker() {
            super();
        }
        
        @Override
        protected BasicBlock makeBlock(final int a1) {
            return new TypedBlock(a1);
        }
        
        @Override
        protected BasicBlock[] makeArray(final int a1) {
            return new TypedBlock[a1];
        }
    }
}
