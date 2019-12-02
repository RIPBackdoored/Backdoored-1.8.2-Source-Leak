package javassist.convert;

import javassist.*;
import javassist.bytecode.*;

public final class TransformNew extends Transformer
{
    private int nested;
    private String classname;
    private String trapClass;
    private String trapMethod;
    
    public TransformNew(final Transformer a1, final String a2, final String a3, final String a4) {
        super(a1);
        this.classname = a2;
        this.trapClass = a3;
        this.trapMethod = a4;
    }
    
    @Override
    public void initialize(final ConstPool a1, final CodeAttribute a2) {
        this.nested = 0;
    }
    
    @Override
    public int transform(final CtClass v-5, final int v-4, final CodeIterator v-3, final ConstPool v-2) throws CannotCompileException {
        final int v0 = v-3.byteAt(v-4);
        if (v0 == 187) {
            final int a3 = v-3.u16bitAt(v-4 + 1);
            if (v-2.getClassInfo(a3).equals(this.classname)) {
                if (v-3.byteAt(v-4 + 3) != 89) {
                    throw new CannotCompileException("NEW followed by no DUP was found");
                }
                v-3.writeByte(0, v-4);
                v-3.writeByte(0, v-4 + 1);
                v-3.writeByte(0, v-4 + 2);
                v-3.writeByte(0, v-4 + 3);
                ++this.nested;
                final StackMapTable a4 = (StackMapTable)v-3.get().getAttribute("StackMapTable");
                if (a4 != null) {
                    a4.removeNew(v-4);
                }
                final StackMap a5 = (StackMap)v-3.get().getAttribute("StackMap");
                if (a5 != null) {
                    a5.removeNew(v-4);
                }
            }
        }
        else if (v0 == 183) {
            final int u16bit = v-3.u16bitAt(v-4 + 1);
            final int v2 = v-2.isConstructor(this.classname, u16bit);
            if (v2 != 0 && this.nested > 0) {
                final int a6 = this.computeMethodref(v2, v-2);
                v-3.writeByte(184, v-4);
                v-3.write16bit(a6, v-4 + 1);
                --this.nested;
            }
        }
        return v-4;
    }
    
    private int computeMethodref(int a1, final ConstPool a2) {
        final int v1 = a2.addClassInfo(this.trapClass);
        final int v2 = a2.addUtf8Info(this.trapMethod);
        a1 = a2.addUtf8Info(Descriptor.changeReturnType(this.classname, a2.getUtf8Info(a1)));
        return a2.addMethodrefInfo(v1, a2.addNameAndTypeInfo(v2, a1));
    }
}
