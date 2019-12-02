package org.spongepowered.asm.mixin;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.*;

static class MixinLogWatcher
{
    static MixinAppender appender;
    static Logger log;
    static Level oldLevel;
    
    MixinLogWatcher() {
        super();
    }
    
    static void begin() {
        final org.apache.logging.log4j.Logger v1 = LogManager.getLogger("FML");
        if (!(v1 instanceof Logger)) {
            return;
        }
        MixinLogWatcher.log = (Logger)v1;
        MixinLogWatcher.oldLevel = MixinLogWatcher.log.getLevel();
        MixinLogWatcher.appender.start();
        MixinLogWatcher.log.addAppender((Appender)MixinLogWatcher.appender);
        MixinLogWatcher.log.setLevel(Level.ALL);
    }
    
    static void end() {
        if (MixinLogWatcher.log != null) {
            MixinLogWatcher.log.removeAppender((Appender)MixinLogWatcher.appender);
        }
    }
    
    static {
        MixinLogWatcher.appender = new MixinAppender();
        MixinLogWatcher.oldLevel = null;
    }
    
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
}
