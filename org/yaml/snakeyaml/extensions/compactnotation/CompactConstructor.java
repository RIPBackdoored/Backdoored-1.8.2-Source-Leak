package org.yaml.snakeyaml.extensions.compactnotation;

import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.introspector.*;
import java.util.regex.*;
import java.util.*;
import org.yaml.snakeyaml.nodes.*;

public class CompactConstructor extends Constructor
{
    private static final Pattern GUESS_COMPACT;
    private static final Pattern FIRST_PATTERN;
    private static final Pattern PROPERTY_NAME_PATTERN;
    private Construct compactConstruct;
    
    public CompactConstructor() {
        super();
    }
    
    protected Object constructCompactFormat(final ScalarNode v-1, final CompactData v0) {
        try {
            final Object a1 = this.createInstance(v-1, v0);
            final Map<String, Object> a2 = new HashMap<String, Object>(v0.getProperties());
            this.setProperties(a1, a2);
            return a1;
        }
        catch (Exception v) {
            throw new YAMLException(v);
        }
    }
    
    protected Object createInstance(final ScalarNode v1, final CompactData v2) throws Exception {
        final Class<?> v3 = this.getClassForName(v2.getPrefix());
        final Class<?>[] v4 = (Class<?>[])new Class[v2.getArguments().size()];
        for (int a1 = 0; a1 < v4.length; ++a1) {
            v4[a1] = String.class;
        }
        final java.lang.reflect.Constructor<?> v5 = v3.getDeclaredConstructor(v4);
        v5.setAccessible(true);
        return v5.newInstance(v2.getArguments().toArray());
    }
    
    protected void setProperties(final Object v-4, final Map<String, Object> v-3) throws Exception {
        if (v-3 == null) {
            throw new NullPointerException("Data for Compact Object Notation cannot be null.");
        }
        for (final Map.Entry<String, Object> entry : v-3.entrySet()) {
            final String a2 = entry.getKey();
            final Property v1 = this.getPropertyUtils().getProperty(v-4.getClass(), a2);
            try {
                v1.set(v-4, entry.getValue());
            }
            catch (IllegalArgumentException a3) {
                throw new YAMLException("Cannot set property='" + a2 + "' with value='" + v-3.get(a2) + "' (" + v-3.get(a2).getClass() + ") in " + v-4);
            }
        }
    }
    
    public CompactData getCompactData(final String v-9) {
        if (!v-9.endsWith(")")) {
            return null;
        }
        if (v-9.indexOf(40) < 0) {
            return null;
        }
        final Matcher matcher = CompactConstructor.FIRST_PATTERN.matcher(v-9);
        if (!matcher.matches()) {
            return null;
        }
        final String trim = matcher.group(1).trim();
        final String group = matcher.group(3);
        final CompactData compactData = new CompactData(trim);
        if (group.length() == 0) {
            return compactData;
        }
        final String[] split = group.split("\\s*,\\s*");
        for (int i = 0; i < split.length; ++i) {
            final String s = split[i];
            if (s.indexOf(61) < 0) {
                compactData.getArguments().add(s);
            }
            else {
                final Matcher matcher2 = CompactConstructor.PROPERTY_NAME_PATTERN.matcher(s);
                if (!matcher2.matches()) {
                    return null;
                }
                final String a1 = matcher2.group(1);
                final String v1 = matcher2.group(2).trim();
                compactData.getProperties().put(a1, v1);
            }
        }
        return compactData;
    }
    
    private Construct getCompactConstruct() {
        if (this.compactConstruct == null) {
            this.compactConstruct = this.createCompactConstruct();
        }
        return this.compactConstruct;
    }
    
    protected Construct createCompactConstruct() {
        return new ConstructCompactObject();
    }
    
