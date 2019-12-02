package org.yaml.snakeyaml;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;

private static class NodeIterable implements Iterable<Node>
{
    private Iterator<Node> iterator;
    
    public NodeIterable(final Iterator<Node> a1) {
        super();
        this.iterator = a1;
    }
    
    @Override
    public Iterator<Node> iterator() {
        return this.iterator;
    }
}
