package org.spongepowered.asm.launch.platform;

import java.util.*;
import org.spongepowered.asm.service.*;
import java.io.*;
import java.net.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;

public class MixinPlatformManager
{
    private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
    private static final String MIXIN_TWEAKER_CLASS = "org.spongepowered.asm.launch.MixinTweaker";
    private static final Logger logger;
    private final Map<URI, MixinContainer> containers;
    private MixinContainer primaryContainer;
    private boolean prepared;
    private boolean injected;
    
    public MixinPlatformManager() {
        super();
        this.containers = new LinkedHashMap<URI, MixinContainer>();
        this.prepared = false;
    }
    
    public void init() {
        MixinPlatformManager.logger.debug("Initialising Mixin Platform Manager");
        URI v0 = null;
        try {
            v0 = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            if (v0 != null) {
                MixinPlatformManager.logger.debug("Mixin platform: primary container is {}", new Object[] { v0 });
                this.primaryContainer = this.addContainer(v0);
            }
        }
        catch (URISyntaxException v2) {
            v2.printStackTrace();
        }
        this.scanClasspath();
    }
    
    public Collection<String> getPhaseProviderClasses() {
        final Collection<String> v1 = this.primaryContainer.getPhaseProviders();
        if (v1 != null) {
            return Collections.unmodifiableCollection((Collection<? extends String>)v1);
        }
        return (Collection<String>)Collections.emptyList();
    }
    
    public final MixinContainer addContainer(final URI a1) {
        final MixinContainer v1 = this.containers.get(a1);
        if (v1 != null) {
            return v1;
        }
        MixinPlatformManager.logger.debug("Adding mixin platform agents for container {}", new Object[] { a1 });
        final MixinContainer v2 = new MixinContainer(this, a1);
        this.containers.put(a1, v2);
        if (this.prepared) {
            v2.prepare();
        }
        return v2;
    }
    
    public final void prepare(final List<String> v0) {
        this.prepared = true;
        for (final MixinContainer a1 : this.containers.values()) {
            a1.prepare();
        }
        if (v0 != null) {
            this.parseArgs(v0);
        }
        else {
            final String v = System.getProperty("sun.java.command");
            if (v != null) {
                this.parseArgs(Arrays.asList(v.split(" ")));
            }
        }
    }
    
    private void parseArgs(final List<String> v2) {
        boolean v3 = false;
        for (final String a1 : v2) {
            if (v3) {
                this.addConfig(a1);
            }
            v3 = "--mixin".equals(a1);
        }
    }
    
    public final void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        if (this.primaryContainer != null) {
            this.primaryContainer.initPrimaryContainer();
        }
        this.scanClasspath();
        MixinPlatformManager.logger.debug("inject() running with {} agents", new Object[] { this.containers.size() });
        for (final MixinContainer v0 : this.containers.values()) {
            try {
                v0.inject();
            }
            catch (Exception v2) {
                v2.printStackTrace();
            }
        }
    }
    
    private void scanClasspath() {
        final URL[] classPath;
        final URL[] array = classPath = MixinService.getService().getClassProvider().getClassPath();
        for (final URL v0 : classPath) {
            try {
                final URI v2 = v0.toURI();
                if (!this.containers.containsKey(v2)) {
                    MixinPlatformManager.logger.debug("Scanning {} for mixin tweaker", new Object[] { v2 });
                    if ("file".equals(v2.getScheme()) && new File(v2).exists()) {
                        final MainAttributes v3 = MainAttributes.of(v2);
                        final String v4 = v3.get("TweakClass");
                        if ("org.spongepowered.asm.launch.MixinTweaker".equals(v4)) {
                            MixinPlatformManager.logger.debug("{} contains a mixin tweaker, adding agents", new Object[] { v2 });
                            this.addContainer(v2);
                        }
                    }
                }
            }
            catch (Exception v5) {
                v5.printStackTrace();
            }
        }
    }
    
    public String getLaunchTarget() {
        for (final MixinContainer v0 : this.containers.values()) {
            final String v2 = v0.getLaunchTarget();
            if (v2 != null) {
                return v2;
            }
        }
        return "net.minecraft.client.main.Main";
    }
    
    final void setCompatibilityLevel(final String v0) {
        try {
            final MixinEnvironment.CompatibilityLevel a1 = MixinEnvironment.CompatibilityLevel.valueOf(v0.toUpperCase());
            MixinPlatformManager.logger.debug("Setting mixin compatibility level: {}", new Object[] { a1 });
            MixinEnvironment.setCompatibilityLevel(a1);
        }
        catch (IllegalArgumentException v) {
            MixinPlatformManager.logger.warn("Invalid compatibility level specified: {}", new Object[] { v0 });
        }
    }
    
    final void addConfig(String v-1) {
        if (v-1.endsWith(".json")) {
            MixinPlatformManager.logger.debug("Registering mixin config: {}", new Object[] { v-1 });
            Mixins.addConfiguration(v-1);
        }
        else if (v-1.contains(".json@")) {
            final int a1 = v-1.indexOf(".json@");
            final String v1 = v-1.substring(a1 + 6);
            v-1 = v-1.substring(0, a1 + 5);
            final MixinEnvironment.Phase v2 = MixinEnvironment.Phase.forName(v1);
            if (v2 != null) {
                MixinPlatformManager.logger.warn("Setting config phase via manifest is deprecated: {}. Specify target in config instead", new Object[] { v-1 });
                MixinPlatformManager.logger.debug("Registering mixin config: {}", new Object[] { v-1 });
                MixinEnvironment.getEnvironment(v2).addConfiguration(v-1);
            }
        }
    }
    
    final void addTokenProvider(final String v-1) {
        if (v-1.contains("@")) {
            final String[] a1 = v-1.split("@", 2);
            final MixinEnvironment.Phase v1 = MixinEnvironment.Phase.forName(a1[1]);
            if (v1 != null) {
                MixinPlatformManager.logger.debug("Registering token provider class: {}", new Object[] { a1[0] });
                MixinEnvironment.getEnvironment(v1).registerTokenProviderClass(a1[0]);
            }
            return;
        }
        MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(v-1);
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
