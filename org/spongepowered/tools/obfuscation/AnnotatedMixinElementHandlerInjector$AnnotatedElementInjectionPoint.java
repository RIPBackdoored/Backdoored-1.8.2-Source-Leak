package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.tools.obfuscation.struct.*;
import javax.lang.model.element.*;
import java.util.*;

static class AnnotatedElementInjectionPoint extends AnnotatedElement<ExecutableElement>
{
    private final AnnotationHandle at;
    private Map<String, String> args;
    private final InjectorRemap state;
    
    public AnnotatedElementInjectionPoint(final ExecutableElement a1, final AnnotationHandle a2, final AnnotationHandle a3, final InjectorRemap a4) {
        super((Element)a1, a2);
        this.at = a3;
        this.state = a4;
    }
    
    public boolean shouldRemap() {
        return this.at.getBoolean("remap", this.state.shouldRemap());
    }
    
    public AnnotationHandle getAt() {
        return this.at;
    }
    
    public String getAtArg(final String v-1) {
        if (this.args == null) {
            this.args = new HashMap<String, String>();
            for (final String v1 : this.at.getList("args")) {
                if (v1 == null) {
                    continue;
                }
                final int a1 = v1.indexOf(61);
                if (a1 > -1) {
                    this.args.put(v1.substring(0, a1), v1.substring(a1 + 1));
                }
                else {
                    this.args.put(v1, "");
                }
            }
        }
        return this.args.get(v-1);
    }
    
    public void notifyRemapped() {
        this.state.notifyRemapped();
    }
}
