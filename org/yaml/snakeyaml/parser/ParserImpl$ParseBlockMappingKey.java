package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.events.*;

private class ParseBlockMappingKey implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseBlockMappingKey(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        if (this.this$0.scanner.checkToken(Token.ID.Key)) {
            final Token token = this.this$0.scanner.getToken();
            if (!this.this$0.scanner.checkToken(Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
                ParserImpl.access$600(this.this$0).push(this.this$0.new ParseBlockMappingValue());
                return ParserImpl.access$2200(this.this$0);
            }
            ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockMappingValue());
            return ParserImpl.access$1200(this.this$0, token.getEndMark());
        }
        else {
            if (!this.this$0.scanner.checkToken(Token.ID.BlockEnd)) {
                final Token token = this.this$0.scanner.peekToken();
                throw new ParserException("while parsing a block mapping", ParserImpl.access$1100(this.this$0).pop(), "expected <block end>, but found " + token.getTokenId(), token.getStartMark());
            }
            final Token token = this.this$0.scanner.getToken();
            final Event event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.access$102(this.this$0, ParserImpl.access$600(this.this$0).pop());
            ParserImpl.access$1100(this.this$0).pop();
            return event;
        }
    }
    
    ParseBlockMappingKey(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
