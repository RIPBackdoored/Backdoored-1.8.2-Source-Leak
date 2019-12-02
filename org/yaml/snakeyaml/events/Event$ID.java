package org.yaml.snakeyaml.events;

public enum ID
{
    Alias, 
    DocumentEnd, 
    DocumentStart, 
    MappingEnd, 
    MappingStart, 
    Scalar, 
    SequenceEnd, 
    SequenceStart, 
    StreamEnd, 
    StreamStart;
    
    private static final /* synthetic */ ID[] $VALUES;
    
    public static ID[] values() {
        return ID.$VALUES.clone();
    }
    
    public static ID valueOf(final String name) {
        return Enum.valueOf(ID.class, name);
    }
    
    static {
        $VALUES = new ID[] { ID.Alias, ID.DocumentEnd, ID.DocumentStart, ID.MappingEnd, ID.MappingStart, ID.Scalar, ID.SequenceEnd, ID.SequenceStart, ID.StreamEnd, ID.StreamStart };
    }
}
