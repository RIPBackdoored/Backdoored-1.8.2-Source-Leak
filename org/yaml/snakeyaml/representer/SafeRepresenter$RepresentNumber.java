package org.yaml.snakeyaml.representer;

import java.math.*;
import org.yaml.snakeyaml.nodes.*;

protected class RepresentNumber implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentNumber(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        Tag tag;
        String value;
        if (data instanceof Byte || data instanceof Short || data instanceof Integer || data instanceof Long || data instanceof BigInteger) {
            tag = Tag.INT;
            value = data.toString();
        }
        else {
            final Number number = (Number)data;
            tag = Tag.FLOAT;
            if (number.equals(Double.NaN)) {
                value = ".NaN";
            }
            else if (number.equals(Double.POSITIVE_INFINITY)) {
                value = ".inf";
            }
            else if (number.equals(Double.NEGATIVE_INFINITY)) {
                value = "-.inf";
            }
            else {
                value = number.toString();
            }
        }
        return this.this$0.representScalar(this.this$0.getTag(data.getClass(), tag), value);
    }
}
