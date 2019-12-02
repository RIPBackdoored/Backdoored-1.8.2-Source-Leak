package org.yaml.snakeyaml.representer;

import java.util.*;

private static class IteratorWrapper implements Iterable<Object>
{
    private Iterator<Object> iter;
    
    public IteratorWrapper(final Iterator<Object> iter) {
        super();
        this.iter = iter;
    }
    
    @Override
    public Iterator<Object> iterator() {
        return this.iter;
    }
}
