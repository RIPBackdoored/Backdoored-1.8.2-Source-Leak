package org.yaml.snakeyaml.introspector;

import java.lang.reflect.*;
import java.util.*;
import org.yaml.snakeyaml.error.*;

public class PropertyUtils
{
    private final Map<Class<?>, Map<String, Property>> propertiesCache;
    private final Map<Class<?>, Set<Property>> readableProperties;
    private BeanAccess beanAccess;
    private boolean allowReadOnlyProperties;
    private boolean skipMissingProperties;
    
    public PropertyUtils() {
        super();
        this.propertiesCache = new HashMap<Class<?>, Map<String, Property>>();
        this.readableProperties = new HashMap<Class<?>, Set<Property>>();
        this.beanAccess = BeanAccess.DEFAULT;
        this.allowReadOnlyProperties = false;
        this.skipMissingProperties = false;
    }
    
    protected Map<String, Property> getPropertiesMap(final Class<?> type, final BeanAccess bAccess) {
        if (this.propertiesCache.containsKey(type)) {
            return this.propertiesCache.get(type);
        }
        final Map<String, Property> properties = new LinkedHashMap<String, Property>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            for (final Field field : c.getDeclaredFields()) {
                final int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers) && !properties.containsKey(field.getName())) {
                    properties.put(field.getName(), new FieldProperty(field));
                }
            }
        }
        this.propertiesCache.put(type, properties);
        return properties;
    }
    
    public Set<Property> getProperties(final Class<?> type) {
        return this.getProperties(type, this.beanAccess);
    }
    
    public Set<Property> getProperties(final Class<?> type, final BeanAccess bAccess) {
        if (this.readableProperties.containsKey(type)) {
            return this.readableProperties.get(type);
        }
        final Set<Property> properties = this.createPropertySet(type, bAccess);
        this.readableProperties.put(type, properties);
        return properties;
    }
    
    protected Set<Property> createPropertySet(final Class<?> type, final BeanAccess bAccess) {
        final Set<Property> properties = new TreeSet<Property>();
        final Collection<Property> props = this.getPropertiesMap(type, bAccess).values();
        for (final Property property : props) {
            if (property.isReadable() && (this.allowReadOnlyProperties || property.isWritable())) {
                properties.add(property);
            }
        }
        return properties;
    }
    
    public Property getProperty(final Class<?> type, final String name) {
        return this.getProperty(type, name, this.beanAccess);
    }
    
    public Property getProperty(final Class<?> type, final String name, final BeanAccess bAccess) {
        final Map<String, Property> properties = this.getPropertiesMap(type, bAccess);
        Property property = properties.get(name);
        if (property == null && this.skipMissingProperties) {
            property = new MissingProperty(name);
        }
        if (property == null || !property.isWritable()) {
            throw new YAMLException("Unable to find property '" + name + "' on class: " + type.getName());
        }
        return property;
    }
    
    public void setAllowReadOnlyProperties(final boolean allowReadOnlyProperties) {
        if (this.allowReadOnlyProperties != allowReadOnlyProperties) {
            this.allowReadOnlyProperties = allowReadOnlyProperties;
            this.readableProperties.clear();
        }
    }
    
    public void setSkipMissingProperties(final boolean skipMissingProperties) {
        if (this.skipMissingProperties != skipMissingProperties) {
            this.skipMissingProperties = skipMissingProperties;
            this.readableProperties.clear();
        }
    }
}
