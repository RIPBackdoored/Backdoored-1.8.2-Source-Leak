package org.yaml.snakeyaml.representer;

import java.util.*;
import org.yaml.snakeyaml.nodes.*;

class BaseRepresenter$1 extends IdentityHashMap<Object, Node> {
    final /* synthetic */ BaseRepresenter this$0;
    
    BaseRepresenter$1(final BaseRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node put(final Object key, final Node value) {
        return super.put(key, new AnchorNode(value));
    }
    
    @Override
    public /* bridge */ Object put(final Object key, final Object o) {
        return this.put(key, (Node)o);
    }
}