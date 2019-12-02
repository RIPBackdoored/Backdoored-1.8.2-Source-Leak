package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.external.biz.base64Coder.*;

public class ConstructYamlBinary extends AbstractConstruct
{
    final /* synthetic */ SafeConstructor this$0;
    
    public ConstructYamlBinary(final SafeConstructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object construct(final Node node) {
        final String noWhiteSpaces = this.this$0.constructScalar((ScalarNode)node).toString().replaceAll("\\s", "");
        final byte[] decoded = Base64Coder.decode(noWhiteSpaces.toCharArray());
        return decoded;
    }
}
