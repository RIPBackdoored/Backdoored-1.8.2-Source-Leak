package javassist.bytecode;

public static final class ConstPoolWriter
{
    ByteStream output;
    protected int startPos;
    protected int num;
    
    ConstPoolWriter(final ByteStream a1) {
        super();
        this.output = a1;
        this.startPos = a1.getPos();
        this.num = 1;
        this.output.writeShort(1);
    }
    
    public int[] addClassInfo(final String[] v2) {
        final int v3 = v2.length;
        final int[] v4 = new int[v3];
        for (int a1 = 0; a1 < v3; ++a1) {
            v4[a1] = this.addClassInfo(v2[a1]);
        }
        return v4;
    }
    
    public int addClassInfo(final String a1) {
        final int v1 = this.addUtf8Info(a1);
        this.output.write(7);
        this.output.writeShort(v1);
        return this.num++;
    }
    
    public int addClassInfo(final int a1) {
        this.output.write(7);
        this.output.writeShort(a1);
        return this.num++;
    }
    
    public int addNameAndTypeInfo(final String a1, final String a2) {
        return this.addNameAndTypeInfo(this.addUtf8Info(a1), this.addUtf8Info(a2));
    }
    
    public int addNameAndTypeInfo(final int a1, final int a2) {
        this.output.write(12);
        this.output.writeShort(a1);
        this.output.writeShort(a2);
        return this.num++;
    }
    
    public int addFieldrefInfo(final int a1, final int a2) {
        this.output.write(9);
        this.output.writeShort(a1);
        this.output.writeShort(a2);
        return this.num++;
    }
    
    public int addMethodrefInfo(final int a1, final int a2) {
        this.output.write(10);
        this.output.writeShort(a1);
        this.output.writeShort(a2);
        return this.num++;
    }
    
    public int addInterfaceMethodrefInfo(final int a1, final int a2) {
        this.output.write(11);
        this.output.writeShort(a1);
        this.output.writeShort(a2);
        return this.num++;
    }
    
    public int addMethodHandleInfo(final int a1, final int a2) {
        this.output.write(15);
        this.output.write(a1);
        this.output.writeShort(a2);
        return this.num++;
    }
    
    public int addMethodTypeInfo(final int a1) {
        this.output.write(16);
        this.output.writeShort(a1);
        return this.num++;
    }
    
    public int addInvokeDynamicInfo(final int a1, final int a2) {
        this.output.write(18);
        this.output.writeShort(a1);
        this.output.writeShort(a2);
        return this.num++;
    }
    
    public int addStringInfo(final String a1) {
        final int v1 = this.addUtf8Info(a1);
        this.output.write(8);
        this.output.writeShort(v1);
        return this.num++;
    }
    
    public int addIntegerInfo(final int a1) {
        this.output.write(3);
        this.output.writeInt(a1);
        return this.num++;
    }
    
    public int addFloatInfo(final float a1) {
        this.output.write(4);
        this.output.writeFloat(a1);
        return this.num++;
    }
    
    public int addLongInfo(final long a1) {
        this.output.write(5);
        this.output.writeLong(a1);
        final int v1 = this.num;
        this.num += 2;
        return v1;
    }
    
    public int addDoubleInfo(final double a1) {
        this.output.write(6);
        this.output.writeDouble(a1);
        final int v1 = this.num;
        this.num += 2;
        return v1;
    }
    
    public int addUtf8Info(final String a1) {
        this.output.write(1);
        this.output.writeUTF(a1);
        return this.num++;
    }
    
    void end() {
        this.output.writeShort(this.startPos, this.num);
    }
}
