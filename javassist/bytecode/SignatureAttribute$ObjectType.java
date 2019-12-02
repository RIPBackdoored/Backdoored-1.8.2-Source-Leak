package javassist.bytecode;

public abstract static class ObjectType extends Type
{
    public ObjectType() {
        super();
    }
    
    public String encode() {
        final StringBuffer v1 = new StringBuffer();
        this.encode(v1);
        return v1.toString();
    }
}
