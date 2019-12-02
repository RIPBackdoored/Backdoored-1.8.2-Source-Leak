package javassist.bytecode;

import javassist.bytecode.annotation.*;

static class Parser extends Walker
{
    ConstPool pool;
    Annotation[][] allParams;
    Annotation[] allAnno;
    Annotation currentAnno;
    MemberValue currentMember;
    
    Parser(final byte[] a1, final ConstPool a2) {
        super(a1);
        this.pool = a2;
    }
    
    Annotation[][] parseParameters() throws Exception {
        this.parameters();
        return this.allParams;
    }
    
    Annotation[] parseAnnotations() throws Exception {
        this.annotationArray();
        return this.allAnno;
    }
    
    MemberValue parseMemberValue() throws Exception {
        this.memberValue(0);
        return this.currentMember;
    }
    
    @Override
    void parameters(final int v1, int v2) throws Exception {
        final Annotation[][] v3 = new Annotation[v1][];
        for (int a1 = 0; a1 < v1; ++a1) {
            v2 = this.annotationArray(v2);
            v3[a1] = this.allAnno;
        }
        this.allParams = v3;
    }
    
    @Override
    int annotationArray(int v1, final int v2) throws Exception {
        final Annotation[] v3 = new Annotation[v2];
        for (int a1 = 0; a1 < v2; ++a1) {
            v1 = this.annotation(v1);
            v3[a1] = this.currentAnno;
        }
        this.allAnno = v3;
        return v1;
    }
    
    @Override
    int annotation(final int a1, final int a2, final int a3) throws Exception {
        this.currentAnno = new Annotation(a2, this.pool);
        return super.annotation(a1, a2, a3);
    }
    
    @Override
    int memberValuePair(int a1, final int a2) throws Exception {
        a1 = super.memberValuePair(a1, a2);
        this.currentAnno.addMemberValue(a2, this.currentMember);
        return a1;
    }
    
    @Override
    void constValueMember(final int v-1, final int v0) throws Exception {
        final ConstPool v = this.pool;
        MemberValue v2 = null;
        switch (v-1) {
            case 66: {
                final MemberValue a1 = new ByteMemberValue(v0, v);
                break;
            }
            case 67: {
                final MemberValue a2 = new CharMemberValue(v0, v);
                break;
            }
            case 68: {
                v2 = new DoubleMemberValue(v0, v);
                break;
            }
            case 70: {
                v2 = new FloatMemberValue(v0, v);
                break;
            }
            case 73: {
                v2 = new IntegerMemberValue(v0, v);
                break;
            }
            case 74: {
                v2 = new LongMemberValue(v0, v);
                break;
            }
            case 83: {
                v2 = new ShortMemberValue(v0, v);
                break;
            }
            case 90: {
                v2 = new BooleanMemberValue(v0, v);
                break;
            }
            case 115: {
                v2 = new StringMemberValue(v0, v);
                break;
            }
            default: {
                throw new RuntimeException("unknown tag:" + v-1);
            }
        }
        this.currentMember = v2;
        super.constValueMember(v-1, v0);
    }
    
    @Override
    void enumMemberValue(final int a1, final int a2, final int a3) throws Exception {
        this.currentMember = new EnumMemberValue(a2, a3, this.pool);
        super.enumMemberValue(a1, a2, a3);
    }
    
    @Override
    void classMemberValue(final int a1, final int a2) throws Exception {
        this.currentMember = new ClassMemberValue(a2, this.pool);
        super.classMemberValue(a1, a2);
    }
    
    @Override
    int annotationMemberValue(int a1) throws Exception {
        final Annotation v1 = this.currentAnno;
        a1 = super.annotationMemberValue(a1);
        this.currentMember = new AnnotationMemberValue(this.currentAnno, this.pool);
        this.currentAnno = v1;
        return a1;
    }
    
    @Override
    int arrayMemberValue(int v1, final int v2) throws Exception {
        final ArrayMemberValue v3 = new ArrayMemberValue(this.pool);
        final MemberValue[] v4 = new MemberValue[v2];
        for (int a1 = 0; a1 < v2; ++a1) {
            v1 = this.memberValue(v1);
            v4[a1] = this.currentMember;
        }
        v3.setValue(v4);
        this.currentMember = v3;
        return v1;
    }
}
