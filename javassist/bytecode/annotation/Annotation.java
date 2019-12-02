package javassist.bytecode.annotation;

import javassist.bytecode.*;
import java.util.*;
import javassist.*;
import java.io.*;

public class Annotation
{
    ConstPool pool;
    int typeIndex;
    LinkedHashMap members;
    
    public Annotation(final int a1, final ConstPool a2) {
        super();
        this.pool = a2;
        this.typeIndex = a1;
        this.members = null;
    }
    
    public Annotation(final String a1, final ConstPool a2) {
        this(a2.addUtf8Info(Descriptor.of(a1)), a2);
    }
    
    public Annotation(final ConstPool v2, final CtClass v3) throws NotFoundException {
        this(v2.addUtf8Info(Descriptor.of(v3.getName())), v2);
        if (!v3.isInterface()) {
            throw new RuntimeException("Only interfaces are allowed for Annotation creation.");
        }
        final CtMethod[] v4 = v3.getDeclaredMethods();
        if (v4.length > 0) {
            this.members = new LinkedHashMap();
        }
        for (int a2 = 0; a2 < v4.length; ++a2) {
            final CtClass a3 = v4[a2].getReturnType();
            this.addMemberValue(v4[a2].getName(), createMemberValue(v2, a3));
        }
    }
    
    public static MemberValue createMemberValue(final ConstPool v-1, final CtClass v0) throws NotFoundException {
        if (v0 == CtClass.booleanType) {
            return new BooleanMemberValue(v-1);
        }
        if (v0 == CtClass.byteType) {
            return new ByteMemberValue(v-1);
        }
        if (v0 == CtClass.charType) {
            return new CharMemberValue(v-1);
        }
        if (v0 == CtClass.shortType) {
            return new ShortMemberValue(v-1);
        }
        if (v0 == CtClass.intType) {
            return new IntegerMemberValue(v-1);
        }
        if (v0 == CtClass.longType) {
            return new LongMemberValue(v-1);
        }
        if (v0 == CtClass.floatType) {
            return new FloatMemberValue(v-1);
        }
        if (v0 == CtClass.doubleType) {
            return new DoubleMemberValue(v-1);
        }
        if (v0.getName().equals("java.lang.Class")) {
            return new ClassMemberValue(v-1);
        }
        if (v0.getName().equals("java.lang.String")) {
            return new StringMemberValue(v-1);
        }
        if (v0.isArray()) {
            final CtClass a1 = v0.getComponentType();
            final MemberValue a2 = createMemberValue(v-1, a1);
            return new ArrayMemberValue(a2, v-1);
        }
        if (v0.isInterface()) {
            final Annotation v = new Annotation(v-1, v0);
            return new AnnotationMemberValue(v, v-1);
        }
        final EnumMemberValue v2 = new EnumMemberValue(v-1);
        v2.setType(v0.getName());
        return v2;
    }
    
    public void addMemberValue(final int a1, final MemberValue a2) {
        final Pair v1 = new Pair();
        v1.name = a1;
        v1.value = a2;
        this.addMemberValue(v1);
    }
    
    public void addMemberValue(final String a1, final MemberValue a2) {
        final Pair v1 = new Pair();
        v1.name = this.pool.addUtf8Info(a1);
        v1.value = a2;
        if (this.members == null) {
            this.members = new LinkedHashMap();
        }
        this.members.put(a1, v1);
    }
    
    private void addMemberValue(final Pair a1) {
        final String v1 = this.pool.getUtf8Info(a1.name);
        if (this.members == null) {
            this.members = new LinkedHashMap();
        }
        this.members.put(v1, a1);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("@");
        sb.append(this.getTypeName());
        if (this.members != null) {
            sb.append("(");
            final Iterator v0 = this.members.keySet().iterator();
            while (v0.hasNext()) {
                final String v2 = v0.next();
                sb.append(v2).append("=").append(this.getMemberValue(v2));
                if (v0.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }
    
    public String getTypeName() {
        return Descriptor.toClassName(this.pool.getUtf8Info(this.typeIndex));
    }
    
    public Set getMemberNames() {
        if (this.members == null) {
            return null;
        }
        return this.members.keySet();
    }
    
    public MemberValue getMemberValue(final String v2) {
        if (this.members == null) {
            return null;
        }
        final Pair a1 = this.members.get(v2);
        if (a1 == null) {
            return null;
        }
        return a1.value;
    }
    
    public Object toAnnotationType(final ClassLoader a1, final ClassPool a2) throws ClassNotFoundException, NoSuchClassError {
        return AnnotationImpl.make(a1, MemberValue.loadClass(a1, this.getTypeName()), a2, this);
    }
    
    public void write(final AnnotationsWriter v2) throws IOException {
        final String v3 = this.pool.getUtf8Info(this.typeIndex);
        if (this.members == null) {
            v2.annotation(v3, 0);
            return;
        }
        v2.annotation(v3, this.members.size());
        for (final Pair a1 : this.members.values()) {
            v2.memberValuePair(a1.name);
            a1.value.write(v2);
        }
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (a1 == null || !(a1 instanceof Annotation)) {
            return false;
        }
        final Annotation v1 = (Annotation)a1;
        if (!this.getTypeName().equals(v1.getTypeName())) {
            return false;
        }
        final LinkedHashMap v2 = v1.members;
        if (this.members == v2) {
            return true;
        }
        if (this.members == null) {
            return v2 == null;
        }
        return v2 != null && this.members.equals(v2);
    }
    
    static class Pair
    {
        int name;
        MemberValue value;
        
        Pair() {
            super();
        }
    }
}
