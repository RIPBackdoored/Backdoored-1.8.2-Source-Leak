package org.reflections;

public class ReflectionsException extends RuntimeException
{
    public ReflectionsException(final String a1) {
        super(a1);
    }
    
    public ReflectionsException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
    
    public ReflectionsException(final Throwable a1) {
        super(a1);
    }
}
