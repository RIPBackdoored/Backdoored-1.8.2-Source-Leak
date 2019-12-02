package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.util.*;

public static class Context implements PrettyPrinter.IPrettyPrintable
{
    final Target target;
    final Type returnType;
    final AbstractInsnNode node;
    final int baseArgIndex;
    final Local[] locals;
    private final boolean isStatic;
    
    public Context(final Type a1, final boolean a2, final Target a3, final AbstractInsnNode a4) {
        super();
        this.isStatic = Bytecode.methodIsStatic(a3.method);
        this.returnType = a1;
        this.target = a3;
        this.node = a4;
        this.baseArgIndex = (this.isStatic ? 0 : 1);
        this.locals = this.initLocals(a3, a2, a4);
        this.initOrdinals();
    }
    
    private Local[] initLocals(final Target v-4, final boolean v-3, final AbstractInsnNode v-2) {
        if (!v-3) {
            final LocalVariableNode[] a3 = Locals.getLocalsAt(v-4.classNode, v-4.method, v-2);
            if (a3 != null) {
                final Local[] a4 = new Local[a3.length];
                for (int a5 = 0; a5 < a3.length; ++a5) {
                    if (a3[a5] != null) {
                        a4[a5] = new Local(a3[a5].name, Type.getType(a3[a5].desc));
                    }
                }
                return a4;
            }
        }
        final Local[] array = new Local[this.baseArgIndex + v-4.arguments.length];
        if (!this.isStatic) {
            array[0] = new Local("this", Type.getType(v-4.classNode.name));
        }
        for (int v0 = this.baseArgIndex; v0 < array.length; ++v0) {
            final Type v2 = v-4.arguments[v0 - this.baseArgIndex];
            array[v0] = new Local("arg" + v0, v2);
        }
        return array;
    }
    
    private void initOrdinals() {
        final Map<Type, Integer> map = new HashMap<Type, Integer>();
        for (int v0 = 0; v0 < this.locals.length; ++v0) {
            Integer v2 = 0;
            if (this.locals[v0] != null) {
                v2 = map.get(this.locals[v0].type);
                map.put(this.locals[v0].type, v2 = ((v2 == null) ? 0 : (v2 + 1)));
                this.locals[v0].ord = v2;
            }
        }
    }
    
    @Override
    public void print(final PrettyPrinter v-3) {
        v-3.add("%5s  %7s  %30s  %-50s  %s", "INDEX", "ORDINAL", "TYPE", "NAME", "CANDIDATE");
        for (int i = this.baseArgIndex; i < this.locals.length; ++i) {
            final Local local = this.locals[i];
            if (local != null) {
                final Type a1 = local.type;
                final String v1 = local.name;
                final int v2 = local.ord;
                final String v3 = this.returnType.equals(a1) ? "YES" : "-";
                v-3.add("[%3d]    [%3d]  %30s  %-50s  %s", i, v2, SignaturePrinter.getTypeName(a1, false), v1, v3);
            }
            else if (i > 0) {
                final Local v4 = this.locals[i - 1];
                final boolean v5 = v4 != null && v4.type != null && v4.type.getSize() > 1;
                v-3.add("[%3d]           %30s", i, v5 ? "<top>" : "-");
            }
        }
    }
    
    public class Local
    {
        int ord;
        String name;
        Type type;
        final /* synthetic */ Context this$0;
        
        public Local(final Context a1, final String a2, final Type a3) {
            this.this$0 = a1;
            super();
            this.ord = 0;
            this.name = a2;
            this.type = a3;
        }
        
        @Override
        public String toString() {
            return String.format("Local[ordinal=%d, name=%s, type=%s]", this.ord, this.name, this.type);
        }
    }
}
