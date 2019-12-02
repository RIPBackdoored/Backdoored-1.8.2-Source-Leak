package org.yaml.snakeyaml;

import java.util.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.parser.*;

class Yaml$3 implements Iterator<Event> {
    final /* synthetic */ Parser val$parser;
    final /* synthetic */ Yaml this$0;
    
    Yaml$3(final Yaml a1, final Parser val$parser) {
        this.this$0 = a1;
        this.val$parser = val$parser;
        super();
    }
    
    @Override
    public boolean hasNext() {
        return this.val$parser.peekEvent() != null;
    }
    
    @Override
    public Event next() {
        return this.val$parser.getEvent();
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