package org.spongepowered.asm.mixin;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.launch.*;
import java.util.*;
import org.apache.logging.log4j.*;

public final class Mixins
{
    private static final Logger logger;
    private static final String CONFIGS_KEY = "mixin.configs.queue";
    private static final Set<String> errorHandlers;
    
    private Mixins() {
        super();
    }
    
    public static void addConfigurations(final String... v1) {
        final MixinEnvironment v2 = MixinEnvironment.getDefaultEnvironment();
        for (final String a1 : v1) {
            createConfiguration(a1, v2);
        }
    }
    
    public static void addConfiguration(final String a1) {
        createConfiguration(a1, MixinEnvironment.getDefaultEnvironment());
    }
    
    @Deprecated
    static void addConfiguration(final String a1, final MixinEnvironment a2) {
        createConfiguration(a1, a2);
    }
    
    private static void createConfiguration(final String a2, final MixinEnvironment v1) {
        Config v2 = null;
        try {
            v2 = Config.create(a2, v1);
        }
        catch (Exception a3) {
            Mixins.logger.error("Error encountered reading mixin config " + a2 + ": " + a3.getClass().getName() + " " + a3.getMessage(), (Throwable)a3);
        }
        registerConfiguration(v2);
    }
    
    private static void registerConfiguration(final Config a1) {
        if (a1 == null) {
            return;
        }
        final MixinEnvironment v1 = a1.getEnvironment();
        if (v1 != null) {
            v1.registerConfig(a1.getName());
        }
        getConfigs().add(a1);
    }
    
    public static int getUnvisitedCount() {
        int n = 0;
        for (final Config v1 : getConfigs()) {
            if (!v1.isVisited()) {
                ++n;
            }
        }
        return n;
    }
    
    public static Set<Config> getConfigs() {
        Set<Config> v1 = GlobalProperties.get("mixin.configs.queue");
        if (v1 == null) {
            v1 = new LinkedHashSet<Config>();
            GlobalProperties.put("mixin.configs.queue", v1);
        }
        return v1;
    }
    
    public static void registerErrorHandlerClass(final String a1) {
        if (a1 != null) {
            Mixins.errorHandlers.add(a1);
        }
    }
    
    public static Set<String> getErrorHandlerClasses() {
        return Collections.unmodifiableSet((Set<? extends String>)Mixins.errorHandlers);
    }
    
    static {
        logger = LogManager.getLogger("mixin");
        errorHandlers = new LinkedHashSet<String>();
    }
}
