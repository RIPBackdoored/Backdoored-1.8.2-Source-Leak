package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.signature.*;

public abstract class Remapper
{
    public Remapper() {
        super();
    }
    
    public String mapDesc(final String v-1) {
        final Type v0 = Type.getType(v-1);
        switch (v0.getSort()) {
            case 9: {
                String v2 = this.mapDesc(v0.getElementType().getDescriptor());
                for (int a1 = 0; a1 < v0.getDimensions(); ++a1) {
                    v2 = '[' + v2;
                }
                return v2;
            }
            case 10: {
                final String v3 = this.map(v0.getInternalName());
                if (v3 != null) {
                    return 'L' + v3 + ';';
                }
                break;
            }
        }
        return v-1;
    }
    
    private Type mapType(final Type v0) {
        switch (v0.getSort()) {
            case 9: {
                String v = this.mapDesc(v0.getElementType().getDescriptor());
                for (int a1 = 0; a1 < v0.getDimensions(); ++a1) {
                    v = '[' + v;
                }
                return Type.getType(v);
            }
            case 10: {
                final String v = this.map(v0.getInternalName());
                return (v != null) ? Type.getObjectType(v) : v0;
            }
            case 11: {
                return Type.getMethodType(this.mapMethodDesc(v0.getDescriptor()));
            }
            default: {
                return v0;
            }
        }
    }
    
    public String mapType(final String a1) {
        if (a1 == null) {
            return null;
        }
        return this.mapType(Type.getObjectType(a1)).getInternalName();
    }
    
    public String[] mapTypes(final String[] v-4) {
        String[] array = null;
        boolean b = false;
        for (int i = 0; i < v-4.length; ++i) {
            final String a1 = v-4[i];
            final String v1 = this.map(a1);
            if (v1 != null && array == null) {
                array = new String[v-4.length];
                if (i > 0) {
                    System.arraycopy(v-4, 0, array, 0, i);
                }
                b = true;
            }
            if (b) {
                array[i] = ((v1 == null) ? a1 : v1);
            }
        }
        return b ? array : v-4;
    }
    
    public String mapMethodDesc(final String v2) {
        if ("()V".equals(v2)) {
            return v2;
        }
        final Type[] v3 = Type.getArgumentTypes(v2);
        final StringBuilder v4 = new StringBuilder("(");
        for (int a1 = 0; a1 < v3.length; ++a1) {
            v4.append(this.mapDesc(v3[a1].getDescriptor()));
        }
        final Type v5 = Type.getReturnType(v2);
        if (v5 == Type.VOID_TYPE) {
            v4.append(")V");
            return v4.toString();
        }
        v4.append(')').append(this.mapDesc(v5.getDescriptor()));
        return v4.toString();
    }
    
    public Object mapValue(final Object v2) {
        if (v2 instanceof Type) {
            return this.mapType((Type)v2);
        }
        if (v2 instanceof Handle) {
            final Handle a1 = (Handle)v2;
            return new Handle(a1.getTag(), this.mapType(a1.getOwner()), this.mapMethodName(a1.getOwner(), a1.getName(), a1.getDesc()), this.mapMethodDesc(a1.getDesc()), a1.isInterface());
        }
        return v2;
    }
    
    public String mapSignature(final String a1, final boolean a2) {
        if (a1 == null) {
            return null;
        }
        final SignatureReader v1 = new SignatureReader(a1);
        final SignatureWriter v2 = new SignatureWriter();
        final SignatureVisitor v3 = this.createSignatureRemapper(v2);
        if (a2) {
            v1.acceptType(v3);
        }
        else {
            v1.accept(v3);
        }
        return v2.toString();
    }
    
    @Deprecated
    protected SignatureVisitor createRemappingSignatureAdapter(final SignatureVisitor a1) {
        return new SignatureRemapper(a1, this);
    }
    
    protected SignatureVisitor createSignatureRemapper(final SignatureVisitor a1) {
        return this.createRemappingSignatureAdapter(a1);
    }
    
    public String mapMethodName(final String a1, final String a2, final String a3) {
        return a2;
    }
    
    public String mapInvokeDynamicMethodName(final String a1, final String a2) {
        return a1;
    }
    
    public String mapFieldName(final String a1, final String a2, final String a3) {
        return a2;
    }
    
    public String map(final String a1) {
        return a1;
    }
}
