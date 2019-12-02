package org.spongepowered.asm.mixin.transformer.debug;

import org.jetbrains.java.decompiler.main.extern.*;
import org.jetbrains.java.decompiler.util.*;
import java.io.*;

class RuntimeDecompiler$1 implements IBytecodeProvider {
    private byte[] byteCode;
    final /* synthetic */ RuntimeDecompiler this$0;
    
    RuntimeDecompiler$1(final RuntimeDecompiler a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public byte[] getBytecode(final String a1, final String a2) throws IOException {
        if (this.byteCode == null) {
            this.byteCode = InterpreterUtil.getBytes(new File(a1));
        }
        return this.byteCode;
    }
}