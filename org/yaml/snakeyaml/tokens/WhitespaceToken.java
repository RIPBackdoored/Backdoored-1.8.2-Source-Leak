package org.yaml.snakeyaml.tokens;

import org.yaml.snakeyaml.error.*;

public class WhitespaceToken extends Token
{
    public WhitespaceToken(final Mark a1, final Mark a2) {
        super(a1, a2);
    }
    
    @Override
    public ID getTokenId() {
        return ID.Whitespace;
    }
}
