package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.tokens.*;

private class ParseFlowSequenceEntryMappingEnd implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseFlowSequenceEntryMappingEnd(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowSequenceEntry(false));
        final Token token = this.this$0.scanner.peekToken();
        return new MappingEndEvent(token.getStartMark(), token.getEndMark());
    }
    
    ParseFlowSequenceEntryMappingEnd(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
