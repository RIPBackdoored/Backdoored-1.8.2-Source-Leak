package org.yaml.snakeyaml.emitter;

import java.io.*;

private class ExpectFlowMappingValue implements EmitterState
{
    final /* synthetic */ Emitter this$0;
    
    private ExpectFlowMappingValue(final Emitter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void expect() throws IOException {
        if (Emitter.access$1000(this.this$0) || Emitter.access$2100(this.this$0) > Emitter.access$2200(this.this$0) || Emitter.access$2400(this.this$0)) {
            this.this$0.writeIndent();
        }
        this.this$0.writeIndicator(":", true, false, false);
        Emitter.access$1500(this.this$0).push(this.this$0.new ExpectFlowMappingKey());
        Emitter.access$1600(this.this$0, false, true, false);
    }
    
    ExpectFlowMappingValue(final Emitter x0, final Emitter$1 x1) {
        this(x0);
    }
}
