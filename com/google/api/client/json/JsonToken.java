package com.google.api.client.json;

public enum JsonToken
{
    START_ARRAY, 
    END_ARRAY, 
    START_OBJECT, 
    END_OBJECT, 
    FIELD_NAME, 
    VALUE_STRING, 
    VALUE_NUMBER_INT, 
    VALUE_NUMBER_FLOAT, 
    VALUE_TRUE, 
    VALUE_FALSE, 
    VALUE_NULL, 
    NOT_AVAILABLE;
    
    private static final /* synthetic */ JsonToken[] $VALUES;
    
    public static JsonToken[] values() {
        return JsonToken.$VALUES.clone();
    }
    
    public static JsonToken valueOf(final String a1) {
        return Enum.valueOf(JsonToken.class, a1);
    }
    
    static {
        $VALUES = new JsonToken[] { JsonToken.START_ARRAY, JsonToken.END_ARRAY, JsonToken.START_OBJECT, JsonToken.END_OBJECT, JsonToken.FIELD_NAME, JsonToken.VALUE_STRING, JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT, JsonToken.VALUE_TRUE, JsonToken.VALUE_FALSE, JsonToken.VALUE_NULL, JsonToken.NOT_AVAILABLE };
    }
}
