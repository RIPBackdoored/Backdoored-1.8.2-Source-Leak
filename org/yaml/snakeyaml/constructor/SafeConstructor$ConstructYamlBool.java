package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;

public class ConstructYamlBool extends AbstractConstruct
{
    final /* synthetic */ SafeConstructor this$0;
    
    public ConstructYamlBool(final SafeConstructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object construct(final Node node) {
        final String val = (String)this.this$0.constructScalar((ScalarNode)node);
        return SafeConstructor.access$000().get(val.toLowerCase());
    }
}
