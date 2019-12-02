package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.error.*;

public class ScannerException extends MarkedYAMLException
{
    public ScannerException(final String context, final Mark contextMark, final String problem, final Mark problemMark, final String note) {
        super(context, contextMark, problem, problemMark, note);
    }
    
    public ScannerException(final String context, final Mark contextMark, final String problem, final Mark problemMark) {
        this(context, contextMark, problem, problemMark, (String)null);
    }
}
