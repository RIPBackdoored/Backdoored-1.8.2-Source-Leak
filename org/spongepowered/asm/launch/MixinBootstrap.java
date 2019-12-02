package org.spongepowered.asm.launch;

import org.spongepowered.asm.launch.platform.*;
import org.spongepowered.asm.service.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;

public abstract class MixinBootstrap
{
    public static final String VERSION = "0.7.11";
    private static final Logger logger;
    private static boolean initialised;
    private static boolean initState;
    private static MixinPlatformManager platform;
    
    private MixinBootstrap() {
        super();
    }
    
    @Deprecated
    public static void addProxy() {
        MixinService.getService().beginPhase();
    }
    
    public static MixinPlatformManager getPlatform() {
        if (MixinBootstrap.platform == null) {
            final Object v1 = GlobalProperties.get("mixin.platform");
            if (v1 instanceof MixinPlatformManager) {
                MixinBootstrap.platform = (MixinPlatformManager)v1;
            }
            else {
                GlobalProperties.put("mixin.platform", MixinBootstrap.platform = new MixinPlatformManager());
                MixinBootstrap.platform.init();
            }
        }
        return MixinBootstrap.platform;
    }
    
    public static void init() {
        if (!start()) {
            return;
        }
        doInit(null);
    }
    
    static boolean start() {
        if (!isSubsystemRegistered()) {
            registerSubsystem("0.7.11");
            if (!MixinBootstrap.initialised) {
                MixinBootstrap.initialised = true;
                final String v1 = System.getProperty("sun.java.command");
                if (v1 != null && v1.contains("GradleStart")) {
                    System.setProperty("mixin.env.remapRefMap", "true");
                }
                final MixinEnvironment.Phase v2 = MixinService.getService().getInitialPhase();
                if (v2 == MixinEnvironment.Phase.DEFAULT) {
                    MixinBootstrap.logger.error("Initialising mixin subsystem after game pre-init phase! Some mixins may be skipped.");
                    MixinEnvironment.init(v2);
                    getPlatform().prepare(null);
                    MixinBootstrap.initState = false;
                }
                else {
                    MixinEnvironment.init(v2);
                }
                MixinService.getService().beginPhase();
            }
            getPlatform();
            return true;
        }
        if (!checkSubsystemVersion()) {
            throw new MixinInitialisationError("Mixin subsystem version " + getActiveSubsystemVersion() + " was already initialised. Cannot bootstrap version " + "0.7.11");
        }
        return false;
    }
    
    static void doInit(final List<String> a1) {
        if (MixinBootstrap.initialised) {
            getPlatform().getPhaseProviderClasses();
            if (MixinBootstrap.initState) {
                getPlatform().prepare(a1);
                MixinService.getService().init();
            }
            return;
        }
        if (isSubsystemRegistered()) {
            MixinBootstrap.logger.warn("Multiple Mixin containers present, init suppressed for 0.7.11");
            return;
        }
        throw new IllegalStateException("MixinBootstrap.doInit() called before MixinBootstrap.start()");
    }
    
    static void inject() {
        getPlatform().inject();
    }
    
    private static boolean isSubsystemRegistered() {
        return GlobalProperties.get("mixin.initialised") != null;
    }
    
    private static boolean checkSubsystemVersion() {
        return "0.7.11".equals(getActiveSubsystemVersion());
    }
    
    private static Object getActiveSubsystemVersion() {
        final Object v1 = GlobalProperties.get("mixin.initialised");
        return (v1 != null) ? v1 : "";
    }
    
    private static void registerSubsystem(final String a1) {
        GlobalProperties.put("mixin.initialised", a1);
    }
    
    static {
        logger = LogManager.getLogger("mixin");
        MixinBootstrap.initialised = false;
        MixinBootstrap.initState = true;
        MixinService.boot();
        MixinService.getService().prepare();
    }
}
