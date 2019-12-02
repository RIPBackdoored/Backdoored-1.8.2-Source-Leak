package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.util.*;

public class LocalVariableDiscriminator
{
    private final boolean argsOnly;
    private final int ordinal;
    private final int index;
    private final Set<String> names;
    private final boolean print;
    
    public LocalVariableDiscriminator(final boolean a1, final int a2, final int a3, final Set<String> a4, final boolean a5) {
        super();
        this.argsOnly = a1;
        this.ordinal = a2;
        this.index = a3;
        this.names = Collections.unmodifiableSet((Set<? extends String>)a4);
        this.print = a5;
    }
    
    public boolean isArgsOnly() {
        return this.argsOnly;
    }
    
    public int getOrdinal() {
        return this.ordinal;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public Set<String> getNames() {
        return this.names;
    }
    
    public boolean hasNames() {
        return !this.names.isEmpty();
    }
    
    public boolean printLVT() {
        return this.print;
    }
    
    protected boolean isImplicit(final Context a1) {
        return this.ordinal < 0 && this.index < a1.baseArgIndex && this.names.isEmpty();
    }
    
    public int findLocal(final Type a3, final boolean a4, final Target v1, final AbstractInsnNode v2) {
        try {
            return this.findLocal(new Context(a3, a4, v1, v2));
        }
        catch (InvalidImplicitDiscriminatorException a5) {
            return -2;
        }
    }
    
    public int findLocal(final Context a1) {
        if (this.isImplicit(a1)) {
            return this.findImplicitLocal(a1);
        }
        return this.findExplicitLocal(a1);
    }
    
    private int findImplicitLocal(final Context v-2) {
        int n = 0;
        int v0 = 0;
        for (int v2 = v-2.baseArgIndex; v2 < v-2.locals.length; ++v2) {
            final Context.Local a1 = v-2.locals[v2];
            if (a1 != null) {
                if (a1.type.equals(v-2.returnType)) {
                    ++v0;
                    n = v2;
                }
            }
        }
        if (v0 == 1) {
            return n;
        }
        throw new InvalidImplicitDiscriminatorException("Found " + v0 + " candidate variables but exactly 1 is required.");
    }
    
    private int findExplicitLocal(final Context v0) {
        for (int v = v0.baseArgIndex; v < v0.locals.length; ++v) {
            final Context.Local a1 = v0.locals[v];
            if (a1 != null) {
                if (a1.type.equals(v0.returnType)) {
                    if (this.ordinal > -1) {
                        if (this.ordinal == a1.ord) {
                            return v;
                        }
                    }
                    else if (this.index >= v0.baseArgIndex) {
                        if (this.index == v) {
                            return v;
                        }
                    }
                    else if (this.names.contains(a1.name)) {
                        return v;
                    }
                }
            }
        }
        return -1;
    }
    
    public static LocalVariableDiscriminator parse(final AnnotationNode a1) {
        final boolean v1 = Annotations.getValue(a1, "argsOnly", Boolean.FALSE);
        final int v2 = Annotations.getValue(a1, "ordinal", -1);
        final int v3 = Annotations.getValue(a1, "index", -1);
        final boolean v4 = Annotations.getValue(a1, "print", Boolean.FALSE);
        final Set<String> v5 = new HashSet<String>();
        final List<String> v6 = Annotations.getValue(a1, "name", (List<String>)null);
        if (v6 != null) {
            v5.addAll(v6);
        }
        return new LocalVariableDiscriminator(v1, v2, v3, v5, v4);
    }
    
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
}
