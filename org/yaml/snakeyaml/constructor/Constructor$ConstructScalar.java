package org.yaml.snakeyaml.constructor;

import java.math.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;

protected class ConstructScalar extends AbstractConstruct
{
    final /* synthetic */ Constructor this$0;
    
    protected ConstructScalar(final Constructor this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Object construct(final Node nnode) {
        final ScalarNode node = (ScalarNode)nnode;
        final Class<?> type = node.getType();
        Object result;
        if (type.isPrimitive() || type == String.class || Number.class.isAssignableFrom(type) || type == Boolean.class || Date.class.isAssignableFrom(type) || type == Character.class || type == BigInteger.class || type == BigDecimal.class || Enum.class.isAssignableFrom(type) || Tag.BINARY.equals(node.getTag()) || Calendar.class.isAssignableFrom(type) || type == UUID.class) {
            result = this.constructStandardJavaInstance(type, node);
        }
        else {
            final java.lang.reflect.Constructor<?>[] javaConstructors = type.getDeclaredConstructors();
            int oneArgCount = 0;
            java.lang.reflect.Constructor<?> javaConstructor = null;
            for (final java.lang.reflect.Constructor<?> c : javaConstructors) {
                if (c.getParameterTypes().length == 1) {
                    ++oneArgCount;
                    javaConstructor = c;
                }
            }
            if (javaConstructor == null) {
                throw new YAMLException("No single argument constructor found for " + type);
            }
            Object argument;
            if (oneArgCount == 1) {
                argument = this.constructStandardJavaInstance(javaConstructor.getParameterTypes()[0], node);
            }
            else {
                argument = this.this$0.constructScalar(node);
                try {
                    javaConstructor = type.getDeclaredConstructor(String.class);
                }
                catch (Exception e) {
                    throw new YAMLException("Can't construct a java object for scalar " + node.getTag() + "; No String constructor found. Exception=" + e.getMessage(), e);
                }
            }
            try {
                javaConstructor.setAccessible(true);
                result = javaConstructor.newInstance(argument);
            }
            catch (Exception e) {
                throw new ConstructorException(null, null, "Can't construct a java object for scalar " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
            }
        }
        return result;
    }
    
    private Object constructStandardJavaInstance(final Class type, final ScalarNode node) {
        Object result;
        if (type == String.class) {
            final Construct stringConstructor = this.this$0.yamlConstructors.get(Tag.STR);
            result = stringConstructor.construct(node);
        }
        else if (type == Boolean.class || type == Boolean.TYPE) {
            final Construct boolConstructor = this.this$0.yamlConstructors.get(Tag.BOOL);
            result = boolConstructor.construct(node);
        }
        else if (type == Character.class || type == Character.TYPE) {
            final Construct charConstructor = this.this$0.yamlConstructors.get(Tag.STR);
            final String ch = (String)charConstructor.construct(node);
            if (ch.length() == 0) {
                result = null;
            }
            else {
                if (ch.length() != 1) {
                    throw new YAMLException("Invalid node Character: '" + ch + "'; length: " + ch.length());
                }
                result = ch.charAt(0);
            }
        }
        else if (Date.class.isAssignableFrom(type)) {
            final Construct dateConstructor = this.this$0.yamlConstructors.get(Tag.TIMESTAMP);
            final Date date = (Date)dateConstructor.construct(node);
            if (type == Date.class) {
                result = date;
            }
            else {
                try {
                    final java.lang.reflect.Constructor<?> constr = type.getConstructor(Long.TYPE);
                    result = constr.newInstance(date.getTime());
                }
                catch (RuntimeException e) {
                    throw e;
                }
                catch (Exception e2) {
                    throw new YAMLException("Cannot construct: '" + type + "'");
                }
            }
        }
        else if (type == Float.class || type == Double.class || type == Float.TYPE || type == Double.TYPE || type == BigDecimal.class) {
            if (type == BigDecimal.class) {
                result = new BigDecimal(node.getValue());
            }
            else {
                final Construct doubleConstructor = this.this$0.yamlConstructors.get(Tag.FLOAT);
                result = doubleConstructor.construct(node);
                if (type == Float.class || type == Float.TYPE) {
                    result = new Float((double)result);
                }
            }
        }
        else if (type == Byte.class || type == Short.class || type == Integer.class || type == Long.class || type == BigInteger.class || type == Byte.TYPE || type == Short.TYPE || type == Integer.TYPE || type == Long.TYPE) {
            final Construct intConstructor = this.this$0.yamlConstructors.get(Tag.INT);
            result = intConstructor.construct(node);
            if (type == Byte.class || type == Byte.TYPE) {
                result = Byte.valueOf(result.toString());
            }
            else if (type == Short.class || type == Short.TYPE) {
                result = Short.valueOf(result.toString());
            }
            else if (type == Integer.class || type == Integer.TYPE) {
                result = Integer.parseInt(result.toString());
            }
            else if (type == Long.class || type == Long.TYPE) {
                result = Long.valueOf(result.toString());
            }
            else {
                result = new BigInteger(result.toString());
            }
        }
        else if (Enum.class.isAssignableFrom(type)) {
            final String enumValueName = node.getValue();
            try {
                result = Enum.valueOf((Class<Object>)type, enumValueName);
            }
            catch (Exception ex) {
                throw new YAMLException("Unable to find enum value '" + enumValueName + "' for enum class: " + type.getName());
            }
        }
        else if (Calendar.class.isAssignableFrom(type)) {
            final ConstructYamlTimestamp contr = new ConstructYamlTimestamp();
            contr.construct(node);
            result = contr.getCalendar();
        }
        else if (Number.class.isAssignableFrom(type)) {
            final ConstructYamlNumber contr2 = this.this$0.new ConstructYamlNumber();
            result = contr2.construct(node);
        }
        else if (UUID.class == type) {
            result = UUID.fromString(node.getValue());
        }
        else {
            if (!this.this$0.yamlConstructors.containsKey(node.getTag())) {
                throw new YAMLException("Unsupported class: " + type);
            }
            result = this.this$0.yamlConstructors.get(node.getTag()).construct(node);
        }
        return result;
    }
}
