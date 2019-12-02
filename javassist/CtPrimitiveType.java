package javassist;

public final class CtPrimitiveType extends CtClass
{
    private char descriptor;
    private String wrapperName;
    private String getMethodName;
    private String mDescriptor;
    private int returnOp;
    private int arrayType;
    private int dataSize;
    
    CtPrimitiveType(final String a1, final char a2, final String a3, final String a4, final String a5, final int a6, final int a7, final int a8) {
        super(a1);
        this.descriptor = a2;
        this.wrapperName = a3;
        this.getMethodName = a4;
        this.mDescriptor = a5;
        this.returnOp = a6;
        this.arrayType = a7;
        this.dataSize = a8;
    }
    
    @Override
    public boolean isPrimitive() {
        return true;
    }
    
    @Override
    public int getModifiers() {
        return 17;
    }
    
    public char getDescriptor() {
        return this.descriptor;
    }
    
    public String getWrapperName() {
        return this.wrapperName;
    }
    
    public String getGetMethodName() {
        return this.getMethodName;
    }
    
    public String getGetMethodDescriptor() {
        return this.mDescriptor;
    }
    
    public int getReturnOp() {
        return this.returnOp;
    }
    
    public int getArrayType() {
        return this.arrayType;
    }
    
    public int getDataSize() {
        return this.dataSize;
    }
}
