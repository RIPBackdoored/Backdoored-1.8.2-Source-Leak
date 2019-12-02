package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;

protected class ConstructSequence implements Construct
{
    final /* synthetic */ Constructor this$0;
    
    protected ConstructSequence(final Constructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object construct(final Node node) {
        final SequenceNode snode = (SequenceNode)node;
        if (Set.class.isAssignableFrom(node.getType())) {
            if (node.isTwoStepsConstruction()) {
                throw new YAMLException("Set cannot be recursive.");
            }
            return this.this$0.constructSet(snode);
        }
        else if (Collection.class.isAssignableFrom(node.getType())) {
            if (node.isTwoStepsConstruction()) {
                return this.this$0.createDefaultList(snode.getValue().size());
            }
            return this.this$0.constructSequence(snode);
        }
        else {
            if (!node.getType().isArray()) {
                final List<java.lang.reflect.Constructor<?>> possibleConstructors = new ArrayList<java.lang.reflect.Constructor<?>>(snode.getValue().size());
                for (final java.lang.reflect.Constructor<?> constructor : node.getType().getDeclaredConstructors()) {
                    if (snode.getValue().size() == constructor.getParameterTypes().length) {
                        possibleConstructors.add(constructor);
                    }
                }
                if (!possibleConstructors.isEmpty()) {
                    if (possibleConstructors.size() == 1) {
                        final Object[] argumentList = new Object[snode.getValue().size()];
                        final java.lang.reflect.Constructor<?> c = possibleConstructors.get(0);
                        int index = 0;
                        for (final Node argumentNode : snode.getValue()) {
                            final Class<?> type = c.getParameterTypes()[index];
                            argumentNode.setType(type);
                            argumentList[index++] = this.this$0.constructObject(argumentNode);
                        }
                        try {
                            c.setAccessible(true);
                            return c.newInstance(argumentList);
                        }
                        catch (Exception e) {
                            throw new YAMLException(e);
                        }
                    }
                    final List<Object> argumentList2 = (List<Object>)this.this$0.constructSequence(snode);
                    final Class<?>[] parameterTypes = (Class<?>[])new Class[argumentList2.size()];
                    int index = 0;
                    for (final Object parameter : argumentList2) {
                        parameterTypes[index] = parameter.getClass();
                        ++index;
                    }
                    for (final java.lang.reflect.Constructor<?> c2 : possibleConstructors) {
                        final Class<?>[] argTypes = c2.getParameterTypes();
                        boolean foundConstructor = true;
                        for (int i = 0; i < argTypes.length; ++i) {
                            if (!this.wrapIfPrimitive(argTypes[i]).isAssignableFrom(parameterTypes[i])) {
                                foundConstructor = false;
                                break;
                            }
                        }
                        if (foundConstructor) {
                            try {
                                c2.setAccessible(true);
                                return c2.newInstance(argumentList2.toArray());
                            }
                            catch (Exception e2) {
                                throw new YAMLException(e2);
                            }
                        }
                    }
                }
                throw new YAMLException("No suitable constructor with " + String.valueOf(snode.getValue().size()) + " arguments found for " + node.getType());
            }
            if (node.isTwoStepsConstruction()) {
                return this.this$0.createArray(node.getType(), snode.getValue().size());
            }
            return this.this$0.constructArray(snode);
        }
    }
    
    private final Class<?> wrapIfPrimitive(final Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return clazz;
        }
        if (clazz == Integer.TYPE) {
            return Integer.class;
        }
        if (clazz == Float.TYPE) {
            return Float.class;
        }
        if (clazz == Double.TYPE) {
            return Double.class;
        }
        if (clazz == Boolean.TYPE) {
            return Boolean.class;
        }
        if (clazz == Long.TYPE) {
            return Long.class;
        }
        if (clazz == Character.TYPE) {
            return Character.class;
        }
        if (clazz == Short.TYPE) {
            return Short.class;
        }
        if (clazz == Byte.TYPE) {
            return Byte.class;
        }
        throw new YAMLException("Unexpected primitive " + clazz);
    }
    
    @Override
    public void construct2ndStep(final Node node, final Object object) {
        final SequenceNode snode = (SequenceNode)node;
        if (List.class.isAssignableFrom(node.getType())) {
            final List<Object> list = (List<Object>)object;
            this.this$0.constructSequenceStep2(snode, list);
        }
        else {
            if (!node.getType().isArray()) {
                throw new YAMLException("Immutable objects cannot be recursive.");
            }
            this.this$0.constructArrayStep2(snode, object);
        }
    }
}
