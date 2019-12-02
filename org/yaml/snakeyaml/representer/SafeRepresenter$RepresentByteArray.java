package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.external.biz.base64Coder.*;
import org.yaml.snakeyaml.nodes.*;

protected class RepresentByteArray implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentByteArray(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        final char[] binary = Base64Coder.encode((byte[])data);
        return this.this$0.representScalar(Tag.BINARY, String.valueOf(binary), '|');
    }
}
