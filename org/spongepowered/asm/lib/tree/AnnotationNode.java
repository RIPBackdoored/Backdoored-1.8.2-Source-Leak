package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class AnnotationNode extends AnnotationVisitor
{
    public String desc;
    public List<Object> values;
    
    public AnnotationNode(final String a1) {
        this(327680, a1);
        if (this.getClass() != AnnotationNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public AnnotationNode(final int a1, final String a2) {
        super(a1);
        this.desc = a2;
    }
    
    AnnotationNode(final List<Object> a1) {
        super(327680);
        this.values = a1;
    }
    
    @Override
    public void visit(final String v-2, final Object v-1) {
        if (this.values == null) {
            this.values = new ArrayList<Object>((this.desc != null) ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(v-2);
        }
        if (v-1 instanceof byte[]) {
            final byte[] a2 = (byte[])v-1;
            final ArrayList<Byte> v1 = new ArrayList<Byte>(a2.length);
            for (final byte a3 : a2) {
                v1.add(a3);
            }
            this.values.add(v1);
        }
        else if (v-1 instanceof boolean[]) {
            final boolean[] v2 = (boolean[])v-1;
            final ArrayList<Boolean> v3 = new ArrayList<Boolean>(v2.length);
            for (final boolean v4 : v2) {
                v3.add(v4);
            }
            this.values.add(v3);
        }
        else if (v-1 instanceof short[]) {
            final short[] v5 = (short[])v-1;
            final ArrayList<Short> v6 = new ArrayList<Short>(v5.length);
            for (final short v7 : v5) {
                v6.add(v7);
            }
            this.values.add(v6);
        }
        else if (v-1 instanceof char[]) {
            final char[] v8 = (char[])v-1;
            final ArrayList<Character> v9 = new ArrayList<Character>(v8.length);
            for (final char v10 : v8) {
                v9.add(v10);
            }
            this.values.add(v9);
        }
        else if (v-1 instanceof int[]) {
            final int[] v11 = (int[])v-1;
            final ArrayList<Integer> v12 = new ArrayList<Integer>(v11.length);
            for (final int v13 : v11) {
                v12.add(v13);
            }
            this.values.add(v12);
        }
        else if (v-1 instanceof long[]) {
            final long[] v14 = (long[])v-1;
            final ArrayList<Long> v15 = new ArrayList<Long>(v14.length);
            for (final long v16 : v14) {
                v15.add(v16);
            }
            this.values.add(v15);
        }
        else if (v-1 instanceof float[]) {
            final float[] v17 = (float[])v-1;
            final ArrayList<Float> v18 = new ArrayList<Float>(v17.length);
            for (final float v19 : v17) {
                v18.add(v19);
            }
            this.values.add(v18);
        }
        else if (v-1 instanceof double[]) {
            final double[] v20 = (double[])v-1;
            final ArrayList<Double> v21 = new ArrayList<Double>(v20.length);
            for (final double v22 : v20) {
                v21.add(v22);
            }
            this.values.add(v21);
        }
        else {
            this.values.add(v-1);
        }
    }
    
    @Override
    public void visitEnum(final String a1, final String a2, final String a3) {
        if (this.values == null) {
            this.values = new ArrayList<Object>((this.desc != null) ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(a1);
        }
        this.values.add(new String[] { a2, a3 });
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final String a2) {
        if (this.values == null) {
            this.values = new ArrayList<Object>((this.desc != null) ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(a1);
        }
        final AnnotationNode v1 = new AnnotationNode(a2);
        this.values.add(v1);
        return v1;
    }
    
    @Override
    public AnnotationVisitor visitArray(final String a1) {
        if (this.values == null) {
            this.values = new ArrayList<Object>((this.desc != null) ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(a1);
        }
        final List<Object> v1 = new ArrayList<Object>();
        this.values.add(v1);
        return new AnnotationNode(v1);
    }
    
    @Override
    public void visitEnd() {
    }
    
    public void check(final int a1) {
    }
    
    public void accept(final AnnotationVisitor v-2) {
        if (v-2 != null) {
            if (this.values != null) {
                for (int i = 0; i < this.values.size(); i += 2) {
                    final String a1 = this.values.get(i);
                    final Object v1 = this.values.get(i + 1);
                    accept(v-2, a1, v1);
                }
            }
            v-2.visitEnd();
        }
    }
    
    static void accept(final AnnotationVisitor v-3, final String v-2, final Object v-1) {
        if (v-3 != null) {
            if (v-1 instanceof String[]) {
                final String[] a1 = (String[])v-1;
                v-3.visitEnum(v-2, a1[0], a1[1]);
            }
            else if (v-1 instanceof AnnotationNode) {
                final AnnotationNode a2 = (AnnotationNode)v-1;
                a2.accept(v-3.visitAnnotation(v-2, a2.desc));
            }
            else if (v-1 instanceof List) {
                final AnnotationVisitor v0 = v-3.visitArray(v-2);
                if (v0 != null) {
                    final List<?> v2 = (List<?>)v-1;
                    for (int a3 = 0; a3 < v2.size(); ++a3) {
                        accept(v0, null, v2.get(a3));
                    }
                    v0.visitEnd();
                }
            }
            else {
                v-3.visit(v-2, v-1);
            }
        }
    }
}
