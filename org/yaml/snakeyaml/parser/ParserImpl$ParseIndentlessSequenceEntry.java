package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.tokens.*;
import org.yaml.snakeyaml.events.*;

private class ParseIndentlessSequenceEntry implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseIndentlessSequenceEntry(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry)) {
            final Token token = this.this$0.scanner.peekToken();
            final Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
            ParserImpl.access$102(this.this$0, ParserImpl.access$600(this.this$0).pop());
            return event;
        }
        final Token token = this.this$0.scanner.getToken();
        if (!this.this$0.scanner.checkToken(Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd)) {
            ParserImpl.access$600(this.this$0).push(this.this$0.new ParseIndentlessSequenceEntry());
            return this.this$0.new ParseBlockNode().produce();
        }
        ParserImpl.access$102(this.this$0, this.this$0.new ParseIndentlessSequenceEntry());
        return ParserImpl.access$1200(this.this$0, token.getEndMark());
    }
    
    ParseIndentlessSequenceEntry(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
