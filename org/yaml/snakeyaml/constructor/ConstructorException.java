package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.error.*;

public class ConstructorException extends MarkedYAMLException
{
    protected ConstructorException(final String context, final Mark contextMark, final String problem, final Mark problemMark, final Throwable cause) {
        super(context, contextMark, problem, problemMark, cause);
    }
    
    protected ConstructorException(final String context, final Mark contextMark, final String problem, final Mark problemMark) {
        this(context, contextMark, problem, problemMark, (Throwable)null);
    }
}
