package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;

public class ConstructYamlPairs extends AbstractConstruct
{
    final /* synthetic */ SafeConstructor this$0;
    
    public ConstructYamlPairs(final SafeConstructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object construct(final Node node) {
        if (!(node instanceof SequenceNode)) {
            throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a sequence, but found " + node.getNodeId(), node.getStartMark());
        }
        final SequenceNode snode = (SequenceNode)node;
        final List<Object[]> pairs = new ArrayList<Object[]>(snode.getValue().size());
        for (final Node subnode : snode.getValue()) {
            if (!(subnode instanceof MappingNode)) {
                throw new ConstructorException("while constructingpairs", node.getStartMark(), "expected a mapping of length 1, but found " + subnode.getNodeId(), subnode.getStartMark());
            }
            final MappingNode mnode = (MappingNode)subnode;
            if (mnode.getValue().size() != 1) {
                throw new ConstructorException("while constructing pairs", node.getStartMark(), "expected a single mapping item, but found " + mnode.getValue().size() + " items", mnode.getStartMark());
            }
            final Node keyNode = mnode.getValue().get(0).getKeyNode();
            final Node valueNode = mnode.getValue().get(0).getValueNode();
            final Object key = this.this$0.constructObject(keyNode);
            final Object value = this.this$0.constructObject(valueNode);
            pairs.add(new Object[] { key, value });
        }
        return pairs;
    }
}
