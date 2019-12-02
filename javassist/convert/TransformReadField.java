package javassist.convert;

import javassist.*;
import javassist.bytecode.*;

public class TransformReadField extends Transformer
{
    protected String fieldname;
    protected CtClass fieldClass;
    protected boolean isPrivate;
    protected String methodClassname;
    protected String methodName;
    
    public TransformReadField(final Transformer a1, final CtField a2, final String a3, final String a4) {
        super(a1);
        this.fieldClass = a2.getDeclaringClass();
        this.fieldname = a2.getName();
        this.methodClassname = a3;
        this.methodName = a4;
        this.isPrivate = Modifier.isPrivate(a2.getModifiers());
    }
    
    static String isField(final ClassPool a2, final ConstPool a3, final CtClass a4, final String a5, final boolean a6, final int v1) {
        if (!a3.getFieldrefName(v1).equals(a5)) {
            return null;
        }
        try {
            final CtClass a7 = a2.get(a3.getFieldrefClassName(v1));
            if (a7 == a4 || (!a6 && isFieldInSuper(a7, a4, a5))) {
                return a3.getFieldrefType(v1);
            }
        }
        catch (NotFoundException ex) {}
        return null;
    }
    
    static boolean isFieldInSuper(final CtClass a2, final CtClass a3, final String v1) {
        if (!a2.subclassOf(a3)) {
            return false;
        }
        try {
            final CtField a4 = a2.getField(v1);
            return a4.getDeclaringClass() == a3;
        }
        catch (NotFoundException ex) {
            return false;
        }
    }
    
    @Override
    public int transform(final CtClass v-5, int v-4, final CodeIterator v-3, final ConstPool v-2) throws BadBytecode {
        final int byte1 = v-3.byteAt(v-4);
        if (byte1 == 180 || byte1 == 178) {
            final int a4 = v-3.u16bitAt(v-4 + 1);
            final String v1 = isField(v-5.getClassPool(), v-2, this.fieldClass, this.fieldname, this.isPrivate, a4);
            if (v1 != null) {
                if (byte1 == 178) {
                    v-3.move(v-4);
                    v-4 = v-3.insertGap(1);
                    v-3.writeByte(1, v-4);
                    v-4 = v-3.next();
                }
                final String a5 = "(Ljava/lang/Object;)" + v1;
                final int a6 = v-2.addClassInfo(this.methodClassname);
                final int a7 = v-2.addMethodrefInfo(a6, this.methodName, a5);
                v-3.writeByte(184, v-4);
                v-3.write16bit(a7, v-4 + 1);
                return v-4;
            }
        }
        return v-4;
    }
}
