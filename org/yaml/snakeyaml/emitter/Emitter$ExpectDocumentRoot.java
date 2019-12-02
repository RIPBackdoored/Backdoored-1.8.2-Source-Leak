package org.yaml.snakeyaml.emitter;

import java.io.*;

private class ExpectDocumentRoot implements EmitterState
{
    final /* synthetic */ Emitter this$0;
    
    private ExpectDocumentRoot(final Emitter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void expect() throws IOException {
        Emitter.access$1500(this.this$0).push(this.this$0.new ExpectDocumentEnd());
        Emitter.access$1600(this.this$0, true, false, false);
    }
    
    ExpectDocumentRoot(final Emitter x0, final Emitter$1 x1) {
        this(x0);
    }
}
