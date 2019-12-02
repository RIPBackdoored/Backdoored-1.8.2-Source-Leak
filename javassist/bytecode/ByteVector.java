package javassist.bytecode;

class ByteVector implements Cloneable
{
    private byte[] buffer;
    private int size;
    
    public ByteVector() {
        super();
        this.buffer = new byte[64];
        this.size = 0;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final ByteVector v1 = (ByteVector)super.clone();
        v1.buffer = this.buffer.clone();
        return v1;
    }
    
    public final int getSize() {
        return this.size;
    }
    
    public final byte[] copy() {
        final byte[] v1 = new byte[this.size];
        System.arraycopy(this.buffer, 0, v1, 0, this.size);
        return v1;
    }
    
    public int read(final int a1) {
        if (a1 < 0 || this.size <= a1) {
            throw new ArrayIndexOutOfBoundsException(a1);
        }
        return this.buffer[a1];
    }
    
    public void write(final int a1, final int a2) {
        if (a1 < 0 || this.size <= a1) {
            throw new ArrayIndexOutOfBoundsException(a1);
        }
        this.buffer[a1] = (byte)a2;
    }
    
    public void add(final int a1) {
        this.addGap(1);
        this.buffer[this.size - 1] = (byte)a1;
    }
    
    public void add(final int a1, final int a2) {
        this.addGap(2);
        this.buffer[this.size - 2] = (byte)a1;
        this.buffer[this.size - 1] = (byte)a2;
    }
    
    public void add(final int a1, final int a2, final int a3, final int a4) {
        this.addGap(4);
        this.buffer[this.size - 4] = (byte)a1;
        this.buffer[this.size - 3] = (byte)a2;
        this.buffer[this.size - 2] = (byte)a3;
        this.buffer[this.size - 1] = (byte)a4;
    }
    
    public void addGap(final int v-1) {
        if (this.size + v-1 > this.buffer.length) {
            int a1 = this.size << 1;
            if (a1 < this.size + v-1) {
                a1 = this.size + v-1;
            }
            final byte[] v1 = new byte[a1];
            System.arraycopy(this.buffer, 0, v1, 0, this.size);
            this.buffer = v1;
        }
        this.size += v-1;
    }
}
