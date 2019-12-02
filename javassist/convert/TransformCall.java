package javassist.convert;

import javassist.bytecode.*;
import javassist.*;

public class TransformCall extends Transformer
{
    protected String classname;
    protected String methodname;
    protected String methodDescriptor;
    protected String newClassname;
    protected String newMethodname;
    protected boolean newMethodIsPrivate;
    protected int newIndex;
    protected ConstPool constPool;
    
    public TransformCall(final Transformer a1, final CtMethod a2, final CtMethod a3) {
        this(a1, a2.getName(), a3);
        this.classname = a2.getDeclaringClass().getName();
    }
    
    public TransformCall(final Transformer a1, final String a2, final CtMethod a3) {
        super(a1);
        this.methodname = a2;
        this.methodDescriptor = a3.getMethodInfo2().getDescriptor();
        final String name = a3.getDeclaringClass().getName();
        this.newClassname = name;
        this.classname = name;
        this.newMethodname = a3.getName();
        this.constPool = null;
        this.newMethodIsPrivate = Modifier.isPrivate(a3.getModifiers());
    }
    
    @Override
    public void initialize(final ConstPool a1, final CodeAttribute a2) {
        if (this.constPool != a1) {
            this.newIndex = 0;
        }
    }
    
    @Override
    public int transform(final CtClass v1, int v2, final CodeIterator v3, final ConstPool v4) throws BadBytecode {
        final int v5 = v3.byteAt(v2);
        if (v5 == 185 || v5 == 183 || v5 == 184 || v5 == 182) {
            final int a2 = v3.u16bitAt(v2 + 1);
            final String a3 = v4.eqMember(this.methodname, this.methodDescriptor, a2);
            if (a3 != null && this.matchClass(a3, v1.getClassPool())) {
                final int a4 = v4.getMemberNameAndType(a2);
                v2 = this.match(v5, v2, v3, v4.getNameAndTypeDescriptor(a4), v4);
            }
        }
        return v2;
    }
    
    private boolean matchClass(final String v-1, final ClassPool v0) {
        if (this.classname.equals(v-1)) {
            return true;
        }
        try {
            final CtClass v = v0.get(v-1);
            final CtClass v2 = v0.get(this.classname);
            if (v.subtypeOf(v2)) {
                try {
                    final CtMethod a1 = v.getMethod(this.methodname, this.methodDescriptor);
                    return a1.getDeclaringClass().getName().equals(this.classname);
                }
                catch (NotFoundException a2) {
                    return true;
                }
            }
        }
        catch (NotFoundException v3) {
            return false;
        }
        return false;
    }
    
    protected int match(final int a4, final int a5, final CodeIterator v1, final int v2, final ConstPool v3) throws BadBytecode {
        if (this.newIndex == 0) {
            final int a6 = v3.addNameAndTypeInfo(v3.addUtf8Info(this.newMethodname), v2);
            final int a7 = v3.addClassInfo(this.newClassname);
            if (a4 == 185) {
                this.newIndex = v3.addInterfaceMethodrefInfo(a7, a6);
            }
            else {
                if (this.newMethodIsPrivate && a4 == 182) {
                    v1.writeByte(183, a5);
                }
                this.newIndex = v3.addMethodrefInfo(a7, a6);
            }
            this.constPool = v3;
        }
        v1.write16bit(this.newIndex, a5 + 1);
        return a5;
    }
}
