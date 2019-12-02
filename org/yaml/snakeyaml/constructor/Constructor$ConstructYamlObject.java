package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.error.*;

protected class ConstructYamlObject implements Construct
{
    final /* synthetic */ Constructor this$0;
    
    protected ConstructYamlObject(final Constructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    private Construct getConstructor(final Node node) {
        final Class<?> cl = this.this$0.getClassForNode(node);
        node.setType(cl);
        final Construct constructor = this.this$0.yamlClassConstructors.get(node.getNodeId());
        return constructor;
    }
    
    @Override
    public Object construct(final Node node) {
        Object result = null;
        try {
            result = this.getConstructor(node).construct(node);
        }
        catch (ConstructorException e) {
            throw e;
        }
        catch (Exception e2) {
            throw new ConstructorException(null, null, "Can't construct a java object for " + node.getTag() + "; exception=" + e2.getMessage(), node.getStartMark(), e2);
        }
        return result;
    }
    
    @Override
    public void construct2ndStep(final Node node, final Object object) {
        try {
            this.getConstructor(node).construct2ndStep(node, object);
        }
        catch (Exception e) {
            throw new ConstructorException(null, null, "Can't construct a second step for a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
        }
    }
}
