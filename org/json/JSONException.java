package org.json;

public class JSONException extends RuntimeException
{
    private static final long serialVersionUID = 0L;
    
    public JSONException(final String a1) {
        super(a1);
    }
    
    public JSONException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
    
    public JSONException(final Throwable a1) {
        super(a1.getMessage(), a1);
    }
}
