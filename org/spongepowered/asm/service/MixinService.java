package org.spongepowered.asm.service;

import java.util.*;
import org.apache.logging.log4j.*;

public final class MixinService
{
    private static final Logger logger;
    private static MixinService instance;
    private ServiceLoader<IMixinServiceBootstrap> bootstrapServiceLoader;
    private final Set<String> bootedServices;
    private ServiceLoader<IMixinService> serviceLoader;
    private IMixinService service;
    
    private MixinService() {
        super();
        this.bootedServices = new HashSet<String>();
        this.service = null;
        this.runBootServices();
    }
    
    private void runBootServices() {
        this.bootstrapServiceLoader = ServiceLoader.load(IMixinServiceBootstrap.class, this.getClass().getClassLoader());
        for (final IMixinServiceBootstrap v0 : this.bootstrapServiceLoader) {
            try {
                v0.bootstrap();
                this.bootedServices.add(v0.getServiceClassName());
            }
            catch (Throwable v2) {
                MixinService.logger.catching(v2);
            }
        }
    }
    
    private static MixinService getInstance() {
        if (MixinService.instance == null) {
            MixinService.instance = new MixinService();
        }
        return MixinService.instance;
    }
    
    public static void boot() {
        getInstance();
    }
    
    public static IMixinService getService() {
        return getInstance().getServiceInstance();
    }
    
    private synchronized IMixinService getServiceInstance() {
        if (this.service == null) {
            this.service = this.initService();
            if (this.service == null) {
                throw new ServiceNotAvailableError("No mixin host service is available");
            }
        }
        return this.service;
    }
    
    private IMixinService initService() {
        this.serviceLoader = ServiceLoader.load(IMixinService.class, this.getClass().getClassLoader());
        final Iterator<IMixinService> v0 = this.serviceLoader.iterator();
        while (v0.hasNext()) {
            try {
                final IMixinService v2 = v0.next();
                if (this.bootedServices.contains(v2.getClass().getName())) {
                    MixinService.logger.debug("MixinService [{}] was successfully booted in {}", new Object[] { v2.getName(), this.getClass().getClassLoader() });
                }
                if (v2.isValid()) {
                    return v2;
                }
                continue;
            }
            catch (ServiceConfigurationError v3) {
                v3.printStackTrace();
            }
            catch (Throwable v4) {
                v4.printStackTrace();
            }
        }
        return null;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
