package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.error.*;

public class ParserException extends MarkedYAMLException
{
    public ParserException(final String context, final Mark contextMark, final String problem, final Mark problemMark) {
        super(context, contextMark, problem, problemMark, null, null);
    }
}
