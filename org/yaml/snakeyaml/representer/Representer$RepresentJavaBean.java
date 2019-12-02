package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;

protected class RepresentJavaBean implements Represent
{
    final /* synthetic */ Representer this$0;
    
    protected RepresentJavaBean(final Representer this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        return this.this$0.representJavaBean(this.this$0.getProperties(data.getClass()), data);
    }
}
