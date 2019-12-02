package org.spongepowered.asm.mixin;

import org.apache.logging.log4j.core.appender.*;
import java.io.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;

static class MixinAppender extends AbstractAppender
{
    protected MixinAppender(final String a1, final Filter a2, final Layout<? extends Serializable> a3) {
        super(a1, a2, (Layout)a3);
    }
    
    public void append(final LogEvent a1) {
        if (a1.getLevel() == Level.DEBUG && "Validating minecraft".equals(a1.getMessage().getFormat())) {
            MixinEnvironment.gotoPhase(Phase.INIT);
        }
    }
}
