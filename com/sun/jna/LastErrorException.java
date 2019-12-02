package com.sun.jna;

public class LastErrorException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private int errorCode;
    
    private static String formatMessage(final int a1) {
        return Platform.isWindows() ? ("GetLastError() returned " + a1) : ("errno was " + a1);
    }
    
    private static String parseMessage(final String v1) {
        try {
            return formatMessage(Integer.parseInt(v1));
        }
        catch (NumberFormatException a1) {
            return v1;
        }
    }
    
    public int getErrorCode() {
        return this.errorCode;
    }
    
    public LastErrorException(String v2) {
        super(parseMessage(v2.trim()));
        try {
            if (v2.startsWith("[")) {
                v2 = v2.substring(1, v2.indexOf("]"));
            }
            this.errorCode = Integer.parseInt(v2);
        }
        catch (NumberFormatException a1) {
            this.errorCode = -1;
        }
    }
    
    public LastErrorException(final int a1) {
        this(a1, formatMessage(a1));
    }
    
    protected LastErrorException(final int a1, final String a2) {
        super(a2);
        this.errorCode = a1;
    }
}
