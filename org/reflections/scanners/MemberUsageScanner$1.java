package org.reflections.scanners;

import org.reflections.*;
import javassist.*;
import javassist.expr.*;

class MemberUsageScanner$1 extends ExprEditor {
    final /* synthetic */ String val$key;
    final /* synthetic */ MemberUsageScanner this$0;
    
    MemberUsageScanner$1(final MemberUsageScanner a1, final String val$key) {
        this.this$0 = a1;
        this.val$key = val$key;
        super();
    }
    
    @Override
    public void edit(final NewExpr v2) throws CannotCompileException {
        try {
            MemberUsageScanner.access$000(this.this$0, v2.getConstructor().getDeclaringClass().getName() + ".<init>(" + this.this$0.parameterNames(v2.getConstructor().getMethodInfo()) + ")", v2.getLineNumber(), this.val$key);
        }
        catch (NotFoundException a1) {
            throw new ReflectionsException("Could not find new instance usage in " + this.val$key, a1);
        }
    }
    
    @Override
    public void edit(final MethodCall v2) throws CannotCompileException {
        try {
            MemberUsageScanner.access$000(this.this$0, v2.getMethod().getDeclaringClass().getName() + "." + v2.getMethodName() + "(" + this.this$0.parameterNames(v2.getMethod().getMethodInfo()) + ")", v2.getLineNumber(), this.val$key);
        }
        catch (NotFoundException a1) {
            throw new ReflectionsException("Could not find member " + v2.getClassName() + " in " + this.val$key, a1);
        }
    }
    
    @Override
    public void edit(final ConstructorCall v2) throws CannotCompileException {
        try {
            MemberUsageScanner.access$000(this.this$0, v2.getConstructor().getDeclaringClass().getName() + ".<init>(" + this.this$0.parameterNames(v2.getConstructor().getMethodInfo()) + ")", v2.getLineNumber(), this.val$key);
        }
        catch (NotFoundException a1) {
            throw new ReflectionsException("Could not find member " + v2.getClassName() + " in " + this.val$key, a1);
        }
    }
    
    @Override
    public void edit(final FieldAccess v2) throws CannotCompileException {
        try {
            MemberUsageScanner.access$000(this.this$0, v2.getField().getDeclaringClass().getName() + "." + v2.getFieldName(), v2.getLineNumber(), this.val$key);
        }
        catch (NotFoundException a1) {
            throw new ReflectionsException("Could not find member " + v2.getFieldName() + " in " + this.val$key, a1);
        }
    }
}