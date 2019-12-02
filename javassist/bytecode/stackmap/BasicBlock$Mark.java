package javassist.bytecode.stackmap;

static class Mark implements Comparable
{
    int position;
    BasicBlock block;
    BasicBlock[] jump;
    boolean alwaysJmp;
    int size;
    Catch catcher;
    
    Mark(final int a1) {
        super();
        this.position = a1;
        this.block = null;
        this.jump = null;
        this.alwaysJmp = false;
        this.size = 0;
        this.catcher = null;
    }
    
    @Override
    public int compareTo(final Object v2) {
        if (v2 instanceof Mark) {
            final int a1 = ((Mark)v2).position;
            return this.position - a1;
        }
        return -1;
    }
    
    void setJump(final BasicBlock[] a1, final int a2, final boolean a3) {
        this.jump = a1;
        this.size = a2;
        this.alwaysJmp = a3;
    }
}
