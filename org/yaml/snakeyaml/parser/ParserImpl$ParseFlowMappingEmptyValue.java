package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.*;

private class ParseFlowMappingEmptyValue implements Production
{
    final /* synthetic */ ParserImpl this$0;
    
    private ParseFlowMappingEmptyValue(final ParserImpl this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Event produce() {
        ParserImpl.access$102(this.this$0, this.this$0.new ParseFlowMappingKey(false));
        return ParserImpl.access$1200(this.this$0, this.this$0.scanner.peekToken().getStartMark());
    }
    
    ParseFlowMappingEmptyValue(final ParserImpl x0, final ParserImpl$1 x1) {
        this(x0);
    }
}
