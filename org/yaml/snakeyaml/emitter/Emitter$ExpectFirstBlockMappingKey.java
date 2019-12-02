package org.yaml.snakeyaml.emitter;

import java.io.*;

private class ExpectFirstBlockMappingKey implements EmitterState
{
    final /* synthetic */ Emitter this$0;
    
    private ExpectFirstBlockMappingKey(final Emitter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public void expect() throws IOException {
        this.this$0.new ExpectBlockMappingKey(true).expect();
    }
    
    ExpectFirstBlockMappingKey(final Emitter x0, final Emitter$1 x1) {
        this(x0);
    }
}
