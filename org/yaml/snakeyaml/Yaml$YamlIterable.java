package org.yaml.snakeyaml;

import java.util.*;

private static class YamlIterable implements Iterable<Object>
{
    private Iterator<Object> iterator;
    
    public YamlIterable(final Iterator<Object> a1) {
        super();
        this.iterator = a1;
    }
    
    @Override
    public Iterator<Object> iterator() {
        return this.iterator;
    }
}
