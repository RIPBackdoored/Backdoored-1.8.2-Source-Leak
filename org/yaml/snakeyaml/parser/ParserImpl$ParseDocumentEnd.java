package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.error.*;

private class ParseDocumentEnd implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseDocumentEnd(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        Token token = this.this$0.scanner.peekToken();
        Mark endMark;
        final Mark startMark = endMark = token.getStartMark();
        boolean explicit = false;
        if (this.this$0.scanner.checkToken(Token.ID.DocumentEnd)) {
            token = this.this$0.scanner.getToken();
            endMark = token.getEndMark();
            explicit = true;
        }
        final Event event = new DocumentEndEvent(startMark, endMark, explicit);
        ParserImpl.access$102(this.this$0, this.this$0.new ParseDocumentStart());
        return event;
    }
    
    ParseDocumentEnd(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
