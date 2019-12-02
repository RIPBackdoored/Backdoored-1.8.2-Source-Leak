package org.spongepowered.tools.obfuscation;

import java.util.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;

static class AliasedElementName
{
    protected final String originalName;
    private final List<String> aliases;
    private boolean caseSensitive;
    
    public AliasedElementName(final Element a1, final AnnotationHandle a2) {
        super();
        this.originalName = a1.getSimpleName().toString();
        this.aliases = a2.getList("aliases");
    }
    
    public AliasedElementName setCaseSensitive(final boolean a1) {
        this.caseSensitive = a1;
        return this;
    }
    
    public boolean isCaseSensitive() {
        return this.caseSensitive;
    }
    
    public boolean hasAliases() {
        return this.aliases.size() > 0;
    }
    
    public List<String> getAliases() {
        return this.aliases;
    }
    
    public String elementName() {
        return this.originalName;
    }
    
    public String baseName() {
        return this.originalName;
    }
    
    public boolean hasPrefix() {
        return false;
    }
}
