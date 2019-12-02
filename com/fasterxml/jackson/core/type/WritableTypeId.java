package com.fasterxml.jackson.core.type;

import com.fasterxml.jackson.core.*;

public class WritableTypeId
{
    public Object forValue;
    public Class<?> forValueType;
    public Object id;
    public String asProperty;
    public Inclusion include;
    public JsonToken valueShape;
    public boolean wrapperWritten;
    public Object extra;
    
    public WritableTypeId() {
        super();
    }
    
    public WritableTypeId(final Object a1, final JsonToken a2) {
        this(a1, a2, null);
    }
    
    public WritableTypeId(final Object a1, final Class<?> a2, final JsonToken a3) {
        this(a1, a3, null);
        this.forValueType = a2;
    }
    
    public WritableTypeId(final Object a1, final JsonToken a2, final Object a3) {
        super();
        this.forValue = a1;
        this.id = a3;
        this.valueShape = a2;
    }
    
    public enum Inclusion
    {
        WRAPPER_ARRAY, 
        WRAPPER_OBJECT, 
        METADATA_PROPERTY, 
        PAYLOAD_PROPERTY, 
        PARENT_PROPERTY;
        
        private static final /* synthetic */ Inclusion[] $VALUES;
        
        public static Inclusion[] values() {
            return Inclusion.$VALUES.clone();
        }
        
        public static Inclusion valueOf(final String a1) {
            return Enum.valueOf(Inclusion.class, a1);
        }
        
        public boolean requiresObjectContext() {
            return this == Inclusion.METADATA_PROPERTY || this == Inclusion.PAYLOAD_PROPERTY;
        }
        
        static {
            $VALUES = new Inclusion[] { Inclusion.WRAPPER_ARRAY, Inclusion.WRAPPER_OBJECT, Inclusion.METADATA_PROPERTY, Inclusion.PAYLOAD_PROPERTY, Inclusion.PARENT_PROPERTY };
        }
    }
}
