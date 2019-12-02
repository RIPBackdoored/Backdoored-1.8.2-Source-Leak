package org.yaml.snakeyaml;

import java.util.*;

class Yaml$1 implements Iterator<Object> {
    final /* synthetic */ Yaml this$0;
    
    Yaml$1(final Yaml a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.this$0.constructor.checkData();
    }
    
    @Override
    public Object next() {
        return this.this$0.constructor.getData();
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}