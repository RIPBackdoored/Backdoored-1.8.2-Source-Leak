package org.yaml.snakeyaml.emitter;

import org.yaml.snakeyaml.events.*;
import java.io.*;

private class ExpectStreamStart implements EmitterState
{
    final /* synthetic */ Emitter this$0;
    
    private ExpectStreamStart(final Emitter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void expect() throws IOException {
        if (Emitter.access$100(this.this$0) instanceof StreamStartEvent) {
            this.this$0.writeStreamStart();
            Emitter.access$202(this.this$0, this.this$0.new ExpectFirstDocumentStart());
            return;
        }
        throw new EmitterException("expected StreamStartEvent, but got " + Emitter.access$100(this.this$0));
    }
    
    ExpectStreamStart(final Emitter x0, final Emitter$1 x1) {
        this(x0);
    }
}
