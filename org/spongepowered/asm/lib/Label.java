package org.spongepowered.asm.lib;

public class Label
{
    static final int DEBUG = 1;
    static final int RESOLVED = 2;
    static final int RESIZED = 4;
    static final int PUSHED = 8;
    static final int TARGET = 16;
    static final int STORE = 32;
    static final int REACHABLE = 64;
    static final int JSR = 128;
    static final int RET = 256;
    static final int SUBROUTINE = 512;
    static final int VISITED = 1024;
    static final int VISITED2 = 2048;
    public Object info;
    int status;
    int line;
    int position;
    private int referenceCount;
    private int[] srcAndRefPositions;
    int inputStackTop;
    int outputStackMax;
    Frame frame;
    Label successor;
    Edge successors;
    Label next;
    
    public Label() {
        super();
    }
    
    public int getOffset() {
        if ((this.status & 0x2) == 0x0) {
            throw new IllegalStateException("Label offset position has not been resolved yet");
        }
        return this.position;
    }
    
    void put(final MethodWriter a1, final ByteVector a2, final int a3, final boolean a4) {
        if ((this.status & 0x2) == 0x0) {
            if (a4) {
                this.addReference(-1 - a3, a2.length);
                a2.putInt(-1);
            }
            else {
                this.addReference(a3, a2.length);
                a2.putShort(-1);
            }
        }
        else if (a4) {
            a2.putInt(this.position - a3);
        }
        else {
            a2.putShort(this.position - a3);
        }
    }
    
    private void addReference(final int v1, final int v2) {
        if (this.srcAndRefPositions == null) {
            this.srcAndRefPositions = new int[6];
        }
        if (this.referenceCount >= this.srcAndRefPositions.length) {
            final int[] a1 = new int[this.srcAndRefPositions.length + 6];
            System.arraycopy(this.srcAndRefPositions, 0, a1, 0, this.srcAndRefPositions.length);
            this.srcAndRefPositions = a1;
        }
        this.srcAndRefPositions[this.referenceCount++] = v1;
        this.srcAndRefPositions[this.referenceCount++] = v2;
    }
    
    boolean resolve(final MethodWriter v-5, final int v-4, final byte[] v-3) {
        boolean b = false;
        this.status |= 0x2;
        this.position = v-4;
        int i = 0;
        while (i < this.referenceCount) {
            final int a3 = this.srcAndRefPositions[i++];
            int v1 = this.srcAndRefPositions[i++];
            if (a3 >= 0) {
                final int a4 = v-4 - a3;
                if (a4 < -32768 || a4 > 32767) {
                    final int a5 = v-3[v1 - 1] & 0xFF;
                    if (a5 <= 168) {
                        v-3[v1 - 1] = (byte)(a5 + 49);
                    }
                    else {
                        v-3[v1 - 1] = (byte)(a5 + 20);
                    }
                    b = true;
                }
                v-3[v1++] = (byte)(a4 >>> 8);
                v-3[v1] = (byte)a4;
            }
            else {
                final int v2 = v-4 + a3 + 1;
                v-3[v1++] = (byte)(v2 >>> 24);
                v-3[v1++] = (byte)(v2 >>> 16);
                v-3[v1++] = (byte)(v2 >>> 8);
                v-3[v1] = (byte)v2;
            }
        }
        return b;
    }
    
    Label getFirst() {
        return (this.frame == null) ? this : this.frame.owner;
    }
    
    boolean inSubroutine(final long a1) {
        return (this.status & 0x400) != 0x0 && (this.srcAndRefPositions[(int)(a1 >>> 32)] & (int)a1) != 0x0;
    }
    
    boolean inSameSubroutine(final Label v2) {
        if ((this.status & 0x400) == 0x0 || (v2.status & 0x400) == 0x0) {
            return false;
        }
        for (int a1 = 0; a1 < this.srcAndRefPositions.length; ++a1) {
            if ((this.srcAndRefPositions[a1] & v2.srcAndRefPositions[a1]) != 0x0) {
                return true;
            }
        }
        return false;
    }
    
    void addToSubroutine(final long a1, final int a2) {
        if ((this.status & 0x400) == 0x0) {
            this.status |= 0x400;
            this.srcAndRefPositions = new int[a2 / 32 + 1];
        }
        final int[] srcAndRefPositions = this.srcAndRefPositions;
        final int n = (int)(a1 >>> 32);
        srcAndRefPositions[n] |= (int)a1;
    }
    
    void visitSubroutine(final Label v2, final long v3, final int v5) {
        Label v6 = this;
        while (v6 != null) {
            final Label a2 = v6;
            v6 = a2.next;
            a2.next = null;
            if (v2 != null) {
                if ((a2.status & 0x800) != 0x0) {
                    continue;
                }
                final Label label = a2;
                label.status |= 0x800;
                if ((a2.status & 0x100) != 0x0 && !a2.inSameSubroutine(v2)) {
                    final Edge a3 = new Edge();
                    a3.info = a2.inputStackTop;
                    a3.successor = v2.successors.successor;
                    a3.next = a2.successors;
                    a2.successors = a3;
                }
            }
            else {
                if (a2.inSubroutine(v3)) {
                    continue;
                }
                a2.addToSubroutine(v3, v5);
            }
            for (Edge a4 = a2.successors; a4 != null; a4 = a4.next) {
                if (((a2.status & 0x80) == 0x0 || a4 != a2.successors.next) && a4.successor.next == null) {
                    a4.successor.next = v6;
                    v6 = a4.successor;
                }
            }
        }
    }
    
    @Override
    public String toString() {
        return "L" + System.identityHashCode(this);
    }
}
