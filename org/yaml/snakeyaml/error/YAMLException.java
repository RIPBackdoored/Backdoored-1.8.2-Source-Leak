package org.yaml.snakeyaml.error;

public class YAMLException extends RuntimeException
{
    public YAMLException(final String message) {
        super(message);
    }
    
    public YAMLException(final Throwable cause) {
        super(cause);
    }
    
    public YAMLException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
