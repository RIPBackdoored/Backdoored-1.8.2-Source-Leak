package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.io.*;

class CheckMethodAdapter$1 extends MethodNode {
    final /* synthetic */ MethodVisitor val$cmv;
    
    CheckMethodAdapter$1(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6, final MethodVisitor val$cmv) {
        this.val$cmv = val$cmv;
        super(a1, a2, a3, a4, a5, a6);
    }
    
    @Override
    public void visitEnd() {
        final Analyzer<BasicValue> v-6 = new Analyzer<BasicValue>(new BasicVerifier());
        try {
            v-6.analyze("dummy", this);
        }
        catch (Exception v0) {
            if (v0 instanceof IndexOutOfBoundsException && this.maxLocals == 0 && this.maxStack == 0) {
                throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
            }
            v0.printStackTrace();
            final StringWriter v2 = new StringWriter();
            final PrintWriter v3 = new PrintWriter(v2, true);
            CheckClassAdapter.printAnalyzerResult(this, v-6, v3);
            v3.close();
            throw new RuntimeException(v0.getMessage() + ' ' + v2.toString());
        }
        this.accept(this.val$cmv);
    }
}