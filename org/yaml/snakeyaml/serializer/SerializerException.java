package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.error.*;

public class SerializerException extends YAMLException
{
    public SerializerException(final String message) {
        super(message);
    }
}
