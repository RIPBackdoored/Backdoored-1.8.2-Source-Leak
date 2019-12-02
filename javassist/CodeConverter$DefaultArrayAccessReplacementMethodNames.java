package javassist;

public static class DefaultArrayAccessReplacementMethodNames implements ArrayAccessReplacementMethodNames
{
    public DefaultArrayAccessReplacementMethodNames() {
        super();
    }
    
    @Override
    public String byteOrBooleanRead() {
        return "arrayReadByteOrBoolean";
    }
    
    @Override
    public String byteOrBooleanWrite() {
        return "arrayWriteByteOrBoolean";
    }
    
    @Override
    public String charRead() {
        return "arrayReadChar";
    }
    
    @Override
    public String charWrite() {
        return "arrayWriteChar";
    }
    
    @Override
    public String doubleRead() {
        return "arrayReadDouble";
    }
    
    @Override
    public String doubleWrite() {
        return "arrayWriteDouble";
    }
    
    @Override
    public String floatRead() {
        return "arrayReadFloat";
    }
    
    @Override
    public String floatWrite() {
        return "arrayWriteFloat";
    }
    
    @Override
    public String intRead() {
        return "arrayReadInt";
    }
    
    @Override
    public String intWrite() {
        return "arrayWriteInt";
    }
    
    @Override
    public String longRead() {
        return "arrayReadLong";
    }
    
    @Override
    public String longWrite() {
        return "arrayWriteLong";
    }
    
    @Override
    public String objectRead() {
        return "arrayReadObject";
    }
    
    @Override
    public String objectWrite() {
        return "arrayWriteObject";
    }
    
    @Override
    public String shortRead() {
        return "arrayReadShort";
    }
    
    @Override
    public String shortWrite() {
        return "arrayWriteShort";
    }
}
