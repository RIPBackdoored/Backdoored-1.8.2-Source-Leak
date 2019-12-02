package org.json;

public class JSONPointerException extends JSONException
{
    private static final long serialVersionUID = 8872944667561856751L;
    
    public JSONPointerException(final String a1) {
        super(a1);
    }
    
    public JSONPointerException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
