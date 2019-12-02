package org.yaml.snakeyaml;

import java.util.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.composer.*;

class Yaml$2 implements Iterator<Node> {
    final /* synthetic */ Composer val$composer;
    final /* synthetic */ Yaml this$0;
    
    Yaml$2(final Yaml a1, final Composer val$composer) {
        this.this$0 = a1;
        this.val$composer = val$composer;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.val$composer.checkNode();
    }
    
    @Override
    public Node next() {
        return this.val$composer.getNode();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public /* bridge */ Object next() {
        return this.next();
    }
}