    @Override
    protected Construct getConstructor(final Node v-2) {
        if (v-2 instanceof MappingNode) {
            final MappingNode mappingNode = (MappingNode)v-2;
            final List<NodeTuple> v0 = mappingNode.getValue();
            if (v0.size() == 1) {
                final NodeTuple v2 = v0.get(0);
                final Node v3 = v2.getKeyNode();
                if (v3 instanceof ScalarNode) {
                    final ScalarNode a1 = (ScalarNode)v3;
                    if (CompactConstructor.GUESS_COMPACT.matcher(a1.getValue()).matches()) {
                        return this.getCompactConstruct();
                    }
                }
            }
        }
        else if (v-2 instanceof ScalarNode) {
            final ScalarNode scalarNode = (ScalarNode)v-2;
            if (CompactConstructor.GUESS_COMPACT.matcher(scalarNode.getValue()).matches()) {
                return this.getCompactConstruct();
            }
        }
        return super.getConstructor(v-2);
    }
    
    protected void applySequence(final Object v2, final List<?> v3) {
        try {
            final Property a1 = this.getPropertyUtils().getProperty(v2.getClass(), this.getSequencePropertyName(v2.getClass()));
            a1.set(v2, v3);
        }
        catch (Exception a2) {
            throw new YAMLException(a2);
        }
    }
    
    protected String getSequencePropertyName(final Class<?> v-1) {
        final Set<Property> v0 = this.getPropertyUtils().getProperties(v-1);
        final Iterator<Property> v2 = v0.iterator();
        while (v2.hasNext()) {
            final Property a1 = v2.next();
            if (!List.class.isAssignableFrom(a1.getType())) {
                v2.remove();
            }
        }
        if (v0.size() == 0) {
            throw new YAMLException("No list property found in " + v-1);
        }
        if (v0.size() > 1) {
            throw new YAMLException("Many list properties found in " + v-1 + "; Please override getSequencePropertyName() to specify which property to use.");
        }
        return v0.iterator().next().getName();
    }
    
    static /* synthetic */ List access$000(final CompactConstructor a1, final SequenceNode a2) {
        return a1.constructSequence(a2);
    }
    
    static /* synthetic */ Object access$100(final CompactConstructor a1, final ScalarNode a2) {
        return a1.constructScalar(a2);
    }
    
    static {
        GUESS_COMPACT = Pattern.compile("\\p{Alpha}.*\\s*\\((?:,?\\s*(?:(?:\\w*)|(?:\\p{Alpha}\\w*\\s*=.+))\\s*)+\\)");
        FIRST_PATTERN = Pattern.compile("(\\p{Alpha}.*)(\\s*)\\((.*?)\\)");
        PROPERTY_NAME_PATTERN = Pattern.compile("\\s*(\\p{Alpha}\\w*)\\s*=(.+)");
    }
    
    public class ConstructCompactObject extends ConstructMapping
    {
        final /* synthetic */ CompactConstructor this$0;
        
        public ConstructCompactObject(final CompactConstructor a1) {
            this.this$0 = a1;
            a1.super();
        }
        
        @Override
        public void construct2ndStep(final Node a1, final Object a2) {
            final MappingNode v1 = (MappingNode)a1;
            final NodeTuple v2 = v1.getValue().iterator().next();
            final Node v3 = v2.getValueNode();
            if (v3 instanceof MappingNode) {
                v3.setType(a2.getClass());
                this.constructJavaBean2ndStep((MappingNode)v3, a2);
            }
            else {
                this.this$0.applySequence(a2, BaseConstructor.this.constructSequence((SequenceNode)v3));
            }
        }
        
        @Override
        public Object construct(final Node v-2) {
            ScalarNode scalarNode = null;
            if (v-2 instanceof MappingNode) {
                final MappingNode a1 = (MappingNode)v-2;
                final NodeTuple v1 = a1.getValue().iterator().next();
                v-2.setTwoStepsConstruction(true);
                scalarNode = (ScalarNode)v1.getKeyNode();
            }
            else {
                scalarNode = (ScalarNode)v-2;
            }
            final CompactData v2 = this.this$0.getCompactData(scalarNode.getValue());
            if (v2 == null) {
                return BaseConstructor.this.constructScalar(scalarNode);
            }
            return this.this$0.constructCompactFormat(scalarNode, v2);
        }
    }
}
