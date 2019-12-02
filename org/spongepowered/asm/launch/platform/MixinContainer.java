package org.spongepowered.asm.launch.platform;

import java.net.*;
import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import java.util.*;
import org.spongepowered.asm.launch.*;
import org.spongepowered.asm.service.*;

public class MixinContainer
{
    private static final List<String> agentClasses;
    private final Logger logger;
    private final URI uri;
    private final List<IMixinPlatformAgent> agents;
    
    public MixinContainer(final MixinPlatformManager v-5, final URI v-4) {
        super();
        this.logger = LogManager.getLogger("mixin");
        this.agents = new ArrayList<IMixinPlatformAgent>();
        this.uri = v-4;
        for (final String s : MixinContainer.agentClasses) {
            try {
                final Class<IMixinPlatformAgent> a1 = (Class<IMixinPlatformAgent>)Class.forName(s);
                final Constructor<IMixinPlatformAgent> a2 = a1.getDeclaredConstructor(MixinPlatformManager.class, URI.class);
                this.logger.debug("Instancing new {} for {}", new Object[] { a1.getSimpleName(), this.uri });
                final IMixinPlatformAgent v1 = a2.newInstance(v-5, v-4);
                this.agents.add(v1);
            }
            catch (Exception ex) {
                this.logger.catching((Throwable)ex);
            }
        }
    }
    
    public URI getURI() {
        return this.uri;
    }
    
    public Collection<String> getPhaseProviders() {
        final List<String> list = new ArrayList<String>();
        for (final IMixinPlatformAgent v0 : this.agents) {
            final String v2 = v0.getPhaseProvider();
            if (v2 != null) {
                list.add(v2);
            }
        }
        return list;
    }
    
    public void prepare() {
        for (final IMixinPlatformAgent v1 : this.agents) {
            this.logger.debug("Processing prepare() for {}", new Object[] { v1 });
            v1.prepare();
        }
    }
    
    public void initPrimaryContainer() {
        for (final IMixinPlatformAgent v1 : this.agents) {
            this.logger.debug("Processing launch tasks for {}", new Object[] { v1 });
            v1.initPrimaryContainer();
        }
    }
    
    public void inject() {
        for (final IMixinPlatformAgent v1 : this.agents) {
            this.logger.debug("Processing inject() for {}", new Object[] { v1 });
            v1.inject();
        }
    }
    
    public String getLaunchTarget() {
        for (final IMixinPlatformAgent v0 : this.agents) {
            final String v2 = v0.getLaunchTarget();
            if (v2 != null) {
                return v2;
            }
        }
        return null;
    }
    
    static {
        GlobalProperties.put("mixin.agents", agentClasses = new ArrayList<String>());
        for (final String v1 : MixinService.getService().getPlatformAgents()) {
            MixinContainer.agentClasses.add(v1);
        }
        MixinContainer.agentClasses.add("org.spongepowered.asm.launch.platform.MixinPlatformAgentDefault");
    }
}
