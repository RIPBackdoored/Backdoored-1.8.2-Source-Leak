package javassist.bytecode;

final class LongVector
{
    static final int ASIZE = 128;
    static final int ABITS = 7;
    static final int VSIZE = 8;
    private ConstInfo[][] objects;
    private int elements;
    
    public LongVector() {
        super();
        this.objects = new ConstInfo[8][];
        this.elements = 0;
    }
    
    public LongVector(final int a1) {
        super();
        final int v1 = (a1 >> 7 & 0xFFFFFFF8) + 8;
        this.objects = new ConstInfo[v1][];
        this.elements = 0;
    }
    
    public int size() {
        return this.elements;
    }
    
    public int capacity() {
        return this.objects.length * 128;
    }
    
    public ConstInfo elementAt(final int a1) {
        if (a1 < 0 || this.elements <= a1) {
            return null;
        }
        return this.objects[a1 >> 7][a1 & 0x7F];
    }
    
    public void addElement(final ConstInfo v2) {
        final int v3 = this.elements >> 7;
        final int v4 = this.elements & 0x7F;
        final int v5 = this.objects.length;
        if (v3 >= v5) {
            final ConstInfo[][] a1 = new ConstInfo[v5 + 8][];
            System.arraycopy(this.objects, 0, a1, 0, v5);
            this.objects = a1;
        }
        if (this.objects[v3] == null) {
            this.objects[v3] = new ConstInfo[128];
        }
        this.objects[v3][v4] = v2;
        ++this.elements;
    }
}
