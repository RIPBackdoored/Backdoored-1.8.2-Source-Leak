package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.service.*;
import java.util.*;

public class ObfuscationManager implements IObfuscationManager
{
    private final IMixinAnnotationProcessor ap;
    private final List<ObfuscationEnvironment> environments;
    private final IObfuscationDataProvider obfs;
    private final IReferenceManager refs;
    private final List<IMappingConsumer> consumers;
    private boolean initDone;
    
    public ObfuscationManager(final IMixinAnnotationProcessor a1) {
        super();
        this.environments = new ArrayList<ObfuscationEnvironment>();
        this.consumers = new ArrayList<IMappingConsumer>();
        this.ap = a1;
        this.obfs = new ObfuscationDataProvider(a1, this.environments);
        this.refs = new ReferenceManager(a1, this.environments);
    }
    
    @Override
    public void init() {
        if (this.initDone) {
            return;
        }
        this.initDone = true;
        ObfuscationServices.getInstance().initProviders(this.ap);
        for (final ObfuscationType v1 : ObfuscationType.types()) {
            if (v1.isSupported()) {
                this.environments.add(v1.createEnvironment());
            }
        }
    }
    
    @Override
    public IObfuscationDataProvider getDataProvider() {
        return this.obfs;
    }
    
    @Override
    public IReferenceManager getReferenceManager() {
        return this.refs;
    }
    
    @Override
    public IMappingConsumer createMappingConsumer() {
        final Mappings v1 = new Mappings();
        this.consumers.add(v1);
        return v1;
    }
    
    @Override
    public List<ObfuscationEnvironment> getEnvironments() {
        return this.environments;
    }
    
    @Override
    public void writeMappings() {
        for (final ObfuscationEnvironment v1 : this.environments) {
            v1.writeMappings(this.consumers);
        }
    }
    
    @Override
    public void writeReferences() {
        this.refs.write();
    }
}
