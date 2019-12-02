package org.spongepowered.asm.mixin;

import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.*;

static class MixinAppender extends AbstractAppender
{
    MixinAppender() {
        super("MixinLogWatcherAppender", (Filter)null, (Layout)null);
    }
    
    public void append(final LogEvent a1) {
        if (a1.getLevel() != Level.DEBUG || !"Validating minecraft".equals(a1.getMessage().getFormattedMessage())) {
            return;
        }
        MixinEnvironment.gotoPhase(Phase.INIT);
        if (MixinLogWatcher.log.getLevel() == Level.ALL) {
            MixinLogWatcher.log.setLevel(MixinLogWatcher.oldLevel);
        }
    }
}
