package org.json.simple.parser;

public class ParseException extends Exception
{
    private static final long serialVersionUID = -7880698968187728548L;
    public static final int ERROR_UNEXPECTED_CHAR = 0;
    public static final int ERROR_UNEXPECTED_TOKEN = 1;
    public static final int ERROR_UNEXPECTED_EXCEPTION = 2;
    private int errorType;
    private Object unexpectedObject;
    private int position;
    
    public ParseException(final int a1) {
        this(-1, a1, null);
    }
    
    public ParseException(final int a1, final Object a2) {
        this(-1, a1, a2);
    }
    
    public ParseException(final int a1, final int a2, final Object a3) {
        super();
        this.position = a1;
        this.errorType = a2;
        this.unexpectedObject = a3;
    }
    
    public int getErrorType() {
        return this.errorType;
    }
    
    public void setErrorType(final int a1) {
        this.errorType = a1;
    }
    
    public int getPosition() {
        return this.position;
    }
    
    public void setPosition(final int a1) {
        this.position = a1;
    }
    
    public Object getUnexpectedObject() {
        return this.unexpectedObject;
    }
    
    public void setUnexpectedObject(final Object a1) {
        this.unexpectedObject = a1;
    }
    
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        switch (this.errorType) {
            case 0: {
                v1.append("Unexpected character (").append(this.unexpectedObject).append(") at position ").append(this.position).append(".");
                break;
            }
            case 1: {
                v1.append("Unexpected token ").append(this.unexpectedObject).append(" at position ").append(this.position).append(".");
                break;
            }
            case 2: {
                v1.append("Unexpected exception at position ").append(this.position).append(": ").append(this.unexpectedObject);
                break;
            }
            default: {
                v1.append("Unkown error at position ").append(this.position).append(".");
                break;
            }
        }
        return v1.toString();
    }
}
