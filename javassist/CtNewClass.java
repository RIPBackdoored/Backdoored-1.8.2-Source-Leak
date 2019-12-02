package javassist;

import javassist.bytecode.*;
import java.io.*;

class CtNewClass extends CtClassType
{
    protected boolean hasConstructor;
    
    CtNewClass(final String a3, final ClassPool a4, final boolean v1, final CtClass v2) {
        super(a3, a4);
        this.wasChanged = true;
        String v3 = null;
        if (v1 || v2 == null) {
            final String a5 = null;
        }
        else {
            v3 = v2.getName();
        }
        this.classfile = new ClassFile(v1, a3, v3);
        if (v1 && v2 != null) {
            this.classfile.setInterfaces(new String[] { v2.getName() });
        }
        this.setModifiers(Modifier.setPublic(this.getModifiers()));
        this.hasConstructor = v1;
    }
    
    @Override
    protected void extendToString(final StringBuffer a1) {
        if (this.hasConstructor) {
            a1.append("hasConstructor ");
        }
        super.extendToString(a1);
    }
    
    @Override
    public void addConstructor(final CtConstructor a1) throws CannotCompileException {
        this.hasConstructor = true;
        super.addConstructor(a1);
    }
    
    @Override
    public void toBytecode(final DataOutputStream v2) throws CannotCompileException, IOException {
        if (!this.hasConstructor) {
            try {
                this.inheritAllConstructors();
                this.hasConstructor = true;
            }
            catch (NotFoundException a1) {
                throw new CannotCompileException(a1);
            }
        }
        super.toBytecode(v2);
    }
    
    public void inheritAllConstructors() throws CannotCompileException, NotFoundException {
        final CtClass superclass = this.getSuperclass();
        final CtConstructor[] declaredConstructors = superclass.getDeclaredConstructors();
        int n = 0;
        for (int i = 0; i < declaredConstructors.length; ++i) {
            final CtConstructor ctConstructor = declaredConstructors[i];
            final int v0 = ctConstructor.getModifiers();
            if (this.isInheritable(v0, superclass)) {
                final CtConstructor v2 = CtNewConstructor.make(ctConstructor.getParameterTypes(), ctConstructor.getExceptionTypes(), this);
                v2.setModifiers(v0 & 0x7);
                this.addConstructor(v2);
                ++n;
            }
        }
        if (n < 1) {
            throw new CannotCompileException("no inheritable constructor in " + superclass.getName());
        }
    }
    
    private boolean isInheritable(final int v2, final CtClass v3) {
        if (Modifier.isPrivate(v2)) {
            return false;
        }
        if (!Modifier.isPackage(v2)) {
            return true;
        }
        final String a1 = this.getPackageName();
        final String a2 = v3.getPackageName();
        if (a1 == null) {
            return a2 == null;
        }
        return a1.equals(a2);
    }
}
