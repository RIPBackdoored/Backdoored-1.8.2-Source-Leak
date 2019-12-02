package javassist;

import javassist.compiler.*;
import javassist.bytecode.*;

static class ArrayInitializer extends Initializer
{
    CtClass type;
    int size;
    
    ArrayInitializer(final CtClass a1, final int a2) {
        super();
        this.type = a1;
        this.size = a2;
    }
    
    private void addNewarray(final Bytecode a1) {
        if (this.type.isPrimitive()) {
            a1.addNewarray(((CtPrimitiveType)this.type).getArrayType(), this.size);
        }
        else {
            a1.addAnewarray(this.type, this.size);
        }
    }
    
    @Override
    int compile(final CtClass a1, final String a2, final Bytecode a3, final CtClass[] a4, final Javac a5) throws CannotCompileException {
        a3.addAload(0);
        this.addNewarray(a3);
        a3.addPutfield(Bytecode.THIS, a2, Descriptor.of(a1));
        return 2;
    }
    
    @Override
    int compileIfStatic(final CtClass a1, final String a2, final Bytecode a3, final Javac a4) throws CannotCompileException {
        this.addNewarray(a3);
        a3.addPutstatic(Bytecode.THIS, a2, Descriptor.of(a1));
        return 1;
    }
}
