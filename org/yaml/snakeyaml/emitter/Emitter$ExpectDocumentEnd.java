package org.yaml.snakeyaml.emitter;

import org.yaml.snakeyaml.events.*;
import java.io.*;

private class ExpectDocumentEnd implements EmitterState
{
    final /* synthetic */ Emitter this$0;
    
    private ExpectDocumentEnd(final Emitter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void expect() throws IOException {
        if (Emitter.access$100(this.this$0) instanceof DocumentEndEvent) {
            this.this$0.writeIndent();
            if (((DocumentEndEvent)Emitter.access$100(this.this$0)).getExplicit()) {
                this.this$0.writeIndicator("...", true, false, false);
                this.this$0.writeIndent();
            }
            this.this$0.flushStream();
            Emitter.access$202(this.this$0, this.this$0.new ExpectDocumentStart(false));
            return;
        }
        throw new EmitterException("expected DocumentEndEvent, but got " + Emitter.access$100(this.this$0));
    }
    
    ExpectDocumentEnd(final Emitter x0, final Emitter$1 x1) {
        this(x0);
    }
}
