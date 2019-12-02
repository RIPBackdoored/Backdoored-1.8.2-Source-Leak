package org.yaml.snakeyaml;

import org.yaml.snakeyaml.nodes.*;
import java.util.*;

public final class TypeDescription
{
    private final Class<?> type;
    private Tag tag;
    private Map<String, Class<?>> listProperties;
    private Map<String, Class<?>> keyProperties;
    private Map<String, Class<?>> valueProperties;
    
    public TypeDescription(final Class<?> clazz, final Tag tag) {
        super();
        this.type = clazz;
        this.tag = tag;
        this.listProperties = new HashMap<String, Class<?>>();
        this.keyProperties = new HashMap<String, Class<?>>();
        this.valueProperties = new HashMap<String, Class<?>>();
    }
    
    public TypeDescription(final Class<?> clazz) {
        this(clazz, null);
    }
    
    public Tag getTag() {
        return this.tag;
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public Class<?> getListPropertyType(final String property) {
        return this.listProperties.get(property);
    }
    
    public Class<?> getMapKeyType(final String property) {
        return this.keyProperties.get(property);
    }
    
    public Class<?> getMapValueType(final String property) {
        return this.valueProperties.get(property);
    }
    
    @Override
    public String toString() {
        return "TypeDescription for " + this.getType() + " (tag='" + this.getTag() + "')";
    }
}
