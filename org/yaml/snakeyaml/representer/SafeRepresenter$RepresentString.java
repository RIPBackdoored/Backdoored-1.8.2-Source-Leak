package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.reader.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.external.biz.base64Coder.*;
import java.io.*;

protected class RepresentString implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentString(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        Tag tag = Tag.STR;
        Character style = null;
        String value = data.toString();
        if (!StreamReader.isPrintable(value)) {
            tag = Tag.BINARY;
            char[] binary;
            try {
                final byte[] bytes = value.getBytes("UTF-8");
                final String checkValue = new String(bytes, "UTF-8");
                if (!checkValue.equals(value)) {
                    throw new YAMLException("invalid string value has occurred");
                }
                binary = Base64Coder.encode(bytes);
            }
            catch (UnsupportedEncodingException e) {
                throw new YAMLException(e);
            }
            value = String.valueOf(binary);
            style = '|';
        }
        if (this.this$0.defaultScalarStyle == null && SafeRepresenter.MULTILINE_PATTERN.matcher(value).find()) {
            style = '|';
        }
        return this.this$0.representScalar(tag, value, style);
    }
}
