package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.*;
import java.util.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.error.*;

private class ParseImplicitDocumentStart implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseImplicitDocumentStart(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        if (!this.this$0.scanner.checkToken(Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd)) {
            ParserImpl.access$302(this.this$0, new VersionTagsTuple(null, ParserImpl.access$400()));
            final Token token = this.this$0.scanner.peekToken();
            final Mark endMark;
            final Mark startMark = endMark = token.getStartMark();
            final Event event = new DocumentStartEvent(startMark, endMark, false, null, null);
            ParserImpl.access$600(this.this$0).push(this.this$0.new ParseDocumentEnd());
            ParserImpl.access$102(this.this$0, this.this$0.new ParseBlockNode());
            return event;
        }
        final Production p = this.this$0.new ParseDocumentStart();
        return p.produce();
    }
    
    ParseImplicitDocumentStart(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
