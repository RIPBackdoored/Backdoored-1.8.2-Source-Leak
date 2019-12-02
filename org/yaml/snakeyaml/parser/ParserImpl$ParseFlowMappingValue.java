package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.tokens.*;

private class ParseFlowMappingValue implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseFlowMappingValue(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        if (!this.this$0.scanner.checkToken(Token.ID.Value)) {
            ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowMappingKey(false));
            final Token token = this.this$0.scanner.peekToken();
            return ParserImpl.access$1200(this.this$0, token.getStartMark());
        }
        final Token token = this.this$0.scanner.getToken();
        if (!this.this$0.scanner.checkToken(Token.ID.FlowEntry, Token.ID.FlowMappingEnd)) {
            ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowMappingKey(false));
            return ParserImpl.access$2400(this.this$0);
        }
        ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowMappingKey(false));
        return ParserImpl.access$1200(this.this$0, token.getEndMark());
    }
    
    ParseFlowMappingValue(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
