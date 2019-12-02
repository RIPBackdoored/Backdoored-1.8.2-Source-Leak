package javassist.convert;

import javassist.*;
import javassist.bytecode.*;

public final class TransformFieldAccess extends Transformer
{
    private String newClassname;
    private String newFieldname;
    private String fieldname;
    private CtClass fieldClass;
    private boolean isPrivate;
    private int newIndex;
    private ConstPool constPool;
    
    public TransformFieldAccess(final Transformer a1, final CtField a2, final String a3, final String a4) {
        super(a1);
        this.fieldClass = a2.getDeclaringClass();
        this.fieldname = a2.getName();
        this.isPrivate = Modifier.isPrivate(a2.getModifiers());
        this.newClassname = a3;
        this.newFieldname = a4;
        this.constPool = null;
    }
    
    @Override
    public void initialize(final ConstPool a1, final CodeAttribute a2) {
        if (this.constPool != a1) {
            this.newIndex = 0;
        }
    }
    
    @Override
    public int transform(final CtClass v1, final int v2, final CodeIterator v3, final ConstPool v4) {
        final int v5 = v3.byteAt(v2);
        if (v5 == 180 || v5 == 178 || v5 == 181 || v5 == 179) {
            final int a2 = v3.u16bitAt(v2 + 1);
            final String a3 = TransformReadField.isField(v1.getClassPool(), v4, this.fieldClass, this.fieldname, this.isPrivate, a2);
            if (a3 != null) {
                if (this.newIndex == 0) {
                    final int a4 = v4.addNameAndTypeInfo(this.newFieldname, a3);
                    this.newIndex = v4.addFieldrefInfo(v4.addClassInfo(this.newClassname), a4);
                    this.constPool = v4;
                }
                v3.write16bit(this.newIndex, v2 + 1);
            }
        }
        return v2;
    }
}
