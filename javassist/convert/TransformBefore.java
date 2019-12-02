package javassist.convert;

import javassist.*;
import javassist.bytecode.*;

public class TransformBefore extends TransformCall
{
    protected CtClass[] parameterTypes;
    protected int locals;
    protected int maxLocals;
    protected byte[] saveCode;
    protected byte[] loadCode;
    
    public TransformBefore(final Transformer a1, final CtMethod a2, final CtMethod a3) throws NotFoundException {
        super(a1, a2, a3);
        this.methodDescriptor = a2.getMethodInfo2().getDescriptor();
        this.parameterTypes = a2.getParameterTypes();
        this.locals = 0;
        this.maxLocals = 0;
        final byte[] array = null;
        this.loadCode = array;
        this.saveCode = array;
    }
    
    @Override
    public void initialize(final ConstPool a1, final CodeAttribute a2) {
        super.initialize(a1, a2);
        this.locals = 0;
        this.maxLocals = a2.getMaxLocals();
        final byte[] array = null;
        this.loadCode = array;
        this.saveCode = array;
    }
    
    @Override
    protected int match(final int a5, final int v1, final CodeIterator v2, final int v3, final ConstPool v4) throws BadBytecode {
        if (this.newIndex == 0) {
            String a6 = Descriptor.ofParameters(this.parameterTypes) + 'V';
            a6 = Descriptor.insertParameter(this.classname, a6);
            final int a7 = v4.addNameAndTypeInfo(this.newMethodname, a6);
            final int a8 = v4.addClassInfo(this.newClassname);
            this.newIndex = v4.addMethodrefInfo(a8, a7);
            this.constPool = v4;
        }
        if (this.saveCode == null) {
            this.makeCode(this.parameterTypes, v4);
        }
        return this.match2(v1, v2);
    }
    
    protected int match2(final int a1, final CodeIterator a2) throws BadBytecode {
        a2.move(a1);
        a2.insert(this.saveCode);
        a2.insert(this.loadCode);
        final int v1 = a2.insertGap(3);
        a2.writeByte(184, v1);
        a2.write16bit(this.newIndex, v1 + 1);
        a2.insert(this.loadCode);
        return a2.next();
    }
    
    @Override
    public int extraLocals() {
        return this.locals;
    }
    
    protected void makeCode(final CtClass[] a1, final ConstPool a2) {
        final Bytecode v1 = new Bytecode(a2, 0, 0);
        final Bytecode v2 = new Bytecode(a2, 0, 0);
        final int v3 = this.maxLocals;
        final int v4 = (a1 == null) ? 0 : a1.length;
        v2.addAload(v3);
        this.makeCode2(v1, v2, 0, v4, a1, v3 + 1);
        v1.addAstore(v3);
        this.saveCode = v1.get();
        this.loadCode = v2.get();
    }
    
    private void makeCode2(final Bytecode a3, final Bytecode a4, final int a5, final int a6, final CtClass[] v1, final int v2) {
        if (a5 < a6) {
            final int a7 = a4.addLoad(v2, v1[a5]);
            this.makeCode2(a3, a4, a5 + 1, a6, v1, v2 + a7);
            a3.addStore(v2, v1[a5]);
        }
        else {
            this.locals = v2 - this.maxLocals;
        }
    }
}
