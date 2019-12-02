package javassist.bytecode;

static class TAWalker extends AnnotationsAttribute.Walker
{
    SubWalker subWalker;
    
    TAWalker(final byte[] a1) {
        super(a1);
        this.subWalker = new SubWalker(a1);
    }
    
    @Override
    int annotationArray(int v2, final int v3) throws Exception {
        for (int a2 = 0; a2 < v3; ++a2) {
            final int a3 = this.info[v2] & 0xFF;
            v2 = this.subWalker.targetInfo(v2 + 1, a3);
            v2 = this.subWalker.typePath(v2);
            v2 = this.annotation(v2);
        }
        return v2;
    }
}
