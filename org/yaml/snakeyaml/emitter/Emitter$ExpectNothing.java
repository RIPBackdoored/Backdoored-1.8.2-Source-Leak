package org.yaml.snakeyaml.emitter;

import java.io.*;

private class ExpectNothing implements EmitterState
{
    final /* synthetic */ Emitter this$0;
    
    private ExpectNothing(final Emitter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void expect() throws IOException {
        throw new EmitterException("expecting nothing, but got " + Emitter.access$100(this.this$0));
    }
    
    ExpectNothing(final Emitter x0, final Emitter$1 x1) {
        this(x0);
    }
}
