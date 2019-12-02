package org.yaml.snakeyaml.extensions.compactnotation;

import org.yaml.snakeyaml.constructor.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;

public class ConstructCompactObject extends ConstructMapping
{
    final /* synthetic */ CompactConstructor this$0;
    
    public ConstructCompactObject(final CompactConstructor a1) {
        this.this$0 = a1;
        a1.super();
    }
    
    @Override
    public void construct2ndStep(final Node a1, final Object a2) {
        final MappingNode v1 = (MappingNode)a1;
        final NodeTuple v2 = v1.getValue().iterator().next();
        final Node v3 = v2.getValueNode();
        if (v3 instanceof MappingNode) {
            v3.setType(a2.getClass());
            this.constructJavaBean2ndStep((MappingNode)v3, a2);
        }
        else {
            this.this$0.applySequence(a2, CompactConstructor.access$000(this.this$0, (SequenceNode)v3));
        }
    }
    
    @Override
    public Object construct(final Node v-2) {
        ScalarNode scalarNode = null;
        if (v-2 instanceof MappingNode) {
            final MappingNode a1 = (MappingNode)v-2;
            final NodeTuple v1 = a1.getValue().iterator().next();
            v-2.setTwoStepsConstruction(true);
            scalarNode = (ScalarNode)v1.getKeyNode();
        }
        else {
            scalarNode = (ScalarNode)v-2;
        }
        final CompactData v2 = this.this$0.getCompactData(scalarNode.getValue());
        if (v2 == null) {
            return CompactConstructor.access$100(this.this$0, scalarNode);
        }
        return this.this$0.constructCompactFormat(scalarNode, v2);
    }
}
