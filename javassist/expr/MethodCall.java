package javassist.expr;

import javassist.compiler.*;
import javassist.*;
import javassist.bytecode.*;

public class MethodCall extends Expr
{
    protected MethodCall(final int a1, final CodeIterator a2, final CtClass a3, final MethodInfo a4) {
        super(a1, a2, a3, a4);
    }
    
    private int getNameAndType(final ConstPool a1) {
        final int v1 = this.currentPos;
        final int v2 = this.iterator.byteAt(v1);
        final int v3 = this.iterator.u16bitAt(v1 + 1);
        if (v2 == 185) {
            return a1.getInterfaceMethodrefNameAndType(v3);
        }
        return a1.getMethodrefNameAndType(v3);
    }
    
    @Override
    public CtBehavior where() {
        return super.where();
    }
    
    @Override
    public int getLineNumber() {
        return super.getLineNumber();
    }
    
    @Override
    public String getFileName() {
        return super.getFileName();
    }
    
    protected CtClass getCtClass() throws NotFoundException {
        return this.thisClass.getClassPool().get(this.getClassName());
    }
    
    public String getClassName() {
        final ConstPool v2 = this.getConstPool();
        final int v3 = this.currentPos;
        final int v4 = this.iterator.byteAt(v3);
        final int v5 = this.iterator.u16bitAt(v3 + 1);
        String v6;
        if (v4 == 185) {
            v6 = v2.getInterfaceMethodrefClassName(v5);
        }
        else {
            v6 = v2.getMethodrefClassName(v5);
        }
        if (v6.charAt(0) == '[') {
            v6 = Descriptor.toClassName(v6);
        }
        return v6;
    }
    
    public String getMethodName() {
        final ConstPool v1 = this.getConstPool();
        final int v2 = this.getNameAndType(v1);
        return v1.getUtf8Info(v1.getNameAndTypeName(v2));
    }
    
    public CtMethod getMethod() throws NotFoundException {
        return this.getCtClass().getMethod(this.getMethodName(), this.getSignature());
    }
    
    public String getSignature() {
        final ConstPool v1 = this.getConstPool();
        final int v2 = this.getNameAndType(v1);
        return v1.getUtf8Info(v1.getNameAndTypeDescriptor(v2));
    }
    
    @Override
    public CtClass[] mayThrow() {
        return super.mayThrow();
    }
    
    public boolean isSuper() {
        return this.iterator.byteAt(this.currentPos) == 183 && !this.where().getDeclaringClass().getName().equals(this.getClassName());
    }
    
    @Override
    public void replace(final String v-4) throws CannotCompileException {
        this.thisClass.getClassFile();
        final ConstPool constPool = this.getConstPool();
        final int currentPos = this.currentPos;
        final int u16bit = this.iterator.u16bitAt(currentPos + 1);
        final int v4 = this.iterator.byteAt(currentPos);
        int v5;
        String v6;
        String v7;
        String v8 = null;
        if (v4 == 185) {
            v5 = 5;
            final String a1 = constPool.getInterfaceMethodrefClassName(u16bit);
            v6 = constPool.getInterfaceMethodrefName(u16bit);
            v7 = constPool.getInterfaceMethodrefType(u16bit);
        }
        else {
            if (v4 != 184 && v4 != 183 && v4 != 182) {
                throw new CannotCompileException("not method invocation");
            }
            v5 = 3;
            v8 = constPool.getMethodrefClassName(u16bit);
            v6 = constPool.getMethodrefName(u16bit);
            v7 = constPool.getMethodrefType(u16bit);
        }
        final Javac v9 = new Javac(this.thisClass);
        final ClassPool v10 = this.thisClass.getClassPool();
        final CodeAttribute v11 = this.iterator.get();
        try {
            final CtClass[] v12 = Descriptor.getParameterTypes(v7, v10);
            final CtClass v13 = Descriptor.getReturnType(v7, v10);
            final int v14 = v11.getMaxLocals();
            v9.recordParams(v8, v12, true, v14, this.withinStatic());
            final int v15 = v9.recordReturnType(v13, true);
            if (v4 == 184) {
                v9.recordStaticProceed(v8, v6);
            }
            else if (v4 == 183) {
                v9.recordSpecialProceed("$0", v8, v6, v7, u16bit);
            }
            else {
                v9.recordProceed("$0", v6);
            }
            Expr.checkResultValue(v13, v-4);
            final Bytecode v16 = v9.getBytecode();
            Expr.storeStack(v12, v4 == 184, v14, v16);
            v9.recordLocalVariables(v11, currentPos);
            if (v13 != CtClass.voidType) {
                v16.addConstZero(v13);
                v16.addStore(v15, v13);
            }
            v9.compileStmnt(v-4);
            if (v13 != CtClass.voidType) {
                v16.addLoad(v15, v13);
            }
            this.replace0(currentPos, v16, v5);
        }
        catch (CompileError v17) {
            throw new CannotCompileException(v17);
        }
        catch (NotFoundException v18) {
            throw new CannotCompileException(v18);
        }
        catch (BadBytecode v19) {
            throw new CannotCompileException("broken method");
        }
    }
}
