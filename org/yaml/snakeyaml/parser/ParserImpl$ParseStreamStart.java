package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.events.*;

private class ParseStreamStart implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseStreamStart(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        final StreamStartToken token = (StreamStartToken)this.this$0.scanner.getToken();
        final Event event = new StreamStartEvent(token.getStartMark(), token.getEndMark());
        ParserImpl.access$102(this.this$0, this.this$0.new ParseImplicitDocumentStart());
        return event;
    }
    
    ParseStreamStart(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
