package javassist;

import javassist.convert.*;
import javassist.bytecode.*;

public class CodeConverter
{
    protected Transformer transformers;
    
    public CodeConverter() {
        super();
        this.transformers = null;
    }
    
    public void replaceNew(final CtClass a1, final CtClass a2, final String a3) {
        this.transformers = new TransformNew(this.transformers, a1.getName(), a2.getName(), a3);
    }
    
    public void replaceNew(final CtClass a1, final CtClass a2) {
        this.transformers = new TransformNewClass(this.transformers, a1.getName(), a2.getName());
    }
    
    public void redirectFieldAccess(final CtField a1, final CtClass a2, final String a3) {
        this.transformers = new TransformFieldAccess(this.transformers, a1, a2.getName(), a3);
    }
    
    public void replaceFieldRead(final CtField a1, final CtClass a2, final String a3) {
        this.transformers = new TransformReadField(this.transformers, a1, a2.getName(), a3);
    }
    
    public void replaceFieldWrite(final CtField a1, final CtClass a2, final String a3) {
        this.transformers = new TransformWriteField(this.transformers, a1, a2.getName(), a3);
    }
    
    public void replaceArrayAccess(final CtClass a1, final ArrayAccessReplacementMethodNames a2) throws NotFoundException {
        this.transformers = new TransformAccessArrayField(this.transformers, a1.getName(), a2);
    }
    
    public void redirectMethodCall(final CtMethod a1, final CtMethod a2) throws CannotCompileException {
        final String v1 = a1.getMethodInfo2().getDescriptor();
        final String v2 = a2.getMethodInfo2().getDescriptor();
        if (!v1.equals(v2)) {
            throw new CannotCompileException("signature mismatch: " + a2.getLongName());
        }
        final int v3 = a1.getModifiers();
        final int v4 = a2.getModifiers();
        if (Modifier.isStatic(v3) != Modifier.isStatic(v4) || (Modifier.isPrivate(v3) && !Modifier.isPrivate(v4)) || a1.getDeclaringClass().isInterface() != a2.getDeclaringClass().isInterface()) {
            throw new CannotCompileException("invoke-type mismatch " + a2.getLongName());
        }
        this.transformers = new TransformCall(this.transformers, a1, a2);
    }
    
    public void redirectMethodCall(final String a1, final CtMethod a2) throws CannotCompileException {
        this.transformers = new TransformCall(this.transformers, a1, a2);
    }
    
    public void insertBeforeMethod(final CtMethod v1, final CtMethod v2) throws CannotCompileException {
        try {
            this.transformers = new TransformBefore(this.transformers, v1, v2);
        }
        catch (NotFoundException a1) {
            throw new CannotCompileException(a1);
        }
    }
    
    public void insertAfterMethod(final CtMethod v1, final CtMethod v2) throws CannotCompileException {
        try {
            this.transformers = new TransformAfter(this.transformers, v1, v2);
        }
        catch (NotFoundException a1) {
            throw new CannotCompileException(a1);
        }
    }
    
    protected void doit(final CtClass v-7, final MethodInfo v-6, final ConstPool v-5) throws CannotCompileException {
        final CodeAttribute codeAttribute = v-6.getCodeAttribute();
        if (codeAttribute == null || this.transformers == null) {
            return;
        }
        for (Transformer transformer = this.transformers; transformer != null; transformer = transformer.getNext()) {
            transformer.initialize(v-5, v-7, v-6);
        }
        final CodeIterator iterator = codeAttribute.iterator();
        while (iterator.hasNext()) {
            try {
                int a1 = iterator.next();
                for (Transformer transformer = this.transformers; transformer != null; transformer = transformer.getNext()) {
                    a1 = transformer.transform(v-7, a1, iterator, v-5);
                }
                continue;
            }
            catch (BadBytecode a2) {
                throw new CannotCompileException(a2);
            }
            break;
        }
        int n = 0;
        int v0 = 0;
        for (Transformer transformer = this.transformers; transformer != null; transformer = transformer.getNext()) {
            int a3 = transformer.extraLocals();
            if (a3 > n) {
                n = a3;
            }
            a3 = transformer.extraStack();
            if (a3 > v0) {
                v0 = a3;
            }
        }
        for (Transformer transformer = this.transformers; transformer != null; transformer = transformer.getNext()) {
            transformer.clean();
        }
        if (n > 0) {
            codeAttribute.setMaxLocals(codeAttribute.getMaxLocals() + n);
        }
        if (v0 > 0) {
            codeAttribute.setMaxStack(codeAttribute.getMaxStack() + v0);
        }
        try {
            v-6.rebuildStackMapIf6(v-7.getClassPool(), v-7.getClassFile2());
        }
        catch (BadBytecode v2) {
            throw new CannotCompileException(v2.getMessage(), v2);
        }
    }
    
    public static class DefaultArrayAccessReplacementMethodNames implements ArrayAccessReplacementMethodNames
    {
        public DefaultArrayAccessReplacementMethodNames() {
            super();
        }
        
        @Override
        public String byteOrBooleanRead() {
            return "arrayReadByteOrBoolean";
        }
        
        @Override
        public String byteOrBooleanWrite() {
            return "arrayWriteByteOrBoolean";
        }
        
        @Override
        public String charRead() {
            return "arrayReadChar";
        }
        
        @Override
        public String charWrite() {
            return "arrayWriteChar";
        }
        
        @Override
        public String doubleRead() {
            return "arrayReadDouble";
        }
        
        @Override
        public String doubleWrite() {
            return "arrayWriteDouble";
        }
        
        @Override
        public String floatRead() {
            return "arrayReadFloat";
        }
        
        @Override
        public String floatWrite() {
            return "arrayWriteFloat";
        }
        
        @Override
        public String intRead() {
            return "arrayReadInt";
        }
        
        @Override
        public String intWrite() {
            return "arrayWriteInt";
        }
        
        @Override
        public String longRead() {
            return "arrayReadLong";
        }
        
        @Override
        public String longWrite() {
            return "arrayWriteLong";
        }
        
        @Override
        public String objectRead() {
            return "arrayReadObject";
        }
        
        @Override
        public String objectWrite() {
            return "arrayWriteObject";
        }
        
        @Override
        public String shortRead() {
            return "arrayReadShort";
        }
        
        @Override
        public String shortWrite() {
            return "arrayWriteShort";
        }
    }
    
    public interface ArrayAccessReplacementMethodNames
    {
        String byteOrBooleanRead();
        
        String byteOrBooleanWrite();
        
        String charRead();
        
        String charWrite();
        
        String doubleRead();
        
        String doubleWrite();
        
        String floatRead();
        
        String floatWrite();
        
        String intRead();
        
        String intWrite();
        
        String longRead();
        
        String longWrite();
        
        String objectRead();
        
        String objectWrite();
        
        String shortRead();
        
        String shortWrite();
    }
}
