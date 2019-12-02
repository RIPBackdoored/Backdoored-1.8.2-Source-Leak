package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.nodes.*;

public interface AnchorGenerator
{
    String nextAnchor(final Node p0);
}
