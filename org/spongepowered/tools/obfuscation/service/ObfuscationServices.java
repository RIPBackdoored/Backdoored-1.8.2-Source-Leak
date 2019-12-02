package org.spongepowered.tools.obfuscation.service;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.*;
import javax.tools.*;
import java.util.*;

public final class ObfuscationServices
{
    private static ObfuscationServices instance;
    private final ServiceLoader<IObfuscationService> serviceLoader;
    private final Set<IObfuscationService> services;
    
    private ObfuscationServices() {
        super();
        this.services = new HashSet<IObfuscationService>();
        this.serviceLoader = ServiceLoader.load(IObfuscationService.class, this.getClass().getClassLoader());
    }
    
    public static ObfuscationServices getInstance() {
        if (ObfuscationServices.instance == null) {
            ObfuscationServices.instance = new ObfuscationServices();
        }
        return ObfuscationServices.instance;
    }
    
    public void initProviders(final IMixinAnnotationProcessor v-6) {
        try {
            for (final IObfuscationService obfuscationService : this.serviceLoader) {
                if (!this.services.contains(obfuscationService)) {
                    this.services.add(obfuscationService);
                    final String simpleName = obfuscationService.getClass().getSimpleName();
                    final Collection<ObfuscationTypeDescriptor> obfuscationTypes = obfuscationService.getObfuscationTypes();
                    if (obfuscationTypes == null) {
                        continue;
                    }
                    for (final ObfuscationTypeDescriptor v0 : obfuscationTypes) {
                        try {
                            final ObfuscationType a1 = ObfuscationType.create(v0, v-6);
                            v-6.printMessage(Diagnostic.Kind.NOTE, simpleName + " supports type: \"" + a1 + "\"");
                        }
                        catch (Exception v2) {
                            v2.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (ServiceConfigurationError serviceConfigurationError) {
            v-6.printMessage(Diagnostic.Kind.ERROR, serviceConfigurationError.getClass().getSimpleName() + ": " + serviceConfigurationError.getMessage());
            serviceConfigurationError.printStackTrace();
        }
    }
    
    public Set<String> getSupportedOptions() {
        final Set<String> set = new HashSet<String>();
        for (final IObfuscationService v0 : this.serviceLoader) {
            final Set<String> v2 = v0.getSupportedOptions();
            if (v2 != null) {
                set.addAll(v2);
            }
        }
        return set;
    }
    
    public IObfuscationService getService(final Class<? extends IObfuscationService> v2) {
        for (final IObfuscationService a1 : this.serviceLoader) {
            if (v2.getName().equals(a1.getClass().getName())) {
                return a1;
            }
        }
        return null;
    }
}
