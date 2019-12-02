package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.tokens.*;

private class ParseFlowSequenceEntryMappingKey implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseFlowSequenceEntryMappingKey(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        final Token token = this.this$0.scanner.getToken();
        if (!this.this$0.scanner.checkToken(Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd)) {
            ParserImpl.access$600(this.this$0).push(this.this$0.new ParseFlowSequenceEntryMappingValue());
            return ParserImpl.access$2400(this.this$0);
        }
        ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowSequenceEntryMappingValue());
        return ParserImpl.access$1200(this.this$0, token.getEndMark());
    }
    
    ParseFlowSequenceEntryMappingKey(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
