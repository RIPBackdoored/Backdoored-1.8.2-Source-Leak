package javassist.convert;

import javassist.bytecode.*;
import javassist.*;

public final class TransformNewClass extends Transformer
{
    private int nested;
    private String classname;
    private String newClassName;
    private int newClassIndex;
    private int newMethodNTIndex;
    private int newMethodIndex;
    
    public TransformNewClass(final Transformer a1, final String a2, final String a3) {
        super(a1);
        this.classname = a2;
        this.newClassName = a3;
    }
    
    @Override
    public void initialize(final ConstPool a1, final CodeAttribute a2) {
        this.nested = 0;
        final int newClassIndex = 0;
        this.newMethodIndex = newClassIndex;
        this.newMethodNTIndex = newClassIndex;
        this.newClassIndex = newClassIndex;
    }
    
    @Override
    public int transform(final CtClass v2, final int v3, final CodeIterator v4, final ConstPool v5) throws CannotCompileException {
        final int v6 = v4.byteAt(v3);
        if (v6 == 187) {
            final int a1 = v4.u16bitAt(v3 + 1);
            if (v5.getClassInfo(a1).equals(this.classname)) {
                if (v4.byteAt(v3 + 3) != 89) {
                    throw new CannotCompileException("NEW followed by no DUP was found");
                }
                if (this.newClassIndex == 0) {
                    this.newClassIndex = v5.addClassInfo(this.newClassName);
                }
                v4.write16bit(this.newClassIndex, v3 + 1);
                ++this.nested;
            }
        }
        else if (v6 == 183) {
            final int a2 = v4.u16bitAt(v3 + 1);
            final int a3 = v5.isConstructor(this.classname, a2);
            if (a3 != 0 && this.nested > 0) {
                final int a4 = v5.getMethodrefNameAndType(a2);
                if (this.newMethodNTIndex != a4) {
                    this.newMethodNTIndex = a4;
                    this.newMethodIndex = v5.addMethodrefInfo(this.newClassIndex, a4);
                }
                v4.write16bit(this.newMethodIndex, v3 + 1);
                --this.nested;
            }
        }
        return v3;
    }
}
