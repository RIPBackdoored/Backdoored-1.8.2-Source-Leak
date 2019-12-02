package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;

protected class RepresentArray implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentArray(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        final Object[] array = (Object[])data;
        final List<Object> list = Arrays.asList(array);
        return this.this$0.representSequence(Tag.SEQ, list, null);
    }
}
