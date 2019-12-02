package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;

protected class RepresentNull implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentNull(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        return this.this$0.representScalar(Tag.NULL, "null");
    }
}
