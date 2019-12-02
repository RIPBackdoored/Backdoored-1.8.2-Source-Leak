package org.spongepowered.tools.obfuscation.struct;

import javax.tools.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.annotation.processing.*;

public class InjectorRemap
{
    private final boolean remap;
    private Message message;
    private int remappedCount;
    
    public InjectorRemap(final boolean a1) {
        super();
        this.remap = a1;
    }
    
    public boolean shouldRemap() {
        return this.remap;
    }
    
    public void notifyRemapped() {
        ++this.remappedCount;
        this.clearMessage();
    }
    
    public void addMessage(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationHandle a4) {
        this.message = new Message(a1, a2, a3, a4);
    }
    
    public void clearMessage() {
        this.message = null;
    }
    
    public void dispatchPendingMessages(final Messager a1) {
        if (this.remappedCount == 0 && this.message != null) {
            this.message.sendTo(a1);
        }
    }
}
