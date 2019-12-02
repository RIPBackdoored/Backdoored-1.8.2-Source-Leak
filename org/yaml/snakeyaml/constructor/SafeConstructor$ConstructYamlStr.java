package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;

public class ConstructYamlStr extends AbstractConstruct
{
    final /* synthetic */ SafeConstructor this$0;
    
    public ConstructYamlStr(final SafeConstructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object construct(final Node node) {
        return this.this$0.constructScalar((ScalarNode)node);
    }
}
