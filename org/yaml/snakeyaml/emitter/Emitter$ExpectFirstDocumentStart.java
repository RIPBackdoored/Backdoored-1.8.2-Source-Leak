package org.yaml.snakeyaml.emitter;

import java.io.*;

private class ExpectFirstDocumentStart implements EmitterState
{
    final /* synthetic */ Emitter this$0;
    
    private ExpectFirstDocumentStart(final Emitter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void expect() throws IOException {
        this.this$0.new ExpectDocumentStart(true).expect();
    }
    
    ExpectFirstDocumentStart(final Emitter x0, final Emitter$1 x1) {
        this(x0);
    }
}
