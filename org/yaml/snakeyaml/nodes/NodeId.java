package org.yaml.snakeyaml.nodes;

public enum NodeId
{
    scalar, 
    sequence, 
    mapping, 
    anchor;
    
    private static final /* synthetic */ NodeId[] $VALUES;
    
    public static NodeId[] values() {
        return NodeId.$VALUES.clone();
    }
    
    public static NodeId valueOf(final String name) {
        return Enum.valueOf(NodeId.class, name);
    }
    
    static {
        $VALUES = new NodeId[] { NodeId.scalar, NodeId.sequence, NodeId.mapping, NodeId.anchor };
    }
}
