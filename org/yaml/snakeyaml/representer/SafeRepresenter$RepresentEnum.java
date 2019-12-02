package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;

protected class RepresentEnum implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentEnum(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        final Tag tag = new Tag(data.getClass());
        return this.this$0.representScalar(this.this$0.getTag(data.getClass(), tag), ((Enum)data).name());
    }
}
