package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;

protected class RepresentList implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentList(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        return this.this$0.representSequence(this.this$0.getTag(data.getClass(), Tag.SEQ), (Iterable<?>)data, null);
    }
}
