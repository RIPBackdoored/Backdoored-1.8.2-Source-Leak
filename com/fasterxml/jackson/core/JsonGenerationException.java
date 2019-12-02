package com.fasterxml.jackson.core;

public class JsonGenerationException extends JsonProcessingException
{
    private static final long serialVersionUID = 123L;
    protected transient JsonGenerator _processor;
    
    @Deprecated
    public JsonGenerationException(final Throwable a1) {
        super(a1);
    }
    
    @Deprecated
    public JsonGenerationException(final String a1) {
        super(a1, (JsonLocation)null);
    }
    
    @Deprecated
    public JsonGenerationException(final String a1, final Throwable a2) {
        super(a1, null, a2);
    }
    
    public JsonGenerationException(final Throwable a1, final JsonGenerator a2) {
        super(a1);
        this._processor = a2;
    }
    
    public JsonGenerationException(final String a1, final JsonGenerator a2) {
        super(a1, (JsonLocation)null);
        this._processor = a2;
    }
    
    public JsonGenerationException(final String a1, final Throwable a2, final JsonGenerator a3) {
        super(a1, null, a2);
        this._processor = a3;
    }
    
    public JsonGenerationException withGenerator(final JsonGenerator a1) {
        this._processor = a1;
        return this;
    }
    
    @Override
    public JsonGenerator getProcessor() {
        return this._processor;
    }
    
    @Override
    public /* bridge */ Object getProcessor() {
        return this.getProcessor();
    }
}